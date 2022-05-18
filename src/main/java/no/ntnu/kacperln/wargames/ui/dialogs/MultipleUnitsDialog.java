package no.ntnu.kacperln.wargames.ui.dialogs;

import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import no.ntnu.kacperln.wargames.data.Unit;
import no.ntnu.kacperln.wargames.logic.UnitFactory;

/**
 * Dialog for adding multiple units to the army.
 */
public class MultipleUnitsDialog extends Dialog<List<Unit>> {


  private TextField nameField;
  private ChoiceBox<Unit.UnitType> typeChoiceBox;
  private TextField healthField;
  private TextField numberOfUnitsField;

  private final UnitFactory unitFactory;
  private final DialogFactory dialogFactory;

  /**
   * Constructor for MultipleUnitsDialog.
   */
  public MultipleUnitsDialog() {
    super();
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
    this.setTitle("New units");

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
    this.addNumberFieldListener(this.healthField, healthErrorLabel);

    this.numberOfUnitsField = new TextField();
    this.numberOfUnitsField.setPromptText("Number of units");
    Label numberOfUnitsErrorLabel = new Label();
    numberOfUnitsErrorLabel.setTextFill(Paint.valueOf("#FF0000"));
    numberOfUnitsErrorLabel.setVisible(false);
    this.addNumberFieldListener(this.numberOfUnitsField, numberOfUnitsErrorLabel);

    this.setOkButtonAvailability();

    grid.add(new Label("Name:"), 0, 0);
    grid.add(this.nameField, 1, 0);
    grid.add(nameErrorLabel, 2, 0);
    grid.add(new Label("Type:"), 0, 1);
    grid.add(this.typeChoiceBox, 1, 1);
    grid.add(new Label("Health:"), 0, 2);
    grid.add(this.healthField, 1, 2);
    grid.add(healthErrorLabel, 2, 2);
    grid.add(new Label("Number of units:"), 0, 3);
    grid.add(this.numberOfUnitsField, 1, 3);
    grid.add(numberOfUnitsErrorLabel, 2, 3);

    this.getDialogPane().setContent(grid);
  }

  /**
   * Adds a listener to the number field.
   * Sets the error label and OK button availability accordingly.
   *
   * @param textField the text field to add the listener to
   * @param errorLabel the error label to set
   */
  private void addNumberFieldListener(TextField textField, Label errorLabel) {
    textField.textProperty().addListener((observable, oldValue, newValue) -> {
      // first check if it's a number
      if (!newValue.isBlank()) {
        try {
          int health = Integer.parseInt(newValue);
          if (health <= 0) {
            errorLabel.setText("must be greater than 0");
            errorLabel.setVisible(true);
            this.setOkButtonAvailability();
          } else {
            errorLabel.setVisible(false);
            this.setOkButtonAvailability();
          }
        } catch (NumberFormatException e) {
          textField.setText(oldValue);
        }
      } else {
        errorLabel.setText("cannot be empty");
        errorLabel.setVisible(true);
        this.setOkButtonAvailability();
      }
    });
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
   *
   * @return true if valid
   */
  private boolean isHealthFieldValid() {
    return this.isNumberFieldValid(this.healthField);
  }

  /**
   * Checks whether the number of units is valid.
   * Returns true if the number of units is an integer greater than 0, false otherwise.
   *
   * @return true if valid
   */
  private boolean isNumberOfUnitsFieldValid() {
    return this.isNumberFieldValid(this.numberOfUnitsField);
  }

  /**
   * Checks whether the given number field is valid.
   * Returns true if the number is an integer greater than 0, false otherwise.
   *
   * @param textField the text field to check
   * @return true if valid
   */
  private boolean isNumberFieldValid(TextField textField) {
    boolean valid;
    try {
      int n = Integer.parseInt(textField.getText());
      valid = n > 0;
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
    if (isNameFieldValid() && isHealthFieldValid() && isNumberOfUnitsFieldValid()) {
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
      List<Unit> result = null;
      if (button == ButtonType.OK) {
        // make sure all fields are valid
        if (this.isNameFieldValid() && this.isHealthFieldValid() && this.isNumberOfUnitsFieldValid()) {
          String name = this.nameField.getText();
          Unit.UnitType type = this.typeChoiceBox.getValue();
          int health = Integer.parseInt(this.healthField.getText());
          int numberOfUnits = Integer.parseInt(this.numberOfUnitsField.getText());
          // if mode NEW, create a new units
            result = this.unitFactory.createUnits(numberOfUnits ,type, name, health);

        } else {
          this.dialogFactory.createErrorDialog("Could not create units!",
              "Name cannot be empty and both health and number of units must be greater than 0.");
        }
      }
      return result;
    });
  }
}
