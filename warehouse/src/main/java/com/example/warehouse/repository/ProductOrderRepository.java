package com.example.warehouse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.warehouse.domain.*;
import com.example.warehouse.domain.ProductOrder;

@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrder, Integer>{
	@Query(value = "SELECT * FROM productorder WHERE productionorder = ?1", nativeQuery = true)
	List<ProductOrder> findByProductOrder(int po_id);
}
