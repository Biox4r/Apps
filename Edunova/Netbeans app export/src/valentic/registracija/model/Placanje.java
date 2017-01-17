/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package valentic.registracija.model;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Basic
 */
public class Placanje extends Entitet {

    
    private int vozilo;
   
    private BigDecimal cijenaRegistracije;
    private BigDecimal cijenaTehnickog;
    private BigDecimal naknadaZaCeste;
    private BigDecimal naknadaZaOkolis;
    private BigDecimal biljezi;
    private BigDecimal ukupno;
    private boolean gotovinaKartice;
    private String idkartice;
    private String nazivKartice;
    private int brojRata;
    private Date datumPlacanja;
    private String imePlatitelja;
    private String oibPlatitelja;
    private String idRacuna;
    private SimpleDateFormat sdf;
    private String datumZaListu;
   

    public Placanje() {

        sdf = new SimpleDateFormat("dd.MM.yyyy");
        
    }

   

    

    public int getVozilo() {
        return vozilo;
    }
    
    @Override
    public String toString(){
        datumZaListu = sdf.format(datumPlacanja.getTime());
        return this.datumZaListu + "   " + this.ukupno;
    }

    public void setVozilo(int vozilo) {
        this.vozilo = vozilo;
    }

    
    public BigDecimal getCijenaRegistracije() {
        return cijenaRegistracije;
    }

    public void setCijenaRegistracije(BigDecimal cijenaRegistracije) {
        this.cijenaRegistracije = cijenaRegistracije;
    }

    public BigDecimal getCijenaTehnickog() {
        return cijenaTehnickog;
    }

    public void setCijenaTehnickog(BigDecimal cijenaTehnickog) {
        this.cijenaTehnickog = cijenaTehnickog;
    }

    public BigDecimal getNaknadaZaCeste() {
        return naknadaZaCeste;
    }

    public void setNaknadaZaCeste(BigDecimal naknadaZaCeste) {
        this.naknadaZaCeste = naknadaZaCeste;
    }

    public BigDecimal getNaknadaZaOkolis() {
        return naknadaZaOkolis;
    }

    public void setNaknadaZaOkolis(BigDecimal naknadaZaOkolis) {
        this.naknadaZaOkolis = naknadaZaOkolis;
    }

    public BigDecimal getBiljezi() {
        return biljezi;
    }

    public void setBiljezi(BigDecimal biljezi) {
        this.biljezi = biljezi;
    }

    public BigDecimal getUkupno() {
        return ukupno;
    }

    public void setUkupno(BigDecimal ukupno) {
        this.ukupno = ukupno;
    }

   

    public boolean isGotovinaKartice() {
        return gotovinaKartice;
    }

    public void setGotovinaKartice(boolean gotovinaKartice) {
        this.gotovinaKartice = gotovinaKartice;
    }

    public String getIdkartice() {
        return idkartice;
    }

    public void setIdkartice(String idkartice) {
        this.idkartice = idkartice;
    }

    public String getNazivKartice() {
        return nazivKartice;
    }

    public void setNazivKartice(String nazivKartice) {
        this.nazivKartice = nazivKartice;
    }

    public int getBrojRata() {
        return brojRata;
    }

    public void setBrojRata(int brojRata) {
        this.brojRata = brojRata;
    }

    public Date getDatumPlacanja() {
        return datumPlacanja;
    }

    public void setDatumPlacanja(Date datumPlacanja) {
        this.datumPlacanja = datumPlacanja;
    }

    public String getImePlatitelja() {
        return imePlatitelja;
    }

    public void setImePlatitelja(String imePlatitelja) {
        this.imePlatitelja = imePlatitelja;
    }

    public String getOibPlatitelja() {
        return oibPlatitelja;
    }

    public void setOibPlatitelja(String oibPlatitelja) {
        this.oibPlatitelja = oibPlatitelja;
    }

    public String getIdRacuna() {
        return idRacuna;
    }

    public void setIdRacuna(String idRacuna) {
        this.idRacuna = idRacuna;
    }

    
    
 
    

}
