package co.simplon.heroes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import co.simplon.heroes.repository.HeroRepository;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HeroesApplication {

    private final Logger logger = LoggerFactory.getLogger(HeroesApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(HeroesApplication.class, args);
    }


    // TODO cette méthode fait échouer les tests du repo ..
    @Bean
    CommandLineRunner initDatabase(final DataInitializer initializer) {
        return new CommandLineRunner() {
            @Override
            public void run(String... arg0) throws Exception {
                logger.info(
                        "\n ******** Initializing Data ***********");
                initializer.initData();

                logger.info(
                        "\n ******** Data initialized ***********");
            }
        };
    }
}
