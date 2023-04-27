package com.example.warehouse.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "productorder")
public class ProductOrder {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String productionorder;
	private String orderdate;
	private String materialnumber;
	private String materialdescription;
	private String quantity;

	public int getId() {
		return id;
	}

	public String getProductionorder() {
		return productionorder;
	}

	public String getOrderdate() {
		return orderdate;
	}

	public String getMaterialnumber() {
		return materialnumber;
	}

	public String getMaterialdescription() {
		return materialdescription;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setProductionorder(String productionorder) {
		this.productionorder = productionorder;
	}

	public void setOrderdate(String orderdate) {
		this.orderdate = orderdate;
	}

	public void setMaterialnumber(String materialnumber) {
		this.materialnumber = materialnumber;
	}

	public void setMaterialdescription(String materialdescription) {
		this.materialdescription = materialdescription;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
}
