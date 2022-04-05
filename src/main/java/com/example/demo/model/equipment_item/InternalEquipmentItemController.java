package com.example.demo.model.equipment_item;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("equipment-items")
@SecurityRequirement(name = "bearerAuth")
@ConditionalOnExpression("${internal.controllers.enabled}")
public class InternalEquipmentItemController {

    private final EquipmentItemService equipmentItemService;

    @GetMapping
    @Operation(summary = "Return list of all possible equipment item from db", security = @SecurityRequirement(name = "bearerAuth"))
    public List<EquipmentItem> getEquipmentItems() {

        return equipmentItemService.getEquipmentItems();
    }

    @GetMapping("{id}")
    @Operation(summary = "Return one equipment item from db based on id", security = @SecurityRequirement(name = "bearerAuth"))
    public EquipmentItem getEquipmentItem(@PathVariable UUID id) {

        return equipmentItemService.getEquipmentItem(id);
    }
}
