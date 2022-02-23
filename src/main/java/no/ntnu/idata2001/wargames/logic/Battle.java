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
   * Returns armyOne
   *
   * @return armyOne
   */
  public Army getArmyOne(){
    return armyOne;
  }

  /**
   * Returns armyTwo
   *
   * @return armyTwo
   */
  public Army getArmyTwo(){
    return armyTwo;
  }

  /**
   * Simulates a battle between the armies.
   *
   * @return winning army
   */
  public Army simulate() {
    Army winningArmy;

    while (this.isInProgress()) {
      this.armyAttackArmyB(this.armyOne, this.armyTwo);
      if (this.isInProgress()) { // make sure armyTwo still has units
        this.armyAttackArmyB(this.armyTwo, this.armyOne);
      }
    }
    winningArmy = armyOne.hasUnits() ? armyOne : armyTwo;
    return winningArmy;
  }


  /**
   * Random unit from armyA attacks a random unit from armyB.
   * If the attacked unit is no longer alive it's removed from its army.
   *
   * @param armyA attacking army
   * @param armyB army to be attacked
   */
  public void armyAttackArmyB(Army armyA, Army armyB) {
    Unit attacker = armyA.getRandom();
    Unit recipient = armyB.getRandom();
    attacker.attack(recipient);
    if (!recipient.isAlive()) {
      armyB.remove(recipient);
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
