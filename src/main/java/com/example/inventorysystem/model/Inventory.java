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

package com.example.inventorysystem.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    static {
        Part wheel = new InHouse(1, "Wheel", 10, 15.99, 1, 20, 123);
        Inventory.addPart(wheel);

        for (int i = 0; i < 10; i++) {
            Product bike = new Product(i, "Bike " + i, 1, 299.99, 1, 10);
            bike.addAssociatedPart(wheel);
            Inventory.addProduct(bike);
        }
    }

    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static int productIdCounter = 1;

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }


    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

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
}
