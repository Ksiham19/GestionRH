package com.example.gestionrh.Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
public class Employe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmploye;
    private String cin;
    private String nom;
    private String prenom;
    private String email;
    private String mdp;
    private String telephone;
    private String adresse;
    private String dateEmbauche;
    private String poste;
    private Double salaire;
    private String competences;

    private String nomImage;

    @Lob
    @Column(name = "image", columnDefinition = "LONGBLOB")
    private byte[] image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idResponsable")
    private ResponsableRH responsableRH;

    @OneToMany(mappedBy = "employe")
    private List<DemandeDeConge> demandesConge;

    @OneToMany(mappedBy = "employe")
    private List<ParticipationFormation> participationsFormation;

    @OneToMany(mappedBy = "employe")
    private List<BulletinPaie> bulletinsPaie;
}
