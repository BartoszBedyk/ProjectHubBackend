package com.sensilabs.projecthub.login.pass.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResetPasswordRequest {
    String id;
    Instant createdOn;
    Instant expiredOn;
    String userId;
}
