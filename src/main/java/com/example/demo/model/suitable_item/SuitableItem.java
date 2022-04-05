package com.example.demo.model.suitable_item;

import com.example.demo.model.character.enums.Statistics;
import com.example.demo.model.effect.EffectSource;
import java.util.Map;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
@Table(name = "suitable_item")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class SuitableItem {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(
      name = "UUID",
      strategy = "org.hibernate.id.UUIDGenerator"
  )
  @Column(name = "id")
  private UUID id;

  @Column(name = "level")
  private int level;

  @Column(name = "name")
  private String name;

  public SuitableItem() {

  }

  public SuitableItem(String name) {

    this.level = 1;
    this.name = name;
  }


  public SuitableItem setAll(SuitableItemDTO suitableItemDTO) {

    this.level = suitableItemDTO.getLevel();
    this.name = suitableItemDTO.getName();
    return this;
  }

  public abstract Map<Statistics, Integer> incrementStatistic();

  public abstract EffectSource sourceType();
}
