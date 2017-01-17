/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package valentic.registracija.controller;


import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import valentic.registracija.model.Placanje;
import valentic.registracija.model.Vozilo;


/**
 *
 * @author Basic
 */
public class ObradaPlacanja extends ObradaVeze {

   
    private Placanje placanje;
    public static final int UZ_PLACANJE_JE_VEZANA_REGISTRACIJA=1;

    public ObradaPlacanja() {

        
    }

    

    public List<Placanje> getPlacanja(Vozilo vo) {
        List<Placanje> placanja = new ArrayList<>();
        String query = "Select a.*, b.sifra from placanje a inner join vozilo b on b.sifra=a.vozilo where b.sifra = " + vo.getSifra() + ";";

        try {
            izrazP = veza.prepareStatement(query);
            rs = izrazP.executeQuery();
            while (rs.next()) {
                placanje = new Placanje();
                placanje.setSifra(rs.getInt("sifra"));
                placanje.setVozilo(rs.getInt("vozilo"));
                placanje.setCijenaRegistracije(rs.getBigDecimal("cijenaregistracije"));
                placanje.setCijenaTehnickog(rs.getBigDecimal("cijenatehnickog"));
                placanje.setNaknadaZaCeste(rs.getBigDecimal("naknadazaceste"));
                placanje.setNaknadaZaOkolis(rs.getBigDecimal("naknadazaokolis"));
                placanje.setBiljezi(rs.getBigDecimal("biljezi"));
                placanje.setUkupno(rs.getBigDecimal("ukupno"));
                placanje.setGotovinaKartice(rs.getBoolean("gotovinakartice"));
                placanje.setNazivKartice(rs.getString("nazivkartice"));
                placanje.setBrojRata(rs.getInt("brojrata"));
                try {
                    placanje.setDatumPlacanja(new Date(rs.getDate("datumplacanja").getTime()));
                } catch (Exception e) {
                    placanje.setDatumPlacanja(null);
                }
                placanje.setImePlatitelja(rs.getString("imeplatitelja"));
                placanje.setOibPlatitelja(rs.getString("oibplatitelja"));
                placanje.setIdRacuna(rs.getString("idracuna"));
                placanja.add(placanje);
              

            }
            rs.close();
            izrazP.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return placanja;
    }

    public Placanje getPlacanje(Vozilo vo) {
        //String query = "Select a.*, b.sifra,b.stranka from registracija a inner join vozilo b on b.sifra=a.vozilo where b.sifra = " + vo.getSifra() + ";";
        String query = "SELECT * FROM placanje WHERE vozilo = " + vo.getSifra() + " and datumplacanja = (SELECT MAX(datumplacanja)FROM placanje WHERE vozilo = " + vo.getSifra() + ");"; 
        //String query2 = "Select a.*, b.sifra from placanje a inner join vozilo b on a.sifra=b.stranka inner join registracija c on b.sifra=c.vozilo WHERE vozilo = " + vo.getSifra() + " and datumplacanja = (SELECT MAX(datumplacanja)FROM placanje WHERE vozilo = " + vo.getSifra() + ")";
        placanje = null;
        try {

            izrazP = veza.prepareStatement(query);
            rs = izrazP.executeQuery();
            while (rs.next()) {
                placanje = new Placanje();
                placanje.setSifra(rs.getInt("sifra"));
                placanje.setVozilo(rs.getInt("vozilo"));
                placanje.setCijenaRegistracije(rs.getBigDecimal("cijenaregistracije"));
                placanje.setCijenaTehnickog(rs.getBigDecimal("cijenatehnickog"));
                placanje.setNaknadaZaCeste(rs.getBigDecimal("naknadazaceste"));
                placanje.setNaknadaZaOkolis(rs.getBigDecimal("naknadazaokolis"));
                placanje.setBiljezi(rs.getBigDecimal("biljezi"));
                placanje.setUkupno(rs.getBigDecimal("ukupno"));
                placanje.setGotovinaKartice(rs.getBoolean("gotovinakartice"));
                placanje.setNazivKartice(rs.getString("nazivkartice"));
                placanje.setBrojRata(rs.getInt("brojrata"));
                try {
                    placanje.setDatumPlacanja(new Date(rs.getDate("datumplacanja").getTime()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                placanje.setImePlatitelja(rs.getString("imeplatitelja"));
                placanje.setOibPlatitelja(rs.getString("oibplatitelja"));
                placanje.setIdRacuna(rs.getString("idracuna"));

            }
            rs.close();
            izrazP.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return placanje;
    }

    public boolean dodaj(Placanje pe) {
        String query = "INSERT INTO placanje (vozilo,cijenaregistracije,cijenatehnickog,naknadazaceste,naknadazaokolis,biljezi,ukupno,gotovinakartice,nazivkartice,brojrata,datumplacanja,imeplatitelja,oibplatitelja,idracuna) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            izrazP = veza.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            dodajVrijednostiPlcanja(pe);

            izrazP.executeUpdate();
            rs = izrazP.getGeneratedKeys();
            rs.next();
            pe.setSifra(rs.getInt(1));
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean izmjeni(Placanje pe) {
        String query = "update placanje set vozilo=?,cijenaregistracije=?,cijenatehnickog=?,naknadazaceste=?,naknadazaokolis=?,biljezi=?,ukupno=?,gotovinakartice=?,nazivkartice=?,brojrata=?,datumplacanja=?,imeplatitelja=?,oibplatitelja=?,idracuna=? where sifra =" + pe.getSifra() + ";";
        try {
            izrazP = veza.prepareStatement(query);
            dodajVrijednostiPlcanja(pe);
            izrazP.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean obrisi(Placanje pe)throws IznimkaBrisanja {
        boolean postoji = false;
        try {
            izrazP = veza.prepareStatement("Select sifra from registracija where vozilo=?");
            izrazP.setInt(1, pe.getSifra());
            rs = izrazP.executeQuery();
            while(rs.next()){
                postoji=true;
                break;
            }
            rs.close();
            izrazP.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        if(postoji){
           throw new IznimkaBrisanja(ObradaPlacanja.UZ_PLACANJE_JE_VEZANA_REGISTRACIJA);
        }
        
        
        String query = "delete from placanje where sifra =" + pe.getSifra() + ";";
        try {
            izrazP = veza.prepareStatement(query);
            izrazP.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void dodajVrijednostiPlcanja(Placanje pe) throws SQLException {
        izrazP.setInt(1, pe.getVozilo());
            
            izrazP.setBigDecimal(2, pe.getCijenaRegistracije());
            izrazP.setBigDecimal(3, pe.getCijenaTehnickog());
            izrazP.setBigDecimal(4, pe.getNaknadaZaCeste());
            izrazP.setBigDecimal(5, pe.getNaknadaZaOkolis());
            izrazP.setBigDecimal(6, pe.getBiljezi());
            izrazP.setBigDecimal(7, pe.getUkupno());
            izrazP.setBoolean(8, pe.isGotovinaKartice());
            

            if (pe.getNazivKartice() == null) {
                izrazP.setString(9, null);
            } else {
                izrazP.setString(9, pe.getNazivKartice());
            }

            if (pe.getBrojRata() == 0) {
                izrazP.setInt(10, 0);
            } else {
                izrazP.setInt(10, pe.getBrojRata());
            }

            if (pe.getDatumPlacanja() == null) {
                izrazP.setDate(11, null);
            } else {
                izrazP.setDate(11, new java.sql.Date(pe.getDatumPlacanja().getTime()));
            }
            izrazP.setString(12, pe.getImePlatitelja());
            izrazP.setString(13, pe.getOibPlatitelja());
            izrazP.setString(14, pe.getIdRacuna());
    }
}
