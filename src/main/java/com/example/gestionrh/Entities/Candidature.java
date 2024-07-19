package com.example.gestionrh.Entities;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
public class Candidature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCandidature;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCandidat")
    private Candidat candidat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idOffre")
    private OffreEmploi offreEmploi;

    private Date dateSoumission;
    private String statutCandidature;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idResponsable")
    private ResponsableRH responsableRH;

    @OneToMany(mappedBy = "candidature")
    private List<Entretien> entretiens;
}
