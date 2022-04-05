package com.example.demo.model.quest.model;

import com.example.demo.model.character.model.Character;
import com.example.demo.model.equipment_item.types.CollectibleItem;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "possible_quest")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"character"})
public class PossibleQuest {

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

    @JoinColumn(name = "collectible_item_id", referencedColumnName = "id")
    @OneToOne(cascade = CascadeType.ALL)
    private CollectibleItem collectibleItem;

    @Column(name = "amount_of_items")
    private int amountOfItems;

    @ManyToOne
    @JoinColumn(name = "character_id", referencedColumnName = "id")
    private Character character;

}
