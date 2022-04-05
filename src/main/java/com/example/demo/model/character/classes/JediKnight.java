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

import static com.example.demo.model.equipment_item.item_in_equipment.EquipmentItem.DIAMOND;

@Entity
@Getter
@Setter
public class JediKnight extends Character {

  public JediKnight() {
    super();
  }

  public JediKnight(CharacterDTO characterDTO) {
    super(characterDTO);
  }

  @Override
  public int attack() {
    double randomValue = ThreadLocalRandom.current().nextDouble(0.7, 1);
    double value = randomValue * 80 /*fixme * weaponDmg * */ * this.getStrength();

    return (int) value;
  }

  @Override
  protected boolean parry() {
    SplittableRandom random = new SplittableRandom();
    double rand = this.getDexterity() * random.nextDouble(1,1.2);
    //  0 ... 549 550 551 ...598 599 ... (600 + rand)
    //  └─────────┘└───────────────────────────┘
    //  551 / 600 + rand % | 50 + rand / 600 + rand
    return random.nextInt(0, 600 + Math.min((int) rand, 500)) > 550;
  }

  @Override
  public int criticalHit(int damage) {
    SplittableRandom random = new SplittableRandom();

    if (random.nextInt(0, 1000 + this.getLuck()) > 900) {
      return damage;
    }
    return 0;
  }

  @Override
  public UUID getItemForWeaponUpgrade() {

    return DIAMOND.getId();
  }

  @Override
  public Map<String, Integer> getMapForWeaponUpgrade() {
    return Map.of("Diamond", 1, "Hammer", 1, "Metal", this.getWeapon().getLevel() * 7);
  }
}