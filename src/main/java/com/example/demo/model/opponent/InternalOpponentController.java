package com.example.demo.model.opponent;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("opponent")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@ConditionalOnExpression("${internal.controllers.enabled}")
public class InternalOpponentController {
    private final OpponentService opponentService;

    @GetMapping
    @Operation(summary = "Return list of opponents from DB", description = "Show all opponents in db", security = @SecurityRequirement(name = "bearerAuth"))
    public List<Opponent> getAllOpponents() {

        return opponentService.getAllOpponents();
    }

    @GetMapping("{id}")
    @Operation(summary = "Return single opponent from DB",
            description = "Show single opponent in db", security = @SecurityRequirement(name = "bearerAuth"))
    public Opponent getOpponent(@Parameter(name = "ID value for the opponent you need to retrieve", required = true)
                                @PathVariable UUID id) {

        return opponentService.getOpponent(id);
    }
}
