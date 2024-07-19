package com.example.gestionrh.Repositories;
import com.example.gestionrh.Entities.BulletinPaie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BulletinPaieRepository extends JpaRepository<BulletinPaie, Long> {
    List<BulletinPaie> findByMoisAnnee(String moisAnnee);
    List<BulletinPaie> findByEmployeIdEmploye(Long idEmploye);
    List<BulletinPaie> findByMontantGreaterThanEqual(Double montant);
}
