package com.example.demo.model.equipment_item.item_in_equipment;

import java.util.UUID;

public enum EquipmentItem {
  COTTON("Cotton", "d2e079d2-f314-45ea-9508-6efecfc0d7c6"),
  NEEDLE_AND_THREAD("Needle and Thread", "0d6f3218-603d-4f2c-80ac-6ee6fd7d0f21"),
  SILK("Silk", "4d6223b3-97fb-4fe8-a9a8-f4881070743e"),
  LEATHER("Leather", "0b83427e-f9e3-4b26-b611-980b191a6658"),
  RUBBER("Rubber", "fe619c56-f73f-4b10-954c-072ba2be6863"),
  METAL("Metal", "72a4f8d3-b2cb-41eb-9488-3ebe62a0fa9b"),
  WOOD("Wood", "4f95fccf-5f25-4aec-8684-6bbdf0c76168"),
  HAMMER("Hammer", "9e035809-318d-45c3-9055-10d3aca90b9c"),
  QUARTZ("Quartz", "6fcb4c56-1e7f-49be-8e04-419ca62a28e2"),
  EMERALD("Emerald", "f1d73303-d36a-49a1-a2ce-17e0e65ed513"),
  DIAMOND("Diamond", "17840130-2080-4563-86f1-bde057ab6c01"),
  RUBY("Ruby", "f5f45c38-7f5e-4e11-aacc-4e55eb641150"),
  STRENGTH_POTION("Strength Potion", "f2af46df-977a-4af8-867d-7996f9d2de66"),
  DEXTERITY_POTION("Dexterity Potion", "f799e79c-aa9d-4690-a80a-babd3af02d11"),
  ARMOR_POTION("Armor Potion", "e4ce337e-097f-4ea8-a00b-61e3531c31aa");


  private final String name;
  private final UUID id;

  EquipmentItem(String name, String id) {
    this.name = name;
    this.id = UUID.fromString(id);
  }

  public String getName() {
    return this.name;
  }

  public UUID getId() {
    return this.id;
  }
}
