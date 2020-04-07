package com.everis.bootcamp.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableEurekaClient
@EnableHystrix
@Configuration
public class BeanConfig {

  /**
   * BeanConfig.
   */
  @Bean
  public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
    return builder.routes()
        .route(r -> r.path("/client/**")
            .filters(f -> f.hystrix(c -> c.setName("client")
                .setFallbackUri("forward:/fallback/message")))
            .uri("lb://CLIENTMS/")
            .id("clientms"))

        .route(r -> r.path("/creditprod/**")
            .filters(f -> f.hystrix(c -> c.setName("creditprod")
                .setFallbackUri("forward:/fallback/message")))
            .uri("lb://CREDITPRODUCTMS/")
            .id("creditproductms"))

        .route(r -> r.path("/bankprod/**")
            .filters(f -> f.hystrix(c -> c.setName("bankprod")
                .setFallbackUri("forward:/fallback/message")))
            .uri("lb://BANKPRODUCTMS/")
            .id("bankproductms"))

        .route(r -> r.path("/bank/**")
            .filters(f -> f.hystrix(c -> c.setName("bank")
                .setFallbackUri("forward:/fallback/message")))
            .uri("lb://BANKMS/")
            .id("bankms"))
        .build();
  }
}