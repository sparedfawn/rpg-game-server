package com.example.demo.model.suitable_item.types.weapons.pistoltypes;

import com.example.demo.model.character.enums.Statistics;
import com.example.demo.model.suitable_item.types.weapons.Pistol;
import java.util.Map;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class HeavyPistol extends Pistol {

  public HeavyPistol() {
    super("Heavy Pistol");
  }

  @Override
  public Map<Statistics, Integer> incrementStatistic() {

    return Map.of(Statistics.ARMOR,
        (int) (Math.log(5 * super.getLevel()) / Math.log(2)) * super.getLevel(),
        Statistics.DEXTERITY,
        (int) (Math.log(5 * super.getLevel()) / Math.log(2)) * super.getLevel());
  }
}
