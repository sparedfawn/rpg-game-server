package com.example.demo.model.quest;

import com.example.demo.model.character.CharacterService;
import com.example.demo.model.character.model.Character;
import com.example.demo.model.equipment_item.EquipmentItemService;
import com.example.demo.model.equipment_item.item_in_equipment.ItemInEquipment;
import com.example.demo.model.equipment_item.item_in_equipment.ItemInEquipmentService;
import com.example.demo.model.equipment_item.types.CollectibleItem;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.SplittableRandom;

import com.example.demo.model.quest.model.PossibleQuest;
import com.example.demo.model.quest.model.Quest;
import com.example.demo.model.quest.repository.PossibleQuestRepository;
import com.example.demo.model.quest.repository.QuestRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.mutable.MutableInt;
import org.springframework.stereotype.Service;

import static com.example.demo.model.equipment_item.item_in_equipment.EquipmentItem.*;

@RequiredArgsConstructor
@Service
public class QuestService {

  private final QuestRepository questRepository;
  private final PossibleQuestRepository possibleQuestRepository;

  private final CharacterService characterService;
  private final EquipmentItemService equipmentItemService;
  private final ItemInEquipmentService itemInEquipmentService;


  public List<PossibleQuest> getQuestsForUser() {

    Character character = characterService.getCharacter();

    if (character.getTakenQuest() != null) {

      throw new IllegalArgumentException("You haven't completed your quest yet!");
    }

    if (character.getPossibleQuests().isEmpty()) {

      return List.of(createQuestForUser(character, 0),
              createQuestForUser(character, 1),
              createQuestForUser(character, 2));
    }

    return character.getPossibleQuests();
  }

  public PossibleQuest createQuestForUser(Character character, int type) {

    SplittableRandom splittableRandom = new SplittableRandom();

    int itemValue = splittableRandom.nextInt(1000);
    int goldValue = splittableRandom
        .nextInt(100 * character.getLevel() * (type + 1), 300 * character.getLevel() * (type + 1));

    CollectibleItem collectibleItem = getItemForQuest(itemValue, character);

    LocalTime duration;

    switch (type) {

      case 0 -> duration = LocalTime.of(0, 2, 0);
      case 1 -> duration = LocalTime.of(0, 5, 0);
      case 2 -> duration = LocalTime.of(0, 8, 0);
      default -> throw new IllegalStateException("Unexpected value: " + type);
    }

    PossibleQuest possibleQuest = PossibleQuest.builder()
            .collectibleItem(collectibleItem)
            .amountOfItems(collectibleItem == null ? 0 : type + 1)
            .experience(300 * character.getLevel() * (type + 1) - goldValue)
            .gold(goldValue)
            .character(character)
            .duration(duration)
            .build();

    possibleQuestRepository.save(possibleQuest);

    return possibleQuest;
  }

  private CollectibleItem getItemForQuest(int number, Character character) {

    Map<CollectibleItem, Integer> percentages = getPercentageValues(character);

    MutableInt i = new MutableInt(0);

    for (Map.Entry<CollectibleItem, Integer> entry : percentages.entrySet()) {

      if (i.getValue() + entry.getValue() > number) {
        return entry.getKey();
      }
      else {
        i.add(entry.getValue());
      }
    }

    return null;
  }

  private Map<CollectibleItem, Integer> getPercentageValues(Character character) {

    return Map.of((CollectibleItem) equipmentItemService.getEquipmentItem(WOOD.getId()), 125,
            (CollectibleItem) equipmentItemService.getEquipmentItem(COTTON.getId()), 125,
            (CollectibleItem) equipmentItemService.getEquipmentItem(RUBBER.getId()), 125,
            (CollectibleItem) equipmentItemService.getEquipmentItem(METAL.getId()), 125,
            (CollectibleItem) equipmentItemService.getEquipmentItem(NEEDLE_AND_THREAD.getId()), 100,
            (CollectibleItem) equipmentItemService.getEquipmentItem(HAMMER.getId()), 100,
            (CollectibleItem) equipmentItemService.getEquipmentItem(SILK.getId()), 75,
            (CollectibleItem) equipmentItemService.getEquipmentItem(LEATHER.getId()), 75,
            (CollectibleItem) equipmentItemService.getEquipmentItem(character.getItemForWeaponUpgrade()), 50);
  }


  public List<Quest> getQuests() {

    return questRepository.findAll();
  }

  public void pickQuest (int value) {

    Character character = characterService.getCharacter();

    if (character.getTakenQuest() != null) {

      throw new IllegalArgumentException("You've already taken a quest");
    }
    else if (character.getPossibleQuests().isEmpty()) {

      throw new IllegalArgumentException("You can't do this right now");
    }

    PossibleQuest possibleQuest = character.getPossibleQuests().get(value);

    character.setTakenQuest(Quest.builder()
            .amountOfItems(possibleQuest.getAmountOfItems())
            .collectibleItem(possibleQuest.getCollectibleItem())
            .experience(possibleQuest.getExperience())
            .gold(possibleQuest.getGold())
            .duration(possibleQuest.getDuration())
            .questStarted(LocalDateTime.now())
            .build());

    possibleQuestRepository.deleteAll(character.getPossibleQuests());
  }


  public void completeQuest() {

    Character character = characterService.getCharacter();

    if (character.getTakenQuest() == null) {

      throw new IllegalArgumentException("You haven't taken any quest");
    }

    Quest quest = character.getTakenQuest();
    LocalTime duration = quest.getDuration();

    LocalDateTime now = LocalDateTime.now();
    LocalDateTime questFinished = quest.getQuestStarted()
            .plusSeconds(duration.getSecond())
            .plusMinutes(duration.getMinute());

    if (now.isBefore(questFinished)) {

      throw new IllegalStateException(String.valueOf(ChronoUnit.SECONDS.between(now, questFinished)));
    }

    characterService.addExp(character.getId(), quest.getExperience());

    character.setGold(character.getGold() + quest.getGold());

    if (quest.getCollectibleItem() != null) {

      ItemInEquipment itemInEquipment = itemInEquipmentService.getItemInEquipment(character.getEquipment().getId(), quest.getCollectibleItem().getId());

      if (itemInEquipment == null) {

        itemInEquipmentService.addItemToEquipment(character.getEquipment().getId(), quest.getCollectibleItem().getId(), quest.getAmountOfItems());
      }
      else {
        itemInEquipmentService.editItemAmountInEquipment(itemInEquipment.getId(), itemInEquipment.getAmount() + quest.getAmountOfItems());
      }
    }

    character.setTakenQuest(null);

    questRepository.delete(quest);
  }

  public List<PossibleQuest> getPossibleQuests() {

    return possibleQuestRepository.findAll();
  }
}
