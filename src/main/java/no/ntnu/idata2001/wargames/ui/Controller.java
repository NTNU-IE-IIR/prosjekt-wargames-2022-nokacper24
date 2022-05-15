package no.ntnu.idata2001.wargames.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import no.ntnu.idata2001.wargames.data.Army;
import no.ntnu.idata2001.wargames.data.Unit;
import no.ntnu.idata2001.wargames.logic.UnitFactory;

/**
 * Gui controller for wargames application.
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
  private Button resetArmiesButton;

  @FXML
  private Button setUpArmy1Button;

  @FXML
  private Button setUpArmy2Button;

  @FXML
  private Button startSimulationButton;

  @FXML
  private ChoiceBox<Unit.UnitType> terrainChoiceBox;

  private WarGamesApplication warGamesApplication;

  /**
   * Initializes the controller.
   * This method is called automatically after the fxml file has been loaded.
   */
  public void initialize() {

    Army army1 = new Army("Humans");
    Army army2 = new Army("Glorious army of noobs");

    UnitFactory factory = new UnitFactory();

    army1.addAll(factory.createUnits(5, Unit.UnitType.CAVALRY, "unit1", 150));
    army1.addAll(factory.createUnits(1, Unit.UnitType.COMMANDER, "dddd", 150));
    army1.addAll(factory.createUnits(4, Unit.UnitType.RANGED, "unit1", 150));
    army1.addAll(factory.createUnits(10, Unit.UnitType.INFANTRY, "unit1", 150));

    army2.addAll(factory.createUnits(5, Unit.UnitType.CAVALRY, "un2", 150));
    army2.addAll(factory.createUnits(1, Unit.UnitType.COMMANDER, "dddd", 150));
    army2.addAll(factory.createUnits(4, Unit.UnitType.RANGED, "un2", 150));
    army2.addAll(factory.createUnits(100, Unit.UnitType.INFANTRY, "un2", 150));

    this.setUpTables();

    this.warGamesApplication = new WarGamesApplication(this.army1TableView, this.army2TableView);
    this.warGamesApplication.setArmyOne(army1);
    this.warGamesApplication.setArmyTwo(army2);
    this.warGamesApplication.setUp();
    this.updateArmiesDetails();

    //terrainChoiceBox.setItems(FXCollections.observableList(Arrays.stream(Battle.TerrainType.values()).toList().stream().toList()));


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
  public void updateArmiesDetails() {
    ObservableList<Unit> army1ObservableUnitRegister =
        FXCollections.observableList(this.warGamesApplication.getArmyOne().getAllUnits());
    this.army1TableView.setItems(army1ObservableUnitRegister);
    this.army1NumberOfUnitsField.setText(String.valueOf(army1ObservableUnitRegister.size()));
    this.army1NameField.setText(this.warGamesApplication.getArmyOne().getName());

    ObservableList<Unit> army2ObservableUnitRegister =
        FXCollections.observableList(this.warGamesApplication.getArmyTwo().getAllUnits());
    this.army2TableView.setItems(army2ObservableUnitRegister);
    this.army2NumberOfUnitsField.setText(String.valueOf(army2ObservableUnitRegister.size()));
    this.army2NameField.setText(this.warGamesApplication.getArmyTwo().getName());
  }

  /**
   * Starts simulation of battle.
   * When the simulation is over it shows a dialog with the winning army.
   *
   * @param actionEvent event
   */
  @FXML
  private void handleStartSimulationButton(ActionEvent actionEvent) {
    this.warGamesApplication.simulateBattle();
    this.updateArmiesDetails();
    this.showBattleResultsDialog(warGamesApplication.getBattle().getWinner());
  }

  /**
   * Shows a dialog with the winning army.
   * If null is passed it shows a dialog with a tie.
   *
   * @param army winning army
   */
  private void showBattleResultsDialog(Army army) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Battle results");
    String content;
    if (army != null) {
      alert.setHeaderText(army.getName() + " won!");
      String unitOrUnits = army.getAllUnits().size() > 1 ? "units" : "unit";
      content = "The battle was won by " + army.getName() + "\n" + army.getAllUnits().size() + " " + unitOrUnits +" survived.";
    } else {
      alert.setHeaderText("Draw!");
      content = "The battle ended in a draw. \nNo one survived.";
    }

    alert.setContentText(content);
    alert.showAndWait();
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

}
