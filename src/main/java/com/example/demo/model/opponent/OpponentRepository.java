package com.example.demo.model.opponent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OpponentRepository extends JpaRepository<Opponent, UUID> {
}
