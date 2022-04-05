package com.example.demo.model.quest.model;

import com.example.demo.model.equipment_item.types.CollectibleItem;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Quest {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id")
    @Schema(name = "unique id of quest")
    private UUID id;

    @Column(name = "experience")
    @Schema(name = "experience granted for completing quest")
    private int experience;

    @Column(name = "gold")
    @Schema(name = "gold granted for completing quest")
    private int gold;

    @Column(name = "duration")
    @Schema(name = "time required for traveling to quest")
    private LocalTime duration;

    @Column(name = "started")
    @Schema(name = "time when quest started")
    private LocalDateTime questStarted;

    @JoinColumn(name = "collectible_item_id", referencedColumnName = "id")
    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    private CollectibleItem collectibleItem;

    @Column(name = "amount_of_items")
    private int amountOfItems;
}
