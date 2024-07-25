package com.example.gestionrh.Repositories;
import com.example.gestionrh.Entities.ParticipationFormation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipationFormationRepository extends JpaRepository<ParticipationFormation, Long> {
    List<ParticipationFormation> findByEmployeIdEmploye(Long idEmploye);
    Long findByFormationIdFormation(Long idFormation);
}


