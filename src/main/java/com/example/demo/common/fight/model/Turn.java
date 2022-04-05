package com.example.demo.common.fight.model;

import com.example.demo.common.fight.enums.ActionPerformed;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Turn {

  private int damageDealt;

  private ActionPerformed actionPerformed;

  private String movementProvider;

  private int hpRemainingAttacker;

  private int hpRemainingDefender;
}
