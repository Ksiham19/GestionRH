package com.example.gestionrh.Services;
import com.example.gestionrh.Entities.Employe;
import com.example.gestionrh.Entities.Notification;
import com.example.gestionrh.Repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.List;
import java.util.Optional;

@Service
public class NotifiacationService {
    @Autowired
    private NotificationRepository repository;
    @Autowired
    private JavaMailSender mailSender;

    public List<Notification> getAllNotifications() {
        return repository.findAll();
    }

    public long countNotificationsByCandidatId(Long idCandidat) {
        return repository.countByCandidatId(idCandidat);
    }
    public void envoyerNotificationEmploye(Employe employe, String bulletin) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(employe.getEmail());
        message.setSubject("Votre Bulletin de Paie est Prêt");
        message.setText("Votre bulletin de paie pour le mois/année a été généré. Veuillez le télécharger depuis votre espace employé.");

        mailSender.send(message);
    }

    public Optional<Notification> getNotificationById(Long id) {
        return repository.findById(id);
    }

    public Notification createNotification(Notification notification) {
        return repository.save(notification);
    }

    public Notification updateNotification(Long id, Notification notificationDetails) {
        Notification notification = repository.findById(id).orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setTypeNotification(notificationDetails.getTypeNotification());
        notification.setMessage(notificationDetails.getMessage());
        notification.setDateEnvoi(notificationDetails.getDateEnvoi());
        return repository.save(notification);
    }

    public void deleteNotification(Long id) {
        repository.deleteById(id);
    }

    public List<Notification> getNotificationsByType(String type) {
        return repository.findByTypeNotification(type);
    }

    public List<Notification> touteslesnotifications(Long id){
        return  repository.touteslesnotifications(id);
    }
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }
}
