package com.example.inventorysystem.controller;

import com.example.inventorysystem.model.InHouse;
import com.example.inventorysystem.model.Inventory;
import com.example.inventorysystem.model.Outsourced;
import com.example.inventorysystem.model.Part;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;

public class PartController {

    @FXML private RadioButton inHouseRadioButton;
    @FXML private RadioButton outsourcedRadioButton;
    @FXML private Label machineLabel;
    @FXML private TextField idField, nameField, inventoryField, priceField, minField, maxField, machineField;
    private final ToggleGroup partTypeToggleGroup = new ToggleGroup();

    private Part part;

    @FXML
    public void initialize() {
        inHouseRadioButton.setToggleGroup(partTypeToggleGroup);
        outsourcedRadioButton.setToggleGroup(partTypeToggleGroup);
        inHouseRadioButton.setSelected(true);
        machineLabel.setText("Machine ID");

        partTypeToggleGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle == inHouseRadioButton) {
                machineLabel.setText("Machine ID");
            } else {
                machineLabel.setText("Company Name");
            }
        });
    }


    public void setPart(Part part) {
            this.part = part;
            idField.setText(String.valueOf(part.getId()));
            nameField.setText(part.getName());
            inventoryField.setText(String.valueOf(part.getStock()));
            priceField.setText(String.valueOf(part.getPrice()));
            minField.setText(String.valueOf(part.getMin()));
            maxField.setText(String.valueOf(part.getMax()));

            inHouseRadioButton.setToggleGroup(partTypeToggleGroup);
            outsourcedRadioButton.setToggleGroup(partTypeToggleGroup);

            if (part instanceof InHouse) {
                inHouseRadioButton.setSelected(true);
                machineLabel.setText("Machine ID");
                machineField.setText(String.valueOf(((InHouse) part).getMachineId()));
            } else if (part instanceof Outsourced) {
                outsourcedRadioButton.setSelected(true);
                machineLabel.setText("Company Name");
                machineField.setText(((Outsourced) part).getCompanyName());
            }

            partTypeToggleGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
                if (newToggle == inHouseRadioButton) {
                    machineLabel.setText("Machine ID");
                } else if (newToggle == outsourcedRadioButton) {
                    machineLabel.setText("Company Name");
                }
            });

    }

    @FXML
    private void handleSave() {
        try {
            String name = nameField.getText().trim();
            String stockText = inventoryField.getText().trim();
            String priceText = priceField.getText().trim();
            String minText = minField.getText().trim();
            String maxText = maxField.getText().trim();
            String machineText = machineField.getText().trim();

            if (name.isEmpty() || stockText.isEmpty() || priceText.isEmpty() ||
                    minText.isEmpty() || maxText.isEmpty() || machineText.isEmpty()) {
                showAlert("Missing input", "All fields must be filled.");
                return;
            }

            int stock = Integer.parseInt(stockText);
            double price = Double.parseDouble(priceText);
            int min = Integer.parseInt(minText);
            int max = Integer.parseInt(maxText);

            if (min > max) {
                showAlert("Invalid input", "Min must be less than or equal to Max.");
                return;
            }
            if (stock < min || stock > max) {
                showAlert("Invalid input", "Inventory must be between Min and Max.");
                return;
            }

            int id = (part == null) ? Inventory.getNextPartId() : part.getId();
            Part newPart;

            if (inHouseRadioButton.isSelected()) {
                int machineId = Integer.parseInt(machineText);
                newPart = new InHouse(id, name, stock, price, min, max, machineId);
            } else {
                newPart = new Outsourced(id, name, stock, price, min, max, machineText);
            }

            if (part == null) {
                Inventory.addPart(newPart);
            } else {
                Inventory.updatePart(part.getId(), newPart);
            }
            System.out.println("DEBUG VALUES:");
            System.out.println("name = " + name);
            System.out.println("stock = " + stockText);
            System.out.println("price = " + priceText);
            System.out.println("min = " + minText);
            System.out.println("max = " + maxText);
            System.out.println("machine = " + machineText);
            System.out.println("radio = " + (inHouseRadioButton.isSelected() ? "InHouse" : "Outsourced"));

            returnToMainScreen();
        } catch (NumberFormatException e) {
            showAlert("Invalid input", "Please ensure numbers are valid (e.g., no letters in numeric fields).");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Unexpected error", "Something went wrong. Check your inputs.");
        }
    }

    @FXML
    private void handleCancel() throws IOException {
        returnToMainScreen();
    }

    private void returnToMainScreen() throws IOException {
        Stage stage = (Stage) idField.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/inventorysystem/MainScreen.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.setTitle("Inventory Management System");
        stage.show();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
