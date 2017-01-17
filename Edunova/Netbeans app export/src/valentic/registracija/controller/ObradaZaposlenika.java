/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package valentic.registracija.controller;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.DefaultComboBoxModel;
import valentic.registracija.model.Zaposlenik;
import valentic.registracija.utility.RadSBazom;

/**
 *
 * @author Basic
 */
public class ObradaZaposlenika {

    private Connection veza;
    private ResultSet rs;
    private PreparedStatement izrazP;

    public ObradaZaposlenika() {
        veza = RadSBazom.dohvatiInstancu().getVeza();
    }

    public DefaultComboBoxModel getPopisZaposlenika(String pretraga) {

        DefaultComboBoxModel model = new DefaultComboBoxModel();
        try {

            String query = "SELECT ime,prezime FROM zaposlenik WHERE ime LIKE '" + pretraga + "%' OR prezime like '" + pretraga + "%';";
            Statement izraz = veza.createStatement();
            rs = izraz.executeQuery(query);
            while (rs.next()) {
                model.addElement(rs.getString("ime") + " " + rs.getString("prezime"));
                // model.addElement();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return model;
    }

    public Zaposlenik getZaposleniciExact(String pretraga) {

        Zaposlenik z = new Zaposlenik();
        if (pretraga.indexOf(" ") != -1) {
            String query = "SELECT * FROM zaposlenik WHERE ime LIKE '" + pretraga.substring(0, pretraga.indexOf(" ")) + "%' AND prezime like '" + pretraga.substring(pretraga.indexOf(" ") + 1, pretraga.length()) + "%';";

        try {
            izrazP = veza.prepareStatement(query);
            rs = izrazP.executeQuery();
            while (rs.next()) {
                z = new Zaposlenik();
                z.setSifra(rs.getInt("sifra"));

                z.setIme(rs.getString("ime"));
                z.setPrezime(rs.getString("prezime"));
                z.setKorisnickoIme(rs.getString("korisnickoime"));
                z.setLozinka(rs.getString("lozinka"));

            }
            rs.close();
            izrazP.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        }
        return z;
    }

    public boolean provjeraKorisnickoIme(String korisnickoIme) {
        boolean postoji = false;

        try {
            izrazP = veza.prepareStatement("select sifra from zaposlenik where korisnickoime=?");
            izrazP.setString(1, korisnickoIme);
            rs = izrazP.executeQuery();
            while (rs.next()) {
                postoji = true;
            }
            rs.close();
            izrazP.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return postoji;
    }

    public Zaposlenik getZaposlenici(String korisnickoIme, String lozinka) {
        Zaposlenik z = null;
        try {
            izrazP = veza.prepareStatement("select * from zaposlenik where korisnickoIme=? and lozinka=?");
            izrazP.setString(1, korisnickoIme);
            izrazP.setString(2, lozinka);
            rs = izrazP.executeQuery();
            while (rs.next()) {
                z = new Zaposlenik();
                z.setSifra(rs.getInt("sifra"));
                z.setKorisnickoIme(rs.getString("korisnickoIme"));
                z.setIme(rs.getString("ime"));
                z.setPrezime(rs.getString("prezime"));
                z.setLozinka(rs.getString("lozinka"));
            }
            rs.close();
            izrazP.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return z;
    }

    public boolean dodaj(Zaposlenik z) {
        try {
            izrazP = veza.prepareStatement(
                    "insert into zaposlenik (korisnickoIme,lozinka,ime,prezime) values (?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            dodajVrijednostiZaposlenika(z);
            izrazP.executeUpdate();
            rs = izrazP.getGeneratedKeys();
            rs.next();
            z.setSifra(rs.getInt(1));
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean promjeni(Zaposlenik z) {
        try {
            izrazP = veza.prepareStatement(
                    "update zaposlenik set korisnickoIme=?,lozinka=?,ime=?,prezime=? where sifra=?");
            dodajVrijednostiZaposlenika(z);
            izrazP.setInt(5, z.getSifra());
            izrazP.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean obrisi(Zaposlenik z) {
        try {
            izrazP = veza.prepareStatement(
                    "delete from zaposlenik where sifra=?");

            izrazP.setInt(1, z.getSifra());
            izrazP.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private void dodajVrijednostiZaposlenika(Zaposlenik z) throws SQLException {
        // lozinka mora biti u md5 hash

        izrazP.setString(1, z.getKorisnickoIme());
        izrazP.setString(2, z.getLozinka());
        izrazP.setString(3, z.getIme());
        izrazP.setString(4, z.getPrezime());
    }

}
