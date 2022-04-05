package com.example.demo.model.equipment_item.item_in_equipment;

import com.example.demo.model.character.model.Character;
import com.example.demo.model.effect.EffectService;
import com.example.demo.model.effect.EffectSource;
import com.example.demo.model.equipment_item.EquipmentItemService;
import com.example.demo.model.equipment_item.equipment.EquipmentService;
import com.example.demo.model.equipment_item.types.UsableItem;
import java.util.Map;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemInEquipmentService {

  private final ItemInEquipmentRepository itemInEquipmentRepository;

  private final EquipmentService equipmentService;
  private final EquipmentItemService equipmentItemService;
  private final EffectService effectService;

  private static final int SLOTS_IN_EQUIPMENT = 10;


  public List<ItemInEquipment> getItemsInEquipments() {
    log.info("method: getItemsInEquipments(): retrieving items from eq");
    return itemInEquipmentRepository.findAll();
  }

  public ItemInEquipment getItemInEquipment(UUID id) {
    log.info("method: getItemInEquipment(): retrieving one item from eq");
    return itemInEquipmentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  public ItemInEquipment getItemInEquipment(UUID equipmentId, UUID itemId) {

    return itemInEquipmentRepository.getItemInEquipment(equipmentId, itemId);
  }


  public void addItemToEquipment(UUID equipmentId, UUID itemId, int amount) {

    if (itemInEquipmentRepository.countItemsInEquipment(equipmentId) > SLOTS_IN_EQUIPMENT - 1) {
      log.error("method: addItemToEquipment(): Equipment is full");
      throw new IllegalArgumentException("Equipment is full");
    }

    ItemInEquipment itemInEquipment = itemInEquipmentRepository
        .getItemInEquipment(equipmentId, itemId);

    if (itemInEquipment != null) {

      itemInEquipment.setAmount(itemInEquipment.getAmount() + amount);
      itemInEquipmentRepository.save(itemInEquipment);
    } else {
      itemInEquipmentRepository.save(new ItemInEquipment.ItemInEquipmentBuilder()
          .equipment(equipmentService.getEquipment(equipmentId))
          .equipmentItem(equipmentItemService.getEquipmentItem(itemId))
          .amount(amount)
          .build());
    }
    log.info("method: addItemToEquipment(): add item to equipment");
  }

  public void addItemToEquipment(UUID equipmentId, EquipmentItem equipmentItem, int amount) {

    if (itemInEquipmentRepository.countItemsInEquipment(equipmentId) > 9) {

      log.error("method: addItemToEquipment(): Equipment is full");
      throw new IllegalArgumentException("Equipment is full");
    }

    ItemInEquipment itemInEquipment = itemInEquipmentRepository
        .getItemByName(equipmentId, equipmentItem.getName());

    if (itemInEquipment != null) {

      itemInEquipment.setAmount(itemInEquipment.getAmount() + amount);
      itemInEquipmentRepository.save(itemInEquipment);
    }
    else {
      itemInEquipmentRepository.save(new ItemInEquipment.ItemInEquipmentBuilder()
          .equipment(equipmentService.getEquipment(equipmentId))
          .equipmentItem(equipmentItemService.getEquipmentItem(equipmentItem.getName()))
          .equipmentItem(equipmentItemService.getEquipmentItem(equipmentItem.getName()))
          .amount(amount)
          .build());
    }
    log.info("addItemToEquipment(): adding item to character's equipment");
  }

  public void editItemAmountInEquipment(UUID id, int amount) {

    ItemInEquipment itemInEquipment = getItemInEquipment(id);
    itemInEquipment.setAmount(amount);
    itemInEquipmentRepository.save(itemInEquipment);
    log.info("method: editItemInEquipment(): edit item in equipment");
  }

  public void deleteItemFromEquipment(UUID id) {

    itemInEquipmentRepository.delete(getItemInEquipment(id));
    log.info("method: deleteItemFromEquipment(): delete item from equipment");
  }

  public void useUsableItem(UUID itemId) {

    try {

      ItemInEquipment item = getItemInEquipment(itemId);

      UsableItem usableItem = (UsableItem) item.getEquipmentItem();

      effectService.addEffect(itemInEquipmentRepository.getOwnerOfItem(itemId),
          EffectSource.USABLE_ITEM,
          usableItem.incrementsStatistic(), 10);

      if (item.getAmount() > 1) {

        editItemAmountInEquipment(item.getId(), item.getAmount() - 1);
      } else {

        deleteItemFromEquipment(item.getId());
      }
      log.info("method: useUsableItem(): item was used");
    } catch (Exception e) {
      log.error("method: useUsableItem(): Provided item is not usable or does not exist");
      throw new IllegalArgumentException("Provided item is not usable or does not exist");
    }
  }

  public boolean hasItemsInEquipment(Character character, Map<String, Integer> itemMap) {

    return itemMap.entrySet().stream()
        .allMatch(e -> hasAmountFromEquipment(character, e.getKey(), e.getValue()));
  }

  private boolean hasAmountFromEquipment(Character character, String itemName, int amount) {

    ItemInEquipment itemInEquipment = itemInEquipmentRepository
        .getItemByName(character.getEquipment().getId(), itemName);

    if (itemInEquipment != null) {
      log.info("hasAmountFromEquipment(): checking if amount of item is exact for operation");
      return itemInEquipment.getAmount() >= amount;
    }
    log.error("hasAmountFromEquipment(): item doesn't exists in equipment");
    return false;
  }

  public void removeItemsFromEquipment(Character character, Map<String, Integer> map) {

    map.forEach((key, value) -> removeAmountFromEquipment(character, key, value));
    log.info("removeItemsFromEquipment(): removing used item form equipment");
  }

  private void removeAmountFromEquipment(Character character, String itemName, int amount) {

    ItemInEquipment itemInEquipment = itemInEquipmentRepository
        .getItemByName(character.getEquipment().getId(), itemName);

    if (itemInEquipment.getAmount() == amount) {
      log.info("removeAmountFromEquipment(): removing all exact amount of items from inventory");
      deleteItemFromEquipment(itemInEquipment.getId());
    } else if (itemInEquipment.getAmount() > amount) {
      log.info("removeAmountFromEquipment(): removing exact amount of items from inventory");
      editItemAmountInEquipment(itemInEquipment.getId(), itemInEquipment.getAmount() - amount);
    } else {
      log.error("removeAmountFromEquipment(): not enough items in your inventory");
      throw new IllegalArgumentException("Not enough items in your inventory");
    }
  }
}
