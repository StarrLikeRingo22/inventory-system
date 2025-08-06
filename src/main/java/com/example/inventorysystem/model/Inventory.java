package com.example.inventorysystem.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;

public class Inventory implements Serializable {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    static {
        Part wheel = new InHouse(1, "Wheel", 10, 15.99, 1, 20, 123);
        Inventory.addPart(wheel);

        for (int i = 0; i < 10; i++) {
            Product bike = new Product(i, "Bike " + i, 1, 299.99, 1, 10);
            bike.addAssociatedPart(wheel);
            Inventory.addProduct(bike);
        }
    }

    private static int productIdCounter = 1;


    public static void addPart(Part part) {
        allParts.add(part);
    }

    public static int getNextPartId() {
        return !allParts.isEmpty() ? allParts.getLast().getId() + 1 : 1;
    }

    public static void updatePart(int id, Part newPart) {
        for (int i = 0; i < allParts.size(); i++) {
            if (allParts.get(i).getId() == id) {
                allParts.set(i, newPart);
                return;
            }
        }
    }

    public static boolean deletePart(Part part) {
        return allParts.remove(part);
    }

    public static void addProduct(Product product) {
        allProducts.add(product);
    }

    public static int getNextProductId() {
        return productIdCounter++;
    }

    public static void updateProduct(int id, Product newProduct) {
        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getId() == id) {
                allProducts.set(i, newProduct);
                return;
            }
        }
    }

    public static boolean deleteProduct(Product product) {
        return allProducts.remove(product);
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    public static void setAllParts(ObservableList<Part> parts) {
        allParts.setAll(parts);
    }

    public static void setAllProducts(ObservableList<Product> products) {
        allProducts.setAll(products);
    }
}
