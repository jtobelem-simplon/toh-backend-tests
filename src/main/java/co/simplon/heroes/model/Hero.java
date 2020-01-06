package co.simplon.heroes.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Une simple classe pour représenter un héro : un nom et un id.
 * 
 * @author Josselin Tobelem
 *
 */
@Entity
@Table(name = "heroes") // par defaut, hibernate crée une table appelée le nom de la classe au pluriel
						// (ici ça ne marche pas, car le pluriel de Hero est heroes...)
public class Hero {

	@Id
	// Differents réglages pour la génération de la pk : à étudier
	// IDENTITY : on délègue à la bdd l'action de générer l'id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nom;

	public Hero() {
		// le fait d'avoir créée un constructeur avec un paramètre oblige à écrire
		// explicitement le constructeur sans paramètre
		// https://docs.oracle.com/javase/specs/jls/se7/html/jls-8.html#jls-8.8.9
		
		// en effet, le constructuer sans paramètre est utilisé par spring
		// https://stackoverflow.com/questions/18099127/java-entity-why-do-i-need-an-empty-constructor
	}
	
	/**
	 * Construit un hero avec le nom spécifié.
	 * 
	 * @param nom
	 */
	public Hero(String nom) {
		this.nom = nom;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

}
