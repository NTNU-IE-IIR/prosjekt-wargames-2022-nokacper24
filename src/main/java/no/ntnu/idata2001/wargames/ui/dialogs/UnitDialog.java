package no.ntnu.idata2001.wargames.ui.dialogs;

import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import no.ntnu.idata2001.wargames.data.Unit;
import no.ntnu.idata2001.wargames.logic.UnitFactory;


/**
 * A dialog for creating new units or editing existing ones.
 */
public class UnitDialog extends Dialog<Unit> {

  /**
   * The mode of this dialog.
   * It can either be NEW or EDIT.
   * NEW is used when creating a new unit.
   * EDIT is used when editing an existing unit, thus UnitType cannot be chosen.
   */
  private enum Mode {
    NEW,
    EDIT
  }

  private final Mode mode;
  private Unit existingUnit;

  private TextField nameField;
  private ChoiceBox<Unit.UnitType> typeChoiceBox;
  private TextField healthField;

  private final UnitFactory unitFactory;
  private final DialogFactory dialogFactory;

  /**
   * Constructor for UnitDialog for creation of a new unit.
   */
  public UnitDialog() {
    super();
    this.mode = Mode.NEW;
    this.existingUnit = null;
    this.unitFactory = new UnitFactory();
    this.dialogFactory = new DialogFactory();

    this.createContent();
    this.defineReturnInstance();
  }

  /**
   * Constructor for UnitDialog for editing of an existing unit.
   *
   * @param unit unit to edit
   */
  public UnitDialog(Unit unit) {
    super();
    this.mode = Mode.EDIT;
    this.existingUnit = unit;
    this.unitFactory = new UnitFactory();
    this.dialogFactory = new DialogFactory();

    this.createContent();
    this.defineReturnInstance();
  }

  /**
   * Creates the content of the dialog.
   */
  private void createContent() {
    this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);


    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 10, 10, 10));

    this.nameField = new TextField();
    this.nameField.setPromptText("Name");
    Label nameErrorLabel = new Label("Name cannot be empty");
    nameErrorLabel.setTextFill(Paint.valueOf("#FF0000"));
    nameErrorLabel.setVisible(false);

    //name cannot be blank
    this.nameField.textProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.isBlank()) {
        nameErrorLabel.setVisible(true);
        this.setOkButtonAvailability();
      } else {
        nameErrorLabel.setVisible(false);
        this.setOkButtonAvailability();
      }
    });


    this.typeChoiceBox = new ChoiceBox<>();
    this.typeChoiceBox.getItems().addAll(Unit.UnitType.values());
    this.typeChoiceBox.setValue(Unit.UnitType.values()[0]);

    this.healthField = new TextField();
    this.healthField.setPromptText("Health");
    Label healthErrorLabel = new Label();
    healthErrorLabel.setTextFill(Paint.valueOf("#FF0000"));
    healthErrorLabel.setVisible(false);

    // health must be a number greater than 0
    this.healthField.textProperty().addListener((observable, oldValue, newValue) -> {
      // first check if it's a number
      if (!newValue.isBlank()) {
        try {
          int health = Integer.parseInt(newValue);
          if (health <= 0) {
            healthErrorLabel.setText("must be greater than 0");
            healthErrorLabel.setVisible(true);
            this.setOkButtonAvailability();
          } else {
            healthErrorLabel.setVisible(false);
            this.setOkButtonAvailability();
          }
        } catch (NumberFormatException e) {
          this.healthField.setText(oldValue);
        }
      } else {
        healthErrorLabel.setText("cannot be empty");
        healthErrorLabel.setVisible(true);
        this.setOkButtonAvailability();
      }
    });

    // fill data from existing unit, make type uneditable
    if (this.mode == Mode.EDIT) {
      this.setTitle("Edit unit");
      this.nameField.setText(this.existingUnit.getName());
      this.typeChoiceBox.setValue(this.existingUnit.getUnitType());
      this.typeChoiceBox.setDisable(true);
      this.healthField.setText(Integer.toString(this.existingUnit.getHealth()));
    } else {
      this.setTitle("New unit");
    }
    this.setOkButtonAvailability();


    grid.add(new Label("Name:"), 0, 0);
    grid.add(this.nameField, 1, 0);
    grid.add(nameErrorLabel, 2, 0);
    grid.add(new Label("Type:"), 0, 1);
    grid.add(this.typeChoiceBox, 1, 1);
    grid.add(new Label("Health:"), 0, 2);
    grid.add(this.healthField, 1, 2);
    grid.add(healthErrorLabel, 2, 2);

    this.getDialogPane().setContent(grid);

  }

  /**
   * Checks whether the name is valid.
   * Returns true if the name is valid, false if the text field is blank.
   *
   * @return true if valid
   */
  private boolean isNameFieldValid() {
    return !this.nameField.getText().isBlank();
  }

  /**
   * Checks whether the health is valid.
   * Returns true if the health is an integer greater than 0, false otherwise.
   */
  private boolean isHealthFieldValid() {
    boolean valid;
    try {
      int health = Integer.parseInt(this.healthField.getText());
      valid = health > 0;
    } catch (NumberFormatException e) {
      valid = false;
    }
    return valid;
  }

  /**
   * Enables the OK button if the name and health fields are valid.
   * Disables it if any of the fields are invalid.
   */
  private void setOkButtonAvailability() {
    if (isNameFieldValid() && isHealthFieldValid()) {
      this.getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
    } else {
      this.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
    }
  }

  /**
   * Defines what is returned when the dialog is closed.
   * When ok is pressed and the fields are valid, the unit is created (or edited) and returned.
   */
  private void defineReturnInstance() {
    this.setResultConverter(button -> {
      Unit result = null;
      if (button == ButtonType.OK) {
        // make sure the name and health are valid
        if (this.isNameFieldValid() && this.isHealthFieldValid()) {
          String name = this.nameField.getText();
          Unit.UnitType type = this.typeChoiceBox.getValue();
          int health = Integer.parseInt(this.healthField.getText());
          // if mode NEW, create a new unit
          if (this.mode == Mode.NEW) {
            result = this.unitFactory.createUnit(type, name, health);
            // if mode EDIT, edit the existing unit
          } else if (this.mode == Mode.EDIT) {
            this.existingUnit.setName(name);
            this.existingUnit.setHealth(health);
            result = this.existingUnit;
          }
        } else {
          this.dialogFactory.createErrorDialog("Could not create unit!",
              "Name cannot be empty and health must be greater than 0.");
        }
      }
      return result;
    });
  }
}
