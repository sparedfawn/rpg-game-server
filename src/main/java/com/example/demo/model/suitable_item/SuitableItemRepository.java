package com.example.demo.model.suitable_item;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuitableItemRepository extends JpaRepository<SuitableItem, UUID> {

}
