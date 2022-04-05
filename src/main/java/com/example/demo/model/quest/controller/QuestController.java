package com.example.demo.model.quest.controller;

import com.example.demo.model.quest.QuestService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("quests")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class QuestController {

  private final QuestService questService;


  @GetMapping
  public ResponseEntity<?> getQuestsForUser() {

    try {

      return ResponseEntity.status(HttpStatus.OK).body(questService.getQuestsForUser());
    }
    catch (Exception e) {

     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

  }

  @GetMapping("{id}")
  public ResponseEntity<?> pickQuest(@PathVariable int id) {

    try {

      questService.pickQuest(id);
    }
    catch (Exception e) {

      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @GetMapping("complete")
  public ResponseEntity<?> completeQuest() {

    try {

      questService.completeQuest();
    }
    catch (Exception e) {

      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    return ResponseEntity.status(HttpStatus.OK).build();
  }

}
