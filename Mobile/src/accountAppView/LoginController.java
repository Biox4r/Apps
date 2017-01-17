/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accountAppView;

import accountAppCon.UserController;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.crypto.NoSuchPaddingException;

import accountAppMod.User;
import accountAppUtil.Decrypter;
import accountAppUtil.PassGen;
import accountAppUtil.PopUpMessages;

/**
 *
 * @author Deamon
 */
public class LoginController implements Initializable {

    private UserController empCon;
    private User empMod;
    private PopUpMessages popUp;
    private PassGen passg;
    private Decrypter decr;
    public static BigDecimal tempEmpId;
    public static String tempEmpName;

    public LoginController() throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException {
        empCon = new UserController();
        popUp = new PopUpMessages();
        //decr = new Decrypter();
        decr = new Decrypter();
        passg = new PassGen();

    }

    @FXML
    private Label label;
    @FXML
    private TextField user;
    @FXML
    private PasswordField pass;
    @FXML
    private Label lbluser;
    @FXML
    private Label lblpass;

    @FXML
    public void handleButtonAction(ActionEvent event) throws IOException, Exception {
        if (user.getText().trim().length() == 0 || pass.getText().trim().length() == 0) {
            popUp.WarningMessage("Enter your user name and password please");
            return;
        }

        empMod = empCon.getUser(user.getText(), pass.getText());

        //empMod = empCon.getUser(user.getText(), pass.getText());
        if (empMod == null) {
            popUp.InformationMessage("There is no such User, please try again");
            return;
        } else {
            showCustomerDialog(empMod);

        }
    }

    @FXML
    public void loginPressed(KeyEvent evt) throws Exception {

        if (evt.getCode() == KeyCode.ENTER) {
            if (this.user.getText().length() > 0 || this.pass.getText().length() > 0) {
                handleButtonAction(null);
                ((Node) (evt.getSource())).getScene().getWindow().hide();
            } else if (this.user.getText().length() < 0 || this.pass.getText().length() > 0) {

                user.requestFocus();

            } else if (this.user.getText().length() > 0 || this.pass.getText().length() < 0) {

                pass.requestFocus();
            }
        }
        // TODO add your handling code here:
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void showCustomerDialog(User emp) throws IOException {

        tempEmpId = emp.getId();
        tempEmpName = emp.getName();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
        final Scene scene = new javafx.scene.Scene(root);
        stage.setScene(scene);
        stage.show();

    }
/*
    @FXML
    private void closeButtonAction() {
        // get a handle to the stage
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
*/
}
