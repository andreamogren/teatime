package org.example.teatime;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "My API", version = "v1"))
public class teatimeApplication {

    public static void main(String[] args) {
        SpringApplication.run(teatimeApplication.class, args);
    }

}
