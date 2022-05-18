package no.ntnu.kacperln.wargames.logic;

import no.ntnu.kacperln.wargames.data.Army;
import no.ntnu.kacperln.wargames.data.Unit;

/**
 * Class representing a Battle.
 * Battle is set on a TerrainType, all units are standing on the same TerrainType.
 * It simulates a battle between two armies.
 *
 * @author Kacper L. Nowicki
 * @version 18.05.2022
 */
public class Battle {

  private Army armyOne;
  private Army armyTwo;
  private TerrainType battleTerrain;

  /**
   * Constructor of the Battle class.
   *
   * @param armyOne First army to take part in a battle
   * @param armyTwo Second army to take part in a battle
   */
  public Battle(Army armyOne, Army armyTwo, TerrainType battleTerrain) {
    this.armyOne = armyOne;
    this.armyTwo = armyTwo;
    this.battleTerrain = battleTerrain;
    this.setTerrainTypeForALl();
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
   * Sets the current terrain type for all units in both armies.
   */
  private void setTerrainTypeForALl() {
    for (Unit unit : armyOne.getAllUnits()) {
      unit.setCurrentTerrain(battleTerrain);
    }
    for (Unit unit : armyTwo.getAllUnits()) {
      unit.setCurrentTerrain(battleTerrain);
    }
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
   * Simulates a single step of the battle if battle is in progress.
   */
  public void simulateStep() {
    if (this.isInProgress()) {
      this.armiesAttackEachOther(this.armyOne, this.armyTwo);
    }
  }

  /**
   * Returns the winner of the battle.
   * Returns null if it's a draw.
   * Throws BattleStillInProgressException if both armies have units,
   * i.e. battle is still in progress.
   *
   * @return winning army, null in case of a draw
   * @throws BattleStillInProgressException if battle is still in progress
   */
  public Army getWinner() {
    if (this.armyOne.hasUnits() && this.armyTwo.hasUnits()) {
      // both armies have units
      throw new BattleStillInProgressException();
    } else if (this.armyOne.hasUnits() || this.armyTwo.hasUnits()) {
      // one of the armies has no units, returns the one with units
      return this.armyOne.hasUnits() ? this.armyOne : this.armyTwo;
    } else {
      // draw
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
  private void armiesAttackEachOther(Army armyA, Army armyB) {
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
