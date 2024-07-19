package com.example.gestionrh.Repositories;
import com.example.gestionrh.Entities.OffreEmploi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OffreEmploiRepository extends JpaRepository<OffreEmploi, Long> {
    List<OffreEmploi> findByTitre(String titre);
    List<OffreEmploi> findByType(String type);
    List<OffreEmploi> findByCompetencesRequisesContaining(String competence);
    List<OffreEmploi> findByStatut(String statut);
}
