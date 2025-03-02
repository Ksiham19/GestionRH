package com.example.gestionrh.Entities;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
public class Formation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFormation;
    private String nomFormation;
    private String description;
    private Date dateDebut;
    private Date dateFin;

    private String nomImage;

    @Lob
    @Column(name = "image", columnDefinition = "LONGBLOB")
    private byte[] image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idResponsable")
    private ResponsableRH responsableRH;

    @OneToMany(mappedBy = "formation")
    private List<ParticipationFormation> participations;

    private String statut;
}
