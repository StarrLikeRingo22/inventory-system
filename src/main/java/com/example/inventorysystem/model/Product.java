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

import java.util.ArrayList;
import java.util.List;

public class Product {
    private int id;
    private String name;
    private int stock;
    private double price;
    private int min;
    private int max;
    private final List<Part> associatedParts = new ArrayList<>();

    public Product(int id, String name, int stock, double price, int min, int max) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.min = min;
        this.max = max;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getStock() { return stock; }
    public double getPrice() { return price; }
    public int getMin() { return min; }
    public int getMax() { return max; }
    public List<Part> getAllAssociatedParts() { return associatedParts; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setStock(int stock) { this.stock = stock; }
    public void setPrice(double price) { this.price = price; }
    public void setMin(int min) { this.min = min; }
    public void setMax(int max) { this.max = max; }

    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    public void deleteAssociatedPart(Part part) {
        associatedParts.remove(part);
    }
}
