package com.example.demo.model.equipment_item.equipment;

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
@RequestMapping("equipment")
@SecurityRequirement(name = "bearerAuth")
@ConditionalOnExpression("${internal.controllers.enabled}")
public class InternalEquipmentController {

    private final EquipmentService equipmentService;

    @GetMapping
    @Operation(summary = "Return list of equipments from DB",
        description = "Show all equipments in db", security = @SecurityRequirement(name = "bearerAuth"))
    public List<Equipment> getEquipments() {

        return equipmentService.getEquipments();
    }

    @GetMapping("{id}")
    @Operation(summary = "Return equipment from DB based on equipment id", security = @SecurityRequirement(name = "bearerAuth"))
    public Equipment getEquipment(@PathVariable UUID id) {

        return equipmentService.getEquipment(id);
    }
}
