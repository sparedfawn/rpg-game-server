package com.example.demo.model.character;

import com.example.demo.model.character.model.Character;
import com.example.demo.model.character.model.CharacterDTO;
import com.example.demo.model.character.classes.CharacterFactory;
import com.example.demo.model.character.enums.ItemToUpgrade;
import com.example.demo.model.character.enums.Statistics;
import com.example.demo.model.effect.Effect;
import com.example.demo.model.effect.EffectService;
import com.example.demo.model.effect.EffectSource;
import com.example.demo.model.equipment_item.item_in_equipment.EquipmentItem;
import com.example.demo.model.equipment_item.item_in_equipment.ItemInEquipment;
import com.example.demo.model.equipment_item.item_in_equipment.ItemInEquipmentService;
import com.example.demo.model.player.Player;
import com.example.demo.model.player.PlayerService;
import com.example.demo.model.quest.annotations.QuestProgressable;
import com.example.demo.model.suitable_item.SuitableItemService;
import com.example.demo.model.suitable_item.types.clothing.Boots;
import com.example.demo.model.suitable_item.types.clothing.Cape;
import com.example.demo.model.suitable_item.types.clothing.Suit;
import com.example.demo.model.suitable_item.types.weapons.Weapon;
import com.example.demo.model.suitable_item.types.weapons.WeaponFactory;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Slf4j
public class CharacterService {

  private final CharacterRepository characterRepository;

  private final PlayerService playerService;

  private final SuitableItemService suitableItemService;

  private final EffectService effectService;

  private final ItemInEquipmentService itemInEquipmentService;


  public List<Character> getAllCharacters() {
    log.info("getAllCharacters(): getting all characters from DB");
    return characterRepository.findAll();
  }

  public Character getCharacter() {

    return playerService.getCurrentlyLoggedPlayer().getCharacter();
  }

  public void saveCharacter(Character character) {

    characterRepository.save(character);
  }

  public Character getCharacter(UUID id) {
    log.info("getCharacter(): getting single character based on unique id");
    return characterRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  public void addCharacterForTesting(CharacterDTO characterDTO, CharacterFactory characterType) {
    log.info("addCharacter(): adding character to DB");
    characterRepository.save(characterType.get().setAll(characterDTO));
  }

  public void incrementStatistic(UUID id, Statistics statistic) {
    Character toEdit = characterRepository.getById(id);

    switch (statistic) {
      case STRENGTH -> toEdit.setStrength(toEdit.getStrength() + 1);
      case DEXTERITY -> toEdit.setDexterity(toEdit.getDexterity() + 1);
      case INTELLIGENCE -> toEdit.setIntelligence(toEdit.getIntelligence() + 1);
      case LUCK -> toEdit.setLuck(toEdit.getLuck() + 1);
      case MOVEMENT_SPEED -> toEdit.setMovementSpeed(toEdit.getMovementSpeed() + 1);
      case ARMOR -> toEdit.setArmor(toEdit.getArmor() + 1);
      default -> {
        log.error("incrementStatistic(): invalid statistic name");
        throw new IllegalArgumentException("There is no such statistic");
      }
    }

    characterRepository.save(toEdit);
    log.info("incrementStatistic(): increment " + statistic + " statistic");
  }

  public void boostStatistic(Character character, Statistics statistic, int amount) {

    switch (statistic) {
      case STRENGTH -> character.setStrength(character.getStrength() + amount);
      case DEXTERITY -> character.setDexterity(character.getDexterity() + amount);
      case INTELLIGENCE -> character.setIntelligence(character.getIntelligence() + amount);
      case LUCK -> character.setLuck(character.getLuck() + amount);
      case MOVEMENT_SPEED -> character.setMovementSpeed(character.getMovementSpeed() + amount);
      case ARMOR -> character.setArmor(character.getArmor() + amount);
      default -> {
        log.error("incrementStatistic(): invalid statistic name");
        throw new IllegalArgumentException("There is no such statistic");
      }
    }

  }

  public void deleteCharacter(UUID id) {
    try {
      characterRepository.delete(characterRepository.getById(id));
      log.info("method: deleteCharacter(): removing character from DB");
    } catch (Exception e) {
      log.error("method: deleteCharacter(): There is no character of such id");
      throw new IllegalArgumentException("There is no character of such id");
    }
  }

  public void addExp(UUID id, int exp) {
    Character character = characterRepository.getById(id);
    if (character.getLevel() * 1500 > character.getExperience() + exp) {
      log.info("method: addExp(): adding exp to character");
      character.setExperience(character.getExperience() + exp);
    } else {
      log.info("method: addExp(): leveling up character");
      character.setLevel(character.getLevel() + 1);
      character.setExperience(
          (character.getExperience() + exp) - (character.getLevel() - 1) * 1500);
    }
    characterRepository.save(character);
  }

  public void addCharacter(CharacterFactory characterType, WeaponFactory weaponType) {

    Player player = playerService.getCurrentlyLoggedPlayer();

    if (player.getCharacter() == null) {

      Character character = characterType.get().defaultValues();

      Weapon weapon = weaponType.get();

      if (!validationPassed(characterType, weaponType)) {
        log.error("method: addCharacter(): Selected item is not destined for this class");
        throw new IllegalArgumentException("Selected item is not destined for this class");
      }

      character.setWeapon(weapon);

      player.setCharacter(character);

      characterRepository.save(character);

      suitableItemService.characterCreationItemEffects(character);
      log.info("method: addCharacter(): adding character to DB");
    } else {

      log.error("method: addCharacter(): Player already has character");
      throw new IllegalArgumentException("Player already has character");
    }
  }

  public Character getCharacterWithEffectsOn(UUID id) {

    Character character = getCharacter(id);

    for (Effect effect : character.getEffectList()) {

      boostStatistic(character, effect.getStatIncremented(), effect.getStatValue());

      if (effect.getEffectSource().equals(EffectSource.USABLE_ITEM)) {

        effectService.deleteEffect(effect.getId());
      }
    }
    log.info("method: getCharacterWithEffectsOn(): getting character with boosts in statistics");
    return character;
  }


  private boolean validationPassed(CharacterFactory characterType, WeaponFactory weaponType) {

    switch (weaponType.name()) {
      case "GREEN_LIGHTSABER", "BLUE_LIGHTSABER", "PURPLE_LIGHTSABER" -> {
        if (characterType.name().equals("JEDI_KNIGHT")) {
          log.info("method: validationPassed(): validating type of weapon");
          return true;
        }
      }
      case "DUAL_PISTOL", "HEAVY_PISTOL", "LIGHT_PISTOL" -> {
        if (characterType.name().equals("PRICE_HUNTER")) {
          log.info("method: validationPassed(): validating type of weapon");
          return true;
        }
      }
      case "CROSS_LIGHTSABER", "CURVED_LIGHTSABER", "DOUBLE_LIGHTSABER" -> {
        if (characterType.name().equals("SITH_LORD")) {
          log.info("method: validationPassed(): validating type of weapon");
          return true;
        }
      }
      case "ARCHANGEL_STAFF", "LUDEN_STAFF", "VOID_STAFF" -> {
        if (characterType.name().equals("MAGE")) {
          log.info("method: validationPassed(): validating type of weapon");
          return true;
        }
      }
      default -> {
        log.error("method: validationPassed(): validation failed");
        return false;
      }
    }
    log.error("method: validationPassed(): validation failed");
    return false;
  }


  public void useUsableItem(String itemName) {
    log.info("method: useUsableItem(): use usable item");
    ItemInEquipment item = characterRepository
        .getItemToUseUsingName(getCharacter().getId(), itemName);

    itemInEquipmentService.useUsableItem(item.getId());
  }

  public void levelUpItem(ItemToUpgrade itemToUpgrade) {

    Character character = getCharacter();

    log.info("method: levelUpItem(): level up item");
    switch (itemToUpgrade) {

      case CAPE -> {
        Cape cape = character.getCape();
        Map<String, Integer> map = Map.of("Cotton", cape.getLevel() * 5,
            "Silk", cape.getLevel() * 3, "Needle and Thread", 1);

        if (itemInEquipmentService.hasItemsInEquipment(character, map)) {

          effectService.deleteEffect(
              effectService.getEffectsOfPlayer(character.getId())
                  .stream()
                  .filter(e -> e.getEffectSource().name().equals(itemToUpgrade.name()))
                  .findFirst()
                  .orElseThrow(EntityNotFoundException::new).getId());

          itemInEquipmentService.removeItemsFromEquipment(character, map);

          character.getCape().setLevel(character.getCape().getLevel() + 1);
          characterRepository.save(character);
          suitableItemService
              .addEffectsWithMapIteration(character.getCape().incrementStatistic(), character,
                  EffectSource.CAPE);
        } else {
          throw new IllegalStateException("You don't have enough items for that activity");
        }

      }
      case BOOTS -> {
        Boots boots = character.getBoots();
        Map<String, Integer> map = Map.of("Leather", boots.getLevel() * 2,
            "Rubber", boots.getLevel() * 2, "Needle and Thread", 1);

        if (itemInEquipmentService.hasItemsInEquipment(character, map)) {

          effectService.deleteEffect(
              effectService.getEffectsOfPlayer(character.getId())
                  .stream()
                  .filter(e -> e.getEffectSource().name().equals(itemToUpgrade.name()))
                  .findFirst()
                  .orElseThrow(EntityNotFoundException::new).getId());

          itemInEquipmentService.removeItemsFromEquipment(character, map);

          character.getBoots().setLevel(character.getBoots().getLevel() + 1);
          characterRepository.save(character);
          suitableItemService
              .addEffectsWithMapIteration(character.getBoots().incrementStatistic(), character,
                  EffectSource.BOOTS);
        } else {
          throw new IllegalStateException("You don't have enough items for that activity");
        }
      }
      case SUIT -> {
        Suit suit = character.getSuit();
        Map<String, Integer> map = Map.of("Metal", suit.getLevel() * 4,
            "Cotton", suit.getLevel() * 7, "Needle and Thread", 1);

        if (itemInEquipmentService.hasItemsInEquipment(character, map)) {

          effectService.deleteEffect(
              effectService.getEffectsOfPlayer(character.getId())
                  .stream()
                  .filter(e -> e.getEffectSource().name().equals(itemToUpgrade.name()))
                  .findFirst()
                  .orElseThrow(EntityNotFoundException::new).getId());

          itemInEquipmentService.removeItemsFromEquipment(character, map);

          character.getSuit().setLevel(character.getSuit().getLevel() + 1);
          characterRepository.save(character);
          suitableItemService
              .addEffectsWithMapIteration(character.getSuit().incrementStatistic(), character,
                  EffectSource.SUIT);
        } else {
          throw new IllegalStateException("You don't have enough items for that activity");
        }

      }
      case WEAPON -> {
        Map<String, Integer> map = character.getMapForWeaponUpgrade();

        if (itemInEquipmentService.hasItemsInEquipment(character, map)) {

          effectService.deleteEffect(
              effectService.getEffectsOfPlayer(character.getId())
                  .stream()
                  .filter(e -> e.getEffectSource().name().equals(itemToUpgrade.name()))
                  .findFirst()
                  .orElseThrow(EntityNotFoundException::new).getId());

          itemInEquipmentService.removeItemsFromEquipment(character, map);

          character.getWeapon().setLevel(character.getWeapon().getLevel() + 1);
          characterRepository.save(character);
          suitableItemService
              .addEffectsWithMapIteration(character.getWeapon().incrementStatistic(), character,
                  EffectSource.WEAPON);
        } else {
          throw new IllegalStateException("You don't have enough items for that activity");
        }

      }
      default -> throw new IllegalArgumentException("Provided item cannot be leveled up");
    }
  }

  public void addItemToEquipment(EquipmentItem equipmentItem, int amount) {

    itemInEquipmentService.addItemToEquipment(getCharacter().getEquipment().getId(),
        equipmentItem, amount);
  }


  public void boostRandomStatistic(Character character) {

    int random = ThreadLocalRandom.current().nextInt(6);

    switch (random) {
      case 0 -> character.setStrength(character.getStrength() + 1);
      case 1 -> character.setDexterity(character.getDexterity() + 1);
      case 2 -> character.setIntelligence(character.getIntelligence() + 1);
      case 3 -> character.setLuck(character.getLuck() + 1);
      case 4 -> character.setMovementSpeed(character.getMovementSpeed() + 1);
      case 5 -> character.setArmor(character.getArmor() + 1);
      default -> {
        log.error("incrementStatistic(): invalid statistic name");
        throw new IllegalArgumentException("There is no such statistic");
      }
    }
  }

  public Character createTestCharacter(CharacterFactory characterFactory, WeaponFactory weaponFactory, int level) {

    Character character = characterFactory.get().defaultValues();

    Weapon weapon = weaponFactory.get();

    character.setWeapon(weapon);
    character.setLevel(level);
    character.setHealth(level * 1000);

    for (int i = 0; i < level * 5; i++) { // upgrade statystyki 5 razy na poziom

      boostRandomStatistic(character);
    }

    for (int i = 0; i < level / 10; i++) {  // upgrade przedmiotow raz na 10 poziomow

      character.getWeapon().setLevel(character.getWeapon().getLevel() + 1);
      character.getCape().setLevel(character.getCape().getLevel() + 1);
      character.getSuit().setLevel(character.getSuit().getLevel() + 1);
      character.getBoots().setLevel(character.getBoots().getLevel() + 1);
    }

    setStatisticsFromEffects(character);
    log.info("createTestCharacter(): creating character for testing");
    return character;
  }

  private void setStatisticsFromEffects(Character character) {

    incrementStatsFromMap(character.getSuit().incrementStatistic(), character);
    incrementStatsFromMap(character.getBoots().incrementStatistic(), character);
    incrementStatsFromMap(character.getCape().incrementStatistic(), character);
    incrementStatsFromMap(character.getWeapon().incrementStatistic(), character);
    log.info("setStatisticsFromEffects(): setting statistics from effects");
  }

  private void incrementStatsFromMap(Map<Statistics, Integer> map, Character character) {
    map.forEach((key, value) -> {
      switch (key) {
        case STRENGTH -> character.setStrength(character.getStrength() + value);
        case DEXTERITY -> character.setDexterity(character.getDexterity() + value);
        case INTELLIGENCE -> character.setIntelligence(character.getIntelligence() + value);
        case LUCK -> character.setLuck(character.getLuck() + value);
        case MOVEMENT_SPEED -> character.setMovementSpeed(character.getMovementSpeed() + value);
        case ARMOR -> character.setArmor(character.getArmor() + value);
      }
    });
    log.info("incrementStatsFromMap(): increment statistics for character");
  }
}
