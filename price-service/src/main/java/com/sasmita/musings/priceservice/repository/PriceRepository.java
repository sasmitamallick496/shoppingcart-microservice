package com.sasmita.musings.priceservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sasmita.musings.priceservice.entity.PriceInfo;

public interface PriceRepository extends JpaRepository<PriceInfo, Long>{
	
	public List<PriceInfo> findByProductId(String productId);
	

}
