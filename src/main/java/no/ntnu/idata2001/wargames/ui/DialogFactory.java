package no.ntnu.idata2001.wargames.ui;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

/**
 * Class responsible for creating various dialogs used in WarGamesApplication.
 */
public class DialogFactory {

  /**
   * Create an error dialog with given header and content.
   *
   * @param header header of the dialog
   * @param content content of the dialog
   * @return error dialog
   */
  public Alert createErrorDialog(String header, String content) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText(header);
    alert.setContentText(content);
    return alert;
  }

  /**
   * Create a confirmation dialog with given header and content.
   *
   * @param header header of the dialog
   * @param content content of the dialog
   * @return confirmation dialog
   */
  public Alert createConfirmationDialog(String header, String content) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirmation");
    alert.setHeaderText(header);
    alert.setContentText(content);
    return alert;
  }

  /**
   * Creates and returns a confirm exit dialog.
   * Used for exit confirmation.
   * <p>
   * Use Optional&ltButtonType&gt result = dialog.showAndWait() to open the dialog.
   * Check for ButtonType.OK to confirm exit and call Platform.exit()
   *
   * @return confirmation dialog with exit contents
   */
  public Alert createConfirmExitDialog() {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirm exit");
    alert.setHeaderText("Are you sure you want to exit?");
    alert.setContentText("The application will be closed.");
    return alert;
  }

  /**
   * Create a file chooser dialog.
   *
   * @param header header of the dialog
   * @param content content of the dialog
   * @return information dialog
   */
  public Alert createInformationDialog(String header, String content) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Information");
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
        new FileChooser.ExtensionFilter("CSV","*.csv"));
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

}
