package com.example.gestionrh.Entities;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class ParticipationFormation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idParticipation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idEmploye")
    private Employe employe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idFormation")
    private Formation formation;

    private Date dateInscription;
}
