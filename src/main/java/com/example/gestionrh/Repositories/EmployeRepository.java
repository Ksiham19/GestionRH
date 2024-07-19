package com.example.gestionrh.Repositories;
import com.example.gestionrh.Entities.Employe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeRepository extends JpaRepository<Employe, Long> {
    List<Employe> findByNom(String nom);
    List<Employe> findByCin(String cin);
    List<Employe> findByPrenom(String prenom);
    Employe findByEmail(String email);
    List<Employe> findByPoste(String poste);

    Optional<Employe> findByEmailAndCin(String email, String cin);
}
