/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package valentic.registracija.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import valentic.registracija.utility.RadSBazom;

/**
 *
 * @author Basic
 */
public abstract class ObradaVeze {
    protected PreparedStatement izrazP;
    protected ResultSet rs;
    protected Connection veza;

    public ObradaVeze() {
        veza = RadSBazom.dohvatiInstancu().getVeza();
    }
    
    
}
