package com.example.gestionrh.Repositories;
import com.example.gestionrh.Entities.Entretien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EntretienRepository extends JpaRepository<Entretien, Long> {

    List<Entretien> findByDateEntretienBetween(Date start, Date end);
    List<Entretien> findByLieu(String lieu);
    List<Entretien> findByCandidatureIdCandidature(Long idCandidature);}
