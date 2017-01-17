/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accountAppUtil;

import accountAppMod.Account;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/**
 *
 * @author Deamon
 */
public class PopUpMessages {

    public Alert alert;
    public String text;

    public PopUpMessages() {
        this.text = text;
    }

    public void WarningMessage(String text) {
        alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("Look, a Worning Dialog");
        alert.setContentText(text);
        alert.showAndWait();
    }

    public void InformationMessage(String text) {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("Look, a Worning Dialog");
        alert.setContentText(text);
        alert.showAndWait();
    }

    public void errorMessage(String text) {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("Look, a Worning Dialog");
        alert.setContentText(text);
        alert.showAndWait();
    }

    public void insertMessageOk() {
        alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("Look, a Worning Dialog");
        alert.setContentText("New data is sucesfully inserted");
        alert.showAndWait();
    }

    public void insertMessageNotOk() {
        alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("Look, a Worning Dialog");
        alert.setContentText("There was an error on data insert, please try again");
        alert.showAndWait();
    }

    public void updateMessageOk() {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("Look, a Worning Dialog");
        alert.setContentText("Data is sucesfully updated");
        alert.showAndWait();
    }

    public void updateMessageNotOk() {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("Look, a Worning Dialog");
        alert.setContentText("There was an error on data update, please try again");
        alert.showAndWait();
    }

    public void deleteMessageOk() {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("Look, a Worning Dialog");
        alert.setContentText("Data sucesfully deleted");
        alert.showAndWait();
    }

    public void deleteMessageNotOk() {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("Look, a Worning Dialog");
        alert.setContentText("There was an error while trying to delete, please try again");
        alert.showAndWait();
    }

    public int alertDialogInsert() {
        int choice = 0;
        Alert alert = new Alert(AlertType.CONFIRMATION, "You are about to insert new data , do you want to continue?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            choice = 1;
        }
        return choice;
    }

    public int alertDialogInsertAndGenerateNewPass() {
        int choice = 0;
        Alert alert = new Alert(AlertType.CONFIRMATION, "You will create new random password for User , do you want to continue?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            choice = 1;
        }
        return choice;
    }

    public int alertDialogDelete() {
        int choice = 0;
        Alert alert = new Alert(AlertType.CONFIRMATION, "You are about to delete data, do you want to continue?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            choice = 1;
        }
        return choice;
    }

    public int alertDialogUpdate() {
        int choice = 0;
        Alert alert = new Alert(AlertType.CONFIRMATION, "You are about to update data, do you want to continue?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            choice = 1;
        }
        return choice;
    }

    public int alertVaucherUpdate(String msisdn) {

        int choice = 0;

        Alert alert = new Alert(AlertType.CONFIRMATION, "You are about recharge account " + msisdn, ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);

        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            choice = 1;
        }
        return choice;
    }

    public int alertDialogMultipleUpdate() {
        int choice = 0;
        Alert alert = new Alert(AlertType.CONFIRMATION, "You are about to update multiple categories, do you want to continue?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            choice = 1;
        }
        return choice;
    }

    public int alertDialogMultipleDelete() {
        int choice = 0;
        Alert alert = new Alert(AlertType.CONFIRMATION, "You are about to delete multiple categories, do you want to continue?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            choice = 1;
        }
        return choice;
    }

}
