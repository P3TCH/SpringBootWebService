package com.example.warehouse.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "materialwithdrawal")
public class MaterialWithdrawal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String productionorder;
    private String productionquantity;
    private String orderdate;
    private String issuedby;
    private String receivedby;
    private String location;
    private String status;
    private String materialnumber;
    private String materialdescription;
    private String quantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductionorder() {
        return productionorder;
    }

    public void setProductionorder(String productionorder) {
        this.productionorder = productionorder;
    }

    public String getProductionquantity() {
        return productionquantity;
    }

    public void setProductionquantity(String productionquantity) {
        this.productionquantity = productionquantity;
    }

    public String getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate;
    }

    public String getIssuedby() {
        return issuedby;
    }

    public void setIssuedby(String issuedby) {
        this.issuedby = issuedby;
    }

    public String getReceivedby() {
        return receivedby;
    }

    public void setReceivedby(String recivedby) {
        this.receivedby = recivedby;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
