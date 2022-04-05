package com.example.demo.common.auth.response;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {

  private String token;

  private String type = "Bearer";

  private String refreshToken;

  private UUID id;

  private String username;

  private String email;

  public JwtResponse(String accessToken, String refreshToken, UUID id, String username,
      String email) {
    this.token = accessToken;
    this.refreshToken = refreshToken;
    this.id = id;
    this.username = username;
    this.email = email;
  }
}
