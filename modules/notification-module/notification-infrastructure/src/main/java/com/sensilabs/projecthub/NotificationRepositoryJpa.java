package com.sensilabs.projecthub;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface NotificationRepositoryJpa extends JpaRepository<NotificationEntity, String> {

List<NotificationEntity> findAllBySent(boolean sent);
}
