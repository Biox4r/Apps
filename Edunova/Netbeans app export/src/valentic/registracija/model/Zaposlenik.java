/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package valentic.registracija.model;

/**
 *
 * @author Basic
 */
public class Zaposlenik extends Entitet {


    private String ime;
    private String prezime;
    private String korisnickoIme;
    private String lozinka;

    public Zaposlenik(int sifra, String ime, String prezime, String korisnickoIme, String lozinka) {
        this.sifra = sifra;
        this.ime = ime;
        this.prezime = prezime;
        this.korisnickoIme = korisnickoIme;
        this.lozinka = lozinka;
    }

    public Zaposlenik() {

    }

    public int getSifra() {
        return sifra;
    }

    public void setSifra(int sifra) {
        this.sifra = sifra;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }
    
    public String getImePrezime(){
        return this.ime + " " + this.prezime;
    }
    
    @Override
    public String toString() {
        return this.ime + " " + this.prezime + "(" + this.korisnickoIme + ")";
    }

}
