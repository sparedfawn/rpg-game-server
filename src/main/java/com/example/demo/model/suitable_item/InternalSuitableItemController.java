package com.example.demo.model.suitable_item;

import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("items")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@ConditionalOnExpression("${internal.controllers.enabled}")
public class InternalSuitableItemController {

  private final SuitableItemService suitableItemService;

  @GetMapping
  @Operation(summary = "Return list of current logged character's suitable items", security = @SecurityRequirement(name = "bearerAuth"))
  public List<SuitableItem> getAllItems() {
    return suitableItemService.getAllItems();
  }

  @GetMapping("{id}")
  @Operation(summary = "Return character's suitable item", security = @SecurityRequirement(name = "bearerAuth"))
  public SuitableItem getItem(@PathVariable UUID id) {
    return suitableItemService.getItem(id);
  }

  @PutMapping("{id}")
  @Operation(summary = "Edit character's suitable item", security = @SecurityRequirement(name = "bearerAuth"))
  public void editItem(@PathVariable UUID id, @RequestParam int level) {

    suitableItemService.editItem(id, level);
  }
}