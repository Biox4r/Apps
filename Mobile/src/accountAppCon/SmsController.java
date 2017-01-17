/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accountAppCon;


import accountAppMod.Sms;
import accountAppUtil.ConnectionWorkOut;
import accountAppUtil.TempID;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Statement;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

/**
 *
 * @author Deamon
 */
public class SmsController extends ConnectionWorkOut {

    private Sms sms;
    private TempID getId;
    
    public SmsController() {
    }
    
    
    public boolean insertSms(Sms sms) {
        getId = new TempID();
        String query = "insert into SIEMENSSMS (ACCID,AMOUNT,CHARGED) values (?,?,?)";
        try {
            synchronized (this){prSt = (OraclePreparedStatement) connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            getValuesOfSms(sms);
            //sms.setId(getId.getNextId());
            prSt.executeUpdate();
            rs = (OracleResultSet) prSt.getGeneratedKeys();
            rs.next();
            }
            rs.close();
            prSt.close();
            
        } catch (Exception e) {
        }

        return true;
    }

    private void getValuesOfSms(Sms sms) throws SQLException {       
        prSt.setBigDecimal(1, sms.getAccId());
        prSt.setInt(2, sms.getAmount());
        prSt.setBigDecimal(3, sms.getCharged());

    }
}
