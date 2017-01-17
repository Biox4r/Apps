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
import javax.swing.DefaultComboBoxModel;
import valentic.registracija.model.Stranka;
import valentic.registracija.model.Vozilo;
import valentic.registracija.utility.RadSBazom;

/**
 *
 * @author Basic
 */
public class ObradaStrankeIVozila {

    private Connection veza;
    //private ResultSet rs;
    //private PreparedStatement izrazP;
    //private Statement izraz;
    private int sifra;
    public static final int VOZILO_IMA_EVIDIDENTIRANE_UPLATE = 1;
    public static final int VOZILO_IMA_EVIDENTIRANE_REGISTRACIJE = 2;
    public static final int STRANKA_IMA_EVIDENTIRANA_VOZILA = 3;
    public static final int VOZILO_IMA_EVIDENTIRAN_TEHNICKI_PREGLED = 4;

    public ObradaStrankeIVozila() {

        konekcija();
    }

    private void konekcija() {
        veza = RadSBazom.dohvatiInstancu().getVeza();
    }

    public DefaultComboBoxModel getLista(String pretraga) {

        DefaultComboBoxModel model = new DefaultComboBoxModel();
        try {

            String query = "SELECT ime,prezime FROM stranka WHERE ime LIKE '" + pretraga + "%' OR prezime like '" + pretraga + "%';";
            Statement izraz = veza.createStatement();
            ResultSet rs = izraz.executeQuery(query);
            while (rs.next()) {
                model.addElement(rs.getString("ime") + " " + rs.getString("prezime"));
                // model.addElement();
            }
            rs.close();
            izraz.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return model;
    }

   

    public Stranka getStrankaExact(String pretraga) {
        Stranka st = new Stranka();
        // SELECT * FROM stranka WHERE ime LIKE '" + "%'" + "%' AND prezime like '" + "%'" + "%';";
        if (pretraga.indexOf(" ") != -1) {
            String query = "SELECT * FROM stranka WHERE ime LIKE '" + pretraga.substring(0, pretraga.indexOf(" ")) + "%' AND prezime like '" + pretraga.substring(pretraga.indexOf(" ") + 1, pretraga.length()) + "%';";
            try {
                PreparedStatement izrazP = veza.prepareStatement(query);
                ResultSet rs = izrazP.executeQuery();
                while (rs.next()) {

                    st.setIme(rs.getString("ime"));
                    st.setPrezime(rs.getString("prezime"));
                    st.setOib(rs.getString("oib"));
                    st.setMobitel(rs.getString("mobitel"));
                    st.setAdresa(rs.getString("adresa"));
                    try {
                        st.setDatumRodjenja(new Date(rs.getDate("datumrodjenja").getTime()));
                    } catch (Exception e) {
                        st.setDatumRodjenja(null);
                    }
                    st.setAdresa2(rs.getString("adresa2"));
                    st.setDrugiMobitel(rs.getString("drugimobitel"));
                    st.setDrzava(rs.getString("drzava"));
                    st.setEmail(rs.getString("email"));
                    st.setFiksniTelefon(rs.getString("fiksniTelefon"));
                    st.setGrad(rs.getString("grad"));
                    st.setZip(rs.getInt("zip"));
                    st.setRegija(rs.getString("regija"));
                    st.setSifra(rs.getInt("sifra"));
                    st.setVozila(getVozila(st));
                }
                rs.close();
                izrazP.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return st;
    }

    public boolean dodaj(Stranka st) {
        String query = "INSERT INTO stranka (oib,ime,prezime,mobitel,adresa) values(?,?,?,?,?)";
        try {
            PreparedStatement izrazP = veza.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs;
            dodajVrijednostiStranke(st, izrazP);

            izrazP.executeUpdate();
            rs = izrazP.getGeneratedKeys();
            rs.next();
            st.setSifra(rs.getInt(1));
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean dodajOstaleStranke(Stranka st) {
        String query = "update stranka set datumrodjenja=?,adresa2=?,drugimobitel=?,fiksnitelefon=?,email=?,drzava=?,grad=?,zip=?,regija=? where sifra=" + st.getSifra() + ";";
        try {
            PreparedStatement izrazP = veza.prepareStatement(query);
            dodajOstaleVrijednostiStranke(st, izrazP);
            izrazP.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean izmjeniOstaleStranke(Stranka st) {
        String query = "update stranka set datumrodjenja=?,adresa2=?,drugimobitel=?,fiksnitelefon=?,email=?,drzava=?,grad=?,zip=?,regija=? where sifra=" + st.getSifra() + ";";
        try {
            PreparedStatement izrazP = veza.prepareStatement(query);
            dodajOstaleVrijednostiStranke(st, izrazP);
            izrazP.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean obrisiOstaleStranke(Stranka st) {
        String query = "update stranka set datumrodjenja=null,adresa2=null,drugimobitel=null,fiksnitelefon=null,email=null,drzava=null,grad=null,zip=null,regija=null where sifra=" + st.getSifra() + ";";
        try {
            PreparedStatement izrazP = veza.prepareStatement(query);

            izrazP.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean izmjeni(Stranka st) {
        String query = "update stranka set oib=?,ime=?,prezime=?,mobitel=?,adresa=? where sifra=" + st.getSifra() + ";";
        try {
            PreparedStatement izrazP = veza.prepareStatement(query);
            dodajVrijednostiStranke(st, izrazP);
            izrazP.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean obrisi(Stranka st) throws IznimkaBrisanja {

        boolean postoji = false;
        try {
            PreparedStatement izrazP = veza.prepareStatement("select sifra from vozilo where stranka=?");
            izrazP.setInt(1, st.getSifra());
            ResultSet rs = izrazP.executeQuery();
            while (rs.next()) {
                postoji = true;
                break;
            }
            rs.close();
            izrazP.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (postoji) {
            throw new IznimkaBrisanja(ObradaStrankeIVozila.STRANKA_IMA_EVIDENTIRANA_VOZILA);
        }

        String query = "delete from stranka where sifra=" + st.getSifra() + ";";
        try {
            PreparedStatement izrazP = veza.prepareStatement(query);
            izrazP.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public List<Vozilo> getVozila(Stranka st) {
        List<Vozilo> vozila = new ArrayList<>();

        String query = "SELECT * FROM vozilo WHERE stranka = " + st.getSifra() + ";";
        //String query = "SELECT * FROM vozilo WHERE stranka = 1";
        try {
            Statement izraz = veza.createStatement();
            ResultSet rs = izraz.executeQuery(query);
            while (rs.next()) {
                Vozilo vozilo = new Vozilo();
                vozilo.setSifra(rs.getInt("sifra"));
                vozilo.setStranka(rs.getInt("stranka"));
                vozilo.setNazivVozila(rs.getString("nazivvozila"));
                vozilo.setTablice(rs.getString("tablice"));
                vozilo.setBoja(rs.getString("boja"));
                vozilo.setGodiste(rs.getInt("godiste"));
                vozilo.setModelVozila(rs.getString("modelvozila"));
                vozilo.setKategorijaVozila(rs.getString("kategorijavozila"));
                vozilo.setProizvodjacVozila(rs.getString("proizvodjacvozila"));
                vozilo.setBrojSasije(rs.getString("brojsasije"));
                vozilo.setSnagaMotora(rs.getInt("snagamotora"));
                vozilo.setTezinaVozila(rs.getDouble("tezinavozila"));
                vozilo.setBrojVrata(rs.getInt("brojvrata"));
                vozilo.setOstalo(rs.getString("ostalo"));
                vozila.add(vozilo);
            }
            rs.close();
            izraz.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vozila;
    }

    public boolean dodaj(Vozilo vo) {
        String query = "Insert into vozilo (stranka,modelvozila,kategorijavozila,proizvodjacvozila,nazivvozila,tablice,godiste,boja) values (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement izrazP = veza.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs;
            dodajVrijednotiVozila(vo, izrazP);
            izrazP.executeUpdate();
            rs = izrazP.getGeneratedKeys();
            rs.next();
            vo.setSifra(rs.getInt(1));
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;

    }

    public boolean izmjeni(Vozilo vo) {
        String query = "update vozilo set stranka=?,modelvozila=?,kategorijavozila=?,proizvodjacvozila=?,nazivvozila=?,tablice=?,godiste=?,boja=? where sifra =" + vo.getSifra() + ";";
        try {
            PreparedStatement izrazP = veza.prepareStatement(query);
            dodajVrijednotiVozila(vo, izrazP);

            izrazP.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;

    }

    public boolean obrisi(Vozilo vo) throws IznimkaBrisanja {

        boolean postoji = false;
        boolean postoji1 = false;
        try {
            PreparedStatement izrazP = veza.prepareStatement("Select sifra from placanje where vozilo=?");
            izrazP.setInt(1, vo.getSifra());
            ResultSet rs = izrazP.executeQuery();

            while (rs.next()) {
                postoji = true;
                break;
            }
            rs.close();
            izrazP.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            PreparedStatement izrazP = veza.prepareStatement("Select sifra from tehnicki where vozilo=?");
            izrazP.setInt(1, vo.getSifra());
            ResultSet rs = izrazP.executeQuery();
            while (rs.next()) {
                postoji1 = true;
                break;
            }
            rs.close();
            izrazP.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (postoji) {
            throw new IznimkaBrisanja(ObradaStrankeIVozila.VOZILO_IMA_EVIDIDENTIRANE_UPLATE);
        }

        if (postoji1) {
            throw new IznimkaBrisanja(ObradaStrankeIVozila.VOZILO_IMA_EVIDENTIRAN_TEHNICKI_PREGLED);
        }

        String query = "delete from vozilo where sifra =" + vo.getSifra() + ";";
        try {
            PreparedStatement izrazP = veza.prepareStatement(query);
            izrazP.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;

    }

    public boolean dodajOstaleVozila(Vozilo vo) {
        String query = "update vozilo set brojsasije=?,snagamotora=?,tezinavozila=?,brojvrata=?,ostalo=? where sifra =" + vo.getSifra() + ";";

        try {
            PreparedStatement izrazP = veza.prepareStatement(query);
            dodajOstaleVrijednostiVozila(vo, izrazP);
            izrazP.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;

    }

    public boolean izmjeniOstaleVozila(Vozilo vo) {
        String query = "update vozilo set brojsasije=?,snagamotora=?,tezinavozila=?,brojvrata=?,ostalo=? where sifra =" + vo.getSifra() + ";";
        try {
            PreparedStatement izrazP = veza.prepareStatement(query);
            dodajOstaleVrijednostiVozila(vo, izrazP);
            izrazP.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;

    }

    public boolean obrisiOstaleVozila(Vozilo vo) {
        String query = "update vozilo set brojsasije=null,snagamotora=null,tezinavozila=null,brojvrata=null,ostalo=null where sifra =" + vo.getSifra() + ";";
        try {
            PreparedStatement izrazP = veza.prepareStatement(query);
            izrazP.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;

    }

    private void dodajVrijednostiStranke(Stranka st, PreparedStatement izrazP) throws SQLException {

        izrazP.setString(1, st.getOib());
        izrazP.setString(2, st.getIme());
        izrazP.setString(3, st.getPrezime());
        izrazP.setString(4, st.getMobitel());
        izrazP.setString(5, st.getAdresa());
    }

    private void dodajOstaleVrijednostiStranke(Stranka st, PreparedStatement izrazP) throws SQLException {
        if (st.getDatumRodjenja() == null) {
            izrazP.setDate(1, null);
        } else {
            izrazP.setDate(1, new java.sql.Date(st.getDatumRodjenja().getTime()));
        }
        izrazP.setString(2, st.getAdresa2());
        izrazP.setString(3, st.getDrugiMobitel());
        izrazP.setString(4, st.getFiksniTelefon());
        izrazP.setString(5, st.getEmail());
        izrazP.setString(6, st.getDrzava());
        izrazP.setString(7, st.getGrad());
        izrazP.setInt(8, st.getZip());
        izrazP.setString(9, st.getRegija());
    }

    private void dodajVrijednotiVozila(Vozilo vo, PreparedStatement izrazP) throws SQLException {
        izrazP.setInt(1, vo.getStranka());
        izrazP.setString(2, vo.getModelVozila());
        izrazP.setString(3, vo.getKategorijaVozila());
        izrazP.setString(4, vo.getProizvodjacVozila());
        izrazP.setString(5, vo.getNazivVozila());
        izrazP.setString(6, vo.getTablice());
        izrazP.setInt(7, vo.getGodiste());
        izrazP.setString(8, vo.getBoja());
    }

    private void dodajOstaleVrijednostiVozila(Vozilo vo, PreparedStatement izrazP) throws SQLException {
        izrazP.setString(1, vo.getBrojSasije());
        izrazP.setInt(2, vo.getSnagaMotora());
        izrazP.setDouble(3, vo.getTezinaVozila());
        izrazP.setInt(4, vo.getBrojVrata());
        izrazP.setString(5, vo.getOstalo());
    }

}
