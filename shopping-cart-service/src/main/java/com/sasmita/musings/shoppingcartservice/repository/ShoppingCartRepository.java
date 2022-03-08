package com.sasmita.musings.shoppingcartservice.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sasmita.musings.shoppingcartservice.entity.Cart;

@Repository
public interface ShoppingCartRepository extends JpaRepository<Cart, UUID> {

	public List<Cart> findByCartId(UUID cartId);
	
	
}
