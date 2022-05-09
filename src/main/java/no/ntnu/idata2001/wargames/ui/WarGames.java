package no.ntnu.idata2001.wargames.ui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WarGames extends Application {

  @Override
  public void start(Stage primaryStage) {
    Parent root = null;
    try {
      root = FXMLLoader.load(getClass().getResource("wargamesGui.fxml"));

      primaryStage.setTitle("War Games");
      primaryStage.setScene(new Scene(root));
      primaryStage.show();
    } catch (IOException e) {
      System.out.println("ERROR: " + e.getMessage());
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}