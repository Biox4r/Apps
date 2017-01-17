/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accountAppCon;

import accountAppMod.Account;
import accountAppMod.User;
import accountAppUtil.ConnectionWorkOut;
import accountAppUtil.TempID;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

/**
 *
 * @author Deamon
 */
public class AccountController extends ConnectionWorkOut {

    private Account acc = null;
    private TempID getId;

    public AccountController() {
        getId = new TempID();
    }

    public ComboBox getLista(String pretraga) {
        ComboBox model = new ComboBox();
        try {
            String query = "SELECT key FROM SIEMENSACCOUNT WHERE key LIKE '" + pretraga + "%';";
            prSt = (OraclePreparedStatement) connection.createStatement();
            synchronized (this) {
                rs = (OracleResultSet) prSt.executeQuery(query);
                while (rs.next()) {
                    model.setValue(rs.getString("name"));
                }
            }
        } catch (Exception e) {
        }
        return null;

    }

    public ArrayList<String> getKey() {
        ArrayList<String> listOfUsers = new ArrayList<>();
        String tempAccKey = null;
        String query = "SELECT key FROM SIEMENSACCOUNT";
        try {
            prSt = (OraclePreparedStatement) connection.prepareStatement(query);
            //prSt.setString(1, pretraga);
            synchronized (this) {
                rs = (OracleResultSet) prSt.executeQuery();
                while (rs.next()) {
                    tempAccKey = rs.getString("key");
                    listOfUsers.add(tempAccKey);
                }
            }
            rs.close();
            prSt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listOfUsers;
    }

    public ArrayList<Integer> getIds() {
        ArrayList<Integer> listOfIds = new ArrayList<>();
        Integer tempId = null;
        String query = "SELECT id FROM SIEMENSACCOUNT";
        try {
            prSt = (OraclePreparedStatement) connection.prepareStatement(query);
            //prSt.setString(1, pretraga);
            synchronized (this) {
                rs = (OracleResultSet) prSt.executeQuery();
                while (rs.next()) {
                    tempId = rs.getInt("id");
                    listOfIds.add(tempId);
                }
            }
            rs.close();
            prSt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listOfIds;
    }

    public Account getAccounts(String pretraga) {

        //String query = "SELECT * FROM Employee WHERE Employeename LIKE '" + pretraga.substring(0, pretraga.indexOf(" ")) + "%' AND prezime like '" + pretraga.substring(pretraga.indexOf(" ") + 1, pretraga.length()) + "%';";
        String query1 = "SELECT * FROM SIEMENSACCOUNT WHERE KEY LIKE '" + pretraga + "'";

        try {
            prSt = (OraclePreparedStatement) connection.prepareStatement(query1);
            synchronized (this) {
                rs = (OracleResultSet) prSt.executeQuery();
                while (rs.next()) {
                    acc = new Account();
                    acc.setId(rs.getBigDecimal("id"));
                    acc.setKey(rs.getString("key"));
                    acc.setKeyType(rs.getInt("keytype"));
                    acc.setCurrency(rs.getInt("currency"));
                    acc.setActive(rs.getInt("active"));
                    try {
                        acc.setDateOfCreation(new Date(rs.getDate("INSTM").getTime()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    acc.setInitbalance(rs.getBigDecimal("initbalance"));
                    acc.setMinbalance(rs.getBigDecimal("minbalance"));
                }
            }

            rs.close();
            prSt.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return acc;
    }

    public Account getAccountById(Integer id) {

        String query1 = "SELECT * FROM SIEMENSACCOUNT WHERE id LIKE '" + id + "' AND keytype LIKE 1";

        try {
            prSt = (OraclePreparedStatement) connection.prepareStatement(query1);
            synchronized (this) {
                rs = (OracleResultSet) prSt.executeQuery();
                while (rs.next()) {
                    acc = new Account();
                    acc.setId(rs.getBigDecimal("id"));
                    acc.setKey(rs.getString("key"));
                    acc.setKeyType(rs.getInt("keytype"));
                    acc.setCurrency(rs.getInt("currency"));
                    acc.setActive(rs.getInt("active"));
                    try {
                        acc.setDateOfCreation(new Date(rs.getDate("INSTM").getTime()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    acc.setInitbalance(rs.getBigDecimal("initbalance"));
                    acc.setMinbalance(rs.getBigDecimal("minbalance"));
                }
            }

            rs.close();
            prSt.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return acc;
    }

    public ObservableList<Account> getAccountsObsv() {
        //UserModel usr = null;

        ObservableList<Account> accList = FXCollections.observableArrayList();

        String query1 = "Select * from SIEMENSACCOUNT";
        //String query = "SELECT * FROM vozilo WHERE stranka = 1";
        try {
            prSt = (OraclePreparedStatement) connection.prepareStatement(query1);

            rs = (OracleResultSet) prSt.executeQuery();
            while (rs.next()) {
                acc = new Account();
                /*
                 try {
                 usr.setOwnerName(rs.getString("owner.name"));
                 } catch (Exception e) {
                 usr.setOwnerName(rs.getString(""));
                 }              
                 */
                acc.setId(rs.getBigDecimal("id"));
                acc.setKey(rs.getString("key"));
                acc.setKeyType(rs.getInt("keytype"));

                acc.setCurrency(rs.getInt("currency"));
                acc.setActive(rs.getInt("active"));
                acc.setInitbalance(rs.getBigDecimal("initbalance"));
                acc.setMinbalance(rs.getBigDecimal("minbalance"));

                accList.add(acc);
            }

            rs.close();
            prSt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accList;

    }

    public boolean insertAccount(Account acc) {
        getId = new TempID();

        try {
            String query = "Insert into SIEMENSACCOUNT (key, keytype, currency, active, initbalance, minbalance) values (?,?,?,?,?,?)";

            synchronized (this) {
                prSt = (OraclePreparedStatement) connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                getValuesOfAccount(acc);
                
                prSt.executeUpdate();
                rs = (OracleResultSet) prSt.getGeneratedKeys();
                rs.next();
            }
            rs.close();
            prSt.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean updateAccount(Account acc) {
        try {
            synchronized (this) {
                prSt = (OraclePreparedStatement) connection.prepareStatement("update SIEMENSACCOUNT set key=?,keytype=?,currency=?,active=?,initbalance=?,minbalance=? where ID='" + acc.getId() + "'");
                getValuesOfAccount(acc);
                prSt.executeUpdate();
            }
            prSt.close();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean deleteAccount(Account acc) {
        try {
            synchronized (this) {
                prSt = (OraclePreparedStatement) connection.prepareStatement(
                        "delete SIEMENSACCOUNT where ID='" + acc.getId() + "'");
                prSt.executeUpdate();
            }
            prSt.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private void getValuesOfAccount(Account acc) throws SQLException {
        prSt.setString(1, acc.getKey());
        prSt.setInt(2, acc.getKeyType());
        prSt.setInt(3, acc.getCurrency());
        prSt.setInt(4, acc.getActive());
        prSt.setBigDecimal(5, acc.getInitbalance());
        prSt.setBigDecimal(6, acc.getMinbalance());
    }
}
