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
public abstract class Entity {
    //abstract class  called entity - presnt in all tables as ID
    BigDecimal id;

    public Entity() {
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    

    

    
    
    
}
