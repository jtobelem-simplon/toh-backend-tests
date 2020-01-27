package co.simplon.heroes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import co.simplon.heroes.repository.HeroRepository;

@SpringBootApplication
public class HeroesApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(HeroesApplication.class, args);
	}

}
