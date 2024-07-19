package com.example.gestionrh.Services;
import com.example.gestionrh.Entities.ParticipationFormation;
import com.example.gestionrh.Repositories.ParticipationFormationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParticipationFormationService {

    @Autowired
    private ParticipationFormationRepository repository;

    public List<ParticipationFormation> getAllParticipations() {
        return repository.findAll();
    }

    public Optional<ParticipationFormation> getParticipationById(Long id) {
        return repository.findById(id);
    }

    public ParticipationFormation createParticipation(ParticipationFormation participation) {
        return repository.save(participation);
    }

    public ParticipationFormation updateParticipation(Long id, ParticipationFormation participationDetails) {
        ParticipationFormation participation = repository.findById(id).orElseThrow(() -> new RuntimeException("Participation not found"));
        return repository.save(participation);
    }
    public void deleteParticipation(Long id) {
        repository.deleteById(id);
    }
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }
}
