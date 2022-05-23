package no.ntnu.kacperln.wargames.data;

import java.util.ArrayList;
import java.util.List;
import no.ntnu.kacperln.wargames.data.CavalryUnit;
import no.ntnu.kacperln.wargames.data.CommanderUnit;
import no.ntnu.kacperln.wargames.data.InfantryUnit;
import no.ntnu.kacperln.wargames.data.RangedUnit;
import no.ntnu.kacperln.wargames.data.Unit;

/**
 * Class representing a Unit Factory.
 * It can create units of given type, or it can create a list of n units of given type.
 *
 * @author Kacper L. Nowicki
 * @version 07-05-2022
 */
public class UnitFactory {

  /**
   * Creates and returns a single unit of given type.
   * Takes unitType as string, NOT case-sensitive.
   * Available unit types are "INFANTRY", "RANGED", "CAVALRY", "COMMANDER"
   *
   * @param unitType unit type, not case-sensitive
   * @param unitName name of the unit
   * @param unitHealth health of the unit
   * @return Unit created
   * @throws IllegalArgumentException when illegal unitType provided,
   *                                  or when illegal arguments for Unit constructors provided
   */
  public Unit createUnit(String unitType, String unitName, int unitHealth) {
    return switch (unitType.toUpperCase()) {
      case "INFANTRY" -> new InfantryUnit(unitName, unitHealth);
      case "RANGED" -> new RangedUnit(unitName, unitHealth);
      case "CAVALRY" -> new CavalryUnit(unitName, unitHealth);
      case "COMMANDER" -> new CommanderUnit(unitName, unitHealth);
      default -> throw new IllegalArgumentException("Illegal unit type provided");
    };
  }

  /**
   * Creates and returns a single unit of given type.
   * Takes unitType as UnitType enum.
   *
   * @param unitType UnitType enum
   * @param unitName name of the unit
   * @param unitHealth health of the unit
   * @return Unit created
   * @throws IllegalArgumentException when illegal arguments for Unit constructors provided
   */
  public Unit createUnit(Unit.UnitType unitType, String unitName, int unitHealth) {
    return switch (unitType) {
      case INFANTRY -> new InfantryUnit(unitName, unitHealth);
      case RANGED -> new RangedUnit(unitName, unitHealth);
      case CAVALRY -> new CavalryUnit(unitName, unitHealth);
      case COMMANDER -> new CommanderUnit(unitName, unitHealth);
    };
  }

  /**
   * Creates and returns a List of n units of given type.
   * All units created have the same name and health.
   * Takes unitType as string, NOT case-sensitive.
   * Available unit types are "INFANTRY", "RANGED", "CAVALRY", "COMMANDER"
   *
   * @param n amount of units to create
   * @param unitType unit type, not case-sensitive
   * @param unitName name of the units
   * @param unitHealth health of the units
   * @return List of units created
   * @throws IllegalArgumentException when illegal unitType provided,
   *                                  or when illegal arguments for Unit constructors provided
   */
  public List<Unit> createUnits(int n, String unitType, String unitName, int unitHealth) {
    List<Unit> units = new ArrayList<>();

    switch (unitType.toUpperCase()) {
      case "INFANTRY":
        for (int i = 0; i < n; i++) {
          units.add(new InfantryUnit(unitName, unitHealth));
        }
        return units;
      case "RANGED":
        for (int i = 0; i < n; i++) {
          units.add(new RangedUnit(unitName, unitHealth));
        }
        return units;
      case "CAVALRY":
        for (int i = 0; i < n; i++) {
          units.add(new CavalryUnit(unitName, unitHealth));
        }
        return units;
      case "COMMANDER":
        for (int i = 0; i < n; i++) {
          units.add(new CommanderUnit(unitName, unitHealth));
        }
        return units;
      default:
        throw new IllegalArgumentException("Illegal unit type provided");
    }
  }

  /**
   * Creates and returns a List of n units of given type.
   * All units created have the same name and health.
   *
   * @param n amount of units to create
   * @param unitType UnitType enum
   * @param unitName name of the units
   * @param unitHealth health of the units
   * @return List of units created
   * @throws IllegalArgumentException when illegal arguments for Unit constructors provided
   */
  public List<Unit> createUnits(int n, Unit.UnitType unitType, String unitName, int unitHealth) {
    List<Unit> units = new ArrayList<>();

    switch (unitType) {
      case INFANTRY:
        for (int i = 0; i < n; i++) {
          units.add(new InfantryUnit(unitName, unitHealth));
        }
        return units;
      case RANGED:
        for (int i = 0; i < n; i++) {
          units.add(new RangedUnit(unitName, unitHealth));
        }
        return units;
      case CAVALRY:
        for (int i = 0; i < n; i++) {
          units.add(new CavalryUnit(unitName, unitHealth));
        }
        return units;
      case COMMANDER:
        for (int i = 0; i < n; i++) {
          units.add(new CommanderUnit(unitName, unitHealth));
        }
        return units;
      default:
        return units;
    }
  }

}
