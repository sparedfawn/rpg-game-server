package com.example.demo.model.effect;

import com.example.demo.model.character.model.Character;
import com.example.demo.model.character.enums.Statistics;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EffectService {

  private final EffectRepository effectRepository;

  public List<Effect> getEffects() {

    return effectRepository.findAll();
  }

  public Effect getEffect(UUID id) {
    log.info("method: getEffect(): retrieve effect");
    return effectRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  public void addEffect(Character character, EffectSource effectSource,
      Statistics statistics, int value) {
    log.info("method: addEffect(): add effect");
    effectRepository.save(Effect.builder()
        .character(character)
        .effectSource(effectSource)
        .statIncremented(statistics)
        .statValue(value)
        .build());
  }

  public void editEffect(UUID id, EffectSource effectSource, Statistics statistics, int value) {
    log.info("method: editEffect(): edit effect");
    Effect effect = getEffect(id);

    effect.setStatIncremented(statistics);
    effect.setEffectSource(effectSource);
    effect.setStatValue(value);

    effectRepository.save(effect);
  }

  public void deleteEffect(UUID id) {
    log.info("method: deleteEffect(): delete effect");
    effectRepository.delete(getEffect(id));
  }

  public List<Effect> getEffectsOfPlayer(UUID characterId) {
    log.info("method: deleteEffect(): delete effect");
    return getEffects().stream().filter(e -> e.getCharacter().getId() == characterId).collect(
        Collectors.toList());
  }
}
