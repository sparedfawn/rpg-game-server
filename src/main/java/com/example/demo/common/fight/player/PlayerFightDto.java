package com.example.demo.common.fight.player;

import com.example.demo.common.fight.model.Turn;
import com.example.demo.model.character.model.Character;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PlayerFightDto {

    private Character characterOne;

    private Character characterTwo;

    private List<Turn> playerFightTurns;
}
