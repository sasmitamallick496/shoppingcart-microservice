package com.sasmita.musings.shoppingcartservice.service;

import static com.sasmita.musings.shoppingcartservice.util.ShoppingCartConstants.CART_NOT_FOUND_EXCEPTION_MSG;
import static com.sasmita.musings.shoppingcartservice.util.ShoppingCartConstants.INVENTORY_UNAVAILBALE_EXCEPTION_MSG;
import static com.sasmita.musings.shoppingcartservice.util.ShoppingCartConstants.PRODUCT_NOT_FOUND_EXCEPTION_MSG;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sasmita.musings.shoppingcartservice.entity.Cart;
import com.sasmita.musings.shoppingcartservice.entity.Item;
import com.sasmita.musings.shoppingcartservice.exception.CartNotFoundException;
import com.sasmita.musings.shoppingcartservice.exception.InventoryNotAvailableException;
import com.sasmita.musings.shoppingcartservice.exception.ProductNotFoundException;
import com.sasmita.musings.shoppingcartservice.mapper.RequestResponseMapper;
import com.sasmita.musings.shoppingcartservice.model.CartDetailsDTO;
import com.sasmita.musings.shoppingcartservice.model.PriceInfo;
import com.sasmita.musings.shoppingcartservice.model.ResponseTemplate;
import com.sasmita.musings.shoppingcartservice.model.ShoppingCartRequest;
import com.sasmita.musings.shoppingcartservice.model.ShoppingCartResponse;
import com.sasmita.musings.shoppingcartservice.model.ShoppingCartStatus;
import com.sasmita.musings.shoppingcartservice.proxy.PriceProxy;
import com.sasmita.musings.shoppingcartservice.repository.ShoppingCartRepository;
import com.sasmita.musings.shoppingcartservice.validator.InventoryValidator;
import com.sasmita.musings.shoppingcartservice.validator.ProductValidator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Service
@AllArgsConstructor
public class ShoppingCartService {

	private @Getter ShoppingCartRepository repository;

	private @Getter ProductValidator productValidator;

	private @Getter InventoryValidator inventoryValidator;

	private @Getter PriceProxy priceProxy;
	
	private @Getter RequestResponseMapper mapper;

	public Cart createCart(ShoppingCartRequest request) {
		return getRepository().save(requestToCartMapping(request));
	}

	public ShoppingCartResponse getCartDetails(UUID cartId) {
		Cart cart = getCartById(cartId);
		cart.setCartStatus(ShoppingCartStatus.ACTIVE);
		return new ShoppingCartResponse(ResponseTemplate.StatusEnum.SUCCESS, "SS_001",
				"Cart details fetched successfully", cartToResponseMapping(cart));
	}

	public ShoppingCartResponse addItem(UUID cartId, String productId, int quantity) throws Exception {
		Cart cart = getCartById(cartId);

		validateItemProduct(productId);
		validateItemInventory(productId);

		Optional<Item> itemOptional = findItemByProductId(cart, productId);
		if (itemOptional.isPresent()) {
			Item item = itemOptional.get();
			item.setQuantity(quantity);
		} else {
			Item newItem = createItem(productId, quantity).get();
			cart.addItem(newItem);
		}
		Cart savedCart =  getRepository().save(cart);
		return new ShoppingCartResponse(ResponseTemplate.StatusEnum.SUCCESS, "SS_001",
				"Item added successfully", cartToResponseMapping(savedCart));
	}
	
	public Item updateItem(UUID cartId, String productId, int quantity) throws Exception {

		validateItemProduct(productId);
		validateItemInventory(productId);
		Cart cart = getCartById(cartId);
		Optional<Item> optionalItem = findItemByProductId(cart, productId);
		Item item = optionalItem.get();
		item.setQuantity(quantity);
		item.setAmount(item.getUnitPrice().multiply(new BigDecimal(quantity)));
		getRepository().save(cart);
		return item;
	}

	public void deleteCart(UUID cartId) {
		getRepository().deleteById(cartId);
	}

	public void deleteItem(UUID cartId, String productId) {

		Cart cart = getCartById(cartId);

		Optional<Item> deleteItem = findItemByProductId(cart, productId);
		cart.removeItem(deleteItem.get());
		getRepository().save(cart);
	}
	public ShoppingCartResponse submitCart(ShoppingCartRequest request) {
		Cart savedCart = getRepository().save(requestToCartMapping(request));
		return new ShoppingCartResponse(ResponseTemplate.StatusEnum.SUCCESS, "SS_001",
				"Cart submitted successfully", cartToResponseMapping(savedCart));
	}

	private Cart getCartById(UUID cartId) {
		return getRepository().findByCartId(cartId).stream().findFirst()
				.orElseThrow(() -> new CartNotFoundException(CART_NOT_FOUND_EXCEPTION_MSG + cartId));
	}

	private Optional<Item> createItem(String productId, int quantity) throws Exception {
		PriceInfo priceInfo = getPriceProxy().retrievePriceById(productId);
		Item item = new Item();
		item.setProductId(productId);
		item.setQuantity(quantity);
		item.setUnitPrice(priceInfo.getListPrice());
		item.setAmount(item.getUnitPrice().multiply(new BigDecimal(quantity)));
		return Optional.of(item);
	}

	private void validateItemProduct(String productId) {
		if (!getProductValidator().validate(productId)) {
			throw new ProductNotFoundException(PRODUCT_NOT_FOUND_EXCEPTION_MSG + productId);
		}
	}

	private void validateItemInventory(String productId) throws Exception {
		if (!getInventoryValidator().validate(productId)) {
			throw new InventoryNotAvailableException(INVENTORY_UNAVAILBALE_EXCEPTION_MSG + productId);
		}
	}

	private Optional<Item> findItemByProductId(Cart cart, String productId) {
		return cart.getItems().stream().filter(item -> item.getProductId().equals(productId)).findFirst();

	}
	
	private Cart requestToCartMapping(ShoppingCartRequest request) {
		return getMapper().requestToCart(request);
	}
	
	private CartDetailsDTO cartToResponseMapping(Cart cart) {
		return getMapper().cartToResponse(cart);
		
	}
}
