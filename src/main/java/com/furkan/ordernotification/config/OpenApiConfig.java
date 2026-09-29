package com.furkan.ordernotification.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;


@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI orderNotificationOpenAPI() {

        return new OpenAPI()
            .info(
                new Info()
                    .title("Order Notification System API")
                    .version("1.0.0")
                    .description(
                        "Spring Boot, PostgreSQL ve Observer Pattern "
                        + "kullanılarak geliştirilen sipariş yönetim API'si."
                    )
            );
    }
}