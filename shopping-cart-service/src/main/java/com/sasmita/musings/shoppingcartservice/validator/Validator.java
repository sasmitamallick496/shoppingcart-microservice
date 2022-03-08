package com.sasmita.musings.shoppingcartservice.validator;

import org.springframework.stereotype.Component;

@Component
public interface Validator<T> {
	
	default public boolean validate(T t) throws Exception {
		return Boolean.TRUE;
	}

}
