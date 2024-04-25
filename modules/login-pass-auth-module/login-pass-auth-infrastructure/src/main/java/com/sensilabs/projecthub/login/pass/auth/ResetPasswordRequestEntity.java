package com.sensilabs.projecthub.login.pass.auth;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "reset_password_request", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResetPasswordRequestEntity {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "expiredOn")
    private Instant expiredOn;

    @Column(name = "user_id")
    private String userId;

    public ResetPasswordRequestEntity(ResetPasswordRequest request) {
        this.id = request.getId();
        this.createdOn = request.getCreatedOn();
        this.expiredOn = request.getExpiredOn();
        this.userId = request.getUserId();
    }
}
