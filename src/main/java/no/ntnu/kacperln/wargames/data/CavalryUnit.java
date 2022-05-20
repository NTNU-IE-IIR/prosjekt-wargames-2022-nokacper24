package no.ntnu.kacperln.wargames.data;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import no.ntnu.kacperln.wargames.logic.TerrainType;

/**
 * Class representing a CavalryUnit.
 *
 * @author Kacper L. Nowicki
 * @version 18.05.2022
 */
public class CavalryUnit extends Unit {

  // fields are static, they're shared between all instances of CavalryUnit
  private static final int[] ATTACK_BONUS = {6, 2}; //Initial and default attack bonus
  private static final int RESIST_BONUS = 1;
  private static final Map<TerrainType, Integer> TERRAIN_ATTACK_BONUS =
      new EnumMap<>(TerrainType.class);
  private static final Map<TerrainType, Integer> TERRAIN_RESIST_BONUS =
      new EnumMap<>(TerrainType.class);

  static {
    TERRAIN_ATTACK_BONUS.put(TerrainType.PLAINS, 2);
    TERRAIN_RESIST_BONUS.put(TerrainType.FOREST, -1); // 1-1 = 0 resist bonus in FOREST
  }

  private boolean firstAttack = true;

  /**
   * Constructor of the CavalryUnit.
   *
   * @param name   Name of the unit
   * @param health Initial health of the unit
   * @param attack Base attack damage
   * @param armor  Base defence value
   * @throws IllegalArgumentException when name empty,
   *                                  health equal or less than 0,
   *                                  attack or armor equal or less than 0
   */
  public CavalryUnit(String name, int health, int attack, int armor) {
    super(name, health, attack, armor);
    this.unitType = UnitType.CAVALRY;
  }

  /**
   * Constructor of the CavalryUnit.
   * Sets unit's attack to 20, and armor to 12.
   *
   * @param name   Name of the unit
   * @param health Initial health of the unit
   * @throws IllegalArgumentException when name empty,
   *                                  health equal or less than 0
   */
  public CavalryUnit(String name, int health) {
    super(name, health, 20, 12);
    this.unitType = UnitType.CAVALRY;
  }

  /**
   * Constructor for copy of CavalryUnit.
   *
   * @param unit to be copied
   */
  protected CavalryUnit(CavalryUnit unit) {
    super(unit);
    this.firstAttack = unit.firstAttack;
  }

  /**
   * Returns a copy of the unit object.
   *
   * @return copy of the unit object
   */
  @Override
  public CavalryUnit copy() {
    return new CavalryUnit(this);
  }

  /**
   * Return attack bonus of the unit.
   * If it's the unit's first attack, returns first entry in attackBonus array.
   * If the terrain is PLAINS, bonus is increased.
   *
   * @return attack bonus
   */
  @Override
  public int getAttackBonus() {
    int bonus;
    int index;

    if (!this.firstAttack) {
      index = 1;
    } else {
      index = 0;
      this.firstAttack = false;
    }
    bonus = CavalryUnit.ATTACK_BONUS[index];

    if (CavalryUnit.TERRAIN_ATTACK_BONUS.containsKey(this.currentTerrain)) {
      bonus += CavalryUnit.TERRAIN_ATTACK_BONUS.get(this.currentTerrain);
    }

    return bonus;
  }

  /**
   * Returns resist bonus of the unit.
   * If the terrain is FOREST, bonus is 0.
   *
   * @return resist bonus
   */
  @Override
  public int getResistBonus() {
    int bonus = CavalryUnit.RESIST_BONUS;
    if (CavalryUnit.TERRAIN_RESIST_BONUS.containsKey(this.currentTerrain)) {
      bonus += CavalryUnit.TERRAIN_RESIST_BONUS.get(this.currentTerrain);
    }
    return bonus;
  }

  /**
   * Test if two Units are equal.
   *
   * @param o object to be compared to this
   * @return true if units are equal
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    CavalryUnit that = (CavalryUnit) o;
    return firstAttack == that.firstAttack;
  }

  /**
   * Returns a hashCode of the object.
   *
   * @return a hashcode for Unit object
   */
  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), firstAttack);
  }

  /**
   * Returns unit's fields in form of a string.
   *
   * @return name, health, attack, armor, firstAttack
   */
  @Override
  public String toString() {
    return super.toString() + "firstAttack=" + this.firstAttack;
  }
}
