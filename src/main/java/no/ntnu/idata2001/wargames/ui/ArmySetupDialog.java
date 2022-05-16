package no.ntnu.idata2001.wargames.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import no.ntnu.idata2001.wargames.data.Army;
import no.ntnu.idata2001.wargames.data.Unit;

/**
 * Dialog for setting up an army.
 * This dialog supports: setting army's name, adding units individually, adding multiple
 * units at once, removing units, editing each unit, loading army from file, saving army to file.
 *
 * @author Kacper L. Nowicki
 * @version 16.05.2022
 */
public class ArmySetupDialog extends Dialog<Army> {

  private Army army;
  private DialogFactory dialogFactory;
  private TextField armyNameTextField;
  private TextField armyCountTextField;
  private TableView<Unit> unitsTableView;

  /**
   * Constructor for the ArmySetupDialog.
   *
   * @param army army
   */
  public ArmySetupDialog(Army army) {
    super();
    this.dialogFactory = new DialogFactory();
    this.armyNameTextField = new TextField();
    this.armyCountTextField = new TextField();

    if (army == null) {
      //if no army to edit, create new army with no units
      this.army = new Army("New army");
    } else {
      // if army to edit, copy army
      // so all changes are not reflected in the original army before ok is pressed
      this.army = army.copy();
    }
    this.createContent();
    this.defineReturnInstance();
  }

  private void createContent() {
    this.setTitle("Army Setup");
    this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 10, 10, 10));


  }

  private void setUpUnitsTableView() {
    this.unitsTableView = new TableView<>();
    TableColumn<Unit, Unit.UnitType> typeColumn = new TableColumn<>("Type");
    TableColumn<Unit, String> nameColumn = new TableColumn<>("Name");
    TableColumn<Unit, String> healthColumn = new TableColumn<>("Health");

    this.unitsTableView.getColumns().addAll(typeColumn, nameColumn, healthColumn);
    this.unitsTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    typeColumn.setCellValueFactory(new PropertyValueFactory<Unit, Unit.UnitType>("unitType"));
    nameColumn.setCellValueFactory(new PropertyValueFactory<Unit, String>("name"));
    healthColumn.setCellValueFactory(new PropertyValueFactory<Unit, String>("health"));

  }

  private void updateUnitsDetails() {
    ObservableList<Unit> observableUnitRegister =
        FXCollections.observableList(this.army.getAllUnits());
    this.unitsTableView.setItems(observableUnitRegister);
    this.armyNameTextField.setText(this.army.getName());
    this.armyCountTextField.setText(String.valueOf(this.army.getAllUnits().size()));
  }


  private void defineReturnInstance() {
  }
}
