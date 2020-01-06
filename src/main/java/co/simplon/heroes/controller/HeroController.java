package co.simplon.heroes.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.simplon.heroes.model.Hero;
import co.simplon.heroes.repository.HeroRepository;

/**
 * 
 * Quatre points d'accès pour le controller : add, get, all, find.
 * 
 * @author Josselin Tobelem
 *
 */
@RestController
@RequestMapping(path = "/heroes") 
public class HeroController {

	@Autowired
	private HeroRepository heroRepository;

	/**
	 * Crée un nouvel hero avec le nom spécifié et l'enregistre en base.
	 * @param nom
	 * @return le hero stocké en bdd (avec l'id à jour si généré)
	 */
	@PostMapping(path = "/add") 
	public Hero addNew(@RequestParam String nom) {
		Hero newHero = new Hero();
		newHero.setNom(nom);
		return heroRepository.save(newHero);
	}

	/**
	 * Retourne tous les heros de la base.
	 * @return une liste de heros
	 */
	@GetMapping(path = "/all")
	public @ResponseBody Iterable<Hero> getAll() {
		return heroRepository.findAll();
	}
	
	/**
	 * Retourne le hero d'id spécifié.
	 * @param id
	 * @return
	 */
	@GetMapping(path = "/get")
	public  ResponseEntity<Hero> getOne(@RequestParam int id) {
		Optional<Hero> optHero = heroRepository.findById(id);
		if (optHero.isPresent()) {
			return ResponseEntity.ok(optHero.get());
		}
		else {
			return ResponseEntity.notFound().build();
		}
	}
	
	/**
	 * Cherche un hero de nom spécifié.
	 * @param nom
	 * @return
	 */
	@GetMapping(path = "/find")
	public  ResponseEntity<Hero> findOne(@RequestParam String nom) {
		Optional<Hero> optHero = heroRepository.findByNom(nom);
		if (optHero.isPresent()) {
			return ResponseEntity.ok(optHero.get());
		}
		else {
			return ResponseEntity.notFound().build();
		}
	}
}
