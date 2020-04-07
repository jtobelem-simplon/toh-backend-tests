package co.simplon.heroes.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Une simple classe pour représenter un héro : un nom et un id.
 *
 * @author Josselin Tobelem
 */
@Entity
@Getter
@Setter
// le fait d'avoir créée un constructeur avec un paramètre oblige à écrire
// explicitement le constructeur sans paramètre
// https://docs.oracle.com/javase/specs/jls/se7/html/jls-8.html#jls-8.8.9

// en effet, le constructuer sans paramètre est utilisé par spring
// https://stackoverflow.com/questions/18099127/java-entity-why-do-i-need-an-empty-constructor
@NoArgsConstructor
@Table(name = "heroes") // par defaut, hibernate crée une table appelée le nom de la classe au pluriel
// (ici ça ne marche pas, car le pluriel de Hero est heroes...)
public class Hero {

    @Id
    // Differents réglages pour la génération de la pk : à étudier
    // IDENTITY : on délègue à la bdd l'action de générer l'id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;


    /**
     * Construit un hero avec le nom spécifié.
     *
     * @param name
     */
    public Hero(String name) {
        this.name = name;
    }

}
