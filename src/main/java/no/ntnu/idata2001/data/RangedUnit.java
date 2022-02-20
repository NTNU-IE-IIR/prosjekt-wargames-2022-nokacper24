package no.ntnu.idata2001.data;

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
   */
  public RangedUnit(String name, int health, int attack, int armor) {
    super(name, health, attack, armor);
  }

  /**
   * Constructor of the RangedUnit.
   * Sets unit's attack to 15, and armor to 8.
   *
   * @param name Name of the unit
   * @param health Initial health of the unit
   */
  public RangedUnit(String name, int health) {
    super(name, health, 15, 8);
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
