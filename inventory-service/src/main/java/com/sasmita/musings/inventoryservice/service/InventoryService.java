package com.sasmita.musings.inventoryservice.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sasmita.musings.inventoryservice.entity.InventoryAuditLog;
import com.sasmita.musings.inventoryservice.entity.InventoryInfo;
import com.sasmita.musings.inventoryservice.repository.InventoryAuditLogRepository;
import com.sasmita.musings.inventoryservice.repository.InventoryRepository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import static com.sasmita.musings.inventoryservice.util.InventoryServiceConstants.*;

@Service
@AllArgsConstructor
@Slf4j
public class InventoryService {

	private @Getter InventoryRepository repository;

	private @Getter InventoryAuditLogRepository auditLogRepository;

	public Optional<InventoryInfo> retrieveInventoryById(String productId) {
		List<InventoryInfo> inventoryList = getRepository().findByProductId(productId);
		return inventoryList.stream().findFirst();
	}

	//TODO move all the hard coded strings to a constant file.
	public Boolean reserveInventory(UUID orderId, String productId, int quantity) {
		log.info("------Inside Reserve Inventory Method------");
		InventoryInfo inventoryInfo = retrieveInventoryById(productId).orElseThrow();
		Long totalStock = inventoryInfo.getStockAvailable();
		log.info("------Total stock----"+totalStock);
		if (totalStock >= quantity) {
			inventoryInfo.setStockAvailable(totalStock - quantity);
			log.info("-----After Deduction Total Stock-----"+inventoryInfo.getStockAvailable());
			getRepository().save(inventoryInfo);
			saveAuditLog(orderId, productId, quantity,INVENTORY_RESERVE, INVENTORY_STATUS_SUCCESS);
			return true;
		}
		saveAuditLog(orderId, productId, quantity, INVENTORY_RESERVE, INVENTORY_STATUS_FAILURE);
		return false;
	}

	public Boolean unReserveInventory(UUID orderId, String productId, int quantity) {
		log.info("------Inside Un Reserve Inventory Method------");
		InventoryInfo inventoryInfo = retrieveInventoryById(productId).orElseThrow();
		Long totalStock = inventoryInfo.getStockAvailable();
		log.info("----------Before Un Reserve Total Stock-------"+totalStock);
		inventoryInfo.setStockAvailable(totalStock + quantity);
		log.info("----------After Un Reserve Total Stock-------"+inventoryInfo.getStockAvailable());
		getRepository().save(inventoryInfo);
		saveAuditLog(orderId, productId, quantity, INVENTORY_UNRESERVE, INVENTORY_STATUS_SUCCESS);
		return true;
	}

	private void saveAuditLog(UUID orderId, String productId, int quantity, String inventoryAction, String status) {
		InventoryAuditLog auditLog = new InventoryAuditLog();
		auditLog.setOrderId(orderId);
		auditLog.setProductId(productId);
		auditLog.setQuantity(quantity);
		auditLog.setInventoryAction(inventoryAction);
		auditLog.setStatus(status);
		getAuditLogRepository().save(auditLog);
	}

}
