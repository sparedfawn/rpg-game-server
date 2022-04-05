package com.example.demo.model.character;

import com.example.demo.model.character.model.Character;
import com.example.demo.model.equipment_item.item_in_equipment.ItemInEquipment;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends JpaRepository<Character, UUID> {

  @Query(value = "Select i From ItemInEquipment i, Character c Where i.equipment.id=c.equipment.id and c.id=?1 and i.equipmentItem.name=?2")
  ItemInEquipment getItemToUseUsingName(UUID characterId, String name);
}
