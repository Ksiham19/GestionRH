package com.example.gestionrh.Repositories;

import com.example.gestionrh.Entities.Candidature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidatureRepository extends JpaRepository<Candidature, Long> {
    List<Candidature> findByStatutCandidature(String statut);
    List<Candidature> findByCandidatIdCandidat(Long idCandidat);
    List<Candidature> findByOffreEmploiIdOffre(Long idOffre);
}
