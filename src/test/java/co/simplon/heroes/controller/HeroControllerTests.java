package co.simplon.heroes.controller;

import static org.mockito.ArgumentMatchers.any;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import co.simplon.heroes.model.Hero;
import co.simplon.heroes.repository.HeroRepository;

/**
 * 
 * Les tests du controller doivent vérifier que : 
 * 1. chaque endpoint du controller est bien accessible (l'url est bien écrite), 
 * 2. que le statut de la réponse (OK, NOT_FOUND, ...) est conforme à ce que l'on attend, 
 * 3. que les objets retournés sont bien sérialisés, 
 * 4. que les paramètres envoyés sont bien pris en compte
 * 
 * @author Josselin Tobelem
 *
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

//	@Autowired
//	private ObjectMapper objectMapper;

//	JacksonTester<Hero> heroJacksonTester;

//	@BeforeEach
//	public void setUp() {
//		JacksonTester.initFields(this, objectMapper);
//	}

	@Test
	public void getOne() throws Exception {
		Hero hero = new Hero();
		hero.setNom("antman");

		// on règle le repo pour qu'il retourne un objet si on cherche l'id 42
		when(this.heroRepository.findById(42)).thenReturn(Optional.of(hero));

		// 1er test qui devrait trouver un obet
		this.mockMvc.perform(get("/heroes/get?id=42")) // 1. test de l'url et 4.
		.andExpect(status().isOk()) // 2. test du statut
		.andExpect(jsonPath("nom").value(hero.getNom())); // 3. test de l'objet retourné
		
		// 2nd test qui devrait ne pas trouver d'objet
		this.mockMvc.perform(get("/heroes/get?id=13"))
		.andExpect(status().isNotFound());
	}

	@Test
	public void findOne() throws Exception {
		Hero hero = new Hero();
		hero.setNom("antman");

		// on règle le repo pour qu'il retourne un objet si on cherche le nom antman
		when(this.heroRepository.findByNom("antman")).thenReturn(Optional.of(hero));

		// 1er test qui devrait trouver un obet
		this.mockMvc.perform(get("/heroes/find?nom=antman"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("nom").value(hero.getNom()));
		
		// 2nd test qui devrait ne pas trouver d'objet
		this.mockMvc.perform(get("/heroes/find?nom=joker"))
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void getAll() throws Exception {
		
		List<Hero> allHeroes = Arrays.asList(new Hero("antman"), new Hero("catwoman"));
		
		when(this.heroRepository.findAll()).thenReturn(allHeroes);

		// TODO je n'ai pas trouvé comment tester le nb d'objet que contient la liste
		this.mockMvc.perform(get("/heroes/all"))
		.andExpect(status().isOk());
	}

	@Test
	public void addNew() throws Exception {
		Hero heroWithId = new Hero("antman");
		heroWithId.setId(42);
		
		// on simule la creation d'un id en retournant 42 quel que soit le nom du hero sauvé
		when(this.heroRepository.save(new Hero(any()))).thenReturn(heroWithId);

		// j'ai juste stocké le resultat dans un objet pour debugger, mais on peut faire comme dans les méthodes précédentes
		ResultActions result = this.mockMvc.perform(post("/heroes/add?nom=antman"));
		
		result.andExpect(status().isOk())
		.andExpect(jsonPath("id").value(heroWithId.getId()));
	}

}
