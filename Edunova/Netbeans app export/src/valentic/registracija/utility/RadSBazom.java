package valentic.registracija.utility;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * OVO JE SINGLETON KLASA
 */
public class RadSBazom {

    private static RadSBazom instanca;
    private Connection veza;

    public RadSBazom() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            veza = DriverManager.getConnection(
                    "jdbc:mysql://localhost/registracija?useUnicode=true&characterEncoding=UTF-8",
                    "root", "root");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getVeza() {
        return veza;
    }

    public void setVeza(Connection veza) {
        this.veza = veza;
    }

    public static RadSBazom dohvatiInstancu() {
        if (instanca == null) {
            instanca = new RadSBazom();
        }
        return instanca;
    }

}
