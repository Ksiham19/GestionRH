package com.example.gestionrh.Services;
import com.example.gestionrh.Entities.DemandeDeConge;
import com.example.gestionrh.Repositories.DemandeCongeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DemandeDeCongeService {

    @Autowired
    private DemandeCongeRepository repository;

    public List<DemandeDeConge> getAllDemandes() {
        return repository.findAll();
    }

    public Optional<DemandeDeConge> getDemandeById(Long id) {
        return repository.findById(id);
    }

    public DemandeDeConge createDemande(DemandeDeConge demande) {
        return repository.save(demande);
    }

    public DemandeDeConge updateDemande(Long id, DemandeDeConge demandeDetails) {
        DemandeDeConge demande = repository.findById(id).orElseThrow(() -> new RuntimeException("Demande not found"));
        demande.setDateDebut(demandeDetails.getDateDebut());
        demande.setDateFin(demandeDetails.getDateFin());
        demande.setStatut(demandeDetails.getStatut());
        return repository.save(demande);
    }

    public void deleteDemande(Long id) {
        repository.deleteById(id);
    }

    public List<DemandeDeConge> getDemandesByStatut(String statut) {
        return repository.findByStatut(statut);
    }
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }


    public long countDemande() {
        return repository.count();
    }
}
