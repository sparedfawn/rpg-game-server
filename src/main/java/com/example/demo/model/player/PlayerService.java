package com.example.demo.model.player;

import java.util.List;
import java.util.UUID;

import com.example.demo.configuration.security.PlayerDetails;
import com.example.demo.configuration.security.PlayerDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlayerService {

  private final PlayerRepository playerRepository;
  private final PlayerDetailsService playerDetailsService;

  public List<Player> getAllPlayers() {
    log.info("method: getAllPlayers(): getting all players from DB");
    return playerRepository.findAll();
  }

  public Player getPlayer(UUID id) {
    log.info("method: getPlayer(): getting single player based on unique id");
    return playerRepository.getById(id);
  }

  public Boolean existsByUsername(String username) {
    log.info("method: existsByUsername(): Checking if players exists based on username");
    return playerRepository.existsByUsername(username);
  }

  public Boolean existsByEmail(String email) {
    log.info("method: existsByEmail(): Checking if player exists based on email");
    return playerRepository.existsByEmail(email);
  }

  public Player savePlayer(Player player) {
    log.info("method: savePlayer(): saving player in DB");
    return playerRepository.save(player);
  }

  public Player getCurrentlyLoggedPlayer() {

    PlayerDetails playerDetails = playerDetailsService.getCurrentlyLoggedPlayer();

    log.info("method: getCurrentlyLoggedPlayer: retrieve currently logged");

    return playerRepository.findById(playerDetails.getId()).orElseThrow(EntityNotFoundException::new);
  }

}
