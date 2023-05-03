package com.example.warehouse.controller;

import java.util.List;

import com.example.warehouse.broker.TopicProducer;
import com.example.warehouse.domain.MaterialWithdrawal;
import com.example.warehouse.domain.ProductOrder;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.warehouse.repository.MaterialWithdrawalRepository;

@RestController
@RequestMapping("/WareHouse")
@RequiredArgsConstructor
public class WarehouseController {

	public static class material_list{
		private String materialnumber;
		private String materialdescription;
		private String quantity;
		public String getMaterialnumber() {
			return materialnumber;
		}
		public String getMaterialdescription() {
			return materialdescription;
		}
		public String getQuantity(){
			return quantity;
		}
	}

	public static class material_slip{
		private String productionorder;
		private String productionquantity;
		private String orderdate;
		private String issuedby;
		private String receivedby;
		private String location;
		private String status;
		private material_list[] materiallist;

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

		public void setReceivedby(String receivedby) {
			this.receivedby = receivedby;
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

		public material_list[] getMateriallist() {
			return materiallist;
		}

		public material_list getMateriallist(int i) {
			return materiallist[i];
		}

		public void setMateriallist(material_list[] materiallist) {
			this.materiallist = materiallist;
		}

		public int getMateriallistLength() {
			return materiallist.length;
		}
	}

	@Autowired
	private MaterialWithdrawalRepository materialWithdrawalRepository;

	//kafka
	private final TopicProducer topicProducer;

	@PostMapping(path = "/addMaterialWithdrawal")
    public @ResponseBody String addNewMaterialWithdrawal(@RequestBody String data) {
        Gson gson = new Gson();
		material_slip materialSlip = gson.fromJson(data, material_slip.class);

		for(int i = 0; i < materialSlip.getMateriallistLength(); i++){
			MaterialWithdrawal materialWithdrawal = new MaterialWithdrawal();
			materialWithdrawal.setProductionorder(materialSlip.getProductionorder());
			materialWithdrawal.setProductionquantity(materialSlip.getProductionquantity());
			materialWithdrawal.setOrderdate(materialSlip.getOrderdate());
			materialWithdrawal.setIssuedby(materialSlip.getIssuedby());
			materialWithdrawal.setReceivedby(materialSlip.getReceivedby());
			materialWithdrawal.setLocation(materialSlip.getLocation());
			materialWithdrawal.setStatus(materialSlip.getStatus());
			materialWithdrawal.setMaterialnumber(materialSlip.getMateriallist(i).getMaterialnumber());
			materialWithdrawal.setMaterialdescription(materialSlip.getMateriallist(i).getMaterialdescription());
			materialWithdrawal.setQuantity(materialSlip.getMateriallist(i).getQuantity());
			materialWithdrawalRepository.save(materialWithdrawal);
		}

		//kafka
		topicProducer.send("Saved " + materialSlip.getMateriallistLength() + " material to ProductionOrder " + materialSlip.getProductionorder() + "!");

        return "Saved " + materialSlip.getMateriallistLength() + " material to ProductionOrder " + materialSlip.getProductionorder() + "!";
    }
	
	@GetMapping("/allMaterialWithdrawal")
	public @ResponseBody List<MaterialWithdrawal> getAllUser(){
		List <MaterialWithdrawal> materialWithdrawalList = materialWithdrawalRepository.findAll();
		//Change to String
		String materialWithdrawal = new Gson().toJson(materialWithdrawalList);
		//Send to Kafka
		topicProducer.send(materialWithdrawal);

		return materialWithdrawalList;
	}

	@PostMapping("/ChangeStatus")
	public @ResponseBody String changeStatus(@RequestParam String productionorder, @RequestParam String status){
		materialWithdrawalRepository.updateStatus(status, productionorder);
		//kafka
		topicProducer.send("Status Production Order " + productionorder + " has been changed to " + status + "!");
		return "Status Production Order " + productionorder + " has been changed to " + status + "!";
	}

	@GetMapping("/getMaterialWithdrawalByProductionOrder")
	public @ResponseBody List<MaterialWithdrawal> getMaterialWithdrawalByProductionOrder(@RequestParam String ID){
		List <MaterialWithdrawal> materialWithdrawalList = materialWithdrawalRepository.findByProductOrder(ID);
		//Change to String
		String materialWithdrawal = new Gson().toJson(materialWithdrawalList);
		//Send to Kafka
		topicProducer.send(materialWithdrawal);
		return materialWithdrawalList;
	}
	
}
