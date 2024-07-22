package com.example.gestionrh.Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class DemandeDeConge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDemande;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idEmploye")
    private Employe employe;

    private Date dateDebut;
    private Date dateFin;
    private String statut;


}
