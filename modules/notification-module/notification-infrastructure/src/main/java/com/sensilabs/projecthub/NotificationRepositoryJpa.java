package com.sensilabs.projecthub;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface NotificationRepositoryJpa extends JpaRepository<NotificationEntity, String> {

   @Query("SELECT n FROM notification n WHERE n.sent = ?1 AND n.lastAttemptOn < ?2 AND n.numberOfAttempts <= ?3")
List<NotificationEntity> findAllBySentAndLastAttemptOnAndNumberOfAttempts(boolean sent, Instant lastAttemptOn, int numberOfAttempts);
}
