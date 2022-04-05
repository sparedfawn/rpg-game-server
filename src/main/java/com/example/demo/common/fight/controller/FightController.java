package com.example.demo.common.fight.controller;

import com.example.demo.common.fight.model.Turn;
import com.example.demo.common.fight.service.FightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("fights")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class FightController {

  private final FightService fightService;

  @GetMapping("fight-players")
  @Operation(summary = "Receive a fight", security = @SecurityRequirement(name = "bearerAuth"))
  public List<Turn> getFight(@Parameter(name = "UUID value of ID", required = true)
      @RequestParam UUID idOpponent) {
    return fightService.getFight(idOpponent);
  }

  @GetMapping("fight-bot")
  @Operation(summary = "Receive a fight", security = @SecurityRequirement(name = "bearerAuth"))
  public List<Turn> getFight() {
    return fightService.getFightWithOpponent();
  }
}
