package no.ntnu.kacperln.wargames.ui.dialogs;

import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import no.ntnu.kacperln.wargames.data.Army;
import no.ntnu.kacperln.wargames.data.Unit;

/**
 * Class responsible for creating various dialogs used in WarGamesApplication.
 */
public class DialogFactory {

  /**
   * Create an error dialog with given header and content.
   *
   * @param header  header of the dialog
   * @param content content of the dialog
   * @return error dialog
   */
  public Alert createErrorDialog(String header, String content) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    this.setDialogWindowIcon(alert);
    alert.setHeaderText(header);
    alert.setContentText(content);
    return alert;
  }

  /**
   * Create a confirmation dialog with given header and content.
   *
   * @param header  header of the dialog
   * @param content content of the dialog
   * @return confirmation dialog
   */
  public Alert createConfirmationDialog(String header, String content) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirmation");
    this.setDialogWindowIcon(alert);
    alert.setHeaderText(header);
    alert.setContentText(content);
    return alert;
  }

  /**
   * Creates and returns a confirm exit dialog.
   * Used for exit confirmation.
   *
   * <p>Use Optional&lt;ButtonType&gt; result = dialog.showAndWait() to open the dialog.
   * Check for ButtonType.OK to confirm exit and call Platform.exit()
   *
   * @return confirmation dialog with exit contents
   */
  public Alert createConfirmExitDialog() {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirm exit");
    this.setDialogWindowIcon(alert);
    alert.setHeaderText("Are you sure you want to exit?");
    alert.setContentText("The application will be closed.");
    return alert;
  }

  /**
   * Create a file chooser dialog.
   *
   * @param header  header of the dialog
   * @param content content of the dialog
   * @return information dialog
   */
  public Alert createInformationDialog(String header, String content) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Information");
    this.setDialogWindowIcon(alert);
    alert.setHeaderText(header);
    alert.setContentText(content);
    return alert;
  }

  /**
   * Creates and returns a file chooser dialog with CSV extension filters.
   * Used for army files.
   * Use fileChooser.showOpenDialog(null) to open the dialog.
   *
   * @return file chooser
   */
  public FileChooser createFileChooser() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Army File");
    fileChooser.getExtensionFilters().add(
        new FileChooser.ExtensionFilter("CSV", "*.csv"));
    return fileChooser;
  }

  /**
   * Creates and returns a file chooser dialog with CSV extension filters.
   * Sets initial filename to armyName.csv
   * Used for army files.
   * Use fileChooser.showSaveDialog(null) to open the dialog.
   *
   * @param armyName name of the army to save
   * @return file chooser
   */
  public FileChooser createFileChooserSaving(String armyName) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Save Army File");
    fileChooser.getExtensionFilters().add(
        new FileChooser.ExtensionFilter("CSV", "*.csv"));
    fileChooser.setInitialFileName(armyName + ".csv");
    return fileChooser;
  }

  /**
   * Sets icon for the given dialog.
   *
   * @param dialog dialog to set icon for
   */
  private void setDialogWindowIcon(Dialog<?> dialog) {
    Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
    try {
      Image icon = new Image("no/ntnu/kacperln/wargames/ui/icon.png");
      stage.getIcons().add(icon);
    } catch (Exception e) {
      Alert errorAlert =
          this.createResourceErrorDialog(e.getClass().getSimpleName() + ": " + e.getMessage());
      errorAlert.showAndWait();
    }
  }

  /**
   * Creates and returns a resource error dialog.
   * Used for errors when loading resources. (e.g. fxml files or images)
   *
   * @param details details of the error
   * @return error dialog
   */
  public Alert createResourceErrorDialog(String details) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText("Could not load resource");
    alert.setContentText(
        "Could not find files necessary for the application.\nPlease contact support.\nDetails:\n"
            + details);
    return alert;
  }

  /**
   * Creates and returns a ArmySetupDialog with the given army.
   *
   * @param army army to edit
   * @return ArmySetupDialog
   */
  public ArmySetupDialog createArmySetupDialog(Army army) {
    ArmySetupDialog armySetupDialog = new ArmySetupDialog(army);
    this.setDialogWindowIcon(armySetupDialog);
    return armySetupDialog;
  }

  /**
   * Creates and returns a ArmySetupDialog for a new army.
   *
   * @return ArmySetupDialog
   */
  public ArmySetupDialog createArmySetupDialog() {
    ArmySetupDialog armySetupDialog = new ArmySetupDialog();
    this.setDialogWindowIcon(armySetupDialog);
    return armySetupDialog;
  }

  /**
   * Creates and returns a UnitDialog for a new unit.
   *
   * @return UnitDialog
   */
  public UnitDialog createUnitDialog() {
    UnitDialog unitDialog = new UnitDialog();
    this.setDialogWindowIcon(unitDialog);
    return unitDialog;
  }

  /**
   * Creates and returns a UnitDialog for the given unit.
   *
   * @param unit unit to edit
   * @return UnitDialog
   */
  public UnitDialog createUnitDialog(Unit unit) {
    UnitDialog unitDialog = new UnitDialog(unit);
    this.setDialogWindowIcon(unitDialog);
    return unitDialog;
  }

  /**
   * Creates and returns a MultipleUnitsDialog for creation of multiple units.
   *
   * @return MultipleUnitsDialog
   */
  public MultipleUnitsDialog createMultipleUnitsDialog() {
    MultipleUnitsDialog multipleUnitsDialog = new MultipleUnitsDialog();
    this.setDialogWindowIcon(multipleUnitsDialog);
    return multipleUnitsDialog;
  }

}
