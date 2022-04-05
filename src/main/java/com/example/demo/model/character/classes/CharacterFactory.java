package com.example.demo.model.character.classes;

import static java.util.Objects.requireNonNull;

import com.example.demo.model.character.model.Character;
import java.util.function.Supplier;

public enum CharacterFactory implements Supplier<Character> {
  MAGE(Mage::new),
  JEDI_KNIGHT(JediKnight::new),
  PRICE_HUNTER(PriceHunter::new),
  SITH_LORD(SithLord::new);

  private final Supplier<Character> factory;

  CharacterFactory(Supplier<Character> factory) {
    this.factory = requireNonNull(factory);
  }

  @Override
  public Character get() {
    return factory.get();
  }

}
