package alexandre_davi_miguel.catalogo_pecas_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CatalogoPecasApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatalogoPecasApiApplication.class, args);
	}

}
