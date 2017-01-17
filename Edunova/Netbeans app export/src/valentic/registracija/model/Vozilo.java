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
public class Vozilo extends Entitet {

    //glavni kljuc
   
    //vanjski kljucevi
    private int stranka;
    // podaci o vozilu
    private String modelVozila;
    private String kategorijaVozila;
    private String proizvodjacVozila;
    private String nazivVozila;
    private String tablice;
    private int godiste;
    private String boja;

    private int snagaMotora;
    private double tezinaVozila;
    private String brojSasije;
    private int brojVrata;
    private String ostalo;

    public Vozilo() {

    }

    public int getSnagaMotora() {
        return snagaMotora;
    }

    public void setSnagaMotora(int snagaMotora) {
        this.snagaMotora = snagaMotora;
    }

    public double getTezinaVozila() {
        return tezinaVozila;
    }

    public void setTezinaVozila(double tezinaVozila) {
        this.tezinaVozila = tezinaVozila;
    }

    public String getBrojSasije() {
        return brojSasije;
    }

    public void setBrojSasije(String brojSasije) {
        this.brojSasije = brojSasije;
    }

    public int getBrojVrata() {
        return brojVrata;
    }

    public void setBrojVrata(int brojVrata) {
        this.brojVrata = brojVrata;
    }

    public String getOstalo() {
        return ostalo;
    }

    public void setOstalo(String ostalo) {
        this.ostalo = ostalo;
    }

    @Override
    public String toString() {
        return this.nazivVozila;
    }

    public int getStranka() {
        return stranka;
    }

    public void setStranka(int stranka) {
        this.stranka = stranka;
    }

    public String getModelVozila() {
        return modelVozila;
    }

    public void setModelVozila(String modelVozila) {
        this.modelVozila = modelVozila;
    }

    public String getKategorijaVozila() {
        return kategorijaVozila;
    }

    public void setKategorijaVozila(String kategorijaVozila) {
        this.kategorijaVozila = kategorijaVozila;
    }

    public String getProizvodjacVozila() {
        return proizvodjacVozila;
    }

    public void setProizvodjacVozila(String proizvodjacVozila) {
        this.proizvodjacVozila = proizvodjacVozila;
    }

    public int getGodiste() {
        return godiste;
    }

    public void setGodiste(int godiste) {
        this.godiste = godiste;
    }

    public String getNazivVozila() {
        return nazivVozila;
    }

    public void setNazivVozila(String nazivVozila) {
        this.nazivVozila = nazivVozila;
    }

    public String getBoja() {
        return boja;
    }

    public void setBoja(String boja) {
        this.boja = boja;
    }

    public String getTablice() {
        return tablice;
    }

    public void setTablice(String tablice) {
        this.tablice = tablice;
    }

}
