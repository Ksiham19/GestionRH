package com.example.gestionrh.Services;

import com.example.gestionrh.Entities.Candidat;
import com.example.gestionrh.Entities.Employe;
import com.example.gestionrh.Repositories.EmployeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeService {
    @Autowired
    private EmployeRepository repository;

    public List<Employe> getAllEmployes() {
        return repository.findAll();
    }

    public Optional<Employe> getEmployeById(Long id) {
        return repository.findById(id);
    }

    public Employe createEmploye(Employe employe) {
        return repository.save(employe);
    }

    public Employe findById(Long id) {
        return repository.findById(id).orElse(null);
    }


    public boolean changePassword(Long employeId, String currentPassword, String newPassword) {
        Optional<Employe> optionalEmploye = repository.findById(employeId);
        if (optionalEmploye.isPresent()) {
            Employe employe = optionalEmploye.get();
            if (employe.getMdp().equals(currentPassword)) {
                employe.setMdp(newPassword);
                repository.save(employe);
                return true;
            }
        }
        return false;
    }

    public Employe updateEmploye(Long id, Employe employeDetails) {
        Employe employe = repository.findById(id).orElseThrow(() -> new RuntimeException("Employe not found"));
        employe.setNom(employeDetails.getNom());
        employe.setPrenom(employeDetails.getPrenom());
        employe.setEmail(employeDetails.getEmail());
        employe.setTelephone(employeDetails.getTelephone());
        employe.setAdresse(employeDetails.getAdresse());
        employe.setDateEmbauche(employeDetails.getDateEmbauche());
        employe.setPoste(employeDetails.getPoste());
        employe.setSalaire(employeDetails.getSalaire());
        employe.setCompetences(employeDetails.getCompetences());
        employe.setImage(employeDetails.getImage());
        return repository.save(employe);
    }

    public void deleteEmploye(Long id) {
        repository.deleteById(id);
    }
    public Long getEmployeIdByEmail(String email) {
        Employe employe = repository.findByEmail(email);
        return employe != null ? employe.getIdEmploye() : null;
    }
    public List<Employe> getEmployesByNom(String nom) {
        return repository.findByNom(nom);
    }

    public List<Employe> getEmployesByCin(String cin) {
        return repository.findByCin(cin);
    }

    public boolean authenticate(String enteredEmail, String enteredPassword) {
        Employe employe = repository.findByEmail(enteredEmail);
        return employe != null && employe.getMdp().equals(enteredPassword);
    }

    public long countmploye(){
        return repository.count();
    }

    public boolean resetPassword(String email, String cin, String newPassword) {
        Optional<Employe> employeOptional = repository.findByEmailAndCin(email, cin);
        if (employeOptional.isPresent()) {
            Employe employe = employeOptional.get();
            employe.setMdp(newPassword);
            repository.save(employe);
            return true;
        }
        return false;
    }


}
