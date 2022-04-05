package com.example.demo.model.suitable_item;

import com.example.demo.model.character.model.Character;
import com.example.demo.model.character.enums.Statistics;
import com.example.demo.model.effect.EffectService;
import com.example.demo.model.effect.EffectSource;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SuitableItemService {

  private final SuitableItemRepository suitableItemRepository;

  private final EffectService effectService;

  public SuitableItem getItem(UUID id) {
    return suitableItemRepository.findById(id).get();
  }

  public List<SuitableItem> getAllItems() {
    return suitableItemRepository.findAll();
  }


  public void editItem(UUID id, int level) {

    SuitableItem suitableItem = suitableItemRepository.findById(id).get();
    suitableItem.setLevel(level);
    suitableItemRepository.save(suitableItem);
    log.info("method: editItem(): edit item");
  }

  public void characterCreationItemEffects(Character character) {

    addEffectsWithMapIteration(character.getBoots().incrementStatistic(), character,
        EffectSource.BOOTS);
    addEffectsWithMapIteration(character.getSuit().incrementStatistic(), character,
        EffectSource.SUIT);
    addEffectsWithMapIteration(character.getCape().incrementStatistic(), character,
        EffectSource.CAPE);
    addEffectsWithMapIteration(character.getWeapon().incrementStatistic(), character,
        EffectSource.WEAPON);
  }

  public void addEffectsWithMapIteration(Map<Statistics, Integer> map, Character character,
      EffectSource effectSource) {

    for (Entry<Statistics, Integer> entry : map.entrySet()) {

      effectService.addEffect(character, effectSource, entry.getKey(), entry.getValue());
    }
    log.info("method: addEffectsWithMapIteration(): effects added");
  }
}
