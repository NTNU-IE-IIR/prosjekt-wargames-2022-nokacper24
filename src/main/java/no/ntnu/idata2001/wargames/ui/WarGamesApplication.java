package no.ntnu.idata2001.wargames.ui;

import java.io.File;
import java.io.IOException;
import no.ntnu.idata2001.wargames.data.Army;
import no.ntnu.idata2001.wargames.logic.ArmyFileHandler;
import no.ntnu.idata2001.wargames.logic.Battle;
import no.ntnu.idata2001.wargames.logic.IllegalUnitsFileException;

/**
 * Class representing a WarGames application.
 * Its purpose is to connect the UI to the logic.
 * It provides all necessary methods for the UI to interact with the logic.
 */
public class WarGamesApplication {

  private Battle battle;
  private Army armyOne;
  private Army armyTwo;
  private Army armyOneInitial; // armyOne state before battle
  private Army armyTwoInitial; // armyTwo state before battle

  private ArmyFileHandler armyFileHandler;

  /**
   * Constructor for war games application.
   */
  public WarGamesApplication() {
    this.armyFileHandler = new ArmyFileHandler();
  }

  /**
   * Sets armyOne.
   */
  public void setArmyOne(Army army1) {
    this.armyOne = army1;
  }

  /**
   * Sets armyTwo.
   */
  public void setArmyTwo(Army army2) {
    this.armyTwo = army2;
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
   * Returns Battle object.
   *
   * @return battle
   */
  public Battle getBattle() {
    return battle;
  }

  /**
   * Saves armies initial state.
   */
  private void saveInitialArmies() {
    this.armyOneInitial = armyOne.copy();
    this.armyTwoInitial = armyTwo.copy();
  }

  /**
   * Simulates a battle between armies.
   *
   * @throws IllegalStateException if armies are not set
   * @throws IllegalStateException if there are no units in one of the armies
   */
  public void simulateBattle() {
    if (armyOne == null || armyTwo == null) {
      throw new IllegalStateException(
          "Both armies must be set up before battle can be simulated.");
    } else if (!armyOne.hasUnits() || !armyTwo.hasUnits()) {
      throw new IllegalStateException(
          "Both armies must have some units before battle can be simulated.");
    } else {
      this.saveInitialArmies();
      this.battle = new Battle(this.armyOne, this.armyTwo);
      this.battle.simulate();
    }
  }

  /**
   * Resets armies to initial state.
   */
  public void resetArmies() {
    if (armyOneInitial != null && armyTwoInitial != null) {
      this.armyOne = armyOneInitial.copy();
      this.armyTwo = armyTwoInitial.copy();
    }
  }

  /**
   * Loads army from a file and sets it as armyOne.
   *
   * @param loadedFile file to load from
   * @throws IllegalUnitsFileException if file is not valid
   * @throws IOException if IO error occurs
   */
  public void loadArmy1FromFile(File loadedFile) throws IllegalUnitsFileException, IOException {
    this.armyOne = this.armyFileHandler.loadArmyFromFile(loadedFile);
  }

  /**
   * Loads army from a file and sets it as armyTwo.
   *
   * @param loadedFile file to load from
   * @throws IllegalUnitsFileException if file is not valid
   * @throws IOException if IO error occurs
   */
  public void loadArmy2FromFile(File loadedFile) throws IllegalUnitsFileException, IOException {
    this.armyTwo = this.armyFileHandler.loadArmyFromFile(loadedFile);
  }

}
