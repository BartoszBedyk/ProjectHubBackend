package com.sensilabs.projecthub;

import com.sensilabs.projecthub.notification.forms.NotificationChannel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface NotificationRepositoryJpa extends JpaRepository<NotificationEntity, String>{

   @Query("SELECT n FROM notification n WHERE n.sent = :sent AND n.channel = :channel AND n.numberOfAttempts <= :numberOfAttempts AND (n.lastAttemptOn <= :lastAttemptOn OR n.lastAttemptOn is null)")
List<NotificationEntity> findAllBySentAndLastAttemptOnAndNumberOfAttemptsQuery( @Param("sent") boolean sent,
                                                                                @Param("lastAttemptOn") Instant lastAttemptOn,
                                                                                @Param("numberOfAttempts") int numberOfAttempts,
                                                                                @Param("channel") NotificationChannel channel,
                                                                                Pageable pageable);
}
