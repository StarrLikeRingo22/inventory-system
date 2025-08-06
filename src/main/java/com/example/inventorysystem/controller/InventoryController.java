package com.example.inventorysystem.controller;

import com.example.inventorysystem.model.Inventory;
import com.example.inventorysystem.model.Part;
import com.example.inventorysystem.model.Product;

public class InventoryController {

    public void addProduct(Product product) {
        Inventory.addProduct(product);
    }

    public void updateProduct(int index, Product product) {
        Inventory.updateProduct(index, product);
    }

    public void deleteProduct(Product product) {
        Inventory.deleteProduct(product);
    }

    public void addPart(Part part) {
        Inventory.addPart(part);
    }

    public void updatePart(int index, Part part) {
        Inventory.updatePart(index, part);
    }

    public void deletePart(Part part) {
        Inventory.deletePart(part);
    }
}
