/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accountAppMod;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Deamon
 *
 *
 *
 */
public class Account extends Entity {

    private String key;
    //keyType 1 = MSISDN, 2 = IMSI, 3 = IMEI, 4 = generic
    private int keytype;
    private int currency;
    private int active;
    private BigDecimal initbalance;
    private BigDecimal minbalance;
    private Date dateOfCreation;

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getKeyType() {
        return keytype;
    }

    public void setKeyType(int keytype) {
        this.keytype = keytype;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public BigDecimal getInitbalance() {
        return initbalance;
    }

    public void setInitbalance(BigDecimal initbalance) {
        this.initbalance = initbalance;
    }

    public BigDecimal getMinbalance() {
        return minbalance;
    }

    public void setMinbalance(BigDecimal minbalance) {
        this.minbalance = minbalance;
    }

}
