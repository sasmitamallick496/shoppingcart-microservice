package com.sasmita.musings.inventoryservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sasmita.musings.inventoryservice.entity.InventoryInfo;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryInfo, Long> {

	List<InventoryInfo> findByProductId(String productId);

}
