package com.sasmita.musings.priceservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sasmita.musings.priceservice.entity.PriceInfo;
import com.sasmita.musings.priceservice.repository.PriceRepository;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Service
@AllArgsConstructor
public class PriceInfoService {

	private @Getter PriceRepository repository;

	public Optional<PriceInfo> findPriceByProductId(String productId) {
		List<PriceInfo> priceList = getRepository().findByProductId(productId);
		return priceList.stream().findFirst();
	}

}
