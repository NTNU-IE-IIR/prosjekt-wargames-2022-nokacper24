package no.ntnu.kacperln.wargames.ui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import no.ntnu.kacperln.wargames.data.Army;
import no.ntnu.kacperln.wargames.data.Unit;
import no.ntnu.kacperln.wargames.logic.IllegalUnitsFileException;
import no.ntnu.kacperln.wargames.logic.TerrainType;
import no.ntnu.kacperln.wargames.logic.UnitFactory;
import no.ntnu.kacperln.wargames.ui.dialogs.ArmySetupDialog;
import no.ntnu.kacperln.wargames.ui.dialogs.DialogFactory;

/**
 * Gui controller for wargames application.
 *
 * @author Kacper Lukassz Nowicki
 * @version 18.05.2022
 */
public class Controller {

  @FXML
  private TextField army1NameField;

  @FXML
  private TextField army1NumberOfUnitsField;

  @FXML
  private TableView<Unit> army1TableView;

  @FXML
  private TableColumn<Unit, String> army1TableColumnHealth;

  @FXML
  private TableColumn<Unit, String> army1TableColumnName;

  @FXML
  private TableColumn<Unit, Unit.UnitType> army1TableColumnType;

  @FXML
  private TextField army2NameField;

  @FXML
  private TextField army2NumberOfUnitsField;

  @FXML
  private TableView<Unit> army2TableView;

  @FXML
  private TableColumn<Unit, String> army2TableColumnHealth;

  @FXML
  private TableColumn<Unit, String> army2TableColumnName;

  @FXML
  private TableColumn<Unit, Unit.UnitType> army2TableColumnType;

  @FXML
  private ChoiceBox<TerrainType> terrainChoiceBox;

  private WarGamesApplication warGamesApplication;
  private DialogFactory dialogFactory;

  /**
   * Initializes the controller.
   * This method is called automatically after the fxml file has been loaded.
   */
  public void initialize() {
    this.warGamesApplication = new WarGamesApplication();
    this.dialogFactory = new DialogFactory();
    this.setUpTables();
    this.updateArmiesDetails();
    ObservableList<TerrainType> terrainTypes =
        FXCollections.observableArrayList(TerrainType.values());
    this.terrainChoiceBox.setItems(terrainTypes);
    this.terrainChoiceBox.setValue(TerrainType.values()[0]);
  }


  /**
   * Sets up the table views for army1 and army2.
   */
  private void setUpTables() {
    this.army1TableColumnType.setCellValueFactory(
        new PropertyValueFactory<Unit, Unit.UnitType>("unitType"));
    this.army1TableColumnName.setCellValueFactory(new PropertyValueFactory<Unit, String>("name"));
    this.army1TableColumnHealth.setCellValueFactory(
        new PropertyValueFactory<Unit, String>("health"));

    this.army2TableColumnType.setCellValueFactory(
        new PropertyValueFactory<Unit, Unit.UnitType>("unitType"));
    this.army2TableColumnName.setCellValueFactory(new PropertyValueFactory<Unit, String>("name"));
    this.army2TableColumnHealth.setCellValueFactory(
        new PropertyValueFactory<Unit, String>("health"));
  }

  /**
   * Updated the army tables, army names and number of units.
   */
  private void updateArmiesDetails() {
    if (this.warGamesApplication.getArmyOne() != null) {
      ObservableList<Unit> army1ObservableUnitRegister =
          FXCollections.observableList(this.warGamesApplication.getArmyOne().getAllUnits());
      this.army1TableView.setItems(army1ObservableUnitRegister);
      this.army1TableView.refresh();
      this.army1NumberOfUnitsField.setText(String.valueOf(army1ObservableUnitRegister.size()));
      this.army1NameField.setText(this.warGamesApplication.getArmyOne().getName());
    }

    if (this.warGamesApplication.getArmyTwo() != null) {
      ObservableList<Unit> army2ObservableUnitRegister =
          FXCollections.observableList(this.warGamesApplication.getArmyTwo().getAllUnits());
      this.army2TableView.setItems(army2ObservableUnitRegister);
      this.army2TableView.refresh();
      this.army2NumberOfUnitsField.setText(String.valueOf(army2ObservableUnitRegister.size()));
      this.army2NameField.setText(this.warGamesApplication.getArmyTwo().getName());
    }
  }

  /**
   * Starts simulation of battle.
   * When the simulation is over it shows a dialog with the winning army.
   *
   * @param actionEvent event
   */
  @FXML
  private void handleStartSimulationButton(ActionEvent actionEvent) {
    try {
      this.warGamesApplication.simulateBattle(this.terrainChoiceBox.getValue());
      this.updateArmiesDetails();
      this.showBattleResultsDialog(warGamesApplication.getBattle().getWinner());
    } catch (IllegalStateException e) {
      Alert alert = this.dialogFactory.createErrorDialog(
          "Could not start simulation", e.getMessage());
      alert.showAndWait();
    }
  }

  /**
   * Resets armies to the state before battle.
   * If battle was not simulated yet, it does nothing.
   *
   * @param actionEvent event
   */
  @FXML
  private void handleResetArmiesButton(ActionEvent actionEvent) {
    this.warGamesApplication.resetArmies();
    this.updateArmiesDetails();
  }

  /**
   * Shows a file chooser dialog and lets the user choose an army to load.
   * Loads chosen army to army1 in the application.
   *
   * @param actionEvent event
   */
  @FXML
  private void handleLoadArmy1FromFile(ActionEvent actionEvent) {
    FileChooser fileChooser = this.dialogFactory.createFileChooser();
    File file = fileChooser.showOpenDialog(null);
    if (file != null) {
      try {
        this.warGamesApplication.loadArmy1FromFile(file);
        this.updateArmiesDetails();
      } catch (IllegalUnitsFileException e) {
        this.showErrorLoadingArmyFileDialog(e);
      } catch (IOException e) {
        this.showFileErrorDialog(e);
      }
    }
  }

  /**
   * Shows a file chooser dialog and lets the user choose an army to load.
   * Loads chosen army to army2 in the application.
   *
   * @param actionEvent event
   */
  @FXML
  private void handleLoadArmy2FromFile(ActionEvent actionEvent) {
    FileChooser fileChooser = this.dialogFactory.createFileChooser();
    File file = fileChooser.showOpenDialog(null);
    if (file != null) {
      try {
        this.warGamesApplication.loadArmy2FromFile(file);
        this.updateArmiesDetails();
      } catch (IllegalUnitsFileException e) {
        this.showErrorLoadingArmyFileDialog(e);
      } catch (IOException e) {
        this.showFileErrorDialog(e);
      }
    }
  }

  /**
   * Shows a file chooser dialog and lets the user choose where to save army 1.
   *
   * @param actionEvent event
   */
  @FXML
  private void handleSaveArmy1ToFile(ActionEvent actionEvent) {
    String armyName = this.warGamesApplication.getArmyOne().getName();
    FileChooser fileChooser = this.dialogFactory.createFileChooserSaving(armyName);
    File file = fileChooser.showSaveDialog(null);
    if (file != null) {
      try {
        this.warGamesApplication.saveArmy1ToFile(file);
      } catch (IOException e) {
        this.showFileErrorDialog(e);
      }
    }
  }

  /**
   * Shows a file chooser dialog and lets the user choose where to save army 2.
   *
   * @param actionEvent event
   */
  @FXML
  private void handleSaveArmy2ToFile(ActionEvent actionEvent) {
    String armyName = this.warGamesApplication.getArmyTwo().getName();
    FileChooser fileChooser = this.dialogFactory.createFileChooserSaving(armyName);
    File file = fileChooser.showSaveDialog(null);
    if (file != null) {
      try {
        this.warGamesApplication.saveArmy2ToFile(file);
      } catch (IOException e) {
        this.showFileErrorDialog(e);
      }
    }
  }

  /**
   * Shows invalid army file error dialog.
   * Used when IllegalUnitsFileException occurs.
   *
   * @param e IllegalUnitsFileException
   */
  private void showErrorLoadingArmyFileDialog(IllegalUnitsFileException e) {
    Alert alert = this.dialogFactory.createErrorDialog(
        "Could not load army from file!", "Details:\n" + e.getMessage());
    alert.showAndWait();
  }

  /**
   * Shows a file error dialog.
   * Used when IOException occurs.
   *
   * @param e IOException
   */
  private void showFileErrorDialog(IOException e) {
    Alert alert = this.dialogFactory.createErrorDialog(
        "File error!", "Details:\n" + e.getMessage());
    alert.showAndWait();
  }

  /**
   * Shows a dialog where army1 can be edited.
   *
   * @param actionEvent event
   */
  @FXML
  private void handleSetUpArmy1Button(ActionEvent actionEvent) {
    ArmySetupDialog armySetupDialog;
    if (this.warGamesApplication.getArmyOne() != null) {
      armySetupDialog =
          this.dialogFactory.createArmySetupDialog(this.warGamesApplication.getArmyOne());
    } else {
      armySetupDialog = this.dialogFactory.createArmySetupDialog();
    }

    Optional<Army> result = armySetupDialog.showAndWait();
    if (result.isPresent()) {
      this.warGamesApplication.setArmyOne(result.get());
      this.updateArmiesDetails();
    }
  }

  /**
   * Shows a dialog where army2 can be edited.
   *
   * @param actionEvent event
   */
  @FXML
  private void handleSetUpArmy2Button(ActionEvent actionEvent) {
    ArmySetupDialog armySetupDialog;
    if (this.warGamesApplication.getArmyTwo() != null) {
      armySetupDialog =
          this.dialogFactory.createArmySetupDialog(this.warGamesApplication.getArmyTwo());
    } else {
      armySetupDialog = this.dialogFactory.createArmySetupDialog();
    }

    Optional<Army> result = armySetupDialog.showAndWait();
    if (result.isPresent()) {
      this.warGamesApplication.setArmyTwo(result.get());
      this.updateArmiesDetails();
    }
  }

  /**
   * Shows a dialog with the winning army.
   * If null is passed it shows a dialog with a tie.
   *
   * @param army winning army
   */
  private void showBattleResultsDialog(Army army) {
    String headerText;
    String content;
    if (army != null) {
      headerText = army.getName() + " won!";
      String unitOrUnits = army.getAllUnits().size() > 1 ? "units" : "unit";
      content = "The battle was won by " + army.getName() + "\n" + army.getAllUnits().size() + " "
          + unitOrUnits + " survived.";
    } else {
      headerText = "Draw!";
      content = "The battle ended in a draw. \nNo one survived.";
    }

    Alert informationDialog = this.dialogFactory.createInformationDialog(headerText, content);
    informationDialog.showAndWait();
  }

  /**
   * Shows exit confirmation dialog.
   * When ok is pressed, the application is closed.
   *
   * @param actionEvent event
   */
  @FXML
  private void handleQuitButton(ActionEvent actionEvent) {
    Alert alert = this.dialogFactory.createConfirmExitDialog();
    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {
      Platform.exit();
    }
  }

  /**
   * Creates two sample armies and sets them to the application.
   *
   * @param actionEvent event
   */
  @FXML
  private void handleLoadSampleArmies(ActionEvent actionEvent) {
    UnitFactory unitFactory = new UnitFactory();

    List<Unit> sampleUnitsOne = new ArrayList<>();
    sampleUnitsOne.add(unitFactory.createUnit(Unit.UnitType.COMMANDER, "Mountain King", 180));
    sampleUnitsOne.addAll(unitFactory.createUnits(200, Unit.UnitType.RANGED, "Archer", 100));
    sampleUnitsOne.addAll(unitFactory.createUnits(100, Unit.UnitType.CAVALRY, "Knight", 100));
    sampleUnitsOne.addAll(unitFactory.createUnits(500, Unit.UnitType.INFANTRY, "Footman", 100));
    Army sampleArmyOne = new Army("Human army", sampleUnitsOne);
    this.warGamesApplication.setArmyOne(sampleArmyOne);

    List<Unit> sampleUnitsTwo = new ArrayList<>();
    sampleUnitsTwo.add(unitFactory.createUnit(Unit.UnitType.COMMANDER, " Gul´dan", 180));
    sampleUnitsTwo.addAll(unitFactory.createUnits(200, Unit.UnitType.RANGED, "Spearman", 100));
    sampleUnitsTwo.addAll(unitFactory.createUnits(100, Unit.UnitType.CAVALRY, "Raider", 100));
    sampleUnitsTwo.addAll(unitFactory.createUnits(500, Unit.UnitType.INFANTRY, "Grunt", 100));
    Army sampleArmyTwo = new Army("Orcish horde", sampleUnitsTwo);
    this.warGamesApplication.setArmyTwo(sampleArmyTwo);

    this.updateArmiesDetails();
  }

  /**
   * Shows an information dialog with about info.
   *
   * @param actionEvent event
   */
  @FXML
  private void handleShowAboutInfo(ActionEvent actionEvent) {
    String textBlock = """
        War Games is a simple battle simulation program.
        
        This program is a project for IDATA2001 course at NTNU.
        Author: Kacper Lukasz Nowicki
        """;

    Alert alert = this.dialogFactory.createInformationDialog("About War Games", textBlock);

    alert.showAndWait();
  }

}
