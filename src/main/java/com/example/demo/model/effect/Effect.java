package com.example.demo.model.effect;

import com.example.demo.model.character.enums.Statistics;
import com.example.demo.model.character.model.Character;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "effect")
@JsonIgnoreProperties({"character"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Effect {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id")
    @Schema(name = "unique id of effect")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "character_id", referencedColumnName = "id")
    private Character character;

    @Column(name = "stat_incremented")
    private Statistics statIncremented;

    @Column(name = "stat_value")
    private int statValue;

    @Column(name = "source")
    @Enumerated(EnumType.STRING)
    private EffectSource effectSource;
}
