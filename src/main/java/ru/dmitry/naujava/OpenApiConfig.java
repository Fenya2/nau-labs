package ru.dmitry.naujava;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Coin observer",
                description = "App to keep track of finances", version = "0.0.1",
                contact = @Contact(
                        name = "fenya",
                        email = "fenya74.09@gmail.com"
                )
        )
)
public class OpenApiConfig {

}