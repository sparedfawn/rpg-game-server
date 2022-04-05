package com.example.demo.model.quest.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;

import com.example.demo.model.quest.QuestService;
import com.example.demo.model.quest.model.PossibleQuest;
import com.example.demo.model.quest.model.Quest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("internal-quests")
@SecurityRequirement(name = "bearerAuth")
@ConditionalOnExpression("${internal.controllers.enabled}")
public class InternalQuestController {

  private final QuestService questService;

  @GetMapping
  public List<Quest> getQuests() {

    return questService.getQuests();
  }
}
