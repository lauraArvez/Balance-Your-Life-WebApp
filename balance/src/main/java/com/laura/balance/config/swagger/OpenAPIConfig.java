package com.laura.balance.config.swagger;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración global de OpenAPI/Swagger para la documentación de la API REST.
 *
 * <p>Define la metadata general del proyecto (nombre, versión, contacto, licencia, etc.)
 * que se muestra en Swagger UI al acceder al endpoint de documentación.</p>
 *
 * <p>Esta clase forma parte de la configuración de infraestructura de Spring
 * y se carga automáticamente al iniciar la aplicación.</p>
 */
@Configuration
public class OpenAPIConfig {

    
    /**
     * Define el objeto OpenAPI utilizado por SpringDoc para generar la documentación Swagger.
     *
     * @return instancia personalizada de OpenAPI con título, descripción, contacto y licencia
     */
    @Bean
    public OpenAPI balanceOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Balance Your Life API")
                .description("API REST para calcular, interpretar y consultar el Balance Existencial.")
                .version("1.0.0")
                .termsOfService("https://github.com/lauraArvez/balance-your-life-webapp/blob/main/TERMS.md")
                .contact(new Contact()
                    .name("Laura Arvez")
                    .email("contacto@lauraarvez.dev")
                    .url("https://lauraarvez.dev"))
                .license(new License()
                    .name("MIT License")
                    .url("https://opensource.org/licenses/MIT")))
            .externalDocs(new ExternalDocumentation()
                .description("Repositorio del proyecto y documentación adicional")
                .url("https://github.com/lauraArvez/balance-your-life-webapp"));
    }
}