package com.example.gestionrh.Services;
import com.example.gestionrh.Entities.Formation;
import com.example.gestionrh.Repositories.FormationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FormationService {
    @Autowired
    private FormationRepository repository;

    public List<Formation> getAllFormations() {
        return repository.findAll();
    }

    public Optional<Formation> getFormationById(Long id) {
        return repository.findById(id);
    }

    public Formation createFormation(Formation formation) {
        return repository.save(formation);
    }

    public void updateFormation(Formation formation) {
        repository.save(formation); // Save method can also be used for update
    }

    public void deleteFormationById(Long id) {
        repository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }
}
