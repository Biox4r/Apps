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
import java.util.Date;
import valentic.registracija.model.Tehnicki;
import valentic.registracija.model.Vozilo;
import valentic.registracija.utility.RadSBazom;

/**
 *
 * @author Basic
 */
public class ObradaTehnicki {

    private Connection veza;
    private ResultSet rs;
    private PreparedStatement izrazP;
    private Statement izraz;
    private Tehnicki tehnicki;

    public ObradaTehnicki() {

        konekcija();

    }

    private void konekcija() {

     veza = RadSBazom.dohvatiInstancu().getVeza();
    }

    public Tehnicki getTehnicki(Vozilo vo) {

        String query = "SELECT * FROM tehnicki WHERE vozilo = " + vo.getSifra() + " and datumtehnickog = (SELECT MAX(datumtehnickog)FROM tehnicki WHERE vozilo = " + vo.getSifra() + ");";
        
        tehnicki = null;
        try {

            izrazP = veza.prepareStatement(query);
            rs = izrazP.executeQuery();
            while (rs.next()) {
                tehnicki = new Tehnicki();

                tehnicki.setVozilo(rs.getInt("vozilo"));

                try {
                    tehnicki.setDatumTechnickog(new Date(rs.getDate("datumtehnickog").getTime()));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                tehnicki.setCijenaTehnickog(rs.getBigDecimal("cijenatehnickog"));
                tehnicki.setIspravnoVozilo(rs.getBoolean("ispravnovozilo"));
                tehnicki.setNazivStanice(rs.getString("nazivstanice"));
                tehnicki.setImeOvlasteneOsobe(rs.getString("imeovlasteneosobe"));
                tehnicki.setIdTehnickog(rs.getString("idtehnickog"));
                tehnicki.setSifra(rs.getInt("sifra"));

            }
            rs.close();
            izrazP.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return tehnicki;
    }

    public boolean dodaj(Tehnicki te) {
        String query = "INSERT INTO tehnicki (vozilo,datumtehnickog,cijenatehnickog,ispravnovozilo,nazivstanice,imeovlasteneosobe,idtehnickog) values(?,?,?,?,?,?,?)";
        try {
            izrazP = veza.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            dodajVrijednostiTehnickog(te);
            

            izrazP.executeUpdate();
            rs = izrazP.getGeneratedKeys();
            rs.next();
            te.setSifra(rs.getInt(1));
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean izmjeni(Tehnicki te) {
        String query = "update tehnicki set vozilo=?,datumtehnickog=?,cijenatehnickog=?,ispravnovozilo=?,nazivstanice=?,imeovlasteneosobe=?,idtehnickog=? where vozilo=" + te.getVozilo() + ";";
        try {
            izrazP = veza.prepareStatement(query);

            dodajVrijednostiTehnickog(te);
            

            izrazP.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean obrisi(Tehnicki te) {
        String query = "delete from tehnicki  where vozilo=" + te.getVozilo() + ";";
        try {
            izrazP = veza.prepareStatement(query);
            izrazP.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private void dodajVrijednostiTehnickog(Tehnicki te) throws SQLException {
        izrazP.setInt(1, te.getVozilo());
            if (te.getDatumTechnickog() == null) {
                izrazP.setDate(2, null);
            } else {
                izrazP.setDate(2, new java.sql.Date(te.getDatumTechnickog().getTime()));
            }

            izrazP.setBigDecimal(3, te.getCijenaTehnickog());
            izrazP.setBoolean(4, te.isIspravnoVozilo());
            izrazP.setString(5, te.getNazivStanice());
            izrazP.setString(6, te.getImeOvlasteneOsobe());
            izrazP.setString(7, te.getIdTehnickog());
    }

}
