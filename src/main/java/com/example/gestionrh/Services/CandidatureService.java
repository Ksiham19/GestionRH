package com.example.gestionrh.Services;
import com.example.gestionrh.Entities.Candidat;
import com.example.gestionrh.Entities.Candidature;
import com.example.gestionrh.Entities.Employe;
import com.example.gestionrh.Repositories.CandidatureRepository;
import com.example.gestionrh.Repositories.EmployeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CandidatureService {
    @Autowired
    private CandidatureRepository repository;

    public List<Candidature> getAllCandidatures() {
        return repository.findAll();
    }

    public Optional<Candidature> getCandidatureById(Long id) {
        return repository.findById(id);
    }

    public Candidature createCandidature(Candidature candidature) {
        return repository.save(candidature);
    }

    public Candidature updateCandidature(Long id, Candidature candidatureDetails) {
        Candidature candidature = repository.findById(id).orElseThrow(() -> new RuntimeException("Candidature not found"));
        candidature.setDateSoumission(candidatureDetails.getDateSoumission());
        candidature.setStatutCandidature(candidatureDetails.getStatutCandidature());
        return repository.save(candidature);
    }

    public void deleteCandidature(Long id) {
        repository.deleteById(id);
    }

    public List<Candidature> getCandidaturesByStatut(String statut) {
        return repository.findByStatutCandidature(statut);
    }

    public long countCandidature(){
        return repository.count();
    }

    @Autowired
    private EmployeRepository employeRepository;

    @Transactional
    public void accepterCandidature(Long idCandidature) {
        Candidature candidature = repository.findById(idCandidature).orElseThrow(() -> new RuntimeException("Candidature not found"));

        // Changer le statut de la candidature
        candidature.setStatutCandidature("accepté");
        repository.save(candidature);

        // Créer un nouvel employé à partir de la candidature
        Employe employe = new Employe();
        employe.setCin(candidature.getCandidat().getCin());
        employe.setNom(candidature.getCandidat().getNom());
        employe.setPrenom(candidature.getCandidat().getPrenom());
        employe.setEmail(candidature.getCandidat().getEmail());
        employe.setMdp(candidature.getCandidat().getMdp());
        employe.setTelephone(candidature.getCandidat().getTelephone());
        employe.setAdresse(candidature.getCandidat().getAdresse());
        employe.setCompetences(candidature.getCandidat().getCompetences());
        employe.setImage(candidature.getCandidat().getPhoto());
        employe.setNomImage(candidature.getCandidat().getNomphoto());

        employeRepository.save(employe);
    }
    @Transactional
    public void refuserCandidature(Long idCandidature) {
        Candidature candidature = repository.findById(idCandidature).orElseThrow(() -> new RuntimeException("Candidature not found"));

        // Changer le statut de la candidature
        candidature.setStatutCandidature("refuse");
        repository.save(candidature);

    }

    public boolean annulerCandidature(Long id) {
        try {
            repository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
