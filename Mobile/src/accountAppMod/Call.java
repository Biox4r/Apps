/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accountAppMod;

import java.math.BigDecimal;

/**
 *
 * @author Deamon
 */
public class Call extends Entity {
    
    private BigDecimal AccId;
    private BigDecimal duration;
    private BigDecimal charged;

    public BigDecimal getAccId() {
        return AccId;
    }

    public void setAccId(BigDecimal AccId) {
        this.AccId = AccId;
    }

    public BigDecimal getDuration() {
        return duration;
    }

    public void setDuration(BigDecimal duration) {
        this.duration = duration;
    }

    public BigDecimal getCharged() {
        return charged;
    }

    public void setCharged(BigDecimal charged) {
        this.charged = charged;
    }
    
    
    
}
