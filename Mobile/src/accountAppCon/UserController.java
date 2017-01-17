/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accountAppCon;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

import accountAppMod.User;
import accountAppUtil.ConnectionWorkOut;
import accountAppUtil.PassGen;
import accountAppUtil.TempID;

/**
 *
 * @author Deamon
 */
public class UserController extends ConnectionWorkOut {

    //prepareing new Employee
    User usr = null;

    private PassGen passg;
    private String userName;
    private TempID getId;

    public UserController() {
        //calling for ConnectionWorkOut class and connection methods
        super();
        passg = new PassGen();
        getId = new TempID();
    }

    public ComboBox getLista(String pretraga) {

        ComboBox model = new ComboBox();
        try {

            String query = "SELECT name FROM SIEMENSEMPLOYEE WHERE name LIKE '" + pretraga + "%';";
            prSt = (OraclePreparedStatement) connection.createStatement();
            synchronized (this) {
                rs = (OracleResultSet) prSt.executeQuery(query);
                while (rs.next()) {
                    model.setValue(rs.getString("name"));
                    // model.addElement();
                }
            }
            rs.close();
            prSt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return model;
    }

    public User getUsers(String pretraga) {

        User usr = new User();

        //String query = "SELECT * FROM Employee WHERE Employeename LIKE '" + pretraga.substring(0, pretraga.indexOf(" ")) + "%' AND prezime like '" + pretraga.substring(pretraga.indexOf(" ") + 1, pretraga.length()) + "%';";
        String query1 = "SELECT * FROM SIEMENSEMPLOYEE WHERE name LIKE '" + pretraga + "'";
        String query2 = "Select SIEMENSEMPLOYEE.*, SIEMENSEMPLOYEE.name as oName from SIEMENSEMPLOYEE full join owner ON usr.owner_id=owner.id WHERE usr.name LIKE '" + pretraga + "'";

        try {
            prSt = (OraclePreparedStatement) connection.prepareStatement(query1);
            synchronized (this) {
                rs = (OracleResultSet) prSt.executeQuery();
                while (rs.next()) {
                    usr = new User();

                    usr.setId(rs.getBigDecimal("ID"));

                    usr.setName(rs.getString("NAME"));
                    usr.setUserName(rs.getString("USERNAME"));
                    usr.setPassword(rs.getString("PASSWORD"));
                    try {
                        usr.setDateOfCreation(new Date(rs.getDate("INSTM").getTime()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    usr.setAdmin(rs.getString("ADMIN"));
                    usr.setActive(rs.getString("ACTIVE"));
                }
            }

            rs.close();
            prSt.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return usr;
    }

    public User getUsersById(BigDecimal id) {

        User usr = new User();

        //String query = "SELECT * FROM Employee WHERE Employeename LIKE '" + id.substring(0, id.indexOf(" ")) + "%' AND prezime like '" + id.substring(id.indexOf(" ") + 1, id.length()) + "%';";
        String query1 = "SELECT * FROM SIEMENSEMPLOYEE WHERE id LIKE '" + id + "'";
        String query2 = "Select usr.*, owner.name as oName from usr left join owner ON usr.owner_id=owner.id WHERE usr.id LIKE '" + id + "'";

        try {
            prSt = (OraclePreparedStatement) connection.prepareStatement(query1);
            synchronized (this) {
                rs = (OracleResultSet) prSt.executeQuery();
                while (rs.next()) {
                    usr = new User();
                    usr.setId(rs.getBigDecimal("ID"));
                    usr.setName(rs.getString("NAME"));
                    usr.setUserName(rs.getString("USERNAME"));
                    usr.setPassword(rs.getString("PASSWORD"));
                    try {
                        usr.setDateOfCreation(new Date(rs.getDate("INSTM").getTime()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    usr.setAdmin(rs.getString("ADMIN"));
                    usr.setActive(rs.getString("ACTIVE"));
                }
            }

            rs.close();
            prSt.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return usr;
    }

    public ArrayList<String> getUserNames() {
        ArrayList<String> listOfUsers = new ArrayList<>();
        String tempUserNames = null;
        String query = "SELECT name FROM SIEMENSEMPLOYEE";
        try {
            prSt = (OraclePreparedStatement) connection.prepareStatement(query);
            //prSt.setString(1, pretraga);
            synchronized (this) {
                rs = (OracleResultSet) prSt.executeQuery();
                while (rs.next()) {
                    tempUserNames = rs.getString("NAME");
                    listOfUsers.add(tempUserNames);
                }
            }
            rs.close();
            prSt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listOfUsers;
    }

    public ObservableList<User> getUsersObsv() {
        //UserModel usr = null;

        ObservableList<User> userList = FXCollections.observableArrayList();

        String query1 = "SELECT * FROM SIEMENSEMPLOYEE";
        //String query = "SELECT * FROM vozilo WHERE stranka = 1";
        try {
            prSt = (OraclePreparedStatement) connection.prepareStatement(query1);

            rs = (OracleResultSet) prSt.executeQuery();
            while (rs.next()) {
                usr = new User();
                /*
                 try {
                 usr.setOwnerName(rs.getString("owner.name"));
                 } catch (Exception e) {
                 usr.setOwnerName(rs.getString(""));
                 }              
                 */
                usr.setName(rs.getString("NAME"));
                usr.setUserName(rs.getString("USERNAME"));
                //usr.setPassword(rs.getString("PASSWORD"));

                usr.setAdmin(rs.getString("ADMIN"));

                usr.setActive(rs.getString("ACTIVE"));
                userList.add(usr);
            }

            rs.close();
            prSt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;

    }

    public User getUser(String username, String password) throws SQLException, Exception {
        String temp;
        //query for employee selection based on username and password
        try {
            String sql = "select * from SIEMENSEMPLOYEE where username=?";
            prSt = (OraclePreparedStatement) connection.prepareStatement(sql);
            prSt.setString(1, username);
            //prSt.setString(2, password);
            synchronized (this) {
                rs = (OracleResultSet) prSt.executeQuery();
                while (rs.next()) {
                    usr = new User();
                    usr.setId(rs.getBigDecimal("ID"));
                    usr.setName(rs.getString("NAME"));
                    usr.setUserName(rs.getString("USERNAME"));
                    usr.setPassword(rs.getString("PASSWORD"));
                    if (!passg.check(password, rs.getString("password"))) {
                        System.out.println("Wrong username or password");
                        return usr = null;
                    }
                    try {
                        usr.setDateOfCreation(new Date(rs.getDate("INSTM").getTime()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    usr.setAdmin(rs.getString("ADMIN"));
                    usr.setActive(rs.getString("ACTIVE"));
                }
            }

            rs.close();
            prSt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usr;
    }

    public boolean chekUserName(String username) {
        boolean postoji = false;
        try {
            prSt = (OraclePreparedStatement) connection.prepareStatement("select ID from SIEMENSEMPLOYEE where username=?");
            prSt.setString(1, username);
            synchronized (this) {
                rs = (OracleResultSet) prSt.executeQuery();
                while (rs.next()) {
                    postoji = true;
                }

            }
            rs.close();
            prSt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return postoji;
    }

    public boolean insertUser(User usr) {
        getId = new TempID();
        try {
            synchronized (this) {
                prSt = (OraclePreparedStatement) connection.prepareStatement(
                        "insert into SIEMENSEMPLOYEE (name,username,password,active,admin) values (?,?,?,?,?)",
                        Statement.RETURN_GENERATED_KEYS);
                getValuesOfUser(usr);
                //usr.setId(getId.getNextId());
                prSt.executeUpdate();
                rs = (OracleResultSet) prSt.getGeneratedKeys();
                rs.next();
                //usr.setId(new BigDecimal(1));
            }
            prSt.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean updateUser(User usr) {
        try {
            synchronized (this) {
                prSt = (OraclePreparedStatement) connection.prepareStatement("update SIEMENSEMPLOYEE set name=?,username=?,password=?,active=?,admin=? where ID='" + usr.getId() + "'");
                getValuesOfUser(usr);
                prSt.executeUpdate();
            }
            prSt.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean deleteUser(User usr) {
        try {
            synchronized (this) {
                prSt = (OraclePreparedStatement) connection.prepareStatement(
                        "delete SIEMENSEMPLOYEE where ID='" + usr.getId() + "'");
                prSt.executeUpdate();
            }
            prSt.close();
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private void getValuesOfUser(User usr) throws SQLException {
        prSt.setString(1, usr.getName());
        prSt.setString(2, usr.getUserName());
        prSt.setString(3, usr.getPassword());
        prSt.setString(4, usr.getActive());
        prSt.setString(5, usr.getAdmin());

    }

}
