package com.example.demo.model.equipment_item.types;

import com.example.demo.model.character.enums.Statistics;
import com.example.demo.model.equipment_item.EquipmentItem;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class UsableItem extends EquipmentItem {

    public UsableItem() {
        super();
    }

    public Statistics incrementsStatistic() {

        switch (this.getName()) {

            case "Strength Potion" -> {
                return Statistics.STRENGTH;
            }
            case "Dexterity Potion" -> {
                return Statistics.DEXTERITY;
            }
            case "Armor Potion" -> {
                return Statistics.ARMOR;
            }
            default -> throw new IllegalArgumentException("Wrong item provided");
        }
    }
}
