package no.ntnu.idata2001.wargames.ui;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import no.ntnu.idata2001.wargames.data.Army;
import no.ntnu.idata2001.wargames.data.Unit;
import no.ntnu.idata2001.wargames.logic.UnitFactory;

/**
 * Gui controller.
 */
public class Controller {

  @FXML
  private Label army1NameLabel;

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
  private Label army2NameLabel;

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

  public void initialize() {

    Army army1 = new Army("army1");
    Army army2 = new Army("army2");

    UnitFactory factory = new UnitFactory();

    army1.addAll(factory.createUnits(500, "cavalry", "unit1",150));
    army2.addAll(factory.createUnits(500, "cavalry", "un2",150));

    this.setUpTables();

    this.warGamesApplication = new WarGamesApplication(this.army1TableView, this.army2TableView);
    this.warGamesApplication.setArmyOne(army1);
    this.warGamesApplication.setArmyTwo(army2);
    this.warGamesApplication.setUp();
    this.setArmiesToTables();

    //terrainChoiceBox.setItems(FXCollections.observableList(Arrays.stream(Battle.TerrainType.values()).toList().stream().toList()));


  }

  private void setUpTables() {
    this.army1TableColumnType.setCellValueFactory(new  PropertyValueFactory<Unit, Unit.UnitType>("unitType"));
    this.army1TableColumnName.setCellValueFactory(new PropertyValueFactory<Unit, String>("name"));
    this.army1TableColumnHealth.setCellValueFactory(new PropertyValueFactory<Unit, String>("health"));

    this.army2TableColumnType.setCellValueFactory(new  PropertyValueFactory<Unit, Unit.UnitType>("unitType"));
    this.army2TableColumnName.setCellValueFactory(new PropertyValueFactory<Unit, String>("name"));
    this.army2TableColumnHealth.setCellValueFactory(new PropertyValueFactory<Unit, String>("health"));
  }

  public void setArmiesToTables() {
      ObservableList<Unit> army1ObservableUnitRegister =
          FXCollections.observableList(this.warGamesApplication.getArmyOne().getAllUnits());
      this.army1TableView.setItems(army1ObservableUnitRegister);

    ObservableList<Unit> army2ObservableUnitRegister = FXCollections.observableList(this.warGamesApplication.getArmyTwo().getAllUnits());
    this.army2TableView.setItems(army2ObservableUnitRegister);
  }

  @FXML
  private void handleStartSimulationButton(ActionEvent actionEvent) {
    //this.battle.getArmyOne().remove(this.battle.getArmyOne().getRandom());
   this.warGamesApplication.simulateBattleSlow();
  this.army1TableView.refresh();
  this.army2TableView.refresh();
  }

  @FXML
  private void handleResetArmiesButton(ActionEvent actionEvent) {
    this.warGamesApplication.resetArmies();
    this.setArmiesToTables();
  }

}
