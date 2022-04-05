package com.example.demo.model.effect;

import com.example.demo.model.character.CharacterService;
import com.example.demo.model.character.enums.Statistics;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("effects")
@SecurityRequirement(name = "bearerAuth")
@ConditionalOnExpression("${internal.controllers.enabled}")
public class InternalEffectController {

  private final EffectService effectService;
  private final CharacterService characterService;

  @GetMapping
  @Operation(summary = "Return list of players effects from DB", security = @SecurityRequirement(name = "bearerAuth"))
  public List<Effect> getEffects() {

    return effectService.getEffects();
  }

  @GetMapping("{id}")
  @Operation(summary = "Return effect based on id from DB", security = @SecurityRequirement(name = "bearerAuth"))
  public Effect getEffect(@PathVariable UUID id) {

    return effectService.getEffect(id);
  }

  @PostMapping("{characterId}")
  @Operation(summary = "Add effect to a character", security = @SecurityRequirement(name = "bearerAuth"))
  public void addEffect(@PathVariable UUID characterId, @RequestParam EffectSource effectSource,
      @RequestParam Statistics statistics, @RequestParam int value) {

    effectService.addEffect(characterService.getCharacter(characterId), effectSource, statistics, value);
  }

  @PutMapping("{id}")
  @Operation(summary = "Edit effect based on id", security = @SecurityRequirement(name = "bearerAuth"))
  public void editEffect(@PathVariable UUID id, @RequestParam EffectSource effectSource, @RequestParam Statistics statistics, @RequestParam int value) {

    effectService.editEffect(id, effectSource, statistics, value);
  }

  @DeleteMapping("{id}")
  @Operation(summary = "Remove effect from DB", security = @SecurityRequirement(name = "bearerAuth"))
  public void deleteEffect(@PathVariable UUID id) {

    effectService.deleteEffect(id);
  }
}
