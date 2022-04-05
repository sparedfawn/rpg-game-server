package com.example.demo.model.quest.repository;

import com.example.demo.model.quest.model.PossibleQuest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PossibleQuestRepository extends JpaRepository<PossibleQuest, UUID> {
}
