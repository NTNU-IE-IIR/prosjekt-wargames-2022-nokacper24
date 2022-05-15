package no.ntnu.idata2001.wargames.data;

/**
 * Class representing an InfantryUnit.
 *
 * @author Kacper L. Nowicki
 * @version 20.02.2022
 */
public class InfantryUnit extends Unit {

  private final int attackBonus = 2;
  private final int resistBonus = 1;

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
   * Returns type of the unit.
   *
   * @return UnitType enum
   */
  @Override
  public UnitType getUnitType() {
    return UnitType.INFANTRY;
  }

  /**
   * Return attack bonus of the unit.
   *
   * @return attack bonus
   */
  @Override
  public int getAttackBonus() {
    return this.attackBonus;
  }

  /**
   * Returns resist bonus of the unit.
   *
   * @return resist bonus
   */
  @Override
  public int getResistBonus() {
    return this.resistBonus;
  }
}
