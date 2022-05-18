/**
 * Module info for WarGames application.
 */
module wargames {
  requires javafx.graphics;
  requires javafx.controls;
  requires javafx.fxml;
  opens no.ntnu.kacperln.wargames;
  opens no.ntnu.kacperln.wargames.data;
  opens no.ntnu.kacperln.wargames.logic;
  opens no.ntnu.kacperln.wargames.ui;
  opens no.ntnu.kacperln.wargames.ui.dialogs;
}