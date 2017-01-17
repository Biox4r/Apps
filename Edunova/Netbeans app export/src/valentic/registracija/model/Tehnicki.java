/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package valentic.registracija.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Basic
 */
public class Tehnicki extends Entitet {

   
    private int vozilo;
    private Date datumTechnickog;
    private BigDecimal cijenaTehnickog;
    private boolean ispravnoVozilo;
    private String nazivStanice;
    private String imeOvlasteneOsobe;
    private String idTehnickog;
    

    public Tehnicki() {

    }

    public String getIdTehnickog() {
        return idTehnickog;
    }

    public void setIdTehnickog(String idTehnickog) {
        this.idTehnickog = idTehnickog;
    }


    public int getVozilo() {
        return vozilo;
    }

    public void setVozilo(int vozilo) {
        this.vozilo = vozilo;
    }

    public Date getDatumTechnickog() {
        return datumTechnickog;
    }

    public void setDatumTechnickog(Date datumTechnickog) {
        this.datumTechnickog = datumTechnickog;
    }

    public BigDecimal getCijenaTehnickog() {
        return cijenaTehnickog;
    }

    public void setCijenaTehnickog(BigDecimal cijenaTehnickog) {
        this.cijenaTehnickog = cijenaTehnickog;
    }

    public boolean isIspravnoVozilo() {
        return ispravnoVozilo;
    }

    public void setIspravnoVozilo(boolean ispravnoVozilo) {
        this.ispravnoVozilo = ispravnoVozilo;
    }

    public String getNazivStanice() {
        return nazivStanice;
    }

    public void setNazivStanice(String nazivStanice) {
        this.nazivStanice = nazivStanice;
    }

    public String getImeOvlasteneOsobe() {
        return imeOvlasteneOsobe;
    }

    public void setImeOvlasteneOsobe(String imeOvlasteneOsobe) {
        this.imeOvlasteneOsobe = imeOvlasteneOsobe;
    }

   

}
