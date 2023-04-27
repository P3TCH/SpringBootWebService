package com.example.warehouse.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.warehouse.domain.ProductOrder;
import com.google.gson.Gson;
import com.example.warehouse.repository.ProductOrderRepository;

@RestController
@RequestMapping("/ProductOrder")

public class ProductController {
	@Autowired
	private ProductOrderRepository ProductOrderRepository;

	@PostMapping(path = "/addProductOrder")
    public @ResponseBody String addNewPO(@RequestParam String productionorder, @RequestParam String orderdate, @RequestParam String materialnumber, @RequestParam String materialdescription, @RequestParam String quantity) {

		ProductOrder ProductOrder = new ProductOrder();
		ProductOrder.setProductionorder(productionorder);
		ProductOrder.setOrderdate(orderdate);
		ProductOrder.setMaterialnumber(materialnumber);
		ProductOrder.setMaterialdescription(materialdescription);
		ProductOrder.setQuantity(quantity);
		ProductOrderRepository.save(ProductOrder);
        return "Saved Production Order";
    }

	@GetMapping("/allOrder")
	public @ResponseBody List<ProductOrder> getAllUser(){
		return ProductOrderRepository.findAll();
	}
	
	@GetMapping("/searchByOrder")
	public @ResponseBody List<ProductOrder> getByID(@RequestParam int productorder){
		return ProductOrderRepository.findByProductOrder(productorder);
	}
	
}
