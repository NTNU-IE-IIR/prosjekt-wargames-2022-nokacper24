package no.ntnu.idata2001.wargames.logic;

import no.ntnu.idata2001.wargames.data.Army;
import no.ntnu.idata2001.wargames.data.Unit;

/**
 * Class representing a battle, it simulates a battle between two armies.
 *
 * @author Kacper L. Nowicki
 * @version 22.02.2022
 */
public class Battle {

  private final Army armyOne;
  private final Army armyTwo;

  /**
   * Constructor of the Battle class.
   *
   * @param armyOne First army to take part in a battle
   * @param armyTwo Second army to take part in a battle
   */
  public Battle(Army armyOne, Army armyTwo) {
    this.armyOne = armyOne;
    this.armyTwo = armyTwo;
  }

  /**
   * Returns armyOne.
   *
   * @return armyOne
   */
  public Army getArmyOne() {
    return armyOne;
  }

  /**
   * Returns armyTwo.
   *
   * @return armyTwo
   */
  public Army getArmyTwo() {
    return armyTwo;
  }

  /**
   * Simulates a battle between two armies.
   * Returns the winning army, if it was a draw null is returned.
   *
   * @return winning army, null if it's a draw
   */
  public Army simulate() {
    while (this.isInProgress()) {
      this.armiesAttackEachOther(this.armyOne, this.armyTwo);
    }

    if (this.armyOne.hasUnits() || this.armyTwo.hasUnits()) {
      return this.armyOne.hasUnits() ? this.armyOne : this.armyTwo;
    } else {
      return null;
    }
  }


  /**
   * Two random units from both armies attack each other.
   * If any of the units are no longer alive after attacks
   * they're removed from their armies.
   *
   * @param armyA Army 1
   * @param armyB Army 2
   */
  public void armiesAttackEachOther(Army armyA, Army armyB) {
    Unit randomUnitA = armyA.getRandom();
    Unit randomUnitB = armyB.getRandom();
    randomUnitA.attack(randomUnitB);
    randomUnitB.attack(randomUnitA);

    if (!randomUnitA.isAlive()) {
      armyA.remove(randomUnitA);
    }
    if (!randomUnitB.isAlive()) {
      armyB.remove(randomUnitB);
    }
  }

  /**
   * Checks if the battle can continue,
   * by checking if both armies have available units.
   *
   * @return true if battle is still in progress
   */
  public boolean isInProgress() {
    boolean battleInProgress;
    battleInProgress = armyOne.hasUnits() && armyTwo.hasUnits();
    return battleInProgress;
  }


  /**
   * Returns sizes of the armies in the battle as a String.
   *
   * @return Text with sizes of armies
   */
  public String toString() {
    return "Army1: " + this.armyOne.getAllUnits().size() + "\nArmy2 "
        + this.armyTwo.getAllUnits().size();
  }

}
