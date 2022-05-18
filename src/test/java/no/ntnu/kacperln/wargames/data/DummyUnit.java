package no.ntnu.kacperln.wargames.data;

/**
 * Class representing an DummyUnit.
 * The purpose of this class is only to test functionality of unit class.
 * Since the Unit class is abstract, it is not possible to instantiate
 * an object of this class without inheriting from it.
 *
 * @author Kacper L. Nowicki
 * @version 06.05.2022
 */
public class DummyUnit extends Unit {

  private final int attackBonus = 2;
  private final int resistBonus = 1;

  /**
   * Constructor of the DummyUnit.
   *
   * @param name   Name of the unit
   * @param health Initial health of the unit
   * @param attack Base attack damage
   * @param armor  Base defence value
   */
  public DummyUnit(String name, int health, int attack, int armor) {
    super(name, health, attack, armor);
    this.unitType = null;
  }

  /**
   * Constructor for copy of DummyUnit.
   *
   * @param unit to be copied
   */
  protected DummyUnit(DummyUnit unit) {
    super(unit);
  }

  /**
   * Returns a copy of the unit object.
   *
   * @return copy of the unit object
   */
  @Override
  public Unit copy() {
    return new DummyUnit(this);
  }

  /**
   * Constructor of the DummyUnit.
   * Sets unit's attack to 15, and armor to 10.
   *
   * @param name Name of the unit
   * @param health Initial health of the unit
   */
  public DummyUnit(String name, int health) {
    super(name, health, 15, 10);
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
