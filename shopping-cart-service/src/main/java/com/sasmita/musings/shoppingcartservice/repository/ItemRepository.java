package com.sasmita.musings.shoppingcartservice.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sasmita.musings.shoppingcartservice.entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
	
	@Transactional
	public void deleteByItemId(String itemId);

}
