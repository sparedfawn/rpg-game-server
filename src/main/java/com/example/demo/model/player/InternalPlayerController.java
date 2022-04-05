package com.example.demo.model.player;

import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("players")
@SecurityRequirement(name = "bearerAuth")
@ConditionalOnExpression("${internal.controllers.enabled}")
public class InternalPlayerController {

  private final PlayerService playerService;

  @GetMapping
  @Operation(summary = "Return list of players from db", security = @SecurityRequirement(name = "bearerAuth"))
  public List<Player> getAllPlayers() {

    return playerService.getAllPlayers();
  }

  @GetMapping("{id}")
  @Operation(summary = "Return single player from DB", security = @SecurityRequirement(name = "bearerAuth"))
  public Player getPlayer(@PathVariable UUID id) {

    return playerService.getPlayer(id);
  }
}
