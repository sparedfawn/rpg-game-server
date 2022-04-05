package com.example.demo.model.character.classes;

import com.example.demo.model.character.model.Character;
import com.example.demo.model.character.model.CharacterDTO;
import java.util.Map;
import java.util.SplittableRandom;
import java.util.UUID;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import static com.example.demo.model.equipment_item.item_in_equipment.EquipmentItem.QUARTZ;

@Entity
@Getter
@Setter
public class PriceHunter extends Character {

  public PriceHunter() {
    super();
  }

  public PriceHunter(CharacterDTO characterDTO) {
    super(characterDTO);
  }

  @Override
  public int attack() {

    return 40 /*fixme * weaponDmg * */ * this.getDexterity();
  }

  @Override
  protected boolean dodge() {
    SplittableRandom random = new SplittableRandom();
    double rand = (this.getLuck() + this.getDexterity()) * random.nextDouble(1, 1.3);
    //  0 ... 949 950 951 ...998 999 ... (1000 + rand)
    //  └─────────┘└────────────────────────────┘
    // 951 / 1000 + rand % | 50 + rand / 1000 + rand
    return random.nextInt(0, 1000 + Math.min((int) rand, 400)) > 850;
  }

  @Override
  public int criticalHit(int damage) {
    SplittableRandom random = new SplittableRandom();

    if (random.nextInt(this.getLuck(), 1000) > 900) {
      return (int) (damage * 0.75);
    }
    return 0;
  }

  @Override
  public UUID getItemForWeaponUpgrade() {

    return QUARTZ.getId();
  }

  @Override
  public Map<String, Integer> getMapForWeaponUpgrade() {
    return Map.of("Quartz", 2 * this.getWeapon().getLevel(), "Metal", 10 * this.getWeapon().getLevel(), "Hammer", 1);
  }
}