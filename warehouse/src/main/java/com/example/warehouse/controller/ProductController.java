package com.example.warehouse.controller;


import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
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
import com.example.warehouse.broker.TopicProducer;

@RestController
@RequestMapping("/ProductOrder")
@RequiredArgsConstructor
public class ProductController {

	@Getter
	@Setter
	@RequiredArgsConstructor
	public static class ProductOrderList{
		String productionorder;
		String orderdate;
		productlist[] productlist;

		public productlist getProductIndex(int index) {
			return productlist[index];
		}

		public int getProductListLength() {
			return productlist.length;
		}
	}

	@Getter
	@Setter
	@RequiredArgsConstructor
	public static class productlist{
		String materialnumber;
		String materialdescription;
		String quantity;
	}
	@Autowired
	private ProductOrderRepository ProductOrderRepository;

	//Kafka
	private final TopicProducer topicProducer;

	@PostMapping(path = "/addProductOrderList")
	public @ResponseBody String addPoList(@RequestBody String data){
		ProductOrderList productOrderList = new Gson().fromJson(data, ProductOrderList.class);
		for(int i = 0; i < productOrderList.getProductListLength(); i++){
			ProductOrder productOrder = new ProductOrder();
			productOrder.setProductionorder(productOrderList.getProductionorder());
			productOrder.setOrderdate(productOrderList.getOrderdate());
			productOrder.setMaterialnumber(productOrderList.getProductIndex(i).getMaterialnumber());
			productOrder.setMaterialdescription(productOrderList.getProductIndex(i).getMaterialdescription());
			productOrder.setQuantity(productOrderList.getProductIndex(i).getQuantity());
			ProductOrderRepository.save(productOrder);
		}
		topicProducer.send("Saved " + productOrderList.getProductListLength() + " New Production Order");
		return "Saved " + productOrderList.getProductListLength() + " New Production Order";
	}

	@PostMapping(path = "/addProductOrder")
    public @ResponseBody String addNewPO(@RequestParam String productionorder, @RequestParam String orderdate, @RequestParam String materialnumber, @RequestParam String materialdescription, @RequestParam String quantity) {

		ProductOrder ProductOrder = new ProductOrder();
		ProductOrder.setProductionorder(productionorder);
		ProductOrder.setOrderdate(orderdate);
		ProductOrder.setMaterialnumber(materialnumber);
		ProductOrder.setMaterialdescription(materialdescription);
		ProductOrder.setQuantity(quantity);
		ProductOrderRepository.save(ProductOrder);

		topicProducer.send("Saved New Production Order");
        return "Saved New Production Order";
    }

	@GetMapping("/allOrder")
	public @ResponseBody List<ProductOrder> getAllUser(){
		List <ProductOrder> productOrderList = ProductOrderRepository.findAll();
		//Change to String
		String productOrderListString = new Gson().toJson(productOrderList);
		//Send to Kafka
		topicProducer.send(productOrderListString);
		return productOrderList;
	}
	
	@GetMapping("/searchByOrder")
	public @ResponseBody List<ProductOrder> getByID(@RequestParam int productorder){
		List <ProductOrder> productOrderList = ProductOrderRepository.findByProductOrder(productorder);
		//Change to String
		String productOrderListString = new Gson().toJson(productOrderList);
		//Send to Kafka
		topicProducer.send(productOrderListString);
		return productOrderList;
	}
	
}
