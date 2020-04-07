package co.simplon.heroes.repository;

import co.simplon.heroes.model.Hero;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Une classe pour tester les méthodes du repository. Il n'est pas nécessaire de
 * tester les méthodes héritées de CrudRepository (on fait confiance à Oracle
 * pour qu'elles fonctionnent), par contre toutes les requetes personnalisées
 * doivent être testées.
 * <p>
 * On doit aussi mocker la base de donnée, et pas utiliser la vraie BDD, dont la
 * connexion pourrait ne pas fonctionner, , ce qui empecherait de tester le
 * repository).
 *
 * @author Josselin Tobelem
 */
@DataJpaTest
public class HeroRepositoryTests {

    @Autowired
    private HeroRepository heroRepo;

    @Autowired
    private TestEntityManager testEntityManager;

    /**
     * On a ajouté une méthode dans le HeroRepository, il faut la tester.
     * <p>
     * On fait semblant d'enregistrer un objet grâce à l'entityManager (et pas
     * directement en base).
     * <p>
     * On teste ensuite que le find fonctionne.
     */
    @Test
    public void findHeroByNom() {
        Hero hero = new Hero();
        hero.setName("antman");

        // après avoir été sauvé, il se peut que son id soit mis à jour
        Hero savedHero = testEntityManager.persistFlushFind(hero);

        Optional<Hero> resultHero = this.heroRepo.findByName("antman");

        assertThat(resultHero.isPresent());
        assertThat(resultHero.get().getName()).isEqualTo(savedHero.getName());
    }
}
