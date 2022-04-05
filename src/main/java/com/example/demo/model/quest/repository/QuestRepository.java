package com.example.demo.model.quest.repository;

import java.util.UUID;

import com.example.demo.model.quest.model.Quest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestRepository extends JpaRepository<Quest, UUID> {

}
