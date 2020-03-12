package co.simplon.heroes.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String nom;

    private String mdp;

    @ManyToOne
    private Role role;

    public User(String nom, String mdp, Role role) {
        this.nom = nom;
        this.mdp = mdp;
        this.role = role;
    }
}
