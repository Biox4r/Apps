/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accountAppView;

import accountAppCon.UserController;
import accountAppMod.User;
import accountAppUtil.ControlOfInput;
import accountAppUtil.Decrypter;
import java.math.BigDecimal;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.crypto.NoSuchPaddingException;
import accountAppUtil.FxUtil;
import accountAppUtil.HelpMethods;
import accountAppUtil.PassGen;
import accountAppUtil.PopUpMessages;

/**
 * FXML Controller class
 *
 * @author Deamon
 */
public class UserControl implements Initializable {

    @FXML
    private Label lblMain;
    @FXML
    private Label lblEmployeeNameSearch;
    @FXML
    private Label lblEmployeeName;
    @FXML
    private Label lblUserName;
    @FXML
    private Label lblAdmin;
    @FXML
    private Label lblPassword;
    @FXML
    private Label lblConfirmPass;
    @FXML
    private Label lblSelectUser;
    @FXML
    private Label lblInsertName;
    @FXML
    private Label lblInsertUsername;
    @FXML
    private Label lblInsertPass;
    @FXML
    private Label lblInsertConfirmPass;
    @FXML
    private Label lblInsertActive;
    @FXML
    private Label lblInsertEmployee;
    @FXML
    private Label lblInsertAdmin;
    @FXML
    private Label lblInsertOwner;
    @FXML
    private Label lblFinish;
    @FXML
    private Label lblEditMsg;
    @FXML
    private Label lblEditMsg2;
    @FXML
    private Label lblNewGenPass;
    @FXML
    private ComboBox comboUserSearch;
    @FXML
    private ComboBox comboSearchOwner;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtUsername;
    @FXML
    private CheckBox butAdminYes;
    @FXML
    private CheckBox butAdminNo;
    @FXML
    private CheckBox butEmployeeYes;
    @FXML
    private CheckBox butEmployeeNo;
    @FXML
    private CheckBox butActiveYes;
    @FXML
    private CheckBox butActiveNo;
    @FXML
    private TextField txtDateOfCreation;
    @FXML
    private TextField txtGenratedPass;
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private PasswordField txtOldPass;
    @FXML
    private PasswordField txtNewPass;
    @FXML
    private PasswordField txtConfirmPass;
    @FXML
    private PasswordField txtConfirmNewPass;
    @FXML
    private Button butChangePassword;
    @FXML
    private Button butInsert;
    @FXML
    private Button butUpdate;
    @FXML
    private Button butCancel;
    @FXML
    private Button butDelete;
    @FXML
    private Button butInsertPass;
    @FXML
    private Button butResetPass;
    @FXML
    private Button butShowUsers;
    @FXML
    private Button butDone;
    @FXML
    private RadioButton chekCorrect;
    @FXML
    private RadioButton chekFalse;
    @FXML
    private Button butClose;
    @FXML
    private String date;
    @FXML
    private String tempDate = "11.02.1983";
    @FXML
    private ListView listEmployees;

    public static BigDecimal userId = null;

    //table User
    @FXML
    private TableView<User> tableUser;
    @FXML
    private TableColumn<User, String> tbName;
    @FXML
    private TableColumn<User, String> tbUsername;
    @FXML
    private TableColumn<User, String> tbOwner;
    @FXML
    private TableColumn<User, String> tbEmployee;
    @FXML
    private TableColumn<User, String> tbAdmin;
    @FXML
    private TableColumn<User, String> tbActive;

    private String category1 = "Update";
    private String category2 = "Insert";
    private String tempCategory;

    //pop up messages input
    private String msgName = "Insert new name only with letters, use up to 50 characters";
    private String msgUsername = "Insert username using letters and number, insert up to 50 characters";
    private String msgPass = "The password must have Minimum 8 characters, at least 1 Uppercase Alphabet, 1 Lowercase Alphabet, 1 Number and 1 Special Character(#, ?, !)";

    private Decrypter decr;
    private ObservableList<String> listOfEmployees;
    ObservableList ownerList = FXCollections.observableArrayList();

    private User usrMod;
    private User tempUser = null;

    private UserController usrCon;

    private PopUpMessages popMsg;
    private ControlOfInput conInp;
    private SimpleDateFormat sdf;
    private HelpMethods helpMe;
    private String tempPass;
    private String tempPass1 = "baba";
    private PassGen passg;
    private String search;
    private FxUtil fx;

    private int choiceAct;

    //
    public UserControl() throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException {
        popMsg = new PopUpMessages();
        usrCon = new UserController();
        conInp = new ControlOfInput();
        passg = new PassGen();
        decr = new Decrypter();
        helpMe = new HelpMethods();
        //sdf = new SimpleDateFormat("dd.MM.yyyy");
        this.usrMod = usrMod;
        tempPass = HelpMethods.generatePassword();

        //ko = new Konj(comboUserSearch);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fx = new FxUtil();

        fillUsers();
        //fillOwners();
        setTempCategory(category2);
        setPassFieldsForUpdate();
        hideComponentsForPassGen();
        hideAllLabels();
        hideUserFields();
        hideInsertCancelButtons();
        hideUpdateDeleteButtons();
        lblSelectUser.setText("<<< Select User");
        comboSearchUser();
        // comboSearchOwner();
        selectUserFromTable();
        hidePassFields();

    }

    @FXML
    public void insertUser(ActionEvent evt) {

        if (!txtPassword.getText().equals(txtConfirmPass.getText())) {
            popMsg.InformationMessage("You must enter same passwords");
            return;
        }

        if (!controlOfInputForInsert()) {
            return;
        }

        choiceAct = popMsg.alertDialogInsert();

        if (usrCon.chekUserName(this.txtUsername.getText())) {
            popMsg.WarningMessage("There is already User with same username");
            return;
        }

        usrMod = new User();

        createUser(usrMod);

        if (choiceAct == 1) {
            if (usrCon.insertUser(usrMod)) {
                popMsg.insertMessageOk();
                fillUsers();
                fillAllUsers();
            } else {
                popMsg.insertMessageNotOk();
            }
        }

    }

    @FXML
    public void updateUser(ActionEvent evt) {
        if (tempUser == null) {
            popMsg.InformationMessage("You need to select user first");
            return;
        }

        if (!controlOfInputForUpdate()) {
            return;
        }

        choiceAct = popMsg.alertDialogUpdate();

        updateUser(usrMod);
        if (choiceAct == 1) {
            if (usrCon.updateUser(getTempUser())) {
                popMsg.updateMessageOk();
                removeUsers();
                //               String tempEmp = null;
//                tempEmp = comboUserSearch.getSelectionModel().getSelectedItem().toString();
                tempUser = usrCon.getUsersById(usrMod.getId());
                fillUser(tempUser);
                fillUsers();

            } else {
                popMsg.updateMessageNotOk();
            }
        }
    }

    @FXML
    public void deleteUser(ActionEvent evt
    ) {

        if (usrMod == null) {
            popMsg.InformationMessage("Select user first");
        }

        choiceAct = popMsg.alertDialogDelete();

        if (choiceAct == 1) {
            if (usrCon.deleteUser(usrMod)) {
                popMsg.deleteMessageOk();
                fillUsers();
                fillUser(usrMod);

            } else {
                popMsg.deleteMessageNotOk();
            }
        }
    }

    private void createUser(User usrMod) {
        //HelpMethods.dateControl(tempDate);
        usrMod.setName(txtName.getText());
        usrMod.setUserName(txtUsername.getText());

        if (butActiveYes.isSelected()) {

            usrMod.setActive("1");
        }

        if (butActiveNo.isSelected()) {

            usrMod.setActive("0");
        }

        if (butAdminYes.isSelected()) {

            usrMod.setAdmin("1");
        }

        if (butAdminNo.isSelected()) {

            usrMod.setAdmin("0");
        }

        try {
            usrMod.setPassword(passg.getSaltedHash(txtPassword.getText()));
            //empMod.setPassword(tempPass1);
        } catch (Exception ex) {
            popMsg.WarningMessage("The password that you have entered is not correct");
        }

    }

    private void updateUser(User usrMod) {

        usrMod.setName(txtName.getText());
        usrMod.setUserName(txtUsername.getText());

        if (butActiveYes.isSelected()) {

            usrMod.setActive("1");
        }

        if (butActiveNo.isSelected()) {

            usrMod.setActive("0");
        }

        if (butAdminYes.isSelected()) {

            usrMod.setAdmin("1");
        }

        if (butAdminNo.isSelected()) {

            usrMod.setAdmin("0");
        }

    }

    private void updateUserPass(User usrMod) {

        try {
            usrMod.setPassword(passg.getSaltedHash(txtConfirmNewPass.getText()));
        } catch (Exception ex) {
            Logger.getLogger(UserControl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void updateGeneratedUserPass(User usrMod) {

        try {
            tempPass = HelpMethods.generatePassword();
            lblNewGenPass.setVisible(true);
            butDone.setVisible(true);
            txtGenratedPass.setVisible(true);
            txtGenratedPass.setText(tempPass);
        } catch (Exception e) {
            System.out.println("Problem with password creation");
        }

        try {
            usrMod.setPassword(passg.getSaltedHash(tempPass));
        } catch (Exception ex) {
            Logger.getLogger(UserControl.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Problem with insert of generated password");
        }
    }

    private void loadUserFields(User usrMod) {
        try {
            txtName.setText(usrMod.getName().toString());
        } catch (Exception e) {
            txtName.setText("");
        }
        try {
            txtUsername.setText(usrMod.getUserName());
        } catch (Exception e) {
            txtUsername.setText("");
        }

        try {
            if (usrMod.getActive().equals("0")) {
                butActiveYes.setSelected(false);
                butActiveNo.setSelected(true);
            } else {
                butActiveYes.setSelected(true);
                butActiveNo.setSelected(false);
            }

        } catch (Exception e) {
            butActiveYes.setSelected(false);
            butActiveNo.setSelected(false);
        }

        try {
            if (usrMod.getAdmin().equals("0")) {
                butAdminYes.setSelected(false);
                butAdminNo.setSelected(true);
            } else {
                butAdminYes.setSelected(true);
                butAdminNo.setSelected(false);
            }
        } catch (Exception e) {
            butAdminYes.setSelected(false);
            butAdminNo.setSelected(false);
        }

    }

    @FXML
    private void closeForm() {
        Stage stage = (Stage) butClose.getScene().getWindow();
        stage.close();
    }

    private void fillUsers() {
        usrCon = new UserController();
        fx = new FxUtil();
        ArrayList<String> list = new ArrayList<>();
        list = usrCon.getUserNames();
        //ObservableList<Stavka> oListStavaka = FXCollections.observableArrayList(listStavaka);
        listOfEmployees = FXCollections.observableArrayList(list);
        //new AutoCompleteComboBoxListener(comboUserSearch, listOfEmployees);

        fx.autoCompleteComboBox(comboUserSearch, FxUtil.AutoCompleteMode.CONTAINING, listOfEmployees);
    }

    private void removeUsers() {

        listOfEmployees.removeAll(listOfEmployees);

    }

    @FXML
    public void keyTyped(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            System.out.println("baba");
        }
    }

    @FXML
    private void comboSearchUser() {

        comboUserSearch.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                try {
                    String tempEmp = null;
                    tempEmp = comboUserSearch.getSelectionModel().getSelectedItem().toString();

                    usrMod = usrCon.getUsers(tempEmp);
                    setTempUser(usrMod);
                    //containsOwnerId(ownerList, usrMod.getOwner_id());
                    setUpdateListeners();
                    labelsWhenUpdating();
                    showFiledsWhenUpdateing();
                    showUpdateDeleteButtons();
                    hideInsertCancelButtons();

                    //setPassFieldsForUpdate();
                    clearPasswordMenagmentFields();

                    fillUser(getTempUser());

                    System.out.println("User name =  " + usrMod.getName());
                    loadUserFields(usrMod);
                    lblEditMsg.setText("You are working now with user " + usrMod.getName());
                    lblEditMsg2.setText("for " + usrMod.getName());

                } catch (Exception e) {
                    usrMod = null;
                }
                System.out.println(ov);
                System.out.println(t);
                System.out.println(t1);

            }
        });

        comboUserSearch.getEditor().setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    String tempEmp = null;
                    tempEmp = comboUserSearch.getSelectionModel().getSelectedItem().toString();
                    setUpdateListeners();
                    labelsWhenUpdating();
                    showFiledsWhenUpdateing();
                    showUpdateDeleteButtons();
                    hideInsertCancelButtons();
                    //containsOwnerId(ownerList, usrMod.getOwner_id());

                    setPassFieldsForUpdate();
                    clearPasswordMenagmentFields();
                    fillUser(usrMod);

                    System.out.println("User name =  " + usrMod.getName());
                    loadUserFields(usrMod);
                    lblEditMsg.setText("You are working now with user " + usrMod.getName());
                    lblEditMsg2.setText("for " + usrMod.getName());

                } catch (Exception e) {
                    usrMod = null;
                }
                System.out.println("MOUSE PRESSED!!!");
            }
        });

    }

    @FXML
    private void showUsers(ActionEvent evt) {

        fillAllUsers();
    }

    @FXML
    private void cancelInsert(ActionEvent evt) {

        cancelInsert2();

    }

    @FXML
    private void closeDoneBut(ActionEvent evt) {
        hideComponentsForPassGen();
    }

    private void hideComponentsForPassGen() {
        butDone.setVisible(false);
        txtGenratedPass.setVisible(false);
        lblNewGenPass.setVisible(false);
    }

    @FXML
    private void newUser(ActionEvent evt) {
        setChekBoxesForInsert();

        lblSelectUser.setText("");
        setInsertListeners();
        showLabelsWhenInserting();
        showFiledsWhenInserting();
        txtName.requestFocus();
        lblInsertName.setText("<<< " + category2 + " new name");
        lblInsertUsername.setText("");
        clearFieldsForUpdating();
        showPassFields();
        showInsertCancelButtons();
        hideUpdateDeleteButtons();
        setOptionalLabels(category1);

    }

    @FXML
    public void changePassword(ActionEvent evt) {

        if (usrMod == null) {
            popMsg.WarningMessage("Select User first");
            return;
        }

        if (txtOldPass.getText().trim().length() == 0 || txtNewPass.getText().trim().length() == 0 || txtConfirmPass.getText().trim().length() == 0) {
            popMsg.WarningMessage("You must enter all 3 fields in order to change password");
            return;
        }

        try {
            if (!passg.check(txtOldPass.getText(), usrMod.getPassword())) {
                popMsg.WarningMessage("Old password doesn't match the one stored in database");
                return;
            }
        } catch (Exception ex) {
            Logger.getLogger(UserControl.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (!txtNewPass.getText().equals(txtConfirmNewPass.getText())) {
            popMsg.WarningMessage("Please make sure that new password is same as confirmed one");
            return;
        }

        //ovdje još mogu doći kontrole
        //kvalitete lozinke
        //min 8 znakova, mala velika slova, brojevi i interpukcijski znakovi
        //sve ok za promjeniti u bazi
        choiceAct = popMsg.alertDialogUpdate();

        usrCon = new UserController();

        updateUserPass(usrMod);

        if (choiceAct == 1) {
            if (usrCon.updateUser(usrMod)) {
                popMsg.updateMessageOk();

            } else {
                popMsg.updateMessageNotOk();
                return;
            }
        }


        /*
         if (usrCon.updateUser(usrMod)) {
         popMsg.updateMessageOk();
         } else {
         popMsg.updateMessageNotOk();
         return;
         }
         */
    }

    @FXML
    public void resetPass(ActionEvent evt) {
        if (usrMod == null) {
            popMsg.WarningMessage("Select User first");
            return;
        }

        choiceAct = popMsg.alertDialogInsertAndGenerateNewPass();

        usrCon = new UserController();

        updateGeneratedUserPass(usrMod);

        if (choiceAct == 1) {
            if (usrCon.updateUser(usrMod)) {
                popMsg.updateMessageOk();

            } else {
                popMsg.updateMessageNotOk();
                return;
            }
        } else {
            hideComponentsForPassGen();
        }

    }

    private boolean controlOfInputForInsert() {

        return conInp.chekLettersAndNumbers(txtName, msgName) && conInp.chekLettersAndNumbers(txtUsername, msgUsername) && conInp.chekPassword(txtPassword, msgPass) && conInp.chekPassword(txtConfirmPass, msgPass);

    }

    private boolean controlOfInputForUpdate() {

        return conInp.chekLettersAndNumbers(txtName, msgName) && conInp.chekLettersAndNumbers(txtUsername, msgUsername);

    }

    private void getEmployee() {
        userId = MainFormController.tempEmpId;
        System.out.println("Id is = " + userId);

    }

    private void activateNameListener(String category) {
        txtName.focusedProperty().addListener((observable, oldValue, newValue) -> {

            //fieldNameInsertListeners(category);
        });

        txtName.textProperty().addListener((observable, oldValue, newValue) -> {
            fieldNameInsertListeners(category);
        });
    }

    private void activateUsernameListener(String category) {
        txtUsername.focusedProperty().addListener((observable, oldValue, newValue) -> {

            //fieldUserNameInsertListeners(category);
        });

        txtUsername.textProperty().addListener((observable, oldValue, newValue) -> {

            fieldUserNameInsertListeners(category);

        });
    }

    private void activatePasswordListener(String category) {
        txtPassword.focusedProperty().addListener((observable, oldValue, newValue) -> {
            //fieldPasswordInsertListeners(category);
        });

        txtPassword.textProperty().addListener((observable, oldValue, newValue) -> {
            fieldPasswordInsertListeners(category);
        });
    }

    private void activateConfirmPasswordListener(String category) {
        txtConfirmPass.focusedProperty().addListener((observable, oldValue, newValue) -> {

            //fieldConfirmPasswordInsertListeners(category);
        });

        txtConfirmPass.textProperty().addListener((observable, oldValue, newValue) -> {

            fieldConfirmPasswordInsertListeners(category);

        });
    }

    private void fillAllUsers() {
        usrCon = new UserController();
        ObservableList<User> listOfUserModels = FXCollections.observableArrayList();
        listOfUserModels = usrCon.getUsersObsv();
        tbName.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        tbUsername.setCellValueFactory(new PropertyValueFactory<User, String>("userName"));

        tbAdmin.setCellValueFactory(new PropertyValueFactory<User, String>("admin"));
        tbActive.setCellValueFactory(new PropertyValueFactory<User, String>("active"));
        tableUser.setItems(listOfUserModels);
    }

    private void fillUser(User usr) {
        ObservableList<User> listOfUserModels = FXCollections.observableArrayList();
        listOfUserModels.add(usr);
        System.out.println("List user " + usr.getName());
        tbName.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        tbUsername.setCellValueFactory(new PropertyValueFactory<User, String>("userName"));

        tbAdmin.setCellValueFactory(new PropertyValueFactory<User, String>("admin"));
        tbActive.setCellValueFactory(new PropertyValueFactory<User, String>("active"));
        tableUser.setItems(listOfUserModels);
    }

    private void selectUserFromTable() {

        tableUser.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            //Check whether item is selected and set value of selected item to Label

            if (tableUser.getSelectionModel().getSelectedItem() != null) {
                String tempEmp = null;
                tempEmp = tableUser.getSelectionModel().getSelectedItem().getName();
                System.out.println("User " + tempEmp);
                //usrMod = tableUser.getSelectionModel().getSelectedItem();

                //fillUser(usrMod);
                usrMod = usrCon.getUsers(tempEmp);
                setTempUser(usrMod);
                //comboSearchOwner.valueProperty().set(null);
                BigDecimal tempId = null;
                //containsOwnerId(ownerList, usrMod.getOwner_id());
                System.out.println("User name =  " + usrMod.getAdmin());
                clearPasswordMenagmentFields();
                setUpdateListeners();
                showFiledsWhenUpdateing();
                labelsWhenUpdating();
                showUpdateDeleteButtons();
                loadUserFields(getTempUser());
                hideInsertCancelButtons();
                hidePassFields();
                setPassFieldsForUpdate();
                //setUpdateListeners();
                lblEditMsg.setText("You are working now with user " + usrMod.getName());
                lblEditMsg2.setText("for " + usrMod.getName());

            }

        });

    }
    /*
     public boolean contains1OwnerId(ObservableList<OwnerModel> list, BigDecimal usrId) {
     if (list.stream().filter(o -> o.getId().equals(usrId)).findFirst().isPresent());

     }
     */
    /*
     public void containsOwnerId(ObservableList<OwnerModel> list, BigDecimal usrId) {
     list.stream().filter(o -> o.getId().equals(usrId)).forEach(
     o -> {
     System.out.println("Owner name iter " + o.getOwnerName());
     comboSearchOwner.getSelectionModel().select(o);
     }
     );
     }
     */

    private void loadTableUserFields(User usr) {

    }

    //method for hiding text fields and labels related to password
    private void hidePassFields() {
        txtPassword.setVisible(false);
        lblPassword.setVisible(false);
        txtConfirmPass.setVisible(false);
        lblConfirmPass.setVisible(false);
    }

    //method for showing text fields and labels related to password
    private void showPassFields() {
        txtPassword.setVisible(true);
        lblPassword.setVisible(true);
        txtConfirmPass.setVisible(true);
        lblConfirmPass.setVisible(true);
    }

    private void hideUpdateDeleteButtons() {
        butUpdate.setVisible(false);
        butDelete.setVisible(false);

    }

    private void showUpdateDeleteButtons() {
        butUpdate.setVisible(true);
        butDelete.setVisible(true);
    }

    private void hideInsertCancelButtons() {
        butInsert.setVisible(false);
        butCancel.setVisible(false);
    }

    private void showInsertCancelButtons() {
        butInsert.setVisible(true);
        butCancel.setVisible(true);
    }

    private void fieldNameInsertListeners(String category) {

        ///1 not ok
        fieldConditions(category);

    }

    private void fieldUserNameInsertListeners(String category) {

        fieldConditions(category);

    }

    private void fieldPasswordInsertListeners(String category) {

        fieldConditions(category);

    }

    private void fieldConfirmPasswordInsertListeners(String category) {

        fieldConditions(category);

    }

    //cheking if user fields are set for updateing, if so, only name and username fields are in the process of 
    @FXML
    private void activeYes(ActionEvent evt) {
        if (butActiveYes.isSelected()) {
            butActiveNo.setSelected(false);
        }
    }

    @FXML
    private void activeNo(ActionEvent evt) {
        if (butActiveNo.isSelected()) {
            butActiveYes.setSelected(false);
        }
    }

    @FXML
    private void employeeYes(ActionEvent evt) {
        if (butEmployeeYes.isSelected()) {
            butEmployeeNo.setSelected(false);
        }
    }

    @FXML
    private void employeeNo(ActionEvent evt) {
        if (butEmployeeNo.isSelected()) {
            butEmployeeYes.setSelected(false);
        }
    }

    @FXML
    private void adminYes(ActionEvent evt) {
        if (butAdminYes.isSelected()) {
            butAdminNo.setSelected(false);
        }
    }

    @FXML
    private void adminNo(ActionEvent evt) {
        if (butAdminNo.isSelected()) {
            butAdminYes.setSelected(false);
        }
    }

    public String getTempCategory() {
        return tempCategory;
    }

    public void setTempCategory(String tempCategory) {
        this.tempCategory = tempCategory;
    }

    private void setOptionalLabels(String category) {
        lblInsertActive.setText("<<< Optional ");
        lblInsertEmployee.setText("<<< Optional ");

    }

    private void removeOptionalLabels() {
        lblInsertActive.setText("");
        lblInsertEmployee.setText("");

    }

    private void setUpdateListeners() {
        activateNameListener(category1);
        activateUsernameListener(category1);
        activatePasswordListener(category1);
        activateConfirmPasswordListener(category1);
    }

    private void setListeners() {
        activateNameListener(getTempCategory());
        activateUsernameListener(getTempCategory());
        activatePasswordListener(getTempCategory());
        activateConfirmPasswordListener(getTempCategory());
    }

    private void setInsertListeners() {
        activateNameListener(category2);
        activateUsernameListener(category2);
        activatePasswordListener(category2);
        activateConfirmPasswordListener(category2);
    }

    private void labelsWhenUpdating() {
        lblInsertPass.setVisible(false);
        lblInsertConfirmPass.setVisible(false);
        lblInsertName.setVisible(true);
        lblInsertUsername.setVisible(true);
        lblEmployeeName.setVisible(true);
        lblUserName.setVisible(true);
        lblSelectUser.setText("");
        lblEditMsg2.setVisible(true);
        //lblInsertUsername.setText("");
        //lblInsertName.setText("");
    }

    private void hideAllLabels() {
        lblInsertName.setVisible(false);
        lblInsertUsername.setVisible(false);
        lblInsertPass.setVisible(false);
        lblInsertConfirmPass.setVisible(false);

        lblEmployeeName.setVisible(false);
        lblUserName.setVisible(false);
        lblEditMsg2.setVisible(false);
    }

    private void showLabelsWhenInserting() {
        lblInsertName.setVisible(true);
        lblInsertUsername.setVisible(true);
        lblInsertPass.setVisible(true);
        lblInsertConfirmPass.setVisible(true);
        lblEditMsg2.setVisible(true);
    }

    private void showFiledsWhenInserting() {
        txtName.setVisible(true);
        txtUsername.setVisible(true);
        txtPassword.setVisible(true);
        txtConfirmPass.setVisible(true);
    }

    private void showFiledsWhenUpdateing() {
        txtName.setVisible(true);
        txtUsername.setVisible(true);

    }

    private void clearFieldsForUpdating() {
        txtName.setText("");
        txtUsername.setText("");
        txtPassword.setText("");
        txtConfirmPass.setText("");
    }

    private void cancelInsert2() {
        //txtPassword.requestFocus();
        //removeTextFieldListeners();
        setPassFieldsForUpdate();
        clearFieldsForUpdating();
        hidePassFields();
        hideUserFields();
        hideInsertCancelButtons();
        hideAllLabels();
        removeOptionalLabels();
        //showUpdateDeleteButtons();
        butActiveYes.setSelected(false);
        lblSelectUser.setText("<<< Select User");
    }

    private void setPassFieldsForUpdate() {
        txtPassword.setText("test");
        txtConfirmPass.setText("test");
    }

    private void hideUserFields() {
        txtName.setVisible(false);
        txtUsername.setVisible(false);
    }

    private void fieldConditions(String category) {

        try {
            if (txtName.getText().length() > 0 & txtUsername.getText().length() == 0) {
                //txtUsername.requestFocus();
                lblInsertName.setText("");
                lblInsertUsername.setText("<<< " + category + " Username");
                lblInsertPass.setText("");
                lblInsertConfirmPass.setText("");
            }

            if (txtName.getText().length() == 0 & txtUsername.getText().length() > 0) {
                //txtUsername.requestFocus();
                lblInsertName.setText("<<< " + category + " name");
                lblInsertUsername.setText("");
                lblInsertPass.setText("");
                lblInsertConfirmPass.setText("");
            }

            //1 out of 3 not ok
            //1
            if (txtName.getText().length() == 0 & txtUsername.getText().length() > 0 & txtPassword.getText().length() > 0) {
                //txtUsername.requestFocus();
                lblInsertName.setText("<<< " + category + " name");
                lblInsertUsername.setText("");
                lblInsertPass.setText("");
                lblInsertConfirmPass.setText("");
            }

            //2
            if (txtName.getText().length() > 0 & txtUsername.getText().length() == 0 & txtPassword.getText().length() > 0) {
                //txtUsername.requestFocus();
                lblInsertName.setText("");
                lblInsertUsername.setText("<<< " + category + " Username");
                lblInsertPass.setText("");
                lblInsertConfirmPass.setText("");
            }

            //3
            if (txtName.getText().length() > 0 & txtUsername.getText().length() > 0 & txtPassword.getText().length() == 0) {
                //txtUsername.requestFocus();
                lblInsertName.setText("");
                lblInsertUsername.setText("");
                lblInsertPass.setText("<<< " + category + " Password");
                lblInsertConfirmPass.setText("");
            }

            //2 out of 3 not ok
            //1,2
            if (txtName.getText().length() == 0 & txtUsername.getText().length() == 0 & txtPassword.getText().length() > 0) {
                //txtUsername.requestFocus();
                lblInsertName.setText("<<< " + category + " name");
                lblInsertUsername.setText("");
                lblInsertPass.setText("");
                lblInsertConfirmPass.setText("");
            }

            //1,3
            if (txtName.getText().length() == 0 & txtUsername.getText().length() > 0 & txtPassword.getText().length() == 0) {
                //txtUsername.requestFocus();
                lblInsertName.setText("<<< " + category + " name");
                lblInsertUsername.setText("");
                lblInsertPass.setText("");
                lblInsertConfirmPass.setText("");
            }

            //2,3
            if (txtName.getText().length() > 0 & txtUsername.getText().length() == 0 & txtPassword.getText().length() == 0) {
                //txtUsername.requestFocus();
                lblInsertName.setText("");
                lblInsertUsername.setText("<<< " + category + " Username");
                lblInsertPass.setText("");
                lblInsertConfirmPass.setText("");
            }

            //1 out of 4 not ok
            //1 
            if (txtName.getText().length() == 0 & txtUsername.getText().length() > 0 & txtPassword.getText().length() > 0 & txtConfirmPass.getText().length() > 0) {
                lblInsertName.setText("<<< " + category + " name");
                lblInsertUsername.setText("");
                lblInsertPass.setText("");
                lblInsertConfirmPass.setText("");
            }
            //2
            if (txtName.getText().length() > 0 & txtUsername.getText().length() == 0 & txtPassword.getText().length() > 0 & txtConfirmPass.getText().length() > 0) {
                lblInsertName.setText("");
                lblInsertUsername.setText("<<< " + category + " Username");
                lblInsertPass.setText("");
                lblInsertConfirmPass.setText("");
            }

            //3
            if (txtName.getText().length() > 0 & txtUsername.getText().length() > 0 & txtPassword.getText().length() == 0 & txtConfirmPass.getText().length() > 0) {
                lblInsertName.setText("");
                lblInsertUsername.setText("");
                lblInsertPass.setText("<<< " + category + " Password");
                lblInsertConfirmPass.setText("");
            }

            //4
            if (txtName.getText().length() > 0 & txtUsername.getText().length() > 0 & txtPassword.getText().length() > 0 & txtConfirmPass.getText().length() == 0) {
                lblInsertName.setText("");
                lblInsertUsername.setText("");
                lblInsertPass.setText("");
                lblInsertConfirmPass.setText("<<< Confirm Password");
            }

            ///2 out of 4 not ok
            //2,4
            if (txtName.getText().length() > 0 & txtUsername.getText().length() == 0 & txtPassword.getText().length() > 0 & txtConfirmPass.getText().length() == 0) {
                lblInsertName.setText("");
                lblInsertUsername.setText("<<< " + category + " Username");
                lblInsertPass.setText("");
                lblInsertConfirmPass.setText("");
            }
            //3,4
            if (txtName.getText().length() > 0 & txtUsername.getText().length() > 0 & txtPassword.getText().length() == 0 & txtConfirmPass.getText().length() == 0) {
                lblInsertName.setText("");
                lblInsertUsername.setText("");
                lblInsertPass.setText("<<< " + category + " Password");
                lblInsertConfirmPass.setText("");
            }

            //1,2
            if (txtName.getText().length() == 0 & txtUsername.getText().length() == 0 & txtPassword.getText().length() > 0 & txtConfirmPass.getText().length() > 0) {
                lblInsertName.setText("<<< " + category + " name");
                lblInsertUsername.setText("");
                lblInsertPass.setText("");
                lblInsertConfirmPass.setText("");
            }
            //1,3
            if (txtName.getText().length() == 0 & txtUsername.getText().length() > 0 & txtPassword.getText().length() == 0 & txtConfirmPass.getText().length() > 0) {
                lblInsertName.setText("<<< " + category + " name");
                lblInsertUsername.setText("");
                lblInsertPass.setText("");
                lblInsertConfirmPass.setText("");
            }
            //1,4
            if (txtName.getText().length() == 0 & txtUsername.getText().length() > 0 & txtPassword.getText().length() > 0 & txtConfirmPass.getText().length() == 0) {
                lblInsertName.setText("<<< " + category + " name");
                lblInsertUsername.setText("");
                lblInsertPass.setText("");
                lblInsertConfirmPass.setText("");
            }

            ///3 out of 4 not ok
            //2,3,4
            if (txtName.getText().length() > 0 & txtUsername.getText().length() == 0 & txtPassword.getText().length() == 0 & txtConfirmPass.getText().length() == 0) {
                lblInsertName.setText("");
                lblInsertUsername.setText("<<< " + category + " Username");
                lblInsertPass.setText("");
                lblInsertConfirmPass.setText("");
            }

            //1,3,4
            if (txtName.getText().length() == 0 & txtUsername.getText().length() > 0 & txtPassword.getText().length() == 0 & txtConfirmPass.getText().length() == 0) {
                lblInsertName.setText("<<< " + category + " name");
                lblInsertUsername.setText("");
                lblInsertPass.setText("");
                lblInsertConfirmPass.setText("");
            }

            //1,2,4
            if (txtName.getText().length() == 0 & txtUsername.getText().length() == 0 & txtPassword.getText().length() > 0 & txtConfirmPass.getText().length() == 0) {
                lblInsertName.setText("<<< " + category + " name");
                lblInsertUsername.setText("");
                lblInsertPass.setText("");
                lblInsertConfirmPass.setText("");
            }

            //all ok
            if (txtName.getText().length() > 0 & txtUsername.getText().length() > 0 & txtPassword.getText().length() > 0 & txtConfirmPass.getText().length() > 0) {
                setOptionalLabels(category);
                lblInsertName.setText("");
                lblInsertUsername.setText("");
                lblInsertPass.setText("");
                lblInsertConfirmPass.setText("");
                lblFinish.setText("Press " + category + " if ready >>>");
            }

            //one of fields empty
            if (txtName.getText().length() == 0 | txtUsername.getText().length() == 0 | txtPassword.getText().length() == 0 | txtConfirmPass.getText().length() == 0) {
                //lblInsertName.setText("<<< " + category + " name");
                lblFinish.setText("");
                removeOptionalLabels();

            }

            //all fields empty
            if (txtName.getText().length() == 0 & txtUsername.getText().length() == 0 & txtPassword.getText().length() == 0 & txtConfirmPass.getText().length() == 0) {
                lblInsertName.setText("<<< " + category + " name");
                lblInsertUsername.setText("");
                lblInsertPass.setText("");
                lblInsertConfirmPass.setText("");
                removeOptionalLabels();

            }
        } catch (Exception e) {

        }

    }
    /*
     private void comboSearchOwner() {

     comboSearchOwner.valueProperty().addListener(new ChangeListener<OwnerModel>() {
     @Override
     public void changed(ObservableValue ov, OwnerModel t, OwnerModel t1) {
     try {
     String tempMod = null;
     tempMod = comboSearchOwner.getSelectionModel().getSelectedItem().toString();
     ownMod = ownCon.getOwnerByName(tempMod);

     System.out.println("Owner name =  " + ownMod.getOwnerName());

     } catch (Exception e) {
     ownMod = null;
     }
     System.out.println(ov);
     System.out.println(t);
     System.out.println(t1);

     }
     });

     comboSearchOwner.getEditor().setOnMouseClicked(new EventHandler<MouseEvent>() {
     @Override
     public void handle(MouseEvent event) {
     try {
     String tempMod = null;
     tempMod = comboSearchOwner.getSelectionModel().getSelectedItem().toString();
     ownMod = ownCon.getOwnerByName(tempMod);

     System.out.println("Owner name =  " + ownMod.getOwnerName());

     } catch (Exception e) {

     }
     System.out.println("MOUSE PRESSED!!!");
     }
     });
     }
     */
    /*
     private void fillOwners() {
     ownCon = new OwnerController();
     ownerList = ownCon.getListOfOwners();
     FxUtil.autoCompleteComboBox(comboSearchOwner, FxUtil.AutoCompleteMode.CONTAINING, ownerList);

     }
     */

    private void clearPasswordMenagmentFields() {
        txtNewPass.setText("");
        txtOldPass.setText("");
        txtConfirmNewPass.setText("");
    }

    private void setChekBoxesForInsert() {
        butActiveYes.setSelected(true);
        butActiveNo.setSelected(false);
        butAdminYes.setSelected(false);
        butAdminNo.setSelected(false);

    }

    public User getTempUser() {
        return tempUser;
    }

    public void setTempUser(User tempUser) {
        this.tempUser = tempUser;
    }

}
