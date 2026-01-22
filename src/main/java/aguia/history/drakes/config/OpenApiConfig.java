package aguia.history.drakes.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Águia History API")
                        .version("1.0")
                        .description("Documentação da API para gestão de histórico de partidas de futebol.")
                        .contact(new Contact()
                                .name("Isaac Santos")
                                .email("isaacsantos7953@gmail.com")));
   
                            }
}