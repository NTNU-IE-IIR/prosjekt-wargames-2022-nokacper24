package no.ntnu.kacperln.wargames.ui;

import java.io.File;
import java.io.IOException;
import no.ntnu.kacperln.wargames.data.Army;
import no.ntnu.kacperln.wargames.logic.ArmyFileHandler;
import no.ntnu.kacperln.wargames.logic.Battle;
import no.ntnu.kacperln.wargames.logic.IllegalUnitsFileException;

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
   * Sets armyOne in the application to the given army.
   * Also updates the initial state of armyOne to given army.
   *
   * @param army to set armyOne to
   */
  public void setArmyOne(Army army) {
    this.armyOne = army;
    this.armyOneInitial = this.armyOne.copy();
  }

  /**
   * Sets armyTwo in the application to the given army.
   * Also updates the initial state of armyTwo to given army.
   *
   * @param army to set armyTwo to
   */
  public void setArmyTwo(Army army) {
    this.armyTwo = army;
    this.armyTwoInitial = this.armyTwo.copy();
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
    this.setArmyOne(this.armyFileHandler.loadArmyFromFile(loadedFile));
  }

  /**
   * Loads army from a file and sets it as armyTwo.
   *
   * @param loadedFile file to load from
   * @throws IllegalUnitsFileException if file is not valid
   * @throws IOException if IO error occurs
   */
  public void loadArmy2FromFile(File loadedFile) throws IllegalUnitsFileException, IOException {
    this.setArmyTwo(this.armyFileHandler.loadArmyFromFile(loadedFile));
  }

  /**
   * Saves armyOne to the given file.
   *
   * @param loadedFile file to save to
   * @throws IOException if IO error occurs
   */
  public void saveArmy1ToFile(File loadedFile) throws IOException {
    this.armyFileHandler.saveArmyToFile(this.armyOne, loadedFile);
  }

  /**
   * Saves armyTwo to the given file.
   *
   * @param loadedFile file to save to
   * @throws IOException if IO error occurs
   */
  public void saveArmy2ToFile(File loadedFile) throws IOException {
    this.armyFileHandler.saveArmyToFile(this.armyTwo, loadedFile);
  }
}
