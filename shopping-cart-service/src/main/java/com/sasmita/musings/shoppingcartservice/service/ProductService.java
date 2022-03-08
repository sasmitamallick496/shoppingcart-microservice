package com.sasmita.musings.shoppingcartservice.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.sasmita.musings.shoppingcartservice.model.Product;
@Component
public class ProductService {
	
	private static List<Product> productList;
	
	
	static {
		Product product1 = 
				new Product("1234567890","FOOT BALL","FOOT BALL DESCRIPTION","http://imageurl",true);
		Product product2 = 
				new Product("1234567891","VOLLEY BALL","VOLLEY BALL DESCRIPTION","http://imageurl",true);
		Product product3 = 
				new Product("1234567892","CRICKET BALL","CRICKET BALL DESCRIPTION","http://imageurl",true);
		Product product4 = 
				new Product("1234567893","FUSE BALL","FUSE BALL DESCRIPTION","http://imageurl",true);
		Product product5 = 
				new Product("1234567894","TENNIS BALL","TENNIS BALL DESCRIPTION","http://imageurl",true);
		productList = new ArrayList<>(Arrays.asList(product1,product2,product3,product4,product5));
	}
	
	public List<Product> getProductList(){
		return productList;
	}
	
	public List<String> getProductIds(){
		return productList.stream().map(product -> product.getProductId()).collect(Collectors.toList());
		
	}

}
