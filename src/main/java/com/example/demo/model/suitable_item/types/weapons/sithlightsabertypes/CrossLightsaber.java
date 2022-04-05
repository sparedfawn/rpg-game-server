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
public class CrossLightsaber extends SithLightsaber {

  public CrossLightsaber() {
    super("Cross Lightsaber");
  }

  @Override
  public Map<Statistics, Integer> incrementStatistic() {

    return Map.of(Statistics.STRENGTH,
        (int) (Math.log(5 * super.getLevel()) / Math.log(2)) * super.getLevel(),
        Statistics.ARMOR,
        (int) (Math.log(5 * super.getLevel()) / Math.log(2)) * super.getLevel());
  }

}
