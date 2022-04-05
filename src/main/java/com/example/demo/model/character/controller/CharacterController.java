package com.example.demo.model.character.controller;

import com.example.demo.model.character.CharacterService;
import com.example.demo.model.character.classes.CharacterFactory;
import com.example.demo.model.character.enums.ItemToUpgrade;
import com.example.demo.model.character.model.Character;
import com.example.demo.model.suitable_item.types.weapons.WeaponFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("characters")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class CharacterController {

    private final CharacterService characterService;

    @GetMapping
    @Operation(summary = "Return list of characters from DB",
            description = "Show all characters in db", security = @SecurityRequirement(name = "bearerAuth"))
    public List<Character> getAllCharacters() {

        return characterService.getAllCharacters();
    }

    @GetMapping("current-user")
    @Operation(summary = "Returns character of currently logged in user", security = @SecurityRequirement(name = "bearerAuth"))
    public Character getCharacter() {

        return characterService.getCharacter();
    }

    static class AddCharacterRequestBody {

        public AddCharacterRequestBody(CharacterFactory characterType, WeaponFactory weaponType) {
            this.characterType = characterType;
            this.weaponType = weaponType;
        }

        CharacterFactory characterType;

        WeaponFactory weaponType;
    }

    @PostMapping
    @Operation(summary = "Add new character to DB", description = "Special endpoint for creating character via frontend character body", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<?> addCharacter(
            @RequestBody AddCharacterRequestBody addCharacterRequestBody) {

        try {
            characterService.addCharacter(addCharacterRequestBody.characterType,
                    addCharacterRequestBody.weaponType);
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("use-item")
    @Operation(summary = "Use usable item",
            description = "Use usable item based on item's name", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<?> useUsableItem(@RequestParam String itemName) {

        try {
            characterService.useUsableItem(itemName);
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body("Item used");
    }

    @GetMapping("level-up-item")
    @Operation(summary = "Level up item", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<?> levelUpItem(@RequestParam ItemToUpgrade itemToUpgrade) {

        try {
            characterService.levelUpItem(itemToUpgrade);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body("Item leveled-up");
    }

}
