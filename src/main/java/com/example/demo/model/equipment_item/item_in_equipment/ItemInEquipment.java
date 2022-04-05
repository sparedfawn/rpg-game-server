package com.example.demo.model.equipment_item.item_in_equipment;

import com.example.demo.model.equipment_item.EquipmentItem;
import com.example.demo.model.equipment_item.equipment.Equipment;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "item_in_equipment")
@JsonIgnoreProperties({ "equipment" })
public class ItemInEquipment {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id")
    @Schema(name = "unique id of character")
    private UUID id;

    @Column(name = "amount")
    private int amount;

    @ManyToOne
    @JoinColumn(name = "equipment_id", referencedColumnName = "id")
    private Equipment equipment;

    @ManyToOne
    @JoinColumn(name="equipment_item_id", referencedColumnName = "id")
    private EquipmentItem equipmentItem;
}
