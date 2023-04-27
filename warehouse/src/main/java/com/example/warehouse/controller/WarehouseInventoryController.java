package com.example.warehouse.controller;

import com.example.warehouse.domain.WarehouseInventory;
import com.example.warehouse.repository.WarehouseInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Inventory")
public class WarehouseInventoryController {
    @Autowired
    private WarehouseInventoryRepository warehouseInventoryRepository;

    @PostMapping("/addItem")
    public @ResponseBody String addItem(@RequestParam String materialnumber, @RequestParam String materialdescription, @RequestParam String quantity){
        WarehouseInventory warehouseInventory = new WarehouseInventory();
        warehouseInventory.setMaterialnumber(materialnumber);
        warehouseInventory.setMaterialdescription(materialdescription);
        warehouseInventory.setQuantity(quantity);
        warehouseInventoryRepository.save(warehouseInventory);
        return "Saved Item";
    }

    @GetMapping("/allItem")
    public @ResponseBody Iterable<WarehouseInventory> getAllItem(){
        return warehouseInventoryRepository.findAll();
    }

    @GetMapping("/searchByNumber")
    public @ResponseBody Iterable<WarehouseInventory> getByNumber(@RequestParam String materialnumber){
        return warehouseInventoryRepository.findBymaterialnumber(materialnumber);
    }

    @PostMapping("/updateItem")
    public @ResponseBody String updateItem(@RequestParam String materialnumber, @RequestParam String quantity){
        warehouseInventoryRepository.updateQuantity(quantity,materialnumber);
        return "Updated Item " + materialnumber + " to quantity : " + quantity;
    }
}
