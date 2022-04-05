package com.example.demo.model.equipment_item.equipment;

import com.example.demo.model.equipment_item.item_in_equipment.ItemInEquipment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "equipment")
@Slf4j
public class Equipment {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id")
    @Schema(name = "unique id of character")
    private UUID id;

    @OneToMany(mappedBy="equipment")
    private List<ItemInEquipment> itemsInEquipment;
}
