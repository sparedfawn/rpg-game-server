package com.example.demo.common.fight.service;

import com.example.demo.model.character.CharacterService;
import com.example.demo.model.character.classes.CharacterFactory;
import com.example.demo.model.character.classes.JediKnight;
import com.example.demo.model.character.classes.Mage;
import com.example.demo.model.character.classes.PriceHunter;
import com.example.demo.model.character.classes.SithLord;
import com.example.demo.model.character.model.Character;
import com.example.demo.model.suitable_item.types.weapons.WeaponFactory;
import java.util.Map;
import java.util.TreeMap;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.mutable.MutableInt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InternalFightService {

  private static final int JEDI_KNIGHT_ID = 0;

  private static final int MAGE_ID = 1;

  private static final int PRICE_HUNTER_ID = 2;

  private static final int SITH_LORD_ID = 3;

  private final CharacterService characterService;

  private final FightService fightService;


  public Map<String, String> getFights() {

    Integer[][] fightsWon = {{0, 0, 0, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0}};

    Character[] characters = new Character[12];

    for (int level = 1; level <= 50; level++) {

      createCharacters(characters, level);

      for (int i = 0; i < 11; i++) {
        for (int j = i + 1; j < 12; j++) {

          Character winner = getFight(characters[i], characters[j]);

          if (JediKnight.class.equals(winner.getClass())) {
            fightsWon[(level - 1) / 10][JEDI_KNIGHT_ID] += 1;
          } else if (Mage.class.equals(winner.getClass())) {
            fightsWon[(level - 1) / 10][MAGE_ID] += 1;
          } else if (PriceHunter.class.equals(winner.getClass())) {
            fightsWon[(level - 1) / 10][PRICE_HUNTER_ID] += 1;
          } else if (SithLord.class.equals(winner.getClass())) {
            fightsWon[(level - 1) / 10][SITH_LORD_ID] += 1;
          } else {
            throw new IllegalStateException();
          }
        }
      }
    }

    Map<String, String> map = new TreeMap<>();

    for (int lvlRange = 0; lvlRange < 5; lvlRange++) {

      String value = lvlRange * 10 + 1 + "-" + (lvlRange + 1) * 10;

      map.put("JediKnight(" + value + ")",
          Math.round((double) fightsWon[lvlRange][JEDI_KNIGHT_ID] / 330 * 10000.0) / 100.0 + "%");
      map.put("Mage(" + value + ")",
          Math.round((double) fightsWon[lvlRange][MAGE_ID] / 330 * 10000.0) / 100.0 + "%");
      map.put("PriceHunter(" + value + ")",
          Math.round((double) fightsWon[lvlRange][PRICE_HUNTER_ID] / 330 * 10000.0) / 100.0 + "%");
      map.put("SithLord(" + value + ")",
          Math.round((double) fightsWon[lvlRange][SITH_LORD_ID] / 330 * 10000.0) / 100.0 + "%");
    }

    return map;

  }

  private Character getFight(Character one, Character two) {

    MutableInt hpOne = new MutableInt(one.getHealth());
    MutableInt hpTwo = new MutableInt(two.getHealth());

    while (hpOne.getValue() > 0 && hpTwo.getValue() > 0) {

      fightService.simulateTurn(one, two, hpOne, hpTwo);

      if (hpTwo.getValue() <= 0) {

        return one;
      } else if (hpOne.getValue() <= 0) {

        return two;
      }

      fightService.simulateTurn(two, one, hpTwo, hpOne);
    }

    if (hpTwo.getValue() <= 0) {

      return one;
    } else {

      return two;
    }
  }

  // fixMe jak naprawi sie kod z walka z opponentem to wtedy mozna to zrobic
  public Map<String, String> getOpponentFights() {

    Integer[][] fightsWon = {{0, 0, 0, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0}};

    Character[] characters = new Character[12];

    for (int level = 1; level <= 50; level++) {

      createCharacters(characters, level);

      for (int i = 0; i < 12; i++) {

      }

    }

    return Map.of();
  }

  private void createCharacters(Character[] characters, int level) {
    characters[0] = characterService
        .createTestCharacter(CharacterFactory.JEDI_KNIGHT, WeaponFactory.PURPLE_LIGHTSABER,
            level);
    characters[1] = characterService
        .createTestCharacter(CharacterFactory.JEDI_KNIGHT, WeaponFactory.GREEN_LIGHTSABER, level);
    characters[2] = characterService
        .createTestCharacter(CharacterFactory.JEDI_KNIGHT, WeaponFactory.BLUE_LIGHTSABER, level);
    characters[3] = characterService
        .createTestCharacter(CharacterFactory.MAGE, WeaponFactory.ARCHANGEL_STAFF, level);
    characters[4] = characterService
        .createTestCharacter(CharacterFactory.MAGE, WeaponFactory.LUDEN_STAFF, level);
    characters[5] = characterService
        .createTestCharacter(CharacterFactory.MAGE, WeaponFactory.VOID_STAFF, level);
    characters[6] = characterService
        .createTestCharacter(CharacterFactory.PRICE_HUNTER, WeaponFactory.DUAL_PISTOL, level);
    characters[7] = characterService
        .createTestCharacter(CharacterFactory.PRICE_HUNTER, WeaponFactory.HEAVY_PISTOL, level);
    characters[8] = characterService
        .createTestCharacter(CharacterFactory.PRICE_HUNTER, WeaponFactory.LIGHT_PISTOL, level);
    characters[9] = characterService
        .createTestCharacter(CharacterFactory.SITH_LORD, WeaponFactory.CROSS_LIGHTSABER, level);
    characters[10] = characterService
        .createTestCharacter(CharacterFactory.SITH_LORD, WeaponFactory.CURVED_LIGHTSABER, level);
    characters[11] = characterService
        .createTestCharacter(CharacterFactory.SITH_LORD, WeaponFactory.DOUBLE_LIGHTSABER, level);
  }
}
