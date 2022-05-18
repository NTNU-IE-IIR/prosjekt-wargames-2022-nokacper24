package no.ntnu.idata2001.wargames.ui;

import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import no.ntnu.idata2001.wargames.ui.dialogs.DialogFactory;

/**
 * The main window of the WarGames application, uses javaFX framework for GUI.
 * Gui is loaded from WarGames.fxml file, which is located under resources.
 *
 * <p>If exception is thrown when loading fxml file or icon of the application,
 * an error dialog is shown to the user.
 */
public class WarGames extends Application {

  /**
   * Sets up the main window of the application.
   *
   * @param primaryStage primary stage of the application
   */
  @Override
  public void start(Stage primaryStage) {
    Parent root = null;
    try {
      root = FXMLLoader.load(getClass().getResource("wargamesGui.fxml"));
      Image icon = new Image("no/ntnu/idata2001/wargames/ui/icon.png");
      primaryStage.getIcons().add(icon);

      primaryStage.setTitle("War Games");
      primaryStage.setScene(new Scene(root));
      primaryStage.show();
    } catch (Exception e) {
      // if any exception when loading resources, show error dialog and exit
      DialogFactory dialogFactory = new DialogFactory();
      Alert alert = dialogFactory.createResourceErrorDialog(
          e.getClass().getSimpleName()+ ": " + e.getMessage());
      alert.showAndWait();
    }

    setUpDialogOnClose(primaryStage);
  }

  /**
   * Sets up a confirmation dialog for when the user closes the application
   *
   * @param primaryStage primary stage of the application
   */
  private void setUpDialogOnClose(Stage primaryStage) {
    primaryStage.setOnCloseRequest(event -> {
      DialogFactory dialogFactory = new DialogFactory();
      Alert alert = dialogFactory.createConfirmExitDialog();

      Optional<ButtonType> result = alert.showAndWait();
      if (result.isPresent()) {
        if (result.get() == ButtonType.OK) {
          Platform.exit();
        } else {
          event.consume();
        }
      }
    });
  }

  /**
   * Main method for the WarGames application.
   * Launches javaFX application.
   *
   * @param args arguments
   */
  public static void main(String[] args) {
    launch(args);
  }
}