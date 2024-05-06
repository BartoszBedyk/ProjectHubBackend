package com.sensilabs.projecthub;

import com.sensilabs.projecthub.notification.forms.NotificationChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;

public interface NotificationRepositoryJpa extends JpaRepository<NotificationEntity, String> {

   @Query("SELECT n FROM notification n WHERE n.sent = ?1 AND n.numberOfAttempts <= ?3 AND n.lastAttemptOn < ?2 OR n.lastAttemptOn is null")
List<NotificationEntity> findAllBySentAndLastAttemptOnAndNumberOfAttemptsQuery(boolean sent, Instant lastAttemptOn, int numberOfAttempts, NotificationChannel channel);
}
