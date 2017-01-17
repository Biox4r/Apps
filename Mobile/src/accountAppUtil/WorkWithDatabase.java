/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accountAppUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import oracle.jdbc.OracleConnection;

/**
 *
 * @author Deamon
 */

/* private static WorkWithDatabase instance;
 public OracleConnection connection;

 public WorkWithDatabase(){
 try {
 Class.forName("oracle.jdbc.OracleDriver");
 }catch (ClassNotFoundException e){
 System.out.println("Where is your Oracle JDBC Driver?");
 e.printStackTrace();
 return;

 }

 */
public class WorkWithDatabase {

    public static WorkWithDatabase instance;
    public OracleConnection connection;

    public WorkWithDatabase() {

        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (Exception e) {
            System.out.println("Where is your Oracle jdbc driver");
            e.printStackTrace();
            return;
        }
        System.out.println("Oracle jdbc driver registerd");

        try {
            connection = (OracleConnection) DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:Database", "Siemens", "Progenitor28");
        } catch (Exception e) {
            System.out.println("Something went wrong, connection can not be established");
            e.printStackTrace();
            return;
        }

        if (connection != null) {
            System.out.println("Connection has been established");
        } else {

        }

    }

    public static WorkWithDatabase getInstance() {
        if (instance == null) {
            instance = new WorkWithDatabase();
        }
        return instance;
    }

    public OracleConnection getConnection() {
        return connection;
    }

    public void setConnection(OracleConnection connection) {
        this.connection = connection;
    }

}
