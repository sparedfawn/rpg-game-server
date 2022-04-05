package com.example.demo.model.suitable_item.types.weapons.sithlightsabertypes;

import com.example.demo.model.character.enums.Statistics;
import com.example.demo.model.suitable_item.types.weapons.SithLightsaber;
import java.util.Map;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class DoubleLightsaber extends SithLightsaber {

  public DoubleLightsaber() {
    super("Double-edge Lightsaber");
  }

  @Override
  public Map<Statistics, Integer> incrementStatistic() {

    return Map.of(Statistics.DEXTERITY,
        (int) (Math.log(15 * super.getLevel()) / Math.log(2)) * super.getLevel());
  }


}
