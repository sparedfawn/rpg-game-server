package com.example.demo.model.equipment_item.equipment;

import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;

    public List<Equipment> getEquipments() {

        log.info("method: getEquipments(): retrieve eqs");
        return equipmentRepository.findAll();
    }

    public Equipment getEquipment(UUID id) {
        log.info("method: getEquipments(): retrieve eq");
        return equipmentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

}
