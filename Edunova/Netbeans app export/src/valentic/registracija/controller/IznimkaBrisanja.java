/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package valentic.registracija.controller;

/**
 *
 * @author Basic
 */
public class IznimkaBrisanja extends Exception {
    
    private int iznimka;

    public IznimkaBrisanja(int iznimka) {
        this.iznimka = iznimka;
    }

    public int getIznimka() {
        return iznimka;
    }

    public void setIznimka(int iznimka) {
        this.iznimka = iznimka;
    }
    
   
}
