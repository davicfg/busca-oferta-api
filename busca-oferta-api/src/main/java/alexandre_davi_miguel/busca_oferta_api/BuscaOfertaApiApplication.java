package alexandre_davi_miguel.busca_oferta_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BuscaOfertaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BuscaOfertaApiApplication.class, args);
	}

}
