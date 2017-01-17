/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accountAppUtil;

import oracle.jdbc.OracleConnection;

import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

/**
 *
 * @author Deamon
 */
public abstract class ConnectionWorkOut {

    public OracleConnection connection ;
    public OracleResultSet rs ;
    public OraclePreparedStatement prSt ;

    public ConnectionWorkOut() {
        connection =  (OracleConnection) WorkWithDatabase.getInstance().getConnection();
    }
}
