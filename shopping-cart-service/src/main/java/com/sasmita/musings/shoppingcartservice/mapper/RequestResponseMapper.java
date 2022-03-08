package com.sasmita.musings.shoppingcartservice.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import com.sasmita.musings.shoppingcartservice.entity.Cart;
import com.sasmita.musings.shoppingcartservice.model.CartDetailsDTO;
import com.sasmita.musings.shoppingcartservice.model.ShoppingCartRequest;

@Component
@Mapper(componentModel = "spring")
public interface RequestResponseMapper {
	Cart requestToCart(ShoppingCartRequest request);
	CartDetailsDTO cartToResponse(Cart cart);
}
