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

import com.example.inventorysystem.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class MainScreenController {

    @FXML private TextField searchField;
    @FXML private TableView<Part> partsTableView;
    @FXML private TableColumn<Part, Integer> partIDColumn, inventoryLevelColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, Double> priceColumn;

    @FXML private TableView<Product> productsTableView;
    @FXML private TableColumn<Product, Integer> productIDColumn, productInventoryLevelColumn;
    @FXML private TableColumn<Product, String> productNameColumn;
    @FXML private TableColumn<Product, Double> productPriceColumn;

    @FXML
    public void initialize() {
        // Part table
        partIDColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getId()).asObject());
        partNameColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));
        inventoryLevelColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getStock()).asObject());
        priceColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleDoubleProperty(data.getValue().getPrice()).asObject());

        partsTableView.setItems(Inventory.getAllParts());

        // Product table
        productIDColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getId()).asObject());
        productNameColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));
        productInventoryLevelColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getStock()).asObject());
        productPriceColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleDoubleProperty(data.getValue().getPrice()).asObject());

        productsTableView.setItems(Inventory.getAllProducts());


    }

    @FXML
    private void handleExit() {
        System.exit(0);
    }

    @FXML
    private void handleSearch() {
        String query = searchField.getText().toLowerCase().trim();

        ObservableList<Part> filteredParts = FXCollections.observableArrayList();
        ObservableList<Product> filteredProducts = FXCollections.observableArrayList();

        for (Part part : Inventory.getAllParts()) {
            if (part.getName().toLowerCase().contains(query) || String.valueOf(part.getId()).equals(query)) {
                filteredParts.add(part);
            }
        }

        for (Product product : Inventory.getAllProducts()) {
            if (product.getName().toLowerCase().contains(query) || String.valueOf(product.getId()).equals(query)) {
                filteredProducts.add(product);
            }
        }

        if (filteredParts.isEmpty() && filteredProducts.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Match");
            alert.setHeaderText("No matching parts or products found.");
            alert.showAndWait();
        }

        partsTableView.setItems(filteredParts);
        productsTableView.setItems(filteredProducts);
    }

    @FXML
    private void handleAddPart(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/inventorysystem/addPart.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Add Part");
        stage.show();
    }

    @FXML
    private void handleModifyPart(ActionEvent event) throws IOException {
        Part selected = partsTableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Part Error");
            alert.setHeaderText("No Part Selected");
            alert.showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/inventorysystem/modifyPart.fxml")); // or modifyPart.fxml if that's your actual FXML
        Parent root = loader.load();
        PartController controller = loader.getController();
        controller.setPart(selected);

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Modify Part");
        stage.show();


    }

    @FXML
    private void handleDeletePart() {
        Part selected = partsTableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Inventory.deletePart(selected);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Part Error");
            alert.setHeaderText("No Part Selected");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleAddProduct(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/inventorysystem/addProduct.fxml")));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Add Part");
        stage.show();
    }

    @FXML
    private void handleModifyProduct(ActionEvent event) throws IOException {
        Product selected = productsTableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/inventorysystem/modifyProduct.fxml"));
            Parent root = loader.load();
            ProductController controller = loader.getController();
            controller.setProduct(selected);

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Modify Product");
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Product Error");
            alert.setHeaderText("No Product Selected");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleDeleteProduct() {
        Product selectedProduct = productsTableView.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Product Error");
            alert.setHeaderText("No Product Selected");
            alert.showAndWait();
            return;
        }


        Inventory.deleteProduct(selectedProduct);
        productsTableView.setItems(Inventory.getAllProducts()); // Refresh table
    }
}
