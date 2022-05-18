package no.ntnu.kacperln.wargames.data;

/**
 * Class representing a RangedUnit.
 *
 * @author Kacper L. Nowicki
 * @version 20.02.2022
 */
public class RangedUnit extends Unit {

  private final int attackBonus = 3;
  private final int[] resistBonus = {6, 4, 2}; // Starting resistance bonus, third value is the default
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
   *
   * @return attack bonus
   */
  @Override
  public int getAttackBonus() {
    return this.attackBonus;
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

    return this.resistBonus[index];
  }
}
