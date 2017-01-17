/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accountAppView;

import accountAppCon.AccountController;
import accountAppMod.Account;
import accountAppUtil.ChargingSystem;
import accountAppUtil.ControlOfInput;
import accountAppUtil.Decrypter;
import accountAppUtil.FxUtil;
import accountAppUtil.HelpMethods;
import accountAppUtil.PopUpMessages;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author Deamon
 */
public class MainFormController implements Initializable  {

    @FXML
    public Label lblmain;
    @FXML
    public Label lblSelectAccount;
    @FXML
    public Label lblEditMsg;
    @FXML
    public Label lblActive;
    @FXML
    public Pane paneActive;
    @FXML
    public Pane userPane1;
    @FXML
    public Pane userPane2;
    @FXML
    public Label lblInsertNumber;
    @FXML
    public Label lblNumber;
    @FXML
    public Label lblTypeOfNumber;
    @FXML
    public Label lblCurrency;
    @FXML
    public Label lblRecharge;
    @FXML
    public Pane accountPane;
    @FXML
    public Label lblInsertType;
    @FXML
    public Label lblInsertActive;
    @FXML
    public Label lblInsertCurrency;
    @FXML
    public Label lblInsertRecharge;
    @FXML
    public MenuItem employeeMenu;
    @FXML
    public TextField txtEmployee;
    @FXML
    public TextField sendSms1;
    @FXML
    public TextField sendSms2;
    
    @FXML
    public ComboBox comboAccSearch;
    @FXML
    public ComboBox comboType;
    @FXML
    public ComboBox comboCurrency;
    @FXML
    public ComboBox comboRecharge;

    @FXML
    private CheckBox butActiveYes;
    @FXML
    private CheckBox butActiveNo;
    @FXML
    private Button butInsert;
    @FXML
    private Button butUpdate;
    @FXML
    private Button butCancel;
    @FXML
    public Button butSend1;
    @FXML
    public Button butStopSms;
    @FXML
    public Button butSend2;
    @FXML
    private Button butRecharge;
    @FXML
    private Button butDelete;
    @FXML
    public Button showAccounts;
    @FXML
    public Button butSimulation;
    @FXML
    public Button butNewAcc;
    @FXML
    public Button butExit;
    @FXML
    public Button butStartSimulation;
    @FXML
    public TextField txtNumber;
    @FXML
    public TextArea txtaStatus;
    @FXML
    public TextArea chat1;
    @FXML
    public TextArea chat2;

    public Account getTempAcc() {
        return tempAcc;
    }

    public void setTempAcc(Account tempAcc) {
        this.tempAcc = tempAcc;
    }

    private Account tempAcc;
    private ObservableList<String> listOfTypes;
    private ObservableList<String> listOfCurrencys;
    private ObservableList<String> listOfKeys;
    private ObservableList<String> listOfVauchers;

    private String category1 = "Insert";
    private String category2 = "Update";
    private String tempCategory;

    //table Account
    @FXML
    private TableView<Account> tableAcc;
    @FXML
    private TableColumn<Account, String> tbNumber;
    @FXML
    private TableColumn<Account, String> tbKeytype;
    @FXML
    private TableColumn<Account, String> tbCurrency;
    @FXML
    private TableColumn<Account, String> tbActive;
    @FXML
    private TableColumn<Account, String> tbBalance;

    private Account acc;
    private HelpMethods helpMe;
    private ChargingSystem chSys;
    private FxUtil fx;
    private PopUpMessages popMsg;
    private AccountController accCon;
    private Decrypter decr;
    private Integer choiceAct;
    private ObservableList<String> listOfAccounts;
    private ControlOfInput conInp;
    //private BigDecimal empId;
    public static BigDecimal tempEmpId = null;
    public static String tempEmpName;

    private String msgMsisdn = "Insert Msisdn number in format like +447735234817 ";
    private String msgImei = "Insert Imei number in format like 145125612314789 (15 digits) ";
    private String msgImsi = "Insert Imsi number in format like 789455612312289 (15 digits) ";

    /**
     * Initializes the controller class.
     *
     */
    @FXML
    public void initialize(URL url, ResourceBundle rb) {

        getEmployee();
        fx = new FxUtil();
        comboSearchAccount();
        activateInsertListener(category1);
        selectAccFromTable();
        fillType();
        fillBalance();
        fillKeys();
        fillCurrency();
        setTempCategory(category1);
        hideCombos();
        hidePane();
        hideAllLabels();
        hideUserFields();
        hideInsertCancelButtons();
        hideUpdateDeleteButtons();

        lblSelectAccount.setText("<<< Search for Account");
        //comboRecharge.getSelectionModel().getSelectedItem().toString().equals("50");

        // comboSearchOwner();
        selectAccFromTable();

    }

    private void fillKeys() {
        accCon = new AccountController();
        fx = new FxUtil();
        ArrayList<String> list = new ArrayList<>();
        list = accCon.getKey();
        //ObservableList<Stavka> oListStavaka = FXCollections.observableArrayList(listStavaka);
        listOfKeys = FXCollections.observableArrayList(list);
        //new AutoCompleteComboBoxListener(comboUserSearch, listOfEmployees);

        fx.autoCompleteComboBox(comboAccSearch, FxUtil.AutoCompleteMode.CONTAINING, listOfKeys);
    }

    @FXML
    public void showAccounts(ActionEvent evt) {
        fillAllAccounts();
    }

    public MainFormController() {
        popMsg = new PopUpMessages();
        accCon = new AccountController();
        conInp = new ControlOfInput();

        decr = new Decrypter();
        helpMe = new HelpMethods();
        //sdf = new SimpleDateFormat("dd.MM.yyyy");
        this.acc = acc;

        // tiCon = new TicketController();
    }

    @FXML
    public void insertVaucher(ActionEvent evt) {

        if (acc == null) {
            popMsg.InformationMessage("You need to select account first");
            return;
        }

        choiceAct = popMsg.alertVaucherUpdate(acc.getKey());

        createVaucher(acc);
        if (choiceAct == 1) {
            if (accCon.updateAccount(acc)) {
                popMsg.updateMessageOk();
                //removeUsers();
                //               String tempEmp = null;
//                tempEmp = comboUserSearch.getSelectionModel().getSelectedItem().toString();
                //tempUser = usrCon.getUsersById(usrMod.getIds());
                fillAccount(acc);
                fillAllAccounts();

            } else {
                popMsg.updateMessageNotOk();
            }
        }
    }

    @FXML
    public void insertAccount(ActionEvent evt) {
        if (comboType.getSelectionModel().getSelectedItem().toString().equals(null)) {
            popMsg.InformationMessage("You need to select type of the account first");
        }

        if (comboType.getSelectionModel().getSelectedItem().toString().equals("MSISDN")) {
            if (!controlOfInputForInsertMsisdn()) {
                return;
            }
        }

        if (comboType.getSelectionModel().getSelectedItem().toString().equals("IMSI")) {
            if (!controlOfInputForInsertImsi()) {
                return;
            }
        }

        if (comboType.getSelectionModel().getSelectedItem().toString().equals("IMEI")) {
            if (!controlOfInputForInsertImei()) {
                return;
            }
        }

        choiceAct = popMsg.alertDialogInsert();
        /*
         if (accCon.chekUserName(this.txtNumber.getText())) {
         popMsg.WarningMessage("There is already User with same username");
         return;
         }
         */
        acc = new Account();

        createAccount(acc);

        if (choiceAct == 1) {
            if (accCon.insertAccount(acc)) {
                popMsg.insertMessageOk();
                fillAccount(acc);
                fillAllAccounts();
            } else {
                popMsg.insertMessageNotOk();
            }
        }

    }

    @FXML
    public void updateAccount(ActionEvent evt) {

        if (acc == null) {
            popMsg.InformationMessage("You need to select user first");
            return;
        }

        if (comboType.getSelectionModel().getSelectedItem().toString().equals(null)) {
            popMsg.InformationMessage("You need to select type of the account first");
        }

        if (comboType.getSelectionModel().getSelectedItem().toString().equals("MSISDN")) {
            if (!controlOfInputForInsertMsisdn()) {
                return;
            }
        }

        if (comboType.getSelectionModel().getSelectedItem().toString().equals("IMSI")) {
            if (!controlOfInputForInsertImsi()) {
                return;
            }
        }

        if (comboType.getSelectionModel().getSelectedItem().toString().equals("IMEI")) {
            if (!controlOfInputForInsertImei()) {
                return;
            }
        }

        choiceAct = popMsg.alertDialogUpdate();

        updateAccount(acc);
        if (choiceAct == 1) {
            if (accCon.updateAccount(acc)) {
                popMsg.updateMessageOk();
                //removeUsers();
                //               String tempEmp = null;
//                tempEmp = comboUserSearch.getSelectionModel().getSelectedItem().toString();
                //tempUser = usrCon.getUsersById(usrMod.getIds());
                fillAccount(acc);
                fillAllAccounts();

            } else {
                popMsg.updateMessageNotOk();
            }
        }
    }

    @FXML
    public void deleteAccount(ActionEvent evt
    ) {

        if (acc == null) {
            popMsg.InformationMessage("Select account first");
        }

        choiceAct = popMsg.alertDialogDelete();

        if (choiceAct == 1) {
            if (accCon.deleteAccount(acc)) {
                popMsg.deleteMessageOk();
                fillAllAccounts();
                //fillAccount(acc);

            } else {
                popMsg.deleteMessageNotOk();
            }
        }
    }

    private void createAccount(Account acc) {
        //HelpMethods.dateControl(tempDate);
        acc.setKey(txtNumber.getText());

        if (comboType.getSelectionModel().getSelectedItem().toString().equals("MSISDN")) {
            acc.setKeyType(1);
        }

        if (comboType.getSelectionModel().getSelectedItem().toString().equals("IMSI")) {
            acc.setKeyType(2);
        }

        if (comboType.getSelectionModel().getSelectedItem().toString().equals("IMEI")) {
            acc.setKeyType(3);
        }

        if (comboCurrency.getSelectionModel().getSelectedItem().toString().equals("HRK")) {
            acc.setCurrency(1);
        }

        if (comboCurrency.getSelectionModel().getSelectedItem().toString().equals("EURO")) {
            acc.setCurrency(2);
        }

        acc.setInitbalance(new BigDecimal(50));
        acc.setMinbalance(new BigDecimal(0));

        if (butActiveYes.isSelected()) {

            acc.setActive(1);
        }

        if (butActiveNo.isSelected()) {

            acc.setActive(0);
        }

    }

    private void updateAccount(Account acc) {

        acc.setKey(txtNumber.getText());

        if (comboType.getSelectionModel().getSelectedItem().toString().equals("MSISDN")) {
            acc.setKeyType(1);
        }

        if (comboType.getSelectionModel().getSelectedItem().toString().equals("IMSI")) {
            acc.setKeyType(2);
        }

        if (comboType.getSelectionModel().getSelectedItem().toString().equals("IMEI")) {
            acc.setKeyType(3);
        }

        if (comboCurrency.getSelectionModel().getSelectedItem().toString().equals("HRK")) {
            acc.setCurrency(1);
        }

        if (comboCurrency.getSelectionModel().getSelectedItem().toString().equals("EURO")) {
            acc.setCurrency(2);
        }

        if (butActiveYes.isSelected()) {

            acc.setActive(1);
        }

        if (butActiveNo.isSelected()) {

            acc.setActive(0);
        }

    }

    private void createVaucher(Account acc) {

        if (comboRecharge.getSelectionModel().equals(null)) {
            acc.setInitbalance(acc.getInitbalance());
        }

        if (comboRecharge.getSelectionModel().getSelectedItem().toString().equals("25")) {
            acc.setInitbalance(new BigDecimal(25).add(acc.getInitbalance()));
        }
        if (comboRecharge.getSelectionModel().getSelectedItem().toString().equals("50")) {
            acc.setInitbalance(new BigDecimal(50).add(acc.getInitbalance()));
        }
        if (comboRecharge.getSelectionModel().getSelectedItem().toString().equals("100")) {
            acc.setInitbalance(new BigDecimal(100).add(acc.getInitbalance()));
        }

    }

    private boolean controlOfInputForInsertMsisdn() {

        return conInp.chekMsisdn(txtNumber, msgMsisdn); //&& conInp.chekLettersAndNumbers(txtUsername, msgUsername) && conInp.chekPassword(txtPassword, msgPass) && conInp.chekPassword(txtConfirmPass, msgPass);

    }

    private boolean controlOfInputForInsertImei() {

        return conInp.chekImsiOrImei(txtNumber, msgImei); //&& conInp.chekLettersAndNumbers(txtUsername, msgUsername) && conInp.chekPassword(txtPassword, msgPass) && conInp.chekPassword(txtConfirmPass, msgPass);

    }

    private boolean controlOfInputForInsertImsi() {

        return conInp.chekImsiOrImei(txtNumber, msgImsi); //&& conInp.chekLettersAndNumbers(txtUsername, msgUsername) && conInp.chekPassword(txtPassword, msgPass) && conInp.chekPassword(txtConfirmPass, msgPass);

    }

    private boolean controlOfInputForUpdate() {

        return conInp.chekMsisdn(txtNumber, msgMsisdn); //&& conInp.chekLettersAndNumbers(txtUsername, msgUsername);

    }

    @FXML
    public void activeYes(ActionEvent evt) {
        if (butActiveYes.isSelected()) {
            butActiveNo.setSelected(false);
        }
    }

    @FXML
    public void activeNo(ActionEvent evt) {
        if (butActiveNo.isSelected()) {
            butActiveYes.setSelected(false);
        }
    }

    @FXML
    public void cancelInsert(ActionEvent evt) {
        cancelInsert2();
    }

    @FXML
    public void openEmployeeMenu(ActionEvent menEv) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("UserControl.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("User Control");
        stage.show();
    }

    @FXML
    private void comboSearchAccount() {

        comboAccSearch.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                try {
                    String tempEmp = null;
                    tempEmp = comboAccSearch.getSelectionModel().getSelectedItem().toString();
                    setTempCategory(category2);
                    acc = accCon.getAccounts(tempEmp);
                    showCombos();
                    showPane();
                    //setTempAcc(tempAcc);
                    //containsOwnerId(ownerList, usrMod.getOwner_id());
                    setUpdateListeners();
                    labelsWhenUpdating();
                    showFiledsWhenUpdateing();
                    showUpdateDeleteButtons();
                    hideInsertCancelButtons();

                    //setPassFieldsForUpdate();
                    fillAccount(acc);

                    System.out.println("Acc number =  " + acc.getKey());
                    loadAccountFields(acc);
                    lblEditMsg.setText("You are working now with account " + acc.getKey());

                } catch (Exception e) {

                    acc = null;
                }
                System.out.println(ov);
                System.out.println(t);
                System.out.println(t1);

            }

            private void setTempUser(Account acc) {

            }
        });

        comboAccSearch.getEditor().setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {

                    String tempEmp = null;
                    tempEmp = comboAccSearch.getSelectionModel().getSelectedItem().toString();

                    acc = accCon.getAccounts(tempEmp);
                    setTempCategory(category2);
                    //setTempAcc(tempAcc);
                    //containsOwnerId(ownerList, usrMod.getOwner_id());
                    setUpdateListeners();
                    showCombos();
                    showPane();
                    labelsWhenUpdating();
                    showFiledsWhenUpdateing();
                    showUpdateDeleteButtons();
                    hideInsertCancelButtons();

                    //setPassFieldsForUpdate();
                    fillAccount(acc);

                    System.out.println("Acc number =  " + acc.getKey());
                    loadAccountFields(acc);
                    lblEditMsg.setText("You are working now with account " + acc.getKey());

                } catch (Exception e) {

                    acc = null;

                }
                System.out.println("MOUSE PRESSED!!!");
            }
        });

    }

    private void loadAccountFields(Account acc) {
        try {
            txtNumber.setText(acc.getKey());
        } catch (Exception e) {
            txtNumber.setText("");
        }

        if (acc.getKeyType() == 1) {
            comboType.getSelectionModel().select(1);
        }

        if (acc.getKeyType() == 1) {
            comboType.getSelectionModel().select(0);
        }

        if (acc.getKeyType() == 2) {
            comboType.getSelectionModel().select(1);
        }

        if (acc.getKeyType() == 3) {
            comboType.getSelectionModel().select(2);
        }

        if (acc.getCurrency() == 1) {
            comboCurrency.getSelectionModel().select(0);
        }

        if (acc.getCurrency() == 2) {
            comboCurrency.getSelectionModel().select(1);
        }

        try {
            if (acc.getActive() == 1) {
                butActiveYes.setSelected(true);
                butActiveNo.setSelected(false);
            } else {
                butActiveYes.setSelected(false);
                butActiveNo.setSelected(true);
            }

        } catch (Exception e) {
            butActiveYes.setSelected(false);
            butActiveNo.setSelected(false);
        }

    }

    private void fillAllAccounts() {
        accCon = new AccountController();
        ObservableList<Account> listOfAccountModels = FXCollections.observableArrayList();
        listOfAccountModels = accCon.getAccountsObsv();
        tbNumber.setCellValueFactory(new PropertyValueFactory<Account, String>("key"));
        tbKeytype.setCellValueFactory(new PropertyValueFactory<Account, String>("keytype"));

        tbCurrency.setCellValueFactory(new PropertyValueFactory<Account, String>("currency"));
        tbActive.setCellValueFactory(new PropertyValueFactory<Account, String>("active"));
        tbBalance.setCellValueFactory(new PropertyValueFactory<Account, String>("initbalance"));
        tableAcc.setItems(listOfAccountModels);
    }

    private void fillAccount(Account acc) {
        ObservableList<Account> listOfAccountModels = FXCollections.observableArrayList();
        listOfAccountModels.add(acc);
        System.out.println("List acc " + acc.getKey());
        tbNumber.setCellValueFactory(new PropertyValueFactory<Account, String>("key"));
        tbKeytype.setCellValueFactory(new PropertyValueFactory<Account, String>("keytype"));

        tbCurrency.setCellValueFactory(new PropertyValueFactory<Account, String>("currency"));
        tbActive.setCellValueFactory(new PropertyValueFactory<Account, String>("active"));
        tbBalance.setCellValueFactory(new PropertyValueFactory<Account, String>("initbalance"));
        tableAcc.setItems(listOfAccountModels);
    }

    private void selectAccFromTable() {

        tableAcc.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            //Check whether item is selected and set value of selected item to Label

            if (tableAcc.getSelectionModel().getSelectedItem() != null) {
                setTempCategory(category2);
                activateUpdatetListener(getTempCategory());
                String tempEmp = null;
                tempEmp = tableAcc.getSelectionModel().getSelectedItem().getKey();
                System.out.println("User " + tempEmp);
                //usrMod = tableUser.getSelectionModel().getSelectedItem();

                //fillUser(usrMod);
                acc = accCon.getAccounts(tempEmp);
                setTempAcc(acc);
                showPane();
                showCombos();
                labelsWhenUpdating();
                lblInsertNumber.setText("<<< " + category2 + " new number");
                //comboSearchOwner.valueProperty().set(null);
                BigDecimal tempId = null;
                //containsOwnerId(ownerList, usrMod.getOwner_id());
                System.out.println("Account key =  " + acc.getKey());
                setUpdateListeners();
                showFiledsWhenUpdateing();
                showUpdateDeleteButtons();
                loadAccountFields(getTempAcc());
                hideInsertCancelButtons();

                //setUpdateListeners();
                lblEditMsg.setText("You are working now with Account " + acc.getKey());

            }

        });

    }

    private void fillType() {

        ArrayList<String> list = new ArrayList<>();
        list.add("MSISDN");
        list.add("IMSI");
        list.add("IMEI");

        //ObservableList<Stavka> oListStavaka = FXCollections.observableArrayList(listStavaka);
        listOfTypes = FXCollections.observableArrayList(list);
        //new AutoCompleteComboBoxListener(comboUserSearch, listOfEmployees);
        comboType.getItems().addAll(listOfTypes);
    }

    private void fillBalance() {

        ArrayList<String> list = new ArrayList<>();
        list.add("25");
        list.add("50");
        list.add("100");

        //ObservableList<Stavka> oListStavaka = FXCollections.observableArrayList(listStavaka);
        listOfVauchers = FXCollections.observableArrayList(list);
        //new AutoCompleteComboBoxListener(comboUserSearch, listOfEmployees);
        comboRecharge.getItems().addAll(listOfVauchers);
    }

    private void fillCurrency() {

        ArrayList<String> list = new ArrayList<>();
        list.add("HRK");
        list.add("EURO");

        //ObservableList<Stavka> oListStavaka = FXCollections.observableArrayList(listStavaka);
        listOfCurrencys = FXCollections.observableArrayList(list);
        //new AutoCompleteComboBoxListener(comboUserSearch, listOfEmployees);
        comboCurrency.getItems().addAll(listOfCurrencys);
    }

    @FXML
    public void newAccount(ActionEvent evt) {
        setTempCategory(category1);
        setInsertListeners();
        labelsWhenInserting();
        activateInsertListener(getTempCategory());
        setChekBoxesForInsert();
        showCombos();
        showPane();
        showLabelsWhenInserting();
        showFiledsWhenInserting();
        comboRecharge.getSelectionModel().select(1);
        txtNumber.requestFocus();
        comboType.getSelectionModel().select(0);
        lblInsertNumber.setText("<<< " + category1 + " new number");
        lblInsertCurrency.setText("<<< " + category1 + " currency");
        lblInsertType.setText("<<< " + category1 + " type");
        lblInsertActive.setText("<<< " + category1 + " type");
        lblInsertRecharge.setText("<<< " + category1 + " new balance");

        clearFieldsForInserting();

        showInsertCancelButtons();
        hideUpdateDeleteButtons();

    }

    private void setChekBoxesForInsert() {
        butActiveYes.setSelected(true);
        butActiveNo.setSelected(false);

    }

    private void getEmployee() {
        tempEmpId = LoginController.tempEmpId;
        System.out.println("Id is = " + tempEmpId);
        tempEmpName = LoginController.tempEmpName;
        txtEmployee.setText(tempEmpName);
    }

    private void setInsertListeners() {
        activateInsertListener(category1);

    }

    private void setUpdateListeners() {
        activateInsertListener(category2);

    }

    private void labelsWhenUpdating() {

        lblInsertNumber.setVisible(true);
        lblInsertCurrency.setVisible(true);
        lblInsertActive.setVisible(true);
        lblInsertType.setVisible(true);
        lblNumber.setVisible(true);
        lblTypeOfNumber.setVisible(true);
        lblInsertType.setVisible(true);
        lblInsertCurrency.setVisible(true);
        lblCurrency.setVisible(true);
        lblRecharge.setVisible(true);
        lblActive.setVisible(true);

    }

    private void labelsWhenInserting() {

        lblInsertNumber.setVisible(true);
        lblInsertCurrency.setVisible(true);
        lblInsertActive.setVisible(true);
        lblInsertType.setVisible(true);
        lblNumber.setVisible(true);
        lblTypeOfNumber.setVisible(true);
        lblInsertType.setVisible(true);
        lblInsertCurrency.setVisible(true);
        lblCurrency.setVisible(true);
        lblRecharge.setVisible(true);
        lblActive.setVisible(false);
    }

    private void showFiledsWhenUpdateing() {
        txtNumber.setVisible(true);

    }

    private void showFiledsWhenInserting() {
        txtNumber.setVisible(true);

    }

    private void clearFieldsForInserting() {
        txtNumber.setText("");

    }

    private void hideAllLabels() {
        lblInsertNumber.setVisible(false);
        lblInsertCurrency.setVisible(false);
        lblInsertActive.setVisible(false);
        lblInsertType.setVisible(false);
        lblNumber.setVisible(false);
        lblTypeOfNumber.setVisible(false);
        lblInsertType.setVisible(false);
        lblInsertCurrency.setVisible(false);
        lblCurrency.setVisible(false);
        lblRecharge.setVisible(false);
        lblActive.setVisible(false);
        lblInsertRecharge.setVisible(false);

    }

    private void showCombos() {
        comboType.setVisible(true);
        comboCurrency.setVisible(true);
        comboRecharge.setVisible(true);
        butRecharge.setVisible(true);

    }

    private void hideCombos() {
        comboType.setVisible(false);
        comboCurrency.setVisible(false);
        comboRecharge.setVisible(false);
        butRecharge.setVisible(false);

    }

    private void showPane() {
        paneActive.setVisible(true);
    }

    private void hidePane() {
        paneActive.setVisible(false);
        userPane1.setVisible(false);
        userPane2.setVisible(false);
    }

    private void showLabelsWhenInserting() {
        lblInsertNumber.setVisible(true);
        lblInsertCurrency.setVisible(true);
        lblInsertActive.setVisible(true);
        lblInsertType.setVisible(true);
        lblNumber.setVisible(true);
        lblTypeOfNumber.setVisible(true);
        lblInsertType.setVisible(true);
        lblInsertCurrency.setVisible(true);
        lblCurrency.setVisible(true);
        lblRecharge.setVisible(true);
        lblActive.setVisible(true);
        lblInsertRecharge.setVisible(true);
    }

   
    
    @FXML
    public void closeForm(ActionEvent event) {
    Platform.exit();
    System.exit(0);}

    @FXML
    public void startSimulation() {
        chSys = new ChargingSystem();
        txtaStatus.clear();
        chSys.useAccount(txtaStatus,chat1,chat2,userPane1,userPane2, butStopSms);
    }

    @FXML
    public void stopSimulation() {
        chSys.stopThread();
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

    private void cancelInsert2() {
        //txtPassword.requestFocus();
        //removeTextFieldListeners();
        setTempCategory(category1);
        hidePane();
        activateUpdatetListener(getTempCategory());
        hideAllLabels();
        clearFieldsForInserting();
        hideCombos();
        hideUserFields();
        hideInsertCancelButtons();

        //showUpdateDeleteButtons();
        butActiveYes.setSelected(false);
        lblSelectAccount.setText("<<< Select Account");
    }

    private void hideUserFields() {
        txtNumber.setVisible(false);

    }

    private void activateInsertListener(String category) {
        txtNumber.focusedProperty().addListener((observable, oldValue, newValue) -> {

            //fieldNameInsertListeners(category);
        });

        txtNumber.textProperty().addListener((observable, oldValue, newValue) -> {
            fieldNameInsertListeners(category);
        });
    }

    private void activateUpdatetListener(String category) {
        txtNumber.focusedProperty().addListener((observable, oldValue, newValue) -> {

            //fieldNameInsertListeners(category);
        });

        txtNumber.textProperty().addListener((observable, oldValue, newValue) -> {
            fieldNameUpdateListeners(category);
        });
    }

    public String getTempCategory() {
        return tempCategory;
    }

    public void setTempCategory(String tempCategory) {
        this.tempCategory = tempCategory;
    }

    /*
     @FXML
     private void showUnresolvedTicekts(ActionEvent evt) {
     ownerCon = new OwnerController();
     ObservableList<String> list = ownerCon.getOwnersObsv();
     //ListView<String> listView = new ListView<String>(list);
     listOfOwners.setItems(list);
     }
     */
    private void fieldNameInsertListeners(String category) {
        fieldConditions(category);
    }

    private void fieldNameUpdateListeners(String category) {
        fieldConditions(category);
    }

    private void fieldConditions(String category) {
        try {
            if (lblInsertNumber.getText().length() < 0) {
                //txtUsername.requestFocus();
                lblInsertNumber.setText("<<< " + category + " number");
                lblInsertCurrency.setText("");
                lblInsertType.setText("");
                lblInsertActive.setText("");
            }

            if (lblInsertNumber.getText().length() > 0) {
                lblInsertCurrency.setText("<<< " + category + " currency");
                lblInsertType.setText("<<< " + category + " type");
                lblInsertActive.setText("<<< " + category + " type");
                lblInsertRecharge.setText("<<< " + category + " new balance");

            }

        } catch (Exception e) {
        }
    }

    public Button getButStopSms() {
        return butStopSms;
    }

    public void setButStopSms(Button butStopSms) {
        this.butStopSms = butStopSms;
    }
    
    

    public TextArea getChat1() {
        return chat1;
    }

    public void setChat1(TextArea chat1) {
        this.chat1 = chat1;
    }

    public TextArea getChat2() {
        return chat2;
    }

    public void setChat2(TextArea chat2) {
        this.chat2 = chat2;
    }

    public Pane getUserPane1() {
        return userPane1;
    }

    public void setUserPane1(Pane userPane1) {
        this.userPane1 = userPane1;
    }

    public Pane getUserPane2() {
        return userPane2;
    }

    public void setUserPane2(Pane userPane2) {
        this.userPane2 = userPane2;
    }

    @FXML
    public void senderOne(ActionEvent evt) {
        chSys.sendSmsFromUser1(sendSms1.getText(), txtaStatus);
    }

    @FXML
    public void senderTwo(ActionEvent evt) {
        chSys.sendSmsFromUser2(sendSms2.getText(), txtaStatus);
    }

    @FXML
    public void stopSendingSms(ActionEvent evt) {
        chSys.stopMsgService1();
        chSys.stopMsgService2();
        userPane1.setVisible(false);
        userPane2.setVisible(false);
        butStopSms.setVisible(false);
    }

}
