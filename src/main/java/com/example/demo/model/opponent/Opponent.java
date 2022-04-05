package com.example.demo.model.opponent;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Entity
@Data
@Table(name = "opponent")
@Schema(description = "Single Opponent")
public class Opponent {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id")
    @Schema(name = "unique id of opponent")
    private UUID id;

    @Column(name = "name")
    @Schema(name = "opponents class Name")
    private String name;

    //fixMe potencjalne zlikwidowanie niektorych pol
    @Column(name = "strength")
    @Schema(name = "strength of opponent")
    private int strength;

    @Column(name = "dexterity")
    @Schema(name = "dexterity of opponent")
    private int dexterity;


    @Column(name = "luck")
    @Schema(name = "luck of opponent")
    private int luck;

    @Column(name = "armor")
    @Schema(name = "armor of opponent")
    private int armor;

    @Column(name = "health")
    @Schema(name = "health of opponent")
    private int health;

    public Opponent setAll(int level) {

        this.strength *= level;
        this.dexterity *= level;
        this.luck *= level;
        this.armor *= level;
        this.health *= level;

        return this;
    }

    public boolean dodge() {
        SplittableRandom random = new SplittableRandom();
        double rand = dexterity * random.nextDouble(1, 1.3);
        //  0 ... 949 950 951 ...998 999 ... (1000 + rand)
        //  └─────────┘└────────────────────────────┘
        // 951 / 1000 + rand % | 50 + rand / 1000 + rand
        return random.nextInt(0, 1000 + Math.min((int) rand, 400)) > 850;
    }

    public int attack() {
        double randomValue = ThreadLocalRandom.current().nextDouble(0.7, 1);
        return (int) (strength * randomValue * 60);
    }

    public int criticalHit(int damage) {
        if (ThreadLocalRandom.current().nextInt(0, 1000 + luck) > 900) {
            return damage;
        } else {
            return 0;
        }

    }


}
