package com.example.demo.model.equipment_item.item_in_equipment;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("items_in_equipment")
@SecurityRequirement(name = "bearerAuth")
@ConditionalOnExpression("${internal.controllers.enabled}")
public class InternalItemInEquipmentController {

  private final ItemInEquipmentService itemInEquipmentService;

  @GetMapping
  @Operation(summary = "Return list of player's items from DB", security = @SecurityRequirement(name = "bearerAuth"))
  public List<ItemInEquipment> getItemsInEquipments() {

    return itemInEquipmentService.getItemsInEquipments();
  }

  @GetMapping("{id}")
  @Operation(summary = "Return player's equipment item from DB", security = @SecurityRequirement(name = "bearerAuth"))
  public ItemInEquipment getItemInEquipment(@PathVariable UUID id) {

    return itemInEquipmentService.getItemInEquipment(id);
  }

  @PostMapping
  @Operation(summary = "Add equipment item to player", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<?> addItemToEquipment(@RequestParam UUID equipmentId,
      @RequestParam UUID itemId,
      @RequestParam int amount) {

    try {
      itemInEquipmentService.addItemToEquipment(equipmentId, itemId, amount);
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PutMapping("{id}")
  @Operation(summary = "Edit item amount in equipment", security = @SecurityRequirement(name = "bearerAuth"))
  public void editItemAmountInEquipment(@PathVariable UUID id, @RequestParam int amount) {

    itemInEquipmentService.editItemAmountInEquipment(id, amount);
  }

  @DeleteMapping("{id}")
  @Operation(summary = "Remove item from equipment", security = @SecurityRequirement(name = "bearerAuth"))
  public void deleteItemFromEquipment(@PathVariable UUID id) {

    itemInEquipmentService.deleteItemFromEquipment(id);
  }

  @PostMapping("use")
  @Operation(summary = "Use equipment's item", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<?> useUsableItem(@RequestBody UUID itemId) {

    try {
      itemInEquipmentService.useUsableItem(itemId);
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
