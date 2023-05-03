package com.example.warehouse.controller;

import com.example.warehouse.broker.TopicProducer;
import com.example.warehouse.domain.WarehouseInventory;
import com.example.warehouse.repository.WarehouseInventoryRepository;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Inventory")
@RequiredArgsConstructor
public class WarehouseInventoryController {
    @Autowired
    private WarehouseInventoryRepository warehouseInventoryRepository;

    //Kafka
    private final TopicProducer topicProducer;

    @PostMapping("/addItem")
    public @ResponseBody String addItem(@RequestParam String materialnumber, @RequestParam String materialdescription, @RequestParam String quantity){
        WarehouseInventory warehouseInventory = new WarehouseInventory();
        warehouseInventory.setMaterialnumber(materialnumber);
        warehouseInventory.setMaterialdescription(materialdescription);
        warehouseInventory.setQuantity(quantity);
        warehouseInventoryRepository.save(warehouseInventory);

        topicProducer.send("Saved new Item");
        return "Saved new Item";
    }

    @GetMapping("/allItem")
    public @ResponseBody List<WarehouseInventory> getAllItem(){
        List<WarehouseInventory> warehouseInventoryList = warehouseInventoryRepository.findAll();
        //Change to String
        String warehouseInventoryListString = new Gson().toJson(warehouseInventoryList);
        //Send to Kafka
        topicProducer.send(warehouseInventoryListString);
        return warehouseInventoryList;
    }

    @GetMapping("/searchByNumber")
    public @ResponseBody List<WarehouseInventory> getByNumber(@RequestParam String materialnumber){
        List <WarehouseInventory> warehouseInventoryList = warehouseInventoryRepository.findBymaterialnumber(materialnumber);
        //Change to String
        String warehouseInventoryListString = new Gson().toJson(warehouseInventoryList);
        //Send to Kafka
        topicProducer.send(warehouseInventoryListString);
        return warehouseInventoryList;
    }

    @PostMapping("/updateItem")
    public @ResponseBody String updateItem(@RequestParam String materialnumber, @RequestParam String quantity){
        warehouseInventoryRepository.updateQuantity(quantity,materialnumber);

        //kafka
        topicProducer.send("Updated Item " + materialnumber + " to quantity : " + quantity);
        return "Updated Item " + materialnumber + " to quantity : " + quantity;
    }
}
