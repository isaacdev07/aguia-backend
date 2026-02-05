package aguia.history.drakes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "aguia.history.drakes")
public class DrakesApplication {

	public static void main(String[] args) {
		SpringApplication.run(DrakesApplication.class, args);
	}

}
