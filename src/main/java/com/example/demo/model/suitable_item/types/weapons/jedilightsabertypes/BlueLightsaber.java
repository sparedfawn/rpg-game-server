package com.example.demo.model.suitable_item.types.weapons.jedilightsabertypes;

import com.example.demo.model.character.enums.Statistics;
import com.example.demo.model.suitable_item.types.weapons.JediLightsaber;
import java.util.Map;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BlueLightsaber extends JediLightsaber {

  public BlueLightsaber() {
    super("Blue Lightsaber");
  }

  @Override
  public Map<Statistics, Integer> incrementStatistic() {

    return Map.of(Statistics.STRENGTH,
        (int) (Math.log(5 * super.getLevel()) / Math.log(2)) * super.getLevel());
  }

}
