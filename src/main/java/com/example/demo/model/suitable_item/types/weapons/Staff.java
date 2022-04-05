package com.example.demo.model.suitable_item.types.weapons;

import com.example.demo.model.character.enums.Statistics;
import java.util.Map;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public abstract class Staff extends Weapon {

  public Staff(String name) {
    super(name);
  }

  public Staff() {
    super();
  }

  public abstract Map<Statistics, Integer> incrementStatistic();
}
