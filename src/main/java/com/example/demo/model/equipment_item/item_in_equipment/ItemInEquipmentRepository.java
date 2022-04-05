package com.example.demo.model.equipment_item.item_in_equipment;

import com.example.demo.model.character.model.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ItemInEquipmentRepository extends JpaRepository<ItemInEquipment, UUID> {

  @Query(value = "Select count(i.id) From ItemInEquipment i Where i.equipment.id=?1")
  int countItemsInEquipment(UUID id);

  @Query(value = "Select i From ItemInEquipment i Where i.equipment.id=?1 and i.equipmentItem.id=?2")
  ItemInEquipment getItemInEquipment(UUID equipmentId, UUID itemId);

  @Query(value = "Select c From ItemInEquipment i, Character c Where i.equipment.id=c.equipment.id and i.id=?1")
  Character getOwnerOfItem(UUID itemId);

  @Query(value = "Select i From ItemInEquipment i Where i.equipment.id=?1 and i.equipmentItem.name=?2")
  ItemInEquipment getItemByName(UUID equipmentId, String name);
}
