package com.example.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.example.warehouse.domain.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MaterialWithdrawalRepository extends JpaRepository<MaterialWithdrawal, Integer> {
    boolean ture = true;

    @Query(value = "SELECT * FROM materialwithdrawal WHERE productionorder = ?1", nativeQuery = ture)
    List<MaterialWithdrawal> findByProductOrder(String productionorder);

    //change status to "Approved" from productionorder
    @Modifying
    @Transactional
    @Query(value = "UPDATE materialwithdrawal SET status = ?1 WHERE productionorder = ?2", nativeQuery = true)
    void updateStatus(String productionquantity,String productionorder);


}
