package com.example.gestionrh.Repositories;
import com.example.gestionrh.Entities.ResponsableRH;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResponsableRHRepository extends JpaRepository<ResponsableRH, Long> {
    List<ResponsableRH> findByNom(String nom);
    List<ResponsableRH> findByCin(String cin);
    List<ResponsableRH> findByPrenom(String prenom);
    ResponsableRH findByEmail(String email);

    Optional<ResponsableRH> findByEmailAndCin(String email, String cin);
}
