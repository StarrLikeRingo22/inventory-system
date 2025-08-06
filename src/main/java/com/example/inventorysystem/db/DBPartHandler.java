package com.example.inventorysystem.db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.inventorysystem.model.*;

import java.sql.*;

public class DBPartHandler {

    public static void savePartsToDB(ObservableList<Part> partList) {
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                    "REPLACE INTO parts (id, name, price, stock, min, max, isInHouse, machineID, companyName) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

            for (Part part : partList) {
                ps.setInt(1, part.getId());
                ps.setString(2, part.getName());
                ps.setDouble(3, part.getPrice());
                ps.setInt(4, part.getStock());
                ps.setInt(5, part.getMin());
                ps.setInt(6, part.getMax());

                boolean isInHouse = part instanceof InHouse;
                ps.setBoolean(7, isInHouse);

                if (isInHouse) {
                    ps.setInt(8, ((InHouse) part).getMachineId());
                    ps.setNull(9, Types.VARCHAR);
                } else {
                    ps.setNull(8, Types.INTEGER);
                    ps.setString(9, part.getName()); // dummy company name for demo
                }

                ps.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void loadPartsFromDB() {
        ObservableList<Part> parts = FXCollections.observableArrayList();

        try {
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM parts");

            while (rs.next()) {
                if (rs.getBoolean("isInHouse")) {
                    parts.add(new InHouse(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("stock"),
                            rs.getDouble("price"),
                            rs.getInt("min"),
                            rs.getInt("max"),
                            rs.getInt("machineID")
                    ));
                } else {
                    // Add Outsourced part logic if applicable
                }
            }

            Inventory.setAllParts(parts);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

