package no.ntnu.kacperln.wargames.ui.dialogs;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import no.ntnu.kacperln.wargames.data.Army;
import no.ntnu.kacperln.wargames.data.Unit;
import no.ntnu.kacperln.wargames.logic.ArmyFileHandler;
import no.ntnu.kacperln.wargames.logic.IllegalUnitsFileException;

/**
 * Dialog for setting up an army.
 * This dialog supports: setting army's name, adding units individually, adding multiple
 * units at once, removing units, editing each unit, loading army from file, saving army to file.
 * It automatically checks if name field is valid, adjusts OK button's availability accordingly
 * and gives feedback to user.
 *
 * @author Kacper L. Nowicki
 * @version 18.05.2022
 */
public class ArmySetupDialog extends Dialog<Army> {

  private Army army;
  private final DialogFactory dialogFactory;
  private final ArmyFileHandler armyFileHandler;
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
    this.armyFileHandler = new ArmyFileHandler();
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

    this.initialize();
    this.createContent();
    this.defineReturnInstance();
  }

  /**
   * Constructor for the ArmySetupDialog with no army from before.
   */
  public ArmySetupDialog() {
    super();
    this.dialogFactory = new DialogFactory();
    this.armyFileHandler = new ArmyFileHandler();
    this.armyNameTextField = new TextField();
    this.armyCountTextField = new TextField();
    this.army = new Army("New army");

    this.initialize();
    this.createContent();
    this.defineReturnInstance();
  }

  /**
   * Initializes the dialog.
   */
  private void initialize() {
    this.setUpUnitsTableView();
    this.updateUnitsDetails();
  }

  /**
   * Creates the content of the dialog.
   */
  private void createContent() {
    this.setTitle("Army Setup");
    this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

    this.armyCountTextField.setEditable(false);

    Label armyNameLabel = new Label("Army name:");
    this.armyNameTextField.setPromptText("Army name");
    // check if army name is blank
    // if it is blank, disable OK button and inform user
    this.armyNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.isBlank()) {
        // if army name is blank, inform user and disable OK button
        armyNameLabel.setText("Name cannot be blank!");
        armyNameLabel.setTextFill(Paint.valueOf("#FF0000"));
        armyNameLabel.setStyle("-fx-font-weight: bold");
        this.setOkButtonAvailability();
      } else {
        // if army name is not blank, enable OK button
        armyNameLabel.setText("Army name:");
        armyNameLabel.setTextFill(Paint.valueOf("#000000"));
        armyNameLabel.setStyle("-fx-font-weight: normal");
        this.setOkButtonAvailability();
      }
    });

    VBox vboxButtons = this.createVboxButtons();

    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 10, 10, 10));

    grid.add(armyNameLabel, 0, 0);
    grid.add(this.armyNameTextField, 1, 0);
    grid.add(new Label("Number of units:"), 0, 1);
    grid.add(this.armyCountTextField, 1, 1);
    grid.add(vboxButtons, 0, 2);
    grid.add(this.unitsTableView, 1, 2);

    this.setOkButtonAvailability();

    this.getDialogPane().setContent(grid);
  }

  /**
   * Creates a VBox with buttons to be displayed on the left side of the dialog.
   *
   * @return VBox with buttons
   */
  private VBox createVboxButtons() {
    VBox vboxButtonsTop = new VBox();
    vboxButtonsTop.setSpacing(10);
    vboxButtonsTop.setPadding(new Insets(10, 10, 10, 10));
    vboxButtonsTop.setAlignment(Pos.TOP_CENTER);
    vboxButtonsTop.setPrefHeight(300);

    VBox vboxButtonsBottom = new VBox();
    vboxButtonsBottom.setSpacing(10);
    vboxButtonsBottom.setPadding(new Insets(10, 10, 10, 10));
    vboxButtonsBottom.setAlignment(Pos.BOTTOM_CENTER);

    Button addUnitButton = new Button("Add unit");
    Button addMultipleUnitsButton = new Button("Add multiple units");
    Button editUnitButton = new Button("Edit unit");
    Button removeUnitButton = new Button("Remove unit");
    vboxButtonsTop.getChildren()
        .addAll(addUnitButton, addMultipleUnitsButton, editUnitButton, removeUnitButton);

    Button loadArmyButton = new Button("Load army file");
    Button saveArmyButton = new Button("Save army to file");
    vboxButtonsBottom.getChildren().addAll(loadArmyButton, saveArmyButton);

    // add event handlers to buttons
    addUnitButton.setOnAction(this::handleAddUnitButton);
    addMultipleUnitsButton.setOnAction(this::handleAddMultipleUnitsButton);
    editUnitButton.setOnAction(this::handleEditUnitButton);
    removeUnitButton.setOnAction(this::handleRemoveUnitButton);
    loadArmyButton.setOnAction(this::handleLoadArmyButton);
    saveArmyButton.setOnAction(this::handleSaveArmyButton);

    // make all buttons as wide as possible
    Button[] buttons = {
        addUnitButton,
        addMultipleUnitsButton,
        editUnitButton,
        removeUnitButton,
        loadArmyButton,
        saveArmyButton
    };

    for (Button button : buttons) {
      button.setMaxWidth(Double.MAX_VALUE);
    }

    VBox vboxButtons = new VBox();
    vboxButtons.getChildren().addAll(vboxButtonsTop, vboxButtonsBottom);
    vboxButtons.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

    return vboxButtons;
  }

  /**
   * Enables the OK button if the army name is valid.
   * Disables it if the army name is invalid.
   */
  private void setOkButtonAvailability() {
    if (this.armyNameTextField.getText().isBlank()) {
      this.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
    } else {
      this.getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
    }
  }

  /**
   * Opens UnitDialog to add a new unit to the army.
   *
   * @param event event
   */
  private void handleAddUnitButton(ActionEvent event) {
    UnitDialog unitDialog = this.dialogFactory.createUnitDialog();
    Optional<Unit> result = unitDialog.showAndWait();
    if (result.isPresent()) {
      this.army.add(result.get());
      this.updateUnitsDetails();
    }
  }

  /**
   * Opens MultipleUnitsDialog to add multiple units to the army.
   *
   * @param event event
   */
  private void handleAddMultipleUnitsButton(ActionEvent event) {
    MultipleUnitsDialog multipleUnitsDialog = this.dialogFactory.createMultipleUnitsDialog();
    Optional<List<Unit>> result = multipleUnitsDialog.showAndWait();
    if (result.isPresent()) {
      this.army.addAll(result.get());
      this.updateUnitsDetails();
    }
  }

  /**
   * Opens a dialog to edit the selected unit.
   * If no unit selected shows error dialog.
   *
   * @param event event
   */
  private void handleEditUnitButton(ActionEvent event) {
    Unit selectedUnit = this.unitsTableView.getSelectionModel().getSelectedItem();
    if (selectedUnit == null) {
      Alert alert =
          this.dialogFactory.createErrorDialog("No unit selected!", "Please select unit to edit.");
      alert.showAndWait();
    } else {
      UnitDialog unitDialog = this.dialogFactory.createUnitDialog(selectedUnit);
      Optional<Unit> result = unitDialog.showAndWait();
      if (result.isPresent()) {
        this.army.remove(selectedUnit);
        this.army.add(result.get());
        this.updateUnitsDetails();
      }
    }
  }

  /**
   * Handles the remove unit button.
   * If a unit is selected it is removed from the army.
   * If no unit is selected, error message is displayed.
   *
   * @param event event
   */
  private void handleRemoveUnitButton(ActionEvent event) {
    Unit selectedUnit = this.unitsTableView.getSelectionModel().getSelectedItem();
    if (selectedUnit != null) {
      this.army.remove(selectedUnit);
      this.updateUnitsDetails();
    } else {
      Alert alert = this.dialogFactory.createErrorDialog(
          "No units selected!", "Please select a unit to remove.");
      alert.showAndWait();
    }
  }

  /**
   * Handles the load army button.
   * When button is pressed it opens a file chooser and loads the selected file.'
   *
   * @param event event
   */
  private void handleLoadArmyButton(ActionEvent event) {
    FileChooser fileChooser = this.dialogFactory.createFileChooser();
    File file = fileChooser.showOpenDialog(null);
    if (file != null) {
      try {
        this.army = this.armyFileHandler.loadArmyFromFile(file);
        this.updateUnitsDetails();
      } catch (IllegalUnitsFileException e) {
        this.showErrorLoadingArmyFileDialog(e);
      } catch (IOException e) {
        this.showFileErrorDialog(e);
      }
    }
  }

  /**
   * Handles the save army button.
   * When button is pressed it opens a file chooser and saves the army to selected location.
   * Checks whether army name is valid or not, if not valid it shows an error dialog.
   *
   * @param event event
   */
  private void handleSaveArmyButton(ActionEvent event) {

    boolean isNameValid = false;
    try {
      this.army.setName(this.armyNameTextField.getText());
      isNameValid = true;
    } catch (IllegalArgumentException e) {
      Alert alert = this.dialogFactory.createErrorDialog("Could not save army!", e.getMessage());
      alert.showAndWait();
    }

    if (isNameValid) {
      FileChooser fileChooser = this.dialogFactory.createFileChooserSaving(this.army.getName());
      File file = fileChooser.showSaveDialog(null);
      if (file != null) {
        try {
          this.armyFileHandler.saveArmyToFile(this.army, file);
        } catch (IOException e) {
          this.showFileErrorDialog(e);
        }
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
   * Sets up the table view for the units.
   */
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

    this.unitsTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    // call edit unit when double-clicked on a row
    this.unitsTableView.setOnMouseClicked(event -> {
      if (event.getClickCount() == 2) {
        this.handleEditUnitButton(null);
      }
    });
  }

  /**
   * Updates the table view with the units in the army, army name and army count.
   */
  private void updateUnitsDetails() {
    ObservableList<Unit> observableUnitRegister =
        FXCollections.observableList(this.army.getAllUnits());
    this.unitsTableView.setItems(observableUnitRegister);
    this.unitsTableView.refresh();
    this.armyNameTextField.setText(this.army.getName());
    this.armyCountTextField.setText(String.valueOf(this.army.getAllUnits().size()));
  }


  /**
   * Defines what is returned when the dialog is closed.
   * When ok is pressed and army name is valid it returns a new army.
   */
  private void defineReturnInstance() {
    this.setResultConverter(button -> {
      Army result = null;
      if (button == ButtonType.OK) {
        if (!this.armyNameTextField.getText().isBlank()) {
          // if arny name is not blank, set name and return army
          this.army.setName(this.armyNameTextField.getText());
          result = this.army;
        } else {
          Alert alert = this.dialogFactory.createErrorDialog(
              "Army name cannot be empty!", "Please enter a name!");
          alert.showAndWait();
        }
      }
      return result;
    });
  }
}
