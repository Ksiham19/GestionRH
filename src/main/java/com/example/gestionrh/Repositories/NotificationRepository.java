package com.example.gestionrh.Repositories;
import com.example.gestionrh.Entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("SELECT COUNT(n) FROM Notification n WHERE n.candidat.idCandidat = :idCandidat AND n.typeNotification = 'non_lues'")
    long countByCandidatId(@Param("idCandidat") Long idCandidat);
    @Query("SELECT n FROM Notification n WHERE n.candidat.idCandidat = :idCandidat AND n.typeNotification = 'non_lues'")
    List<Notification> touteslesnotifications(@Param("idCandidat") Long idCandidat);
    List<Notification> findByTypeNotification(String type);
    List<Notification> findByCandidatIdCandidat(Long idCandidat);
    List<Notification> findByDateEnvoiBetween(Date start, Date end);}
