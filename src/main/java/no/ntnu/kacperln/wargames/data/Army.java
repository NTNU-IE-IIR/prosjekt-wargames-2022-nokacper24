package no.ntnu.kacperln.wargames.data;

import static no.ntnu.kacperln.wargames.data.Unit.UnitType.INFANTRY;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Class representing an Army.
 * It has a name and a collection of units.
 *
 * @author Kacper L. Nowicki
 * @version 18.05.2022
 */
public class Army {

  private String name;
  private List<Unit> units;
  private final Random random;

  /**
   * Constructor of the Army with no units.
   *
   * @param name name of the army
   * @throws IllegalArgumentException when name null or blank
   */
  public Army(String name) {
    this.setName(name);
    this.units = new ArrayList<>();
    this.random = new Random();
  }

  /**
   * Constructor of the Army with given collection of units.
   *
   * @param name  name of the army
   * @param units collection of units in the army
   * @throws IllegalArgumentException when name null or blank
   */
  public Army(String name, List<Unit> units) {
    this.setName(name);
    this.units = new ArrayList<>(units);
    this.random = new Random();
  }

  /**
   * Constructor used to make a copy of the army object.
   *
   * @param army to be copied
   */
  private Army(Army army) {
    this.name = army.name;
    this.units = new ArrayList<>();
    // make a copy of each unit in army
    army.units.forEach(unit -> this.units.add(unit.copy()));
    this.random = new Random();
  }

  /**
   * Returns a copy of the army object.
   *
   * @return copy of the army object
   */
  public Army copy() {
    return new Army(this);
  }

  /**
   * Sets the name of this army.
   *
   * @param name name of the army
   * @throws IllegalArgumentException when name null or blank
   */
  public void setName(String name) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Army name cannot be empty.");
    } else {
      this.name = name;
    }
  }

  /**
   * Returns the name of the army.
   *
   * @return name of the army
   */
  public String getName() {
    return this.name;
  }

  /**
   * Adds a specified unit to the army.
   *
   * @param unit unit to be added
   */
  public void add(Unit unit) {
    this.units.add(unit);
  }

  /**
   * Adds all given units to the army.
   *
   * @param units List&lt;Unit&gt; of units to be added
   */
  public void addAll(List<Unit> units) {
    this.units.addAll(units);
  }

  /**
   * Removes specified unit from the army.
   * Returns true if the unit was found.
   *
   * @param unit unit to be removed
   * @return true if unit found, false if not found
   */
  public boolean remove(Unit unit) {
    return units.remove(unit);
  }

  /**
   * Checks if there are units in the army.
   *
   * @return true if units found, false if army is empty
   */
  public boolean hasUnits() {
    return !this.units.isEmpty();
  }

  /**
   * Returns collection of all units in the army.
   *
   * @return all units in army
   */
  public List<Unit> getAllUnits() {
    return this.units;
  }

  /**
   * Returns a collection of all Units of the given type.
   *
   * @param unitType UnitType enum
   * @return all units of given type
   */
  private List<Unit> getUnitsByType(Unit.UnitType unitType) {
    return this.units.stream()
        .filter(unit -> unit.getUnitType() == unitType)
        .toList();
  }

  /**
   * Returns collection of all Infantry units in the army.
   *
   * @return all infantry units in army
   */
  public List<Unit> getInfantryUnits() {
    return this.getUnitsByType(INFANTRY);
  }

  /**
   * Returns collection of all Cavalry units in the army.
   * Does not include Commander units that are subclass of CavalryUnit.
   *
   * @return all cavalry units in army
   */
  public List<Unit> getCavalryUnits() {
    return this.getUnitsByType(Unit.UnitType.CAVALRY);
  }

  /**
   * Returns collection of all ranged units in the army.
   *
   * @return all ranged units in army
   */
  public List<Unit> getRangedUnits() {
    return this.getUnitsByType(Unit.UnitType.RANGED);
  }

  /**
   * Returns collection of all commander units in the army.
   *
   * @return all commander units in army
   */
  public List<Unit> getCommanderUnits() {
    return this.getUnitsByType(Unit.UnitType.COMMANDER);
  }

  /**
   * Returns a random unit from the army collection.
   *
   * @return random unit, null if collection is empty
   */
  public Unit getRandom() {
    if (!units.isEmpty()) {
      int index = random.nextInt(units.size());
      return units.get(index);
    } else {
      return null;
    }
  }

  /**
   * Returns the size of the army.
   * Number of all units in the army.
   *
   * @return size of the army
   */
  public int getUnitCount() {
    return this.units.size();
  }

  /**
   * Returns the number of units of given type in the army.
   *
   * @param unitType UnitType to count
   * @return number of units of given type in army
   */
  public int getUnitCount(Unit.UnitType unitType) {
    return switch (unitType) {
      case INFANTRY -> this.getInfantryUnits().size();
      case RANGED -> this.getRangedUnits().size();
      case CAVALRY -> this.getCavalryUnits().size();
      case COMMANDER -> this.getCommanderUnits().size();
    };
  }

  /**
   * Returns the name and size of the army as a String.
   *
   * @return Text with name and size of army
   */
  @Override
  public String toString() {
    return this.name + "\nSize: " + this.units.size();
  }

  /**
   * Test if two armies are equal.
   * By equal, we mean that they have the same name
   * and the same units in their lists, order does not matter.
   *
   * @param o object to be compared to this
   * @return true if armies are equal
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Army army = (Army) o;
    return name.equals(army.name) && units.size() == army.units.size()
        && units.containsAll(army.units) && army.units.containsAll(units);
  }

  /**
   * Returns a hashCode of the object.
   *
   * @return a hashcode for Army object
   */
  @Override
  public int hashCode() {
    return Objects.hash(name, units);
  }
}
