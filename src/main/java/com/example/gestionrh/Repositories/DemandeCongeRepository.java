package com.example.gestionrh.Repositories;
import com.example.gestionrh.Entities.DemandeDeConge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DemandeCongeRepository extends JpaRepository<DemandeDeConge, Long> {

    List<DemandeDeConge> findByStatut(String statut);
    List<DemandeDeConge> findByEmployeIdEmploye(Long idEmploye);
    List<DemandeDeConge> findByDateDebutBetween(Date start, Date end);}
