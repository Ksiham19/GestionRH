package com.example.gestionrh.Repositories;
import com.example.gestionrh.Entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByTypeNotification(String type);
    List<Notification> findByCandidatIdCandidat(Long idCandidat);
    List<Notification> findByDateEnvoiBetween(Date start, Date end);}
