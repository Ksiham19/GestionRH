package com.example.gestionrh.Entities;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNotification;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCandidat")
    private Candidat candidat;

    private String typeNotification;
    private String message;
    private Date dateEnvoi;
}
