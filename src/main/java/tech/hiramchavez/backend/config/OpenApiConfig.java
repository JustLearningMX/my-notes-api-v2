package tech.hiramchavez.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
public class OpenApiConfig {

    @Value("${todolist.openapi.dev-url}")
    private String devUrl;

    @Value("${todolist.openapi.prod-url}")
    private String prodUrl;

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment");

        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("Server URL in Production environment");

        Contact contact = new Contact();
        contact.setEmail("hiramchavezlopez@gmail.com");
        contact.setName("Hiram Chavez");
        contact.setUrl("https://hiram-oci-mty.duckdns.org/");

        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
          .title("API Rest of Notes v2 application")
          .version("1.0")
          .contact(contact)
          .description("This API Rest allows the management of Notes.")
          .license(mitLicense);

        return new OpenAPI()
          .info(info)
          .servers(
            List.of(prodServer, devServer)
          );
    }

}
