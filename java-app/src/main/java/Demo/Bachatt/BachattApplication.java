package Demo.Bachatt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BachattApplication {

	public static void main(String[] args) {
		SpringApplication.run(BachattApplication.class, args);
	}

}
