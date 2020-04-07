package co.simplon.heroes.controller;

import co.simplon.heroes.model.Hero;
import co.simplon.heroes.repository.HeroRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Les tests du controller doivent vérifier que :
 * 1. chaque endpoint du controller est bien accessible (l'url est bien écrite),
 * 2. que le statut de la réponse (OK, NOT_FOUND, ...) est conforme à ce que l'on attend,
 * 3. que les objets retournés sont bien sérialisés,
 * 4. que les paramètres envoyés sont bien pris en compte
 *
 * @author Josselin Tobelem
 */
@SpringBootTest
@AutoConfigureMockMvc
public class HeroControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    HeroRepository heroRepository;

    // Les lignes de code suivantes (objectMapper, heroJacksonTester et setUp)
    // servent dans le cas où l'on utilise des dto comme paramètres de méthodes
    // post, on peut ainsi générer facilement les json à partir d'objet java
    // (donc facile d'écrire un json pour les tests).

    @Autowired
    private ObjectMapper objectMapper;

    JacksonTester<Hero> heroJacksonTester;

    @BeforeEach
    public void setUp() {
        JacksonTester.initFields(this, objectMapper);
    }

    @Test
    public void getOne() throws Exception {
        Hero hero = new Hero();
        hero.setName("antman");

        // on règle le repo pour qu'il retourne un objet si on cherche l'id 42
        when(this.heroRepository.findById(42)).thenReturn(Optional.of(hero));

        // 1er test qui devrait trouver un obet
        this.mockMvc.perform(get("/heroes/42")) // 1. test de l'url et 4.
                .andExpect(status().isOk()) // 2. test du statut
                .andExpect(jsonPath("name").value(hero.getName())); // 3. test de l'objet retourné

        // 2nd test qui devrait ne pas trouver d'objet
        this.mockMvc.perform(get("/heroes/13"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findOne() throws Exception {
        Hero hero = new Hero();
        hero.setName("antman");

        // on règle le repo pour qu'il retourne un objet si on cherche le nom antman
        when(this.heroRepository.findByName("antman")).thenReturn(Optional.of(hero));

        // 1er test qui devrait trouver un obet
        this.mockMvc.perform(get("/heroes/find?name=antman"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(hero.getName()));

        // 2nd test qui devrait ne pas trouver d'objet
        this.mockMvc.perform(get("/heroes/find?name=joker"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAll() throws Exception {

        List<Hero> allHeroes = Arrays.asList(new Hero("antman"), new Hero("catwoman"));

        when(this.heroRepository.findAll()).thenReturn(allHeroes);

        // TODO je n'ai pas trouvé comment tester le nb d'objet que contient la liste
        this.mockMvc.perform(get("/heroes"))
                .andExpect(status().isOk());
    }

    @Test
    public void addNew() throws Exception {
        Hero heroWithId = new Hero("antman");
        heroWithId.setId(42);

        // on simule la creation d'un id en retournant 42 quel que soit le nom du hero sauvé
        when(this.heroRepository.save(new Hero(any()))).thenReturn(heroWithId);

        // on va créer un objet hero, puis le serialiser en json (comme ferait le front s'il voulait envoyer un hero au controller)
        Hero heroWithoutId = new Hero("antman");
        String jsonContent = heroJacksonTester.write(heroWithoutId).getJson();

        ResultActions result = this.mockMvc.perform(post("/heroes").contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonContent));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("id").value(heroWithId.getId()));
    }

}
