package com.example.inventorysystem.db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.inventorysystem.model.*;

import java.sql.*;

public class DBProductHandler {

    public static void saveProductsToDB(ObservableList<Product> productList) {
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                    "REPLACE INTO products (id, name, price, stock, min, max) " +
                            "VALUES (?, ?, ?, ?, ?, ?)");

            for (Product product : productList) {
                ps.setInt(1, product.getId());
                ps.setString(2, product.getName());
                ps.setDouble(3, product.getPrice());
                ps.setInt(4, product.getStock());
                ps.setInt(5, product.getMin());
                ps.setInt(6, product.getMax());

                ps.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void loadProductsFromDB() {
        ObservableList<Product> products = FXCollections.observableArrayList();

        try {
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM products");

            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("stock"),
                        rs.getDouble("price"),
                        rs.getInt("min"),
                        rs.getInt("max")
                );

                products.add(product);
            }

            Inventory.setAllProducts(products);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
