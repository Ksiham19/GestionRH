package com.example.gestionrh.Entities;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class ResponsableRH {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idResponsable;
    private String cin;
    private String nom;
    private String prenom;
    private String email;
    private String mdp;
    private String telephone;

    @OneToMany(mappedBy = "responsableRH")
    private List<OffreEmploi> offresEmploi;

    @OneToMany(mappedBy = "responsableRH")
    private List<Candidature> candidatures;

    @OneToMany(mappedBy = "responsableRH")
    private List<Formation> formations;

    @OneToMany(mappedBy = "responsableRH")
    private List<Entretien> entretiens;
}
