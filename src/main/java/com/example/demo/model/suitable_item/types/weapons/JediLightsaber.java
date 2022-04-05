package com.example.demo.model.suitable_item.types.weapons;

import com.example.demo.model.character.enums.Statistics;
import java.util.Map;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public abstract class JediLightsaber extends Weapon {

  public JediLightsaber(String name) {
    super(name);
  }

  public JediLightsaber() {
    super();
  }

  public abstract Map<Statistics, Integer> incrementStatistic();
}
