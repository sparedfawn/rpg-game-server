package com.example.demo.model.quest.controller;

import com.example.demo.model.quest.QuestService;
import com.example.demo.model.quest.model.PossibleQuest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("internal-possible-quests")
@SecurityRequirement(name = "bearerAuth")
@ConditionalOnExpression("${internal.controllers.enabled}")
public class InternalPossibleQuestController {

    private final QuestService questService;

    @GetMapping
    public List<PossibleQuest> getPossibleQuests() {

        return questService.getPossibleQuests();
    }
}
