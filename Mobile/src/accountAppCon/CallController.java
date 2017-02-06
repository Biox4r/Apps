/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accountAppCon;

import accountAppMod.Call;
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
public class CallController extends ConnectionWorkOut {

    private Call call = null;
    private TempID getId;

    public CallController() {
    }

    public boolean insertCall(Call call) {
        getId = new TempID();
        String query = "insert into CALLS (ACCID,DURATION,CHARGED) values (?,?,?)";
        try {
            synchronized (this) {
                prSt = (OraclePreparedStatement) connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                getValuesOfCall(call);

                prSt.executeUpdate();
                rs = (OracleResultSet) prSt.getGeneratedKeys();
                rs.next();
            }
            rs.close();
            prSt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    private void getValuesOfCall(Call call) throws SQLException {
        //prSt.setBigDecimal(1, call.getId());
        prSt.setBigDecimal(1, call.getAccId());
        prSt.setBigDecimal(2, call.getDuration());
        prSt.setBigDecimal(3, call.getCharged());

    }

}
