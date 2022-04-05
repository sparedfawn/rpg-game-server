package com.example.demo.model.character.model;

import com.example.demo.model.effect.Effect;
import com.example.demo.model.equipment_item.equipment.Equipment;
import com.example.demo.model.equipment_item.types.CollectibleItem;
import com.example.demo.model.quest.model.Quest;
import com.example.demo.model.quest.model.PossibleQuest;
import com.example.demo.model.suitable_item.types.clothing.Boots;
import com.example.demo.model.suitable_item.types.clothing.Cape;
import com.example.demo.model.suitable_item.types.clothing.Suit;
import com.example.demo.model.suitable_item.types.weapons.Weapon;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.*;
import javax.persistence.*;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "character")
@Schema(description = "Single Character")
@JsonIgnoreProperties({"possibleQuestOne", "possibleQuestTwo", "possibleQuestThree", "mapForWeaponUpgrade"})
public abstract class Character {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(
      name = "UUID",
      strategy = "org.hibernate.id.UUIDGenerator"
  )
  @Column(name = "id")
  @Schema(name = "unique id of character")
  private UUID id;

  @Column(name = "level")
  @Schema(name = "level of character")
  private int level;

  @Column(name = "strength")
  @Schema(name = "strength of character")
  private int strength;

  @Column(name = "dexterity")
  @Schema(name = "dexterity of character")
  private int dexterity;

  @Column(name = "intelligence")
  @Schema(name = "intelligence of character")
  private int intelligence;

  @Column(name = "luck")
  @Schema(name = "luck of character")
  private int luck;

  @Column(name = "movementspeed")
  @Schema(name = "movement Speed of character")
  private int movementSpeed;

  @Column(name = "armor")
  @Schema(name = "armor of character")
  private int armor;

  @Column(name = "experience")
  @Schema(name = "experience of character")
  private int experience;

  @Column(name = "gold")
  @Schema(name = "gold of character")
  private int gold;

  @Column(name = "health")
  @Schema(name = "health of character")
  private int health;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "boots_id", referencedColumnName = "id")
  private Boots boots;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "cape_id", referencedColumnName = "id")
  private Cape cape;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "suit_id", referencedColumnName = "id")
  private Suit suit;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "weapon_id", referencedColumnName = "id")
  private Weapon weapon;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "equipment_id", referencedColumnName = "id")
  private Equipment equipment;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "quest_id", referencedColumnName = "id")
  private Quest takenQuest;

  @OneToMany(mappedBy = "character")
  private List<PossibleQuest> possibleQuests;

  @OneToMany(mappedBy = "character")
  private List<Effect> effectList;


  public Character() {}

  public Character (CharacterDTO characterDTO) {

    this.setAll(characterDTO);
  }


  public abstract int attack();

  public int negateDmg(int damage) {
    boolean dodge = dodge();
    boolean parry = parry();
    if (dodge && parry) {
      return -damage;
    } else if (dodge) {
      return 0;
    } else if (parry) {
      return -(damage / 2);
    } else {
      return damage;
    }
  }

  protected boolean parry() {
    return false;
  }

  protected boolean dodge() {
    return false;
  }

  public abstract int criticalHit(int damage);

  public abstract UUID getItemForWeaponUpgrade();

  public abstract Map<String, Integer> getMapForWeaponUpgrade();

  public Character setAll(CharacterDTO characterDTO) {

    this.level = characterDTO.getLevel();
    this.strength = characterDTO.getStrength();
    this.dexterity = characterDTO.getDexterity();
    this.intelligence = characterDTO.getIntelligence();
    this.luck = characterDTO.getLuck();
    this.movementSpeed = characterDTO.getMovementSpeed();
    this.armor = characterDTO.getArmor();
    this.experience = characterDTO.getExperience();
    this.health = characterDTO.getHealth();
    this.boots = new Boots();
    this.cape = new Cape();
    this.suit = new Suit();
    this.equipment = new Equipment();
    return this;
  }

  public Character defaultValues() {

    this.level = 1;
    this.strength = 1;
    this.dexterity = 1;
    this.intelligence = 1;
    this.luck = 1;
    this.movementSpeed = 1;
    this.armor = 1;
    this.experience = 0;
    this.gold = 0;
    this.health = 300;
    this.boots = new Boots();
    this.cape = new Cape();
    this.suit = new Suit();
    this.equipment = new Equipment();
    return this;
  }
}
