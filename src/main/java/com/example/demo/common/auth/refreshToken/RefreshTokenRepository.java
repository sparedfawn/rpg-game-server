package com.example.demo.common.auth.refreshToken;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {

  @Override
  Optional<RefreshToken> findById(UUID id);

  Optional<RefreshToken> findByToken(String token);
}