package co.simplon.heroes;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class HeroesApplicationTests {

    /**
     * Teste simplement que l'application se lance sans erreur. A exectuer en premier.
     */
    @Test
    void contextLoads() {
        ConfigurableApplicationContext context = SpringApplication.run(HeroesApplication.class);
        assertTrue(context.isRunning());
        context.close();
    }

}
