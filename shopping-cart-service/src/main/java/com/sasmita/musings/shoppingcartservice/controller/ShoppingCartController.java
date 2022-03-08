package com.sasmita.musings.shoppingcartservice.controller;

import java.net.URI;
import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sasmita.musings.shoppingcartservice.entity.Cart;
import com.sasmita.musings.shoppingcartservice.entity.Item;
import com.sasmita.musings.shoppingcartservice.kafka.producer.EmailProducer;
import com.sasmita.musings.shoppingcartservice.model.ShoppingCartRequest;
import com.sasmita.musings.shoppingcartservice.model.ShoppingCartResponse;
import com.sasmita.musings.shoppingcartservice.model.ShoppingCartStatus;
import com.sasmita.musings.shoppingcartservice.model.SubmitOrderContext;
import com.sasmita.musings.shoppingcartservice.saga.SubmitCartSagaExecutor;
import com.sasmita.musings.shoppingcartservice.service.ShoppingCartService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@Validated
@Slf4j
public class ShoppingCartController {

	private @Getter ShoppingCartService cartService;
	
	private @Getter SubmitCartSagaExecutor executor;
	
	private @Getter EmailProducer emailProducer;

	@PostMapping("/cart")
	public ResponseEntity<Object> createCart(@RequestBody ShoppingCartRequest request) {
		log.info("inside create cart");

		if (Objects.isNull(request.getCartId())) {
			request.setCartId(UUID.randomUUID());
		}
		Cart savedCart = getCartService().createCart(request);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedCart.getCartId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@GetMapping("/cart/{cartId}")
	public ResponseEntity<ShoppingCartResponse> getCartDetails(@PathVariable("cartId") UUID cartId) {
		ShoppingCartResponse cartDetails = getCartService().getCartDetails(cartId);
		return new ResponseEntity<ShoppingCartResponse>(cartDetails, HttpStatus.OK);
	}

	@PostMapping("/cart/{cartId}/add-item/{productId}/{quantity}")
	public ResponseEntity<ShoppingCartResponse> addItem(@PathVariable("cartId") UUID cartId,
			@PathVariable("productId") @NotBlank String productId, @PathVariable("quantity") @Min(1) int quantity)
			throws Exception {
		ShoppingCartResponse savedCart = getCartService().addItem(cartId, productId, quantity);
		//URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedCart.getCartId()).toUri();
		return new ResponseEntity<ShoppingCartResponse>(savedCart,HttpStatus.CREATED);
	}
	
	@PostMapping("/cart/submit-order")
	public ResponseEntity<ShoppingCartResponse> submitOrder(@RequestBody ShoppingCartRequest request) throws Throwable{
		SubmitOrderContext context = new SubmitOrderContext();
		context.setRequest(request);
		getExecutor().execute(context);
		request.setCartStatus(ShoppingCartStatus.SUBMITTED);
		ShoppingCartResponse response = getCartService().submitCart(request);
		emailProducer.sendEmail(response.getCartDetails());
		return new ResponseEntity<ShoppingCartResponse>(response,HttpStatus.OK);
	}

	@PutMapping("/cart/{cartId}/update-item/{productId}/{quantity}")
	public ResponseEntity<Item> updateItem(@PathVariable("cartId") UUID cartId,
			@PathVariable("productId") @NotBlank String productId, @PathVariable("quantity") @Min(1) int quantity) throws Exception {
		Item updatedItem = getCartService().updateItem(cartId, productId, quantity);
		return ResponseEntity.ok(updatedItem);
	}

	@DeleteMapping("/cart/{cartId}/delete-item/{productId}")
	public ResponseEntity<Object> deleteItem(@PathVariable("cartId") UUID cartId,
			@PathVariable("productId") @NotBlank String productId) {
		getCartService().deleteItem(cartId, productId);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/cart/{cartId}")
	public ResponseEntity<Object> deleteCart(@PathVariable("cartId") UUID cartId) {
		getCartService().deleteCart(cartId);
		return ResponseEntity.noContent().build();
	}
}
