/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package valentic.registracija.model;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Basic
 */
public class Registracija extends Entitet {

    
    private int vozilo;
 
    private int placanje;
    private Date datumRegistracije;
    private String imePodnositeljaZahtjeva;
    private String oibPodnositeljaZahtjeva;
    private String nazivOsiguravajuceKuce;
    private String idZahtjeva;
    private String nazivServisa;
    private boolean ispravno;
    private SimpleDateFormat sdf;
    private String datumZaListu;

    public Registracija() {

        sdf = new SimpleDateFormat("dd.MM.yyyy");
    }

    public int getPlacanje() {
        return placanje;
    }

    public void setPlacanje(int placanje) {
        this.placanje = placanje;
    }

    public String getNazivServisa() {
        return nazivServisa;
    }

    public void setNazivServisa(String nazivServisa) {
        this.nazivServisa = nazivServisa;
    }

    public int getVozilo() {
        return vozilo;
    }

    public void setVozilo(int vozilo) {
        this.vozilo = vozilo;
    }

   

    public String getNazivOsiguravajuceKuce() {
        return nazivOsiguravajuceKuce;
    }

    public void setNazivOsiguravajuceKuce(String nazivOsiguravajuceKuce) {
        this.nazivOsiguravajuceKuce = nazivOsiguravajuceKuce;
    }

  

    public Date getDatumRegistracije() {
        return datumRegistracije;
    }

    public void setDatumRegistracije(Date datumRegistracije) {
        this.datumRegistracije = datumRegistracije;
    }

    public String getImePodnositeljaZahtjeva() {
        return imePodnositeljaZahtjeva;
    }

    public void setImePodnositeljaZahtjeva(String imePodnositeljaZahtjeva) {
        this.imePodnositeljaZahtjeva = imePodnositeljaZahtjeva;
    }

    public String getOibPodnositeljaZahtjeva() {
        return oibPodnositeljaZahtjeva;
    }

    public void setOibPodnositeljaZahtjeva(String oibPodnositeljaZahtjeva) {
        this.oibPodnositeljaZahtjeva = oibPodnositeljaZahtjeva;
    }

    public String getNazivOsiguravateljskeKuce() {
        return nazivOsiguravajuceKuce;
    }

    public void setNazivOsiguravateljskeKuce(String nazivOsiguravateljskeKuce) {
        this.nazivOsiguravajuceKuce = nazivOsiguravateljskeKuce;
    }

    public String getIdZahtjeva() {
        return idZahtjeva;
    }

    public void setIdZahtjeva(String idZahtjeva) {
        this.idZahtjeva = idZahtjeva;
    }

    public boolean isIspravno() {
        return ispravno;
    }

    public void setIspravno(boolean ispravno) {
        this.ispravno = ispravno;
    }
    
    public String toString(){
        datumZaListu = sdf.format(datumRegistracije.getTime());
        return this.datumZaListu;
    }

}
