package com.example.gestionrh.Entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
public class Candidat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCandidat;
    private String cin;
    private String nom;
    private String prenom;
    private String email;
    private String mdp;
    private String telephone;
    private String adresse;
    private String competences;

    @Lob
    @Column(name = "cv", columnDefinition = "LONGBLOB")
    private byte[] cv;
    private String nomcv;


    @Lob
    @Column(name = "lettreMotivation", columnDefinition = "LONGBLOB")
    private byte[] lettreMotivation;
    private String nomlettreMotivation;

    @Lob
    @Column(name = "photo", columnDefinition = "LONGBLOB")
    private byte[] photo;
    private String nomphoto;

    @OneToMany(mappedBy = "candidat")
    private List<Candidature> candidatures;

    @OneToMany(mappedBy = "candidat")
    private List<Notification> notifications;
}
