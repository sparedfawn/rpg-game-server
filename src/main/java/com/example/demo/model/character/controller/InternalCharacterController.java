package com.example.demo.model.character.controller;

import com.example.demo.model.character.CharacterService;
import com.example.demo.model.character.classes.CharacterFactory;
import com.example.demo.model.character.enums.Statistics;
import com.example.demo.model.character.model.CharacterDTO;
import com.example.demo.model.equipment_item.item_in_equipment.EquipmentItem;
import com.example.demo.model.suitable_item.types.weapons.WeaponFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("characters-internal")
@SecurityRequirement(name = "bearerAuth")
@ConditionalOnExpression("${internal.controllers.enabled}")
public class InternalCharacterController {

    private final CharacterService characterService;

    @PostMapping("add")
    @Operation(summary = "Add a character to db (testing reasons)", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<?> addCharacter(@RequestParam CharacterFactory characterFactory,
                                          @RequestParam
                                                  WeaponFactory weaponFactory) {

        try {
            characterService.addCharacter(characterFactory, weaponFactory);
        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("add-character-to-test")
    @Operation(summary = "Add single character to DB (testing reasons)",
            description = "Adding a character to DB (testing reasons)", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<?> addCharacterForTesting(
            @Parameter(name = "Character body", required = true)
            @RequestBody CharacterDTO characterDTO,
            @RequestParam CharacterFactory characterType) {

        try {
            characterService.addCharacterForTesting(characterDTO, characterType);
        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("{id}")
    @Operation(summary = "Update single character statistic",
            description = "Incrementing single statistic", security = @SecurityRequirement(name = "bearerAuth"))
    public void incrementStatistic(@Parameter(name = "UUID value of ID", required = true)
                                   @PathVariable UUID id, @RequestParam Statistics statistic) {
        characterService.incrementStatistic(id, statistic);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Removing single character from DB",
            description = "Removing a character from DB", security = @SecurityRequirement(name = "bearerAuth"))
    public void deleteCharacter(@Parameter(name = "UUID value of ID", required = true)
                                @PathVariable UUID id) {
        characterService.deleteCharacter(id);
    }

    @PutMapping("{id}/experience")
    @Operation(summary = "Adding experience to character",
            description = "Adding experience", security = @SecurityRequirement(name = "bearerAuth"))
    public void addExp(@Parameter(name = "UUID value of ID", required = true) @PathVariable UUID id,
                       @RequestParam int exp) {
        characterService.addExp(id, exp);
    }

    @PostMapping("add-item-for-current-user")
    public ResponseEntity<?> addItemForCurrentUser(@RequestParam EquipmentItem equipmentItem, @RequestParam int amount) {

        try {
            characterService.addItemToEquipment(equipmentItem, amount);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
