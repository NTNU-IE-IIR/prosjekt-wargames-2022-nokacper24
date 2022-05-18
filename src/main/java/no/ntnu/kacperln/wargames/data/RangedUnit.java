package no.ntnu.kacperln.wargames.data;

import java.util.Objects;
import no.ntnu.kacperln.wargames.logic.TerrainType;

/**
 * Class representing a RangedUnit.
 *
 * @author Kacper L. Nowicki
 * @version 20.02.2022
 */
public class RangedUnit extends Unit {

  // fields are static because they are shared by all instances of RangedUnit
  private static final int ATTACK_BONUS = 3;
  private static final int[] RESIST_BONUS = {6, 4, 2}; // Starting resistance bonus, third value is default

  private int timesHit = 0; // How many times unit got hit

  /**
   * Constructor of the RangedUnit.
   *
   * @param name   Name of the unit
   * @param health Initial health of the unit
   * @param attack Base attack damage
   * @param armor  Base defence value
   * @throws IllegalArgumentException when name empty,
   *                                  health equal or less than 0,
   *                                  attack or armor equal or less than 0
   */
  public RangedUnit(String name, int health, int attack, int armor) {
    super(name, health, attack, armor);
    this.unitType = UnitType.RANGED;
  }

  /**
   * Constructor of the RangedUnit.
   * Sets unit's attack to 15, and armor to 8.
   *
   * @param name Name of the unit
   * @param health Initial health of the unit
   * @throws IllegalArgumentException when name empty,
   *                                  health equal or less than 0
   */
  public RangedUnit(String name, int health) {
    super(name, health, 15, 8);
    this.unitType = UnitType.RANGED;
  }

  /**
   * Constructor for copy of RangedUnit.
   *
   * @param unit to be copied
   */
  protected RangedUnit(RangedUnit unit) {
    super(unit);
  }

  /**
   * Returns a copy of the unit object.
   *
   * @return copy of th eunit object
   */
  @Override
  public RangedUnit copy() {
    return new RangedUnit(this);
  }

  /**
   * Return attack bonus of the unit.
   * Bonus is increased when the terrain is HILL.
   * Bonus is reduced when the unit is FOREST.
   *
   * @return attack bonus
   */
  @Override
  public int getAttackBonus() {
    int bonus = RangedUnit.ATTACK_BONUS;

    if (this.currentTerrain == TerrainType.HILL) {
      bonus += 2;
    } else if (this.currentTerrain == TerrainType.FOREST) {
      bonus -= 2;
    }
    return bonus;
  }

  /**
   * Returns resist bonus of the unit.
   * Adds 1 to timesHit field.
   * The resist bonus is looked up from resistBonus array, value taken depends on timesHit.
   * Third and next hits take the default resist value.
   *
   * @return resist bonus
   */
  @Override
  public int getResistBonus() {
    int index = this.timesHit; //index of ResistBonus to be taken from array
    this.timesHit++;

    index = this.timesHit >= 3 ? 2 : index; //if third hit, default resist value (index = 2)

    return RangedUnit.RESIST_BONUS[index];
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
    RangedUnit that = (RangedUnit) o;
    return timesHit == that.timesHit;
  }

  /**
   * Returns a hashCode of the object.
   *
   * @return a hashcode for Unit object
   */
  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), timesHit);
  }
}
