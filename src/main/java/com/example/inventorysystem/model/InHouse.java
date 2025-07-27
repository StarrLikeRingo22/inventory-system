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

public class InHouse extends Part {
    private int machineId;

    public InHouse(int id, String name, int stock, double price, int min, int max, int machineId) {
        super(id, name, stock, price, min, max);
        this.machineId = machineId;
    }

    public int getMachineId() { return machineId; }
    public void setMachineId(int machineId) { this.machineId = machineId; }
}
