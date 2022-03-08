package com.sasmita.musings.shoppingcartservice.saga;

public interface Toggle {
	
	default boolean isEnabled() {
		return Boolean.TRUE;
	}

}
