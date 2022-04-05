package com.example.demo.model.suitable_item.types.weapons;

import com.example.demo.model.character.enums.Statistics;
import com.example.demo.model.effect.EffectSource;
import com.example.demo.model.suitable_item.SuitableItem;
import java.util.Map;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public abstract class Weapon extends SuitableItem {

  public Weapon(String name) {
    super(name);
  }

  public Weapon() {
    super();
  }

  public abstract Map<Statistics, Integer> incrementStatistic();

  @Override
  public EffectSource sourceType() {

    return EffectSource.WEAPON;
  }
}
