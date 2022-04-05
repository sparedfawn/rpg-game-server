package com.example.demo.model.equipment_item;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "equipment_item")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class EquipmentItem {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id")
    @Schema(name = "unique id of character")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "rarity")
    private String rarity;
}
