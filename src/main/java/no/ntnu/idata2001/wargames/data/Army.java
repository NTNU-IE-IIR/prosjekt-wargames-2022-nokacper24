package no.ntnu.idata2001.wargames.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Class representing an Army, collection of units.
 *
 * @author Kacper L. Nowicki
 * @version 20.02.2022
 */
public class Army {

  private final String name;
  private List<Unit> units;
  private final Random random;

  /**
   * Constructor of the Army with no units.
   *
   * @param name name of the army
   */
  public Army(String name) {
    this.name = name;
    this.units = new ArrayList<>();
    this.random = new Random();
  }

  /**
   * Constructor of the Army with given collection of units.
   *
   * @param name  name of the army
   * @param units collection of units in the army
   */
  public Army(String name, List<Unit> units) {
    this.name = name;
    this.units = units;
    this.random = new Random();
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
   * Adds all specified units to the army.
   *
   * @param units Collection of units to be added
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
   * Returns collection of all Infantry units in the army.
   *
   * @return all infantry units in army
   */
  public List<Unit> getInfantryUnits() {
    return this.units.stream()
        .filter(unit -> unit instanceof InfantryUnit)
        .toList();
  }

  /**
   * Returns collection of all Cavalry units in the army.
   * Does not include Commander units that are subclass of CavalryUnit.
   *
   * @return all cavalry units in army
   */
  public List<Unit> getCavalryUnits() {
    return this.units.stream()
        .filter(unit -> unit instanceof CavalryUnit && !(unit instanceof CommanderUnit))
        .toList();
  }

  /**
   * Returns collection of all ranged units in the army.
   *
   * @return all ranged units in army
   */
  public List<Unit> getRangedUnits() {
    return this.units.stream()
        .filter(unit -> unit instanceof RangedUnit)
        .toList();
  }

  /**
   * Returns collection of all commander units in the army.
   *
   * @return all commander units in army
   */
  public List<Unit> getCommanderUnits() {
    return this.units.stream()
        .filter(unit -> unit instanceof CommanderUnit)
        .toList();
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
   * Returns the name and size of the army as a String.
   *
   * @return Text with name and size of army
   */
  @Override
  public String toString() {
    return this.name + "\nSize: " + this.units.size();
  }

  /**
   * Test if two objects are equal.
   *
   * @param other object to be compared to this
   * @return true if objects are equal
   */
  @Override
  public boolean equals(Object other) {
    if (other instanceof Army) {
      Army otherArmy = (Army) other;
      return this.name.equals(otherArmy.getName())
          && this.units.equals(otherArmy.getAllUnits());
    } else {
      return false;
    }
  }

  /**
   * Returns a hashCode of the object.
   *
   * @return a hashcode for Army
   */
  @Override
  public int hashCode() {
    return Objects.hash(name, units);
  }
}
