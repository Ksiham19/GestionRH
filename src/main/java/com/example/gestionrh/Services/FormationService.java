package com.example.gestionrh.Services;
import com.example.gestionrh.Entities.Formation;
import com.example.gestionrh.Repositories.FormationRepository;
import com.example.gestionrh.Repositories.ParticipationFormationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FormationService {
    @Autowired
    private FormationRepository repository;
    private ParticipationFormationRepository participationRepository;

    public List<Formation> getAllFormations() {
        return repository.findAll();
    }

    public Formation updateFormationStatus(Long id, String statut) {
        Optional<Formation> formation = repository.findById(id);
        if (formation.isPresent()) {
            Formation formationToUpdate = formation.get();
            formationToUpdate.setStatut(statut);
            return repository.save(formationToUpdate);
        } else {
            throw new EntityNotFoundException("Formation not found with id " + id);
        }
    }
    public Optional<Formation> getFormationById(Long id) {
        return repository.findById(id);
    }

    public List<Formation> getFormationBystatut(String  statut) {
        return repository.findBystatut(statut);
    }
    public Formation createFormation(Formation formation) {
        return repository.save(formation);
    }

    public void updateFormation(Formation formation) {
        repository.save(formation); // Save method can also be used for update
    }

    public void deleteFormationById(Long formationId){
        repository.deleteById(formationId);
        Optional<Formation> formation = Optional.of(new Formation());
        formation = getFormationById(formationId);
        // First, delete dependent records if necessary
        participationRepository.deleteById(participationRepository.findByFormationIdFormation(formationId));

        // Then, delete the formation record
        repository.deleteById(formationId);
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }
}
