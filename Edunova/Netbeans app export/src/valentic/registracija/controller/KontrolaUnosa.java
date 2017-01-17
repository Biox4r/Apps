/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package valentic.registracija.controller;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


/**
 *
 * @author Basic
 */
public class KontrolaUnosa extends ObradaVeze {

    private Component rootPane;
    private int counter = 1;

    public KontrolaUnosa() {

    }

    //unos za polja koja zahtjevaju unos imena, prezimena, adrese, boje automobila i td. Iskljucivo za tekstualni dio.
    public boolean provjeraUnosaZaMobitel(JTextField mobitel) {
        String tempmob = mobitel.getText();
        Pattern pattern = Pattern.compile("\\+\\d{3}/\\d{2}-\\d{3}-\\d{3,4}");
        Matcher matcher = pattern.matcher(tempmob);

        if (!matcher.matches()) {
            JOptionPane.showMessageDialog(
                    rootPane,
                    "Unesite mobitel u formatu kao +385/xx-xxx-xxx(x)!",
                    "Pogreška",
                    JOptionPane.ERROR_MESSAGE);

            mobitel.requestFocus();
            return false;

        }
        return true;
    }

    public boolean provjeraUnosFiksnog(JTextField fiksni) {
        String tempmob = fiksni.getText();
        Pattern pattern = Pattern.compile("\\+\\d{3}/\\d{2}-\\d{3}-\\d{3,4}");
        Matcher matcher = pattern.matcher(tempmob);

        if (!matcher.matches()) {
            JOptionPane.showMessageDialog(
                    rootPane,
                    "Unesite fiskni telefon u formatu kao +385/xx-xxx-xxx(x)!",
                    "Pogreška",
                    JOptionPane.ERROR_MESSAGE);

            fiksni.requestFocus();
            return false;

        }
        return true;
    }

    public boolean provjeraUnosaOiba(JTextField oib) {
        String tempoib = oib.getText();
        
        Pattern pattern = Pattern.compile("\\d{11}");
        Matcher matcher = pattern.matcher(tempoib);

        if (!matcher.matches()) {
            JOptionPane.showMessageDialog(
                    rootPane,
                    "Oib se sastoji od 11 brojeva!",
                    "Pogreška",
                    JOptionPane.ERROR_MESSAGE);

            oib.requestFocus();
            return false;
        }
        
        long oiblong = Long.parseLong(tempoib);
        long tempkontrolni = oiblong % 10;
        int a = 10;
        for (int i = 0; i < 10; i++) {
            a = a + Integer.parseInt(tempoib.substring(i, i + 1));
            a = a % 10;
            if (a == 0) {
                a = 10;
            }
            a *= 2;
            a = a % 11;
        }
        int kontrolnioib = 11 - a;
        if (kontrolnioib == 10) {
            kontrolnioib = 0;
        }

        
        //kontrolnioib = Integer.parseInt(tempoib.substring(10, 11));
        if (tempkontrolni != kontrolnioib) {

            JOptionPane.showMessageDialog(
                    rootPane,
                    "Oib nije ispravan, provjerite unos ponovno!",
                    "Pogreška",
                    JOptionPane.ERROR_MESSAGE);
            oib.requestFocus();
            return false;
        }

        return true;
    }

    public boolean provjeraIstogOibaDodavanje(JTextField oib, String query) {
        boolean postoji = false;

        String tempoib = oib.getText();

        try {

            izrazP = veza.prepareStatement(query);
            izrazP.setString(1, tempoib);
            rs = izrazP.executeQuery();
            while (rs.next()) {
                postoji = true;
                JOptionPane.showMessageDialog(
                        rootPane,
                        "U bazi već postoji osoba sa istim oib-om!",
                        "Pogreška",
                        JOptionPane.ERROR_MESSAGE);

                oib.requestFocus();
            }
            rs.close();
            izrazP.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return postoji;
    }

    public boolean provjeraIstogOibaIzmjena(JTextField oib, String query, int sifra) {
        boolean postoji = false;

        String tempoib = oib.getText();
        try {
            izrazP = veza.prepareStatement(query);

            izrazP.setString(1, tempoib);
            izrazP.setInt(2, sifra);
            rs = izrazP.executeQuery();
            while (rs.next()) {

                postoji = true;
                JOptionPane.showMessageDialog(
                        rootPane,
                        "U bazi već postoji osoba sa istim oib-om!",
                        "Pogreška",
                        JOptionPane.ERROR_MESSAGE);

                oib.requestFocus();

            }
            rs.close();
            izrazP.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return postoji;
    }

    public boolean provjeraUnosaVerifikacijskoBrojaKartice(JTextField ver) {
        String tempver = ver.getText();
        Pattern pattern = Pattern.compile("\\d{4}");
        Matcher matcher = pattern.matcher(tempver);

        if (!matcher.matches()) {
            JOptionPane.showMessageDialog(
                    rootPane,
                    "Verifikacijski broj se sastoji od 4 broja, pokušajte ponovno!",
                    "Pogreška",
                    JOptionPane.ERROR_MESSAGE);

            ver.requestFocus();
            return false;

        }
        return true;
    }

    public boolean provjeraUnosaZipa(JTextField zip) {
        String tempzip = zip.getText();
        Pattern pattern = Pattern.compile("\\d{5}");
        Matcher matcher = pattern.matcher(tempzip);

        if (!matcher.matches()) {
            JOptionPane.showMessageDialog(
                    rootPane,
                    "Poštanski broj tj zip kod unesite sa 5 znamenki!",
                    "Pogreška",
                    JOptionPane.ERROR_MESSAGE);

            zip.requestFocus();
            return false;

        }
        return true;
    }

    public boolean provjeraUnosaKonjskihsnaga(JTextField ks) {
        String tempks = ks.getText();
        Pattern pattern = Pattern.compile("\\d{2,4}");
        Matcher matcher = pattern.matcher(tempks);

        if (!matcher.matches()) {
            JOptionPane.showMessageDialog(
                    rootPane,
                    "Unsite broj konjskih snaga za vozilo!",
                    "Pogreška",
                    JOptionPane.ERROR_MESSAGE);

            ks.requestFocus();
            return false;

        }
        return true;
    }

    public boolean provjeraUnosaVrata(JTextField broj) {
        String tempbroj = broj.getText();
        Pattern pattern = Pattern.compile("\\d{1,2}");
        Matcher matcher = pattern.matcher(tempbroj);

        if (!matcher.matches()) {
            JOptionPane.showMessageDialog(
                    rootPane,
                    "Unsite broj vrata na vozilu!",
                    "Pogreška",
                    JOptionPane.ERROR_MESSAGE);

            broj.requestFocus();
            return false;

        }
        return true;
    }

    public boolean provjeraUnosaRata(JTextField rate) {
        String temprate = rate.getText();
        Pattern pattern = Pattern.compile("\\d{1,2}");
        Matcher matcher = pattern.matcher(temprate);

        if (!matcher.matches()) {
            JOptionPane.showMessageDialog(
                    rootPane,
                    "Unsite broj rata za plaćanje!",
                    "Pogreška",
                    JOptionPane.ERROR_MESSAGE);

            rate.requestFocus();
            return false;

        }
        return true;
    }

    public boolean provjeraUnosaGodista(JTextField broj) {
        String tempbroj = broj.getText();
        Pattern pattern = Pattern.compile("\\d{4}");
        Matcher matcher = pattern.matcher(tempbroj);

        if (!matcher.matches()) {
            JOptionPane.showMessageDialog(
                    rootPane,
                    "Unsite godinu proizvodnje vozila!",
                    "Pogreška",
                    JOptionPane.ERROR_MESSAGE);

            broj.requestFocus();
            return false;

        }
        return true;
    }

    public boolean provjeraUnosaID(JTextField id) {
        String tempid = id.getText();
        Pattern pattern = Pattern.compile("^[_A-Za-zšđčćžŠĐČĆŽ0-9-]{1,50}$");
        Matcher matcher = pattern.matcher(tempid);

        if (!matcher.matches()) {
            JOptionPane.showMessageDialog(
                    rootPane,
                    "Neispravan traženi ID,ili broj šasije.Unesite stavku pomoću brojki i slova(Dužina max 50 znakova)!",
                    "Pogreška",
                    JOptionPane.ERROR_MESSAGE);

            id.requestFocus();
            return false;

        }
        return true;
    }

    public boolean provjeraUnosaAdrese(JTextField adresa) {
        String tempadresa = adresa.getText();
        Pattern pattern = Pattern.compile("^[_A-Za-zšđčćžŠĐČĆŽ0-9\\.{3}\\s?{2}]{1,50}$");
        Matcher matcher = pattern.matcher(tempadresa);

        if (!matcher.matches()) {
            JOptionPane.showMessageDialog(
                    rootPane,
                    "Unsite ispravno Adresu koristite brojke i slova(Dužina max 50 znakova)!",
                    "Pogreška",
                    JOptionPane.ERROR_MESSAGE);

            adresa.requestFocus();
            return false;

        }
        return true;
    }

    public boolean provjeraUnosaModelaVozila(JTextField model) {
        String tempmodel = model.getText();
        Pattern pattern = Pattern.compile("^[_A-Za-zšđčćžŠĐČĆŽ0-9\\s?{2}]{1,50}$");
        Matcher matcher = pattern.matcher(tempmodel);

        if (!matcher.matches()) {
            JOptionPane.showMessageDialog(
                    rootPane,
                    "Unsite ispravno Model vozila koristite brojke i slova(Dužina max 50 znakova)!",
                    "Pogreška",
                    JOptionPane.ERROR_MESSAGE);

            model.requestFocus();
            return false;

        }
        return true;
    }

    public boolean provjeraUnosaImenaPrezimena(JTextField imeprezime) {
        String tempimeprezime = imeprezime.getText();
        Pattern pattern = Pattern.compile("^[_A-Za-zšđčćžŠĐČĆŽ\\.{3}\\s?{2}]{1,50}$");
        Matcher matcher = pattern.matcher(tempimeprezime);

        if (!matcher.matches()) {
            JOptionPane.showMessageDialog(
                    rootPane,
                    "Unsite ispravno ime iil prezime,koristite brojeve i slova(Dužina max 50 znakova)!",
                    "Pogreška",
                    JOptionPane.ERROR_MESSAGE);

            imeprezime.requestFocus();
            return false;
        }
        return true;
    }

    public boolean provjeraUnosaKorisnickogImena(JTextField korime) {
        String tempkorime = korime.getText();
        Pattern pattern = Pattern.compile("^[_A-Za-zšđčćžŠĐČĆŽ0-9]{1,50}$");
        Matcher matcher = pattern.matcher(tempkorime);

        if (!matcher.matches()) {
            JOptionPane.showMessageDialog(
                    rootPane,
                    "Unsite ispravno korisničko ime, koristite brojke i slova (Dužina max 50 znakova)!",
                    "Pogreška",
                    JOptionPane.ERROR_MESSAGE);

            korime.requestFocus();
            return false;
        }
        return true;
    }

    public boolean provjeraUnosaKategorijeProizvodjacaVozila(JTextField katpro) {
        String tempkatpro = katpro.getText();
        Pattern pattern = Pattern.compile("^[_A-Za-zšđčćžŠĐČĆŽ\\s?{2}]{1,50}$");
        Matcher matcher = pattern.matcher(tempkatpro);

        if (!matcher.matches()) {
            JOptionPane.showMessageDialog(
                    rootPane,
                    "Neispravan unos proizvodjaca ili kategorije vozila, koristite slova(Dužina max 50 znakova)!",
                    "Pogreška",
                    JOptionPane.ERROR_MESSAGE);

            katpro.requestFocus();
            return false;
        }
        return true;
    }

    public boolean provjeraUnosaNaziva(JTextField naziv) {
        String tempnaziv = naziv.getText();
        Pattern pattern = Pattern.compile("^[_A-Za-zšđčćžŠĐČĆŽ\\s?{2}\\.{3}]{1,50}$");
        Matcher matcher = pattern.matcher(tempnaziv);

        if (!matcher.matches()) {
            JOptionPane.showMessageDialog(
                    rootPane,
                    "Unsite traženi naziv ispravno(grad,država,naziv stanice i td.), koristite slova(Dužina max 50 znakova)!",
                    "Pogreška",
                    JOptionPane.ERROR_MESSAGE);

            naziv.requestFocus();
            return false;
        }
        return true;
    }

    public boolean provjeraUnosaBoje(JTextField naziv) {
        String tempnaziv = naziv.getText();
        Pattern pattern = Pattern.compile("^[_A-Za-zšđčćžŠĐČĆŽ\\s?{2}]{1,50}$");
        Matcher matcher = pattern.matcher(tempnaziv);

        if (!matcher.matches()) {
            JOptionPane.showMessageDialog(
                    rootPane,
                    "Unsite naziv boje ispravno, koristite slova(Dužina max 50 znakova)!",
                    "Pogreška",
                    JOptionPane.ERROR_MESSAGE);

            naziv.requestFocus();
            return false;
        }
        return true;
    }

    public boolean provjeraUnosaZaDatum(JTextField datumrodjenja) {

        String tempdate = datumrodjenja.getText();
        Pattern pattern = Pattern.compile("\\d{2}.\\d{2}.\\d{4}");
        Matcher matcher = pattern.matcher(tempdate);

        if (!matcher.matches()) {
            JOptionPane.showMessageDialog(
                    rootPane,
                    "Unesite datum u formatu dd.MM.yyyy!",
                    "Pogreška",
                    JOptionPane.ERROR_MESSAGE);

            datumrodjenja.requestFocus();
            return false;

        }

        return true;
    }

    public boolean provjeraUnosaZaDecimalniZapis(JTextField decimal) {

        String tempdecimal = decimal.getText();
        Pattern pattern = Pattern.compile("\\d{1,8}.\\d{2}");
        Matcher matcher = pattern.matcher(tempdecimal);

        if (!matcher.matches()) {
            JOptionPane.showMessageDialog(
                    rootPane,
                    "Unesite decimlani broj za cijenu sa dvije decimale (ili težinu vozila)!",
                    "Pogreška",
                    JOptionPane.ERROR_MESSAGE);

            decimal.requestFocus();
            return false;

        }

        return true;
    }

    public boolean provjeraUnosaZaMail(JTextField mail) {
        String tempmail = mail.getText();
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher(tempmail);
        if (!matcher.matches()) {
            JOptionPane.showMessageDialog(
                    rootPane,
                    "Unesite mail u formatu korisnik@domena.com! Nemojte koristit cro znakove",
                    "Pogreška",
                    JOptionPane.ERROR_MESSAGE);
            mail.requestFocus();
            return false;
        }
        return true;
    }

    public boolean provjeraUnosaZaTablice(JTextField tablice) {
        String tempmail = tablice.getText();
        Pattern pattern = Pattern.compile("^[_A-Za-zšđčćžŠĐČĆŽ]{2}-[_A-Za-z0-9-]{2,10}-[_A-Za-zšđčćžŠĐČĆŽ]{2}$");
        Matcher matcher = pattern.matcher(tempmail);
        if (!matcher.matches()) {
            JOptionPane.showMessageDialog(
                    rootPane,
                    "Unesite tablice u formatu primjera OS-4566-DT!",
                    "Pogreška",
                    JOptionPane.ERROR_MESSAGE);

            tablice.requestFocus();
            return false;
        }
        return true;
    }

    //unos godista, oiba
    public boolean brojeviKeyTyped(java.awt.event.KeyEvent evt, JTextField decimalni) {

        char c = evt.getKeyChar();
        if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || c == KeyEvent.VK_DELETE || c == KeyEvent.VK_PERIOD)) {
            evt.consume();
            JOptionPane.showMessageDialog(
                    rootPane,
                    "Unesite stavku samo pomoću znamenki!",
                    "Pogreška",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if(c == ('.')){
            if (decimalni.getText().indexOf(".") < 0){ 
            }else{
                evt.consume();
                JOptionPane.showMessageDialog(
                    rootPane,
                    "Možete unjeti samo jednu decimalnu točku!",
                    "Pogreška",
                    JOptionPane.ERROR_MESSAGE);
            return false;
            }
            
        }

        return true;
        // TODO add your handling code here:
    }

    public boolean kontrolaDecimalneTocke(java.awt.event.KeyEvent evt, JTextField decimalni) {
        if (decimalni.getText().indexOf(".") > 1) {
            JOptionPane.showMessageDialog(
                    rootPane,
                    "Možete unjeti samo jedan decimalni zarez!",
                    "Pogreška",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

   

}
