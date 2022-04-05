package com.example.demo.model.opponent;

import com.example.demo.model.character.*;
import com.example.demo.model.character.model.Character;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class OpponentService {

    private final OpponentRepository opponentRepository;
    private final CharacterRepository characterRepository;


    public List<Opponent> getAllOpponents() {
        log.info("getAllOpponents(): getting all opponents from DB");
        return opponentRepository.findAll();
    }


    public Opponent getOpponent(UUID id) {
        log.info("getOpponent(): getting single opponent based on unique id");
        return opponentRepository.findById(id).get();

    }


    public Opponent adjustStatistic(UUID id, UUID idCharacter) {
        Opponent customizeOpponent = opponentRepository.getById(id);
        Character character = characterRepository.getById(idCharacter);
        customizeOpponent.setAll(character.getLevel());
        log.info("method: adjustStatistic(): adjusting statistics");
        return customizeOpponent;
    }
}
