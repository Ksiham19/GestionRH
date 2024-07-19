package com.example.gestionrh.Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Entretien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEntretien;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCandidature")
    private Candidature candidature;
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idResponsable")
    private ResponsableRH responsableRH;

    private Date dateEntretien;
    private String lieu;
    private String feedback;
}
