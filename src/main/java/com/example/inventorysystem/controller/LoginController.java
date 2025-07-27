/**********************************************
 Workshop # 4 & 5
 Course:<APD545> - Summer
 Last Name:<Abdelgadir>
 First Name:<Abdalla>
 ID:<113734198>
 Section:<NAA>
 This assignment represents my own work in accordance with Seneca Academic Policy.
 Signature
 Date:<July 18, 2025>
 **********************************************/

package com.example.inventorysystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    @FXML
    private void handleLogin() {
        if (usernameField.getText().equals("admin") && passwordField.getText().equals("password")) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/inventorysystem/MainScreen.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Inventory Management System");
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error loading main screen.");
            }
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText("Invalid credentials");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleCancel(javafx.event.ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}

