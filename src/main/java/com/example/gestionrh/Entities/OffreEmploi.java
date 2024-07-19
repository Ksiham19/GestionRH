package com.example.gestionrh.Entities;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
public class OffreEmploi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOffre;
    private String titre;
    private String description;
    private String competencesRequises;
    private Date datePublication;
    private String statut;
    private String type;

    @Lob
    @Column(name = "image", columnDefinition = "LONGBLOB")
    private byte[] image;
    private String nomImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idResponsable")
    private ResponsableRH responsableRH;

    @OneToMany(mappedBy = "offreEmploi")
    private List<Candidature> candidatures;
}
