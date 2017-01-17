/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accountAppUtil;

import java.math.BigDecimal;
import oracle.jdbc.OracleConnection;

import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

/**
 *
 * @author Deamon
 */
public class TempID extends ConnectionWorkOut {

    public TempID() {

    }
public BigDecimal getNextId() {
        BigDecimal num = null;
        String query = null;

        try {
            prSt = (OraclePreparedStatement) connection.prepareStatement("select S_TABLES_ID.nextval as num from dual");

            synchronized (this) {
                rs = (OracleResultSet) prSt.executeQuery();
                if (rs.next()) {
                    num = rs.getBigDecimal(1);
                    System.out.println("The tempID controller is= " + num);
                }
            }
            rs.close();
            prSt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return num;
    }
    

    /*
     try {
     connection.setAutoCommit(false);
     System.out.println("The autocommit is disabled");
     } catch (Exception e) {
     System.out.println("There was an error disableing autocommit ");
     }
         
     try {
     query = "select S_TABLE_ID.nextval as num from dual";
            
     //connection.commit();
     } catch (Exception e) {
     try {
     //we are rolling back the transaction
     connection.rollback();
     System.err.println(e.getMessage());
     System.err.println("The transaction was rolled back");
     } catch (Exception ex) {
     System.out.println("There was an error on rolling back transaction");
     }
     }
     */
}
