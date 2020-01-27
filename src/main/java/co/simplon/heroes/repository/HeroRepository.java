package co.simplon.heroes.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.simplon.heroes.model.Hero;

/**
 * Le repository des heros, on ajoute simplement une méthode de recherche par nom.
 * 
 * @author Josselin Tobelem
 *
 */
// l'héritage de CrudRepo donne des méthodes de base : save, findById, findAll, etc ...
public interface HeroRepository extends CrudRepository<Hero, Integer> {
	
	// on peut générer automatiquement des méthodes de recherche dans le repository
	// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
	Optional<Hero> findByName(String nom);
}
