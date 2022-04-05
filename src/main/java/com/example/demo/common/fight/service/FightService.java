package com.example.demo.common.fight.service;

import com.example.demo.common.fight.enums.ActionPerformed;
import com.example.demo.common.fight.model.Turn;
import com.example.demo.model.character.CharacterService;
import com.example.demo.model.character.classes.Mage;
import com.example.demo.model.character.model.Character;
import com.example.demo.model.opponent.Opponent;
import com.example.demo.model.opponent.OpponentService;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.mutable.MutableInt;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class FightService {

  private final CharacterService characterService;

  private final OpponentService opponentService;

  public List<Turn> getFight(UUID idOpponent) {

    log.info("method: getFight(): method has been called");
    return simulateFight(
        characterService.getCharacterWithEffectsOn(characterService.getCharacter().getId()),
        characterService.getCharacterWithEffectsOn(idOpponent));
  }

  private List<Turn> simulateFight(Character one, Character two) {

    List<Turn> fightTurns = new ArrayList<>();

    MutableInt hpOne = new MutableInt(one.getHealth());
    MutableInt hpTwo = new MutableInt(two.getHealth());

    while (hpOne.getValue() > 0 && hpTwo.getValue() > 0) {

      fightTurns.add(simulateTurn(one, two, hpOne, hpTwo));

      log.info(
          "Action performed by character one: " + fightTurns.get(fightTurns.size() - 1).toString());

      if (hpTwo.getValue() <= 0 || hpOne.getValue() <= 0) {

        return fightTurns;
      }

      fightTurns.add(simulateTurn(two, one, hpTwo, hpOne));

      log.info(
          "Action performed by character two: " + fightTurns.get(fightTurns.size() - 1).toString());
    }

    return fightTurns;
  }

  Turn simulateTurn(Character attacker, Character defender,
      MutableInt hpAttacker, MutableInt hpDefender) {

    int damage = attacker.attack();
    boolean wasCrit = false;
    int crit = attacker.criticalHit(damage);

    if (crit != 0) {
      damage += crit;
      wasCrit = true;
    }

    if (defender.getLevel() - attacker.getLevel() < 15 && attacker.getClass() != Mage.class) {
      damage = defender.negateDmg(damage);
    }

    if (damage == 0) {
      if (attacker.getId() != null) {

        return new Turn(damage,
            ActionPerformed.DODGE,
            attacker.getId().toString(),
            hpAttacker.getValue(),
            hpDefender.getValue()
        );

      }
    } else if (damage < 0) {
      damage += defender.getArmor();
      hpAttacker.add(Math.min(damage, 0));

      if (attacker.getId() != null) {

        return new Turn(
            damage,
            ActionPerformed.PARRY,
            attacker.getId().toString(),
            hpAttacker.getValue(),
            hpDefender.getValue()
        );

      }
    } else {
      damage -= defender.getArmor();
      hpDefender.add(damage < 0 ? 0 : -damage);

      if (attacker.getId() != null) {

        return new Turn(
            damage,
            wasCrit ? ActionPerformed.CRITICAL_HIT : ActionPerformed.ATTACK,
            attacker.getId().toString(),
            hpAttacker.getValue(),
            hpDefender.getValue()
        );

      }
    }
    return new Turn();
  }


  public List<Turn> getFightWithOpponent() {

    log.info("method: getFightWithOpponent(): method has been called");
    List<Opponent> opponentList = opponentService.getAllOpponents();
    Opponent opponent = opponentList.get(new Random().nextInt(opponentList.size()));

    return simulateFightWithOpponent(
        characterService.getCharacterWithEffectsOn(characterService.getCharacter().getId()),
        opponent);
  }

  public List<Turn> simulateFightWithOpponent(Character character, Opponent opponent) {

    List<Turn> fightTurns = new ArrayList<>();

    MutableInt hpOne = new MutableInt(character.getHealth());
    MutableInt hpTwo = new MutableInt(opponent.getHealth());

    while (hpOne.getValue() > 0 && hpTwo.getValue() > 0) {

      fightTurns.add(simulateCharacterTurn(character, opponent, hpOne, hpTwo));

      log.info(
          "Action performed by character: " + fightTurns.get(fightTurns.size() - 1).toString());

      if (hpTwo.getValue() <= 0 || hpOne.getValue() <= 0) {

        return fightTurns;
      }

      fightTurns.add(simulateBotTurn(character, opponent, hpTwo, hpOne));

      log.info(
          "Action performed by oponent: " + fightTurns.get(fightTurns.size() - 1).toString());
    }

    return fightTurns;
  }

  private Turn simulateCharacterTurn(Character character, Opponent opponent, MutableInt hpCharacter,
      MutableInt hpBot) {

    int damage = character.attack();
    boolean wasCritical = false;
    int critical = character.criticalHit(damage);

    if (critical != 0) {
      damage += critical;
      wasCritical = true;
    }

    if (opponent.dodge()) {

      return new Turn(0,
          ActionPerformed.DODGE,
          character.getId().toString(),
          hpCharacter.getValue(),
          hpBot.getValue()
      );

    } else {
      damage -= opponent.getArmor();
      hpBot.add(damage < 0 ? 0 : -damage);

      return new Turn(
          damage,
          wasCritical ? ActionPerformed.CRITICAL_HIT : ActionPerformed.ATTACK,
          character.getId().toString(),
          hpCharacter.getValue(),
          hpBot.getValue()
      );

    }
  }

  private Turn simulateBotTurn(Character character, Opponent opponent, MutableInt hpCharacter,
      MutableInt hpBot) {

    int damage = opponent.attack();
    boolean wasCritical = false;
    int critical = character.criticalHit(damage);

    if (critical != 0) {
      damage += critical;
      wasCritical = true;
    }

    if (opponent.dodge()) {
      damage = 0;
    }

    if (character.getClass() != Mage.class) {
      damage = character.negateDmg(damage);
    }

    switch (damage) {

      case 0 -> {
        return new Turn(
            damage,
            ActionPerformed.DODGE,
            opponent.getName(),
            hpCharacter.getValue(),
            hpBot.getValue()
        );
      }

      default -> {

        if (damage < 0) {
          damage += character.getArmor();
          hpBot.add(Math.min(damage, 0));

          return new Turn(
              damage,
              ActionPerformed.PARRY,
              opponent.getName(),
              hpCharacter.getValue(),
              hpBot.getValue()
          );

        } else {
          damage -= character.getArmor();
          hpCharacter.add(damage < 0 ? 0 : -damage);

          return new Turn(
              damage,
              wasCritical ? ActionPerformed.CRITICAL_HIT : ActionPerformed.ATTACK,
              opponent.getName(),
              hpCharacter.getValue(),
              hpBot.getValue()
          );
        }

      }

    }

  }


}