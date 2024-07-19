package com.example.gestionrh.Repositories;
import com.example.gestionrh.Entities.Candidat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CandidatRepository extends JpaRepository<Candidat, Long> {
    List<Candidat> findByNom(String nom);
    List<Candidat> findByCin(String cin);
    List<Candidat> findByPrenom(String prenom);
    List<Candidat> findByCompetencesContaining(String competence);

    Candidat findByEmail(String enteredEmail);

    Optional<Candidat> findByEmailAndCin(String email, String cin);
}
