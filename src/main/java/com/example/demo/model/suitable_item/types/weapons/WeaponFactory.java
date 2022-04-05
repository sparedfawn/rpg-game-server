package com.example.demo.model.suitable_item.types.weapons;

import static java.util.Objects.requireNonNull;

import com.example.demo.model.suitable_item.types.weapons.jedilightsabertypes.BlueLightsaber;
import com.example.demo.model.suitable_item.types.weapons.jedilightsabertypes.GreenLightsaber;
import com.example.demo.model.suitable_item.types.weapons.jedilightsabertypes.PurpleLightsaber;
import com.example.demo.model.suitable_item.types.weapons.pistoltypes.DualPistol;
import com.example.demo.model.suitable_item.types.weapons.pistoltypes.HeavyPistol;
import com.example.demo.model.suitable_item.types.weapons.pistoltypes.LightPistol;
import com.example.demo.model.suitable_item.types.weapons.sithlightsabertypes.CrossLightsaber;
import com.example.demo.model.suitable_item.types.weapons.sithlightsabertypes.CurvedLightsaber;
import com.example.demo.model.suitable_item.types.weapons.sithlightsabertypes.DoubleLightsaber;
import com.example.demo.model.suitable_item.types.weapons.stafftypes.ArchangelStaff;
import com.example.demo.model.suitable_item.types.weapons.stafftypes.LudenStaff;
import com.example.demo.model.suitable_item.types.weapons.stafftypes.VoidStaff;
import java.util.function.Supplier;

public enum WeaponFactory implements Supplier<Weapon> {
  GREEN_LIGHTSABER(GreenLightsaber::new),
  BLUE_LIGHTSABER(BlueLightsaber::new),
  PURPLE_LIGHTSABER(PurpleLightsaber::new),
  DUAL_PISTOL(DualPistol::new),
  HEAVY_PISTOL(HeavyPistol::new),
  LIGHT_PISTOL(LightPistol::new),
  CROSS_LIGHTSABER(CrossLightsaber::new),
  CURVED_LIGHTSABER(CurvedLightsaber::new),
  DOUBLE_LIGHTSABER(DoubleLightsaber::new),
  ARCHANGEL_STAFF(ArchangelStaff::new),
  LUDEN_STAFF(LudenStaff::new),
  VOID_STAFF(VoidStaff::new);

  private final Supplier<Weapon> factory;

  WeaponFactory(Supplier<Weapon> factory) {
    this.factory = requireNonNull(factory);
  }

  @Override
  public Weapon get() {
    return factory.get();
  }
}
