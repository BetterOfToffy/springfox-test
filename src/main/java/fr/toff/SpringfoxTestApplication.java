package fr.toff;

import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;

@SpringBootApplication
public class SpringfoxTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringfoxTestApplication.class, args);
	}

	@Bean
	public SimpleUrlHandlerMapping hyperiaSimpleUrlHandlerMapping() {
		
		SimpleUrlHandlerMapping suhm = new SimpleUrlHandlerMapping();
		suhm.setOrder(Ordered.HIGHEST_PRECEDENCE);
		Properties mapping = new Properties();
				
		mapping.put("/item/name", "itemController");
		mapping.put("/item/number", "itemController");
		
		suhm.setMappings(mapping);
		return suhm;
	}
}
