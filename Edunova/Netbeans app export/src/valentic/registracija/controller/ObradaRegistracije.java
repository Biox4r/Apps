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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import valentic.registracija.model.Placanje;
import valentic.registracija.model.Registracija;

import valentic.registracija.model.Vozilo;
import valentic.registracija.utility.RadSBazom;

/**
 *
 * @author Basic
 */
public class ObradaRegistracije {

    private Connection veza;
    private ResultSet rs;
    private PreparedStatement izrazP;
    private Registracija registracija;

    public ObradaRegistracije() {

        konekcija();
    }

    private void konekcija() {

        veza = RadSBazom.dohvatiInstancu().getVeza();
    }

    public Registracija getRegistracija(Placanje pe) {
        String query = "SELECT * FROM registracija WHERE placanje = " + pe.getSifra() + " and datumregistracije = (SELECT MAX(datumregistracije)FROM registracija WHERE placanje = " + pe.getSifra() + ");";
        //and datumregistracije = (SELECT MAX(datumregistracije)FROM registracija) ";
        registracija = null;
        try {

            izrazP = veza.prepareStatement(query);
            rs = izrazP.executeQuery();
            while (rs.next()) {
                registracija = new Registracija();
                registracija.setSifra(rs.getInt("sifra"));
                registracija.setVozilo(rs.getInt("vozilo"));
                registracija.setPlacanje(rs.getInt("placanje"));
                registracija.setNazivServisa(rs.getString("nazivservisa"));
                try {
                    registracija.setDatumRegistracije(new Date(rs.getDate("datumregistracije").getTime()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                registracija.setIdZahtjeva(rs.getString("idzahtjeva"));
                registracija.setImePodnositeljaZahtjeva(rs.getString("imepodnositelja"));
                registracija.setOibPodnositeljaZahtjeva(rs.getString("oibpodnositelja"));
                registracija.setNazivOsiguravajuceKuce(rs.getString("nazivosiguravajucekuce"));

            }
            rs.close();
            izrazP.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return registracija;

    }

    public List<Registracija> getRegistracije(Vozilo vo) {
        List<Registracija> registracije = new ArrayList<>();
        //String query2 = "SELECT * from registracija where vozilo = " + vo.getSifra() + "; ";
        String query = "Select a.*, b.sifra,b.stranka from registracija a inner join vozilo b on b.sifra=a.vozilo where b.sifra = " + vo.getSifra() + ";";
        try {
            izrazP = veza.prepareCall(query);
            rs = izrazP.executeQuery();
            while (rs.next()) {
                Registracija registracija = new Registracija();
                registracija.setSifra(rs.getInt("sifra"));
                registracija.setVozilo(rs.getInt("vozilo"));
                registracija.setPlacanje(rs.getInt("placanje"));
                registracija.setNazivServisa(rs.getString("nazivservisa"));
                try {
                    registracija.setDatumRegistracije(new Date(rs.getDate("datumregistracije").getTime()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                registracija.setIdZahtjeva(rs.getString("idzahtjeva"));
                registracija.setImePodnositeljaZahtjeva(rs.getString("imepodnositelja"));
                registracija.setOibPodnositeljaZahtjeva(rs.getString("oibpodnositelja"));
                registracija.setNazivOsiguravajuceKuce(rs.getString("nazivosiguravajucekuce"));
                registracije.add(registracija);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return registracije;
    }

    public boolean dodaj(Registracija re) {
        String query = "INSERT INTO registracija (vozilo,placanje,datumregistracije,imepodnositelja,oibpodnositelja,nazivosiguravajucekuce,idzahtjeva,nazivservisa) values(?,?,?,?,?,?,?,?)";
        try {
            izrazP = veza.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            dodajVrijednostiRegistracije(re);

            izrazP.executeUpdate();
            rs = izrazP.getGeneratedKeys();
            rs.next();
            re.setSifra(rs.getInt(1));
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean izmjeni(Registracija re) {
        String query = "UPDATE registracija set vozilo=?,placanje=?,datumregistracije=?,imepodnositelja=?,oibpodnositelja=?,nazivosiguravajucekuce=?,idzahtjeva=?,nazivservisa=? where sifra =" + re.getSifra() + ";";
        try {
            izrazP = veza.prepareStatement(query);
            dodajVrijednostiRegistracije(re);

            izrazP.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean obrisi(Registracija re) {
        String query = "delete from registracija where sifra= " + re.getSifra()+ ";";
        try {
            izrazP = veza.prepareStatement(query);
            izrazP.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private void dodajVrijednostiRegistracije(Registracija re) throws SQLException {
        izrazP.setInt(1, re.getVozilo());
            
            izrazP.setInt(2, re.getPlacanje());
            if (re.getDatumRegistracije() == null) {
                izrazP.setDate(3, null);
            } else {
                izrazP.setDate(3, new java.sql.Date(re.getDatumRegistracije().getTime()));
            }
            izrazP.setString(4, re.getImePodnositeljaZahtjeva());
            izrazP.setString(5, re.getOibPodnositeljaZahtjeva());
            izrazP.setString(6, re.getNazivOsiguravajuceKuce());
            izrazP.setString(7, re.getIdZahtjeva());
            izrazP.setString(8, re.getNazivServisa());
    }

}
