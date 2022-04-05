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

import static com.example.demo.model.equipment_item.item_in_equipment.EquipmentItem.RUBY;

@Entity
@Getter
@Setter
public class SithLord extends Character {

  public SithLord() {
    super();
  }

  public SithLord(CharacterDTO characterDTO) {
    super(characterDTO);
  }

  @Override
  public int attack() {
    double randomValue = ThreadLocalRandom.current().nextDouble(0.8, 1.1);
    double value =
        randomValue * 25 /*fixme * weaponDmg * */ * (this.getStrength() + getDexterity());

    return (int) value;
  }

  @Override
  protected boolean parry() {
    SplittableRandom random = new SplittableRandom();
    double rand = this.getDexterity() * random.nextDouble(0.8, 1.3);
    //  0 ... 549 550 551 ...598 599 ... (600 + rand)
    //  └──────────┘└──────────────────────────┘
    // 551 / 600 + rand % | 50 + rand / 600 + rand
    return random.nextInt(0, 600 + Math.min((int) rand, 500)) > 550;
  }

  @Override
  protected boolean dodge() {
    SplittableRandom random = new SplittableRandom();
    double rand = this.getDexterity() * random.nextDouble(0.4, 0.6);
    //  0 ... 949 950 951 ...998 999 ... (1000 + rand)
    //  └─────────┘└────────────────────────────┘
    // 951 / 1000 + rand % | 50 + rand / 1000 + rand
    return random.nextInt(0, 1000 + Math.min((int) rand, 200)) > 950;
  }

  @Override
  public int criticalHit(int damage) {
    SplittableRandom random = new SplittableRandom();

    if (random.nextInt(0, 1000 + this.getLuck()) > 900) {
      return (int) (damage * 1.1);
    }
    return 0;
  }

  @Override
  public UUID getItemForWeaponUpgrade() {

    return RUBY.getId();
  }

  @Override
  public Map<String, Integer> getMapForWeaponUpgrade() {
    return Map.of("Ruby", 2, "Metal", 9 * this.getWeapon().getLevel(), "Hammer", 1);
  }
}