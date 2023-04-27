package com.example.warehouse.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "warehouseinventory")
public class WarehouseInventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String materialnumber;
    private String materialdescription;
    private String quantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaterialnumber() {
        return materialnumber;
    }

    public void setMaterialnumber(String materialnumber) {
        this.materialnumber = materialnumber;
    }

    public String getMaterialdescription() {
        return materialdescription;
    }

    public void setMaterialdescription(String materialdescription) {
        this.materialdescription = materialdescription;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
