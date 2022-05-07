package no.ntnu.idata2001.wargames.data;

/**
 * Class representing a CommanderUnit.
 *
 * @author Kacper L. Nowicki
 * @version 20.02.2022
 */
public class CommanderUnit extends CavalryUnit {
  /**
   * Constructor of the CommanderUnit.
   *
   * @param name   Name of the unit
   * @param health Initial health of the unit
   * @param attack Base attack damage
   * @param armor  Base defence value
   * @throws IllegalArgumentException when name empty,
   *                                  health equal or less than 0,
   *                                  attack or armor equal or less than 0
   */
  public CommanderUnit(String name, int health, int attack, int armor) {
    super(name, health, attack, armor);
  }

  /**
   * Constructor of the CommanderUnit.
   * Sets unit's attack to 25, and armor to 15.
   *
   * @param name Name of the unit
   * @param health Initial health of the unit
   * @throws IllegalArgumentException when name empty,
   *                                  health equal or less than 0
   */
  public CommanderUnit(String name, int health) {
    super(name, health, 25, 15);
  }

  /**
   * Returns type of the unit.
   *
   * @return UnitType enum
   */
  @Override
  public UnitType getUnitType() {
    return UnitType.COMMANDER;
  }

}
