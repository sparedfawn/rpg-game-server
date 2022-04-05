package com.example.demo.model.equipment_item;

import java.util.List;
import java.util.UUID;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EquipmentItemService {

    private final EquipmentItemRepository equipmentItemRepository;


    public List<EquipmentItem> getEquipmentItems() {

        log.info("method: getEquipmentItems(): retrieve equipment items");
        return equipmentItemRepository.findAll();
    }

    public EquipmentItem getEquipmentItem(UUID id) {

        log.info("method: getEquipmentItem(): retrieve equipment item");
        return equipmentItemRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public EquipmentItem getEquipmentItem(String name) {
        log.info("getEquipmentItem(): getting item from inventory by name");
        return equipmentItemRepository.getItemByName(name);
    }
}
