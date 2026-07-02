package alexandre_davi_miguel.corretora_imoveis_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = {"alexandre_davi_miguel.corretora_imoveis_api", "alexandre_davi_miguel.busca_oferta_api.framework"})
public class CorretoraImoveisApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CorretoraImoveisApiApplication.class, args);
	}

}
