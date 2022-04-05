package com.example.demo.configuration.security;

import com.example.demo.model.player.Player;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class PlayerDetails implements UserDetails {

  private static final long serialVersionUID = 1L;

  private final UUID id;

  private final String username;

  private final String email;

  @JsonIgnore
  private final String password;


  public PlayerDetails(UUID id, String username, String email, String password) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.password = password;
  }

  public static PlayerDetails build(Player player) {

    return new PlayerDetails(
        player.getId(),
        player.getUsername(),
        player.getEmail(),
        player.getPassword());
  }


  public UUID getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return new ArrayList<>();
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PlayerDetails user = (PlayerDetails) o;
    return Objects.equals(id, user.id);
  }
}