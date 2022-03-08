package com.sasmita.musings.apigateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {


	@Bean
	public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("shopping-cart-service", p -> p.path("/cart/**").uri("lb://shopping-cart-service"))
				.route("inventory-service", p -> p.path("/inventory-service/**").uri("lb://inventory-service"))
				.route("price-service", p -> p.path("/price-service/**").uri("lb://price-service"))
				.route("payment-service", p -> p.path("/payment-service/**").uri("lb://payment-service")).build();
	}


}
