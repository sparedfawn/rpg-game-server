package com.example.demo.model.equipment_item.types;

import com.example.demo.model.equipment_item.EquipmentItem;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class CollectibleItem extends EquipmentItem {

    public CollectibleItem() {
        super();
    }
}
