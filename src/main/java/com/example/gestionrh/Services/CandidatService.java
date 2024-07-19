package com.example.gestionrh.Services;
import com.example.gestionrh.Entities.Candidat;
import com.example.gestionrh.Repositories.CandidatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CandidatService {
    @Autowired
    private CandidatRepository repository;

    public List<Candidat> getAllCandidats() {
        return repository.findAll();
    }

    public boolean authenticate(String enteredEmail, String enteredPassword) {
        Candidat candidat = repository.findByEmail(enteredEmail);
        return candidat != null && candidat.getMdp().equals(enteredPassword);
    }

    public Optional<Candidat> getCandidatById(Long id) {
        return repository.findById(id);
    }

    public Long getCandidatIdByEmail(String email) {
        Candidat candidat = repository.findByEmail(email);
        return candidat != null ? candidat.getIdCandidat() : null;
    }

    public Candidat createCandidat(Candidat candidat) {
        return repository.save(candidat);
    }

    public Candidat updateCandidat(Long id, Candidat candidatDetails) {
        Candidat candidat = repository.findById(id).orElseThrow(() -> new RuntimeException("Candidat not found"));
        candidat.setNom(candidatDetails.getNom());
        candidat.setPrenom(candidatDetails.getPrenom());
        candidat.setEmail(candidatDetails.getEmail());
        candidat.setTelephone(candidatDetails.getTelephone());
        candidat.setAdresse(candidatDetails.getAdresse());
        candidat.setCompetences(candidatDetails.getCompetences());
        candidat.setCv(candidatDetails.getCv());
        candidat.setLettreMotivation(candidatDetails.getLettreMotivation());
        return repository.save(candidat);
    }

    public void deleteCandidat(Long id) {
        repository.deleteById(id);
    }

    public List<Candidat> getCandidatsByNom(String nom) {
        return repository.findByNom(nom);
    }

    public List<Candidat> getCandidatsByCin(String cin) {
        return repository.findByCin(cin);
    }

    public boolean resetPassword(String email, String cin, String newPassword) {
        Optional<Candidat> candidatOptional = repository.findByEmailAndCin(email, cin);
        if (candidatOptional.isPresent()) {
            Candidat candidat = candidatOptional.get();
            candidat.setMdp(newPassword);
            repository.save(candidat);
            return true;
        }
        return false;
    }

}
