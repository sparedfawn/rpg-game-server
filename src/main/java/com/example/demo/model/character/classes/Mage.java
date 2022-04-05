package com.example.demo.model.character.classes;

import com.example.demo.model.character.model.Character;
import com.example.demo.model.character.model.CharacterDTO;
import java.util.Map;
import java.util.SplittableRandom;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import static com.example.demo.model.equipment_item.item_in_equipment.EquipmentItem.EMERALD;

@Entity
@Getter
@Setter
public class Mage extends Character {

  public Mage() {
    super();
  }

  public Mage(CharacterDTO characterDTO) {
    super(characterDTO);
  }

  @Override
  public int attack() {
    double randomValue = ThreadLocalRandom.current().nextDouble(0.6, 1.3);
    double value = randomValue * 40 /*fixme * weaponDmg * */ * this.getIntelligence();

    return (int) value;
  }

  @Override
  public int criticalHit(int damage) {
    SplittableRandom random = new SplittableRandom();

    if (random.nextInt(0, 1000 + this.getLuck()) > 800) {
      return this.attack();
    }
    return 0;
  }

  @Override
  public UUID getItemForWeaponUpgrade() {

    return EMERALD.getId();
  }

  @Override
  public Map<String, Integer> getMapForWeaponUpgrade() {
    return Map.of("Wood", this.getWeapon().getLevel() * 2, "Metal", this.getWeapon().getLevel(), "Emerald", 2);
  }
}