/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package valentic.registracija.model;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Basic
 */
public class Stranka extends Entitet {

  
    private List<Vozilo> vozila;
    private String ime;
    private String prezime;
    private String adresa;
    private String oib;
    private String mobitel;
    private String adresa2;
    private String drugiMobitel;
    private String fiksniTelefon;
    private String email;
    private String drzava;
    private String grad;
    private int zip;
    private String regija;
            
    private Date datumRodjenja;

    public Stranka() {

        
    }

    public String getRegija() {
        return regija;
    }

    public void setRegija(String regija) {
        this.regija = regija;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
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

    public String getOib() {
        return oib;
    }

    public void setOib(String oib) {
        this.oib = oib;
    }

    public String getAdresa2() {
        return adresa2;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public void setAdresa2(String adresa2) {
        this.adresa2 = adresa2;
    }

    public String getDrugiMobitel() {
        return drugiMobitel;
    }

    public void setDrugiMobitel(String drugiMobitel) {
        this.drugiMobitel = drugiMobitel;
    }

    public String getFiksniTelefon() {
        return fiksniTelefon;
    }

    public void setFiksniTelefon(String fiksniTelefon) {
        this.fiksniTelefon = fiksniTelefon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDrzava() {
        return drzava;
    }

    public void setDrzava(String drzava) {
        this.drzava = drzava;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public Date getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(Date datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }


    public List<Vozilo> getVozila() {
        return vozila;
    }

    public void setVozila(List<Vozilo> vozila) {
        this.vozila = vozila;
    }

    public String getMobitel() {
        return mobitel;
    }

    public void setMobitel(String mobitel) {
        this.mobitel = mobitel;
    }

}
