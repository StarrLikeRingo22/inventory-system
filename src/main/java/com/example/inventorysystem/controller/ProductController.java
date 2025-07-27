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

import com.example.inventorysystem.model.Inventory;
import com.example.inventorysystem.model.Part;
import com.example.inventorysystem.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.Optional;

public class ProductController {

    @FXML private TextField idField, nameField, inventoryField, priceField, minField, maxField, searchField;
    @FXML private TableView<Part> partsTableView, associatedPartsTableView;
    @FXML private TableColumn<Part, Integer> partIDColumn, associatedPartIDColumn, partInventoryLevelColumn, associatedPartInventoryLevelColumn;
    @FXML private TableColumn<Part, String> partNameColumn, associatedPartNameColumn;
    @FXML private TableColumn<Part, Double> partPriceColumn, associatedPartPriceColumn;

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private Product selectedProduct;

    @FXML
    public void initialize() {
        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        partsTableView.setItems(Inventory.getAllParts());

        associatedPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        associatedPartsTableView.setItems(associatedParts);
    }

    public void setProduct(Product product) {
        this.selectedProduct = product;
        idField.setText(String.valueOf(product.getId()));
        nameField.setText(product.getName());
        inventoryField.setText(String.valueOf(product.getStock()));
        priceField.setText(String.valueOf(product.getPrice()));
        minField.setText(String.valueOf(product.getMin()));
        maxField.setText(String.valueOf(product.getMax()));
        associatedParts.setAll(product.getAllAssociatedParts());
    }

    @FXML
    private void handleAddPart() {
        Part selected = partsTableView.getSelectionModel().getSelectedItem();
        if (selected != null && !associatedParts.contains(selected)) {
            associatedParts.add(selected);
        }
    }

    @FXML
    private void handleDeleteAssociatedPart() {
        Part selected = associatedPartsTableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirm Delete");
            confirm.setHeaderText("Remove associated part?");
            confirm.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    associatedParts.remove(selected);
                }
            });

        }
        if (associatedParts.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("No Parts Warning");
            alert.setHeaderText("This product has no associated parts.");
            alert.setContentText("Are you sure you want to save it anyway?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isEmpty() || result.get() != ButtonType.OK) {
                return;
            }
        }

        if (!selectedProduct.getAllAssociatedParts().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Delete Error");
            alert.setHeaderText("Product has associated parts.");
            alert.setContentText("Remove all associated parts before deleting.");
            alert.showAndWait();
            return;
        }

    }

    @FXML
    private void handleSave(ActionEvent event) {
        try {
            int id = selectedProduct == null ? Inventory.getNextProductId() : selectedProduct.getId();
            String name = nameField.getText();
            int stock = Integer.parseInt(inventoryField.getText());
            double price = Double.parseDouble(priceField.getText());
            int min = Integer.parseInt(minField.getText());
            int max = Integer.parseInt(maxField.getText());

            if (min > max || stock < min || stock > max) {
                showAlert("Input Error", "Inventory must be between Min and Max, and Min must be ≤ Max.");
                return;
            }

            if (associatedParts.isEmpty()) {
                showAlert("Validation Error", "Product must have at least one associated part.");
                return;
            }

            double totalPartCost = associatedParts.stream().mapToDouble(Part::getPrice).sum();
            if (price < totalPartCost) {
                showAlert("Validation Error", "Product price cannot be less than total cost of parts.");
                return;
            }

            Product newProduct = new Product(id, name, stock, price, min, max);
            for (Part p : associatedParts) newProduct.addAssociatedPart(p);

            if (selectedProduct == null) {
                Inventory.addProduct(newProduct);
            } else {
                Inventory.updateProduct(id, newProduct);
            }

            returnToMainScreen();

        } catch (Exception e) {
            showAlert("Error", "Invalid or missing input field(s). Please check your entries.");
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) throws IOException {
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
