package no.ntnu.idata2001.wargames.data;

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
   */
  public CavalryUnit(String name, int health, int attack, int armor) {
    super(name, health, attack, armor);
  }

  /**
   * Constructor of the CavalryUnit.
   * Sets unit's attack to 20, and armor to 12.
   *
   * @param name Name of the unit
   * @param health Initial health of the unit
   */
  public CavalryUnit(String name, int health) {
    super(name, health, 20, 12);
  }

  /**
   * Returns type of the unit.
   *
   * @return UnitType enum
   */
  @Override
  public UnitType getUnitType() {
    return UnitType.CAVALRY;
  }

  /**
   * Return attack bonus of the unit.
   * If it's the unit's first attack, returns first entry in attackBonus array.
   *
   * @return attack bonus
   */
  @Override
  public int getAttackBonus() {
    int index;

    if (!this.firstAttack) {
      index = 1;
    } else {
      index = 0;
      this.firstAttack = false;
    }

    return this.attackBonus[index];
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
