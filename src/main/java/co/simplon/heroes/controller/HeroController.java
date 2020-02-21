package co.simplon.heroes.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin(origins = "*") // deploiement avec heroku
@RequestMapping(path = "/heroes")
public class HeroController  {

	// 1- Permet d'intialiser le repo, par le mécanisme d'injection de dépendannce (IOC) (on peut commenter 1- xor 2-)
//	@Autowired
	private HeroRepository heroRepository;

	// 2- Permet d'intialiser le repo, par le mécanisme d'injection de dépendannce (IOC) (on peut commenter 1- xor 2-)
    public HeroController(HeroRepository heroRepository) {
        this.heroRepository = heroRepository;
    }

	/**
	 * Crée un nouvel hero avec le name spécifié et l'enregistre en base.
	 * @return le hero stocké en bdd (avec l'id à jour si généré)
	 */
	@PostMapping
	public @ResponseBody Hero addNew(@RequestBody Hero newHero) {
		return heroRepository.save(newHero);
	}

	/**
	 * Retourne tous les heros de la base.
	 * @return une liste de heros
	 */
	@GetMapping
	public @ResponseBody Iterable<Hero> getAll() {
		return heroRepository.findAll();
	}
	
	/**
	 * Retourne le hero d'id spécifié.
	 * @param heroID
	 * @return
	 */
	@GetMapping(path = "/{heroID}")
	public  ResponseEntity<Hero> getOne(@PathVariable int heroID) {
		Optional<Hero> optHero = heroRepository.findById(heroID);
		if (optHero.isPresent()) {
			return ResponseEntity.ok(optHero.get());
		}
		else {
			return ResponseEntity.notFound().build();
		}
	}
	
	/**
	 * Cherche un hero de name spécifié.
	 * @param name
	 * @return
	 */
	@GetMapping(path = "/find")
	public  ResponseEntity<Hero> findOne(@RequestParam String name) {
		Optional<Hero> optHero = heroRepository.findByName(name);
		if (optHero.isPresent()) {
			return ResponseEntity.ok(optHero.get());
		}
		else {
			return ResponseEntity.notFound().build();
		}
	}
}
