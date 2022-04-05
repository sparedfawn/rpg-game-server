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
public class LightPistol extends Pistol {

  public LightPistol() {
    super("Light Pistol");
  }

  @Override
  public Map<Statistics, Integer> incrementStatistic() {

    return Map.of(Statistics.DEXTERITY,
        (int) (Math.log(5 * super.getLevel()) / Math.log(2)) * super.getLevel(),
        Statistics.LUCK,
        (int) (Math.log(7 * super.getLevel()) / Math.log(2)) * super.getLevel());
  }


}
