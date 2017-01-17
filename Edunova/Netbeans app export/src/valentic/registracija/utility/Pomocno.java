/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package valentic.registracija.utility;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;
import javax.swing.JTextField;

/**
 *
 * @author Profesor
 */
public class Pomocno {
    
    public static String generirajMD5(char[] ulaz){
         //zadatak je uneseni niz znakova lozinke
        //prebaciti u md5
        String lozinkaString=new String(ulaz);
     String hashtext="";
 try { 
MessageDigest m = MessageDigest.getInstance("MD5");
m.reset();
m.update(lozinkaString.getBytes());
byte[] digest = m.digest();
BigInteger bigInt = new BigInteger(1,digest);
hashtext = bigInt.toString(16);
// Now we need to zero pad it if you actually want the full 32 chars.
while(hashtext.length() < 32 ){
  hashtext = "0"+hashtext;
}
 } catch (NoSuchAlgorithmException e) {
    e.printStackTrace();
 }
        
 
 //sada hashtext ima md5 vrijedno unesene lozinka
    return hashtext;
    }
    
   public static String generirajLozinku(){
        StringBuilder sb = new StringBuilder();
int n = 8; // how many characters in password
String set = "1234567890qwertzuiopšđasdfghjklčćžyxcvbnm,.-;:_!#$%&/()=?*"; 
int i;
Random r = new Random();
for (i= 0; i < n; i++) {
    int k = r.nextInt(set.length()-1);   
    sb.append(set.charAt(k));
}
return sb.toString();
    }  
   
   public static void KontrolaDatuma(JTextField datum){
       Calendar cal = new GregorianCalendar();
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int tempmonth = 0;
        int tempday = 0;

        if (day > 9) {
            datum.setText(day + "." + (month+1) + "." + year);
        } else {
            datum.setText(tempday + "" + day + "." 
                     + (month+1) + "." + year);
        }
        
        if(month+1 > 9){
            datum.setText(day + "." + (month+1) + "." + year);
        } else {
            datum.setText( day + "." 
                     + tempmonth + "" +(month+1) + "." + year);
        }
        
        if(day>9 & month+1>9){
             datum.setText( day + "." + (month+1) + "." + year);
        } else {
            datum.setText( tempday + day + "." 
                     + tempmonth + "" +(month+1) + "." + year);   
        }
   }
   
   
    
}
