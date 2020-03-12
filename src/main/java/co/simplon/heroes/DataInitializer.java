package co.simplon.heroes;

import co.simplon.heroes.model.Hero;
import co.simplon.heroes.model.Role;
import co.simplon.heroes.model.User;
import co.simplon.heroes.repository.HeroRepository;
import co.simplon.heroes.repository.RoleRepository;
import co.simplon.heroes.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Cette classe est chargée au lancement de l'application. Elle sert à
 * enregistrer des données en base.
 * 
 * @author Josselin Tobelem
 *
 */
@Component
public class DataInitializer {

	private final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

	private HeroRepository heroRepository;
	private RoleRepository roleRepository;
	private UserRepository userRepository;

	public DataInitializer(final HeroRepository heroRepository, final RoleRepository roleRepository,
						   final UserRepository userRepository) {
		this.heroRepository = heroRepository;
		this.roleRepository = roleRepository;
		this.userRepository = userRepository;
	}

	/**
	 * Crée des categories, des titres et des auteurs et les fait persister en base
	 * par les repositories.
	 */
	public void initData() {

		try {
			Set<Hero> heroes = new HashSet<>();
			heroes.add(new Hero("Bombasto"));
			heroes.add(new Hero("Celeritas"));
			heroes.add(new Hero("Dr IQ"));
			heroes.add(new Hero("Dr Nice"));
			heroes.add(new Hero("Dynama"));
			heroes.add(new Hero("Magma"));
			heroes.add(new Hero("Magneta"));
			heroes.add(new Hero("Narco"));
			heroes.add(new Hero("RubberMan"));
			heroes.add(new Hero("Tornado"));

			if (!heroRepository.findAll().iterator().hasNext()) {
				heroRepository.saveAll(heroes);
			}

			Role userRole = new Role("USER");
			Role adminRole = new Role("ADMIN");

			Set<User> users = new HashSet<>();
			users.add(new User("titi", "secret", userRole));
			users.add(new User("capo", "azerty123",adminRole));

			if (!roleRepository.findAll().iterator().hasNext()) {
				roleRepository.saveAll(Arrays.asList(userRole, adminRole));
			}
			if (!userRepository.findAll().iterator().hasNext()) {
				userRepository.saveAll(users);
			}

		} catch (final Exception ex) {
			logger.error("Exception while inserting mock data {}", ex);
		}

	}

}
