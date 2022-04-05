package com.example.demo.model.suitable_item.types.weapons.stafftypes;

import com.example.demo.model.character.enums.Statistics;
import com.example.demo.model.suitable_item.types.weapons.Staff;
import java.util.Map;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ArchangelStaff extends Staff {

  public ArchangelStaff() {
    super("Archangel's Staff");
  }

  @Override
  public Map<Statistics, Integer> incrementStatistic() {

    return Map.of(Statistics.INTELLIGENCE,
        (int) (Math.log(5 * super.getLevel()) / Math.log(2)) * super.getLevel(),
        Statistics.ARMOR,
        (int) (Math.log(10 * super.getLevel()) / Math.log(2)) * super.getLevel());
  }


}
