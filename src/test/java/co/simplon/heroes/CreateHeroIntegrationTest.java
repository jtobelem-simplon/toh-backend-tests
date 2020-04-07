package co.simplon.heroes;

import co.simplon.heroes.model.Hero;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CreateHeroIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * Crée un héro en utilisant l'api puis essaye de retrouver le héro crée.
     * <p>
     * Le repository et la base ne sont pas mockés, donc on teste que le backend dans sa globalité fonctionne.
     */
    @Test
    public void createAndFindHero() {
        HashMap<String, String> params = new HashMap<>();
        params.put("nom", "antman");

        // TODO le passage de paramètre ne me semble pas très propre, je n'ai pas trouvé
        // comment utiliser le param request
        ResponseEntity<Hero> responseEntityCreate = restTemplate.postForEntity("/heroes/add?name=antman", null,
                Hero.class);
        Hero hero = responseEntityCreate.getBody();

        assertEquals(HttpStatus.OK, responseEntityCreate.getStatusCode());
        assertEquals("antman", hero.getName());

        // TODO le passage de paramètre ne me semble pas très propre, je n'ai pas trouvé
        // comment utiliser le param request
        ResponseEntity<Hero> responseEntityFind = restTemplate.getForEntity("/heroes/find?name=antman", null,
                Hero.class);
        hero = responseEntityCreate.getBody();

        assertEquals(HttpStatus.OK, responseEntityFind.getStatusCode());
        assertEquals("antman", hero.getName());
    }
}
