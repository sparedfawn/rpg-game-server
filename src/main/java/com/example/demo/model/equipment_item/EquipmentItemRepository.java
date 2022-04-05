package com.example.demo.model.equipment_item;

import com.example.demo.model.equipment_item.EquipmentItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EquipmentItemRepository extends JpaRepository<EquipmentItem, UUID> {

  @Query(value = "Select i From EquipmentItem i Where i.name=?1")
  EquipmentItem getItemByName(String name);
}
