package com.example.warehouse.controller;

import java.util.List;

import com.example.warehouse.domain.MaterialWithdrawal;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.warehouse.repository.MaterialWithdrawalRepository;

@RestController
@RequestMapping("/WareHouse")
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

        return "Saved list to ProductList";
    }
	
	@GetMapping("/allMaterialWithdrawal")
	public @ResponseBody List<MaterialWithdrawal> getAllUser(){
		return materialWithdrawalRepository.findAll();
	}

	@PostMapping("/ChangeStatus")
	public @ResponseBody String changeStatus(@RequestParam String productionorder, @RequestParam String status){
		materialWithdrawalRepository.updateStatus(status, productionorder);
		return "Status Production Order " + productionorder + " has been changed to " + status + "!";
	}

	@GetMapping("/getMaterialWithdrawalByProductionOrder")
	public @ResponseBody List<MaterialWithdrawal> getMaterialWithdrawalByProductionOrder(@RequestParam String ID){
		return materialWithdrawalRepository.findByProductOrder(ID);
	}
	
}
