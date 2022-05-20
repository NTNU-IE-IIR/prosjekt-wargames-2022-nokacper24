package no.ntnu.kacperln.wargames.data;

import java.util.EnumMap;
import java.util.Map;
import no.ntnu.kacperln.wargames.logic.TerrainType;

/**
 * Class representing an InfantryUnit.
 *
 * @author Kacper L. Nowicki
 * @version 18.05.2022
 */
public class InfantryUnit extends Unit {

  // fields are static, they're shared between all instances of InfantryUnit
  private static final int ATTACK_BONUS = 2;
  private static final int RESIST_BONUS = 1;
  private static final Map<TerrainType, Integer> TERRAIN_ATTACK_BONUS =
      new EnumMap<>(TerrainType.class);
  private static final Map<TerrainType, Integer> TERRAIN_RESIST_BONUS =
      new EnumMap<>(TerrainType.class);

  static {
    TERRAIN_ATTACK_BONUS.put(TerrainType.FOREST, 2);
    TERRAIN_RESIST_BONUS.put(TerrainType.FOREST, 2);
  }

  /**
   * Constructor of the InfantryUnit.
   *
   * @param name   Name of the unit
   * @param health Initial health of the unit
   * @param attack Base attack damage
   * @param armor  Base defence value
   * @throws IllegalArgumentException when name empty,
   *                                  health equal or less than 0,
   *                                  attack or armor equal or less than 0
   */
  public InfantryUnit(String name, int health, int attack, int armor) {
    super(name, health, attack, armor);
    this.unitType = UnitType.INFANTRY;
  }

  /**
   * Constructor of the InfantryUnit.
   * Sets unit's attack to 15, and armor to 10.
   *
   * @param name Name of the unit
   * @param health Initial health of the unit
   * @throws IllegalArgumentException when name empty,
   *                                  health equal or less than 0
   */
  public InfantryUnit(String name, int health) {
    super(name, health, 15, 10);
    this.unitType = UnitType.INFANTRY;
  }

  /**
   * Constructor for copy of InfantryUnit.
   *
   * @param unit to be copied
   */
  protected InfantryUnit(InfantryUnit unit) {
    super(unit);
  }

  /**
   * Returns a copy of the unit object.
   *
   * @return copy of the unit object
   */
  @Override
  public InfantryUnit copy() {
    return new InfantryUnit(this);
  }

  /**
   * Return attack bonus of the unit.
   * Bonus is increased if the terrain is FOREST.
   *
   * @return attack bonus
   */
  @Override
  public int getAttackBonus() {
    int bonus = InfantryUnit.ATTACK_BONUS;

    if (InfantryUnit.TERRAIN_ATTACK_BONUS.containsKey(this.currentTerrain)) {
      bonus += InfantryUnit.TERRAIN_ATTACK_BONUS.get(this.currentTerrain);
    }

    return bonus;
  }

  /**
   * Returns resist bonus of the unit.
   * Bonus is increased if the terrain is FOREST.
   *
   * @return resist bonus
   */
  @Override
  public int getResistBonus() {
    int bonus = InfantryUnit.RESIST_BONUS;

    if (InfantryUnit.TERRAIN_RESIST_BONUS.containsKey(this.currentTerrain)) {
      bonus += InfantryUnit.TERRAIN_RESIST_BONUS.get(this.currentTerrain);
    }

    return bonus;
  }
}
