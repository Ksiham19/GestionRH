package com.example.gestionrh.Repositories;
import com.example.gestionrh.Entities.Formation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface FormationRepository extends JpaRepository<Formation, Long> {
    List<Formation> findByNomFormation(String nomFormation);
    List<Formation> findByDescriptionContaining(String description);
    List<Formation> findByDateDebutBefore(Date date);
    List<Formation> findByDateFinAfter(Date date);
}
