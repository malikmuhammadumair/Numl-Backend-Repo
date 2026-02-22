package cofee.example.cofee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class CofeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CofeeApplication.class, args);
	}

}
