package com.example.gestionrh.Services;
import com.example.gestionrh.Entities.Candidat;
import com.example.gestionrh.Entities.Employe;
import com.example.gestionrh.Entities.ResponsableRH;
import com.example.gestionrh.Repositories.ResponsableRHRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResponsableRHService {
    @Autowired
    private ResponsableRHRepository repository;

    public List<ResponsableRH> getAllResponsables() {
        return repository.findAll();
    }

    public Optional<ResponsableRH> getResponsableById(Long id) {
        return repository.findById(id);
    }

    public ResponsableRH createResponsable(ResponsableRH responsable) {
        return repository.save(responsable);
    }

    public ResponsableRH updateResponsable(Long id, ResponsableRH responsableDetails) {
        ResponsableRH responsable = repository.findById(id).orElseThrow(() -> new RuntimeException("Responsable not found"));
        responsable.setNom(responsableDetails.getNom());
        responsable.setPrenom(responsableDetails.getPrenom());
        responsable.setEmail(responsableDetails.getEmail());
        responsable.setTelephone(responsableDetails.getTelephone());
        return repository.save(responsable);
    }

    public void deleteResponsable(Long id) {
        repository.deleteById(id);
    }

    public List<ResponsableRH> getResponsablesByNom(String nom) {
        return repository.findByNom(nom);
    }
    public List<ResponsableRH> getResponsablesByCin(String cin) {
        return repository.findByCin(cin);
    }

    public boolean authenticate(String enteredEmail, String enteredPassword) {
        ResponsableRH respo = repository.findByEmail(enteredEmail);
        return respo != null && respo.getMdp().equals(enteredPassword);
    }

    public boolean resetPassword(String email, String cin, String newPassword) {
        Optional<ResponsableRH> respoOptional = repository.findByEmailAndCin(email, cin);
        if (respoOptional.isPresent()) {
            ResponsableRH respo = respoOptional.get();
            respo.setMdp(newPassword);
            repository.save(respo);
            return true;
        }
        return false;
    }
}
