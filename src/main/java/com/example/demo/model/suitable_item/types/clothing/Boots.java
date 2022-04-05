package com.example.demo.model.suitable_item.types.clothing;

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
public class Boots extends SuitableItem {

  public Boots() {
    super("Boots");
  }

  @Override
  public Map<Statistics, Integer> incrementStatistic() {

    return Map.of(Statistics.MOVEMENT_SPEED, 5 * super.getLevel());
  }

  @Override
  public EffectSource sourceType() {

    return EffectSource.BOOTS;
  }

}
