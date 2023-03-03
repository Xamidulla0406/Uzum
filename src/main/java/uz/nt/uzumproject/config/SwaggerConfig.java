package uz.nt.uzumproject.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "Authorization",
        in = SecuritySchemeIn.HEADER,
        type = SecuritySchemeType.APIKEY
)
@OpenAPIDefinition(
        info = @Info(
                title = "API Documentation for Uzum Market",
                description = "Uzum market is set of products....",
                termsOfService = "www.example.com",
                contact = @Contact(name = "Abduvohid Ergashev", email = "abduvohid0131@gmail.com", url = "https://t.me/wwis_mwrwq"),
                version = "1.0.0",
                license = @License(name = "MIT", url = "https://choosealicense.com/licenses/mit/")
        ),
        security = @SecurityRequirement(name = "Authorization")
)
public class SwaggerConfig {
}
