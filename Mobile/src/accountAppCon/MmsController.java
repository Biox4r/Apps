/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accountAppCon;

import accountAppMod.Call;
import accountAppMod.Mms;
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
public class MmsController extends ConnectionWorkOut {

    private Mms mms;
    private TempID getId;

    public MmsController() {

    }

    public boolean insertMms(Mms mms) {
        getId = new TempID();
        String query = "insert into MMS (ACCID,AMOUNT,CHARGED) values (?,?,?)";
        
        try {
            synchronized (this){
                prSt = (OraclePreparedStatement) connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            getValuesOfMms(mms);
            //mms.setId(getId.getNextId());
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

    private void getValuesOfMms(Mms mms) throws SQLException {

        prSt.setBigDecimal(1, mms.getAccId());
        prSt.setInt(2, mms.getAmount());
        prSt.setBigDecimal(3, mms.getCharged());

    }

}
