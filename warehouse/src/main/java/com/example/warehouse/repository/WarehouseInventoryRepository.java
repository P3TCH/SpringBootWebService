package com.example.warehouse.repository;

import com.example.warehouse.domain.WarehouseInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface WarehouseInventoryRepository extends JpaRepository<WarehouseInventory, Integer> {
    List<WarehouseInventory> findBymaterialnumber(String materialnumber);
    List<WarehouseInventory> findBymaterialdescription(String materialdescription);

    @Modifying
    @Transactional
    @Query(value = "UPDATE warehouseinventory SET quantity = ?1 WHERE materialnumber = ?2", nativeQuery = true)
    void updateQuantity(String quantity,String materialnumber);

}
