package com.example.demo.common.fight.controller;

import com.example.demo.common.fight.service.InternalFightService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("fights-internal")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@ConditionalOnExpression("${internal.controllers.enabled}")
public class InternalFightController {

  private final InternalFightService internalFightService;

  @GetMapping("series-of-fights")
  public Map<String, String> getSeriesOfFights() {

    return internalFightService.getFights();
  }

  @GetMapping("series-of-opponent-fights")
  public Map<String, String> getSeriesOfOpponentFights() {

    return internalFightService.getOpponentFights();
  }

}
