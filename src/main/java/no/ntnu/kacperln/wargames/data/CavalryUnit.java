package no.ntnu.kacperln.wargames.data;

import no.ntnu.kacperln.wargames.logic.TerrainType;

/**
 * Class representing a CavalryUnit.
 *
 * @author Kacper L. Nowicki
 * @version 20.02.2022
 */
public class CavalryUnit extends Unit {

  private final int[] attackBonus = {6, 2}; //Initial and default attack bonus
  private boolean firstAttack = true;
  private final int resistBonus = 1;

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
    bonus = this.attackBonus[index];

    if (this.currentTerrain == TerrainType.PLAINS) {
      bonus += 2;
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
    int bonus = this.resistBonus;
    if (this.currentTerrain == TerrainType.FOREST) {
      bonus = 0;
    }
    return bonus;
  }
}
