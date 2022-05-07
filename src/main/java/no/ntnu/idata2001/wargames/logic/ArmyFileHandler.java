package no.ntnu.idata2001.wargames.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import no.ntnu.idata2001.wargames.data.Army;
import no.ntnu.idata2001.wargames.data.Unit;

/**
 * Class representing a FileHandler for Army files.
 * Army files are of text files with specific formatting.
 * First line is interpreted as army name,
 * every following line is interpreted as units in the army.
 * Correct formatting for the unit lines is as follows:
 * UnitType,unitName,unitHealth
 * Note that unit type must be supported, name cannot be empty, and health must be a number.
 *
 * @version 07-05-2022
 * @author Kacper L. Nowicki
 */
public class ArmyFileHandler {

  /**
   * Saves provided army in a file.
   *
   * @param army to be saved
   * @param file name of file
   */
  public void saveArmyToFile(Army army, File file) {

    try (FileWriter fileWriter = new FileWriter(file)) {
      PrintWriter writer = new PrintWriter(fileWriter);

      writer.println(army.getName());
      for (Unit unit : army.getAllUnits()) {
        writer.println(

            unit.getUnitType().toString()
                + "," + unit.getName()
                + "," + unit.getHealth()
        );
      }

    } catch (IOException e) {
      System.out.println("File could not be created"); //TODO something reasonable
    }
  }

  /**
   * Loads and returns army from file provided.
   *
   * @param file file
   * @return army from file
   * @throws IllegalUnitsFileException if file format is incorrect
   */
  public Army loadArmyFromFile(File file) throws IllegalUnitsFileException {
    UnitFactory unitFactory = new UnitFactory();
    List<String> lines = this.loadLinesFromFile(file);

    String armyName;
    ArrayList<Unit> collectionOfUnits = new ArrayList<>();

    if (lines.size() >= 2) {
      armyName = lines.get(0);

      for (int i = 1; i < lines.size(); i++) {
        String[] line = lines.get(i).split(",");

        if (line.length == 3) {
          String unitType = line[0];
          String unitName = line[1];
          int unitHealth;

          try {
            unitHealth = Integer.parseInt(line[2]);
          } catch (NumberFormatException e) {
            throw new IllegalUnitsFileException(
                "health number provided has incorrect format");
          }

          Unit newUnit;
          try {
            newUnit = unitFactory.createUnit(unitType, unitName, unitHealth);
          } catch (IllegalArgumentException e) {
            throw new IllegalUnitsFileException(
                "Error occurred when creating units, details:\n" + e.getMessage());
          }

          collectionOfUnits.add(newUnit);

        } else if (!line[0].isBlank()) {
          // throws an exception when line is NOT empty,
          // and it has incorrect number of elements on it
          // in case of empty line (or spaces), skips it
          throw new IllegalUnitsFileException("number of elements on line other than 3");
        }
      }
    } else {
      throw new IllegalUnitsFileException("file has no units to load");
    }

    return new Army(armyName, collectionOfUnits);
  }

  /**
   * Loads a file and returns the List of lines from file.
   *
   * @param file file to be read
   * @return List of lines from file
   */
  private List<String> loadLinesFromFile(File file) {
    Charset charset = StandardCharsets.UTF_8;
    Path path = Path.of(file.getAbsolutePath());

    ArrayList<String> allLines = new ArrayList<>();

    try (BufferedReader reader =
             Files.newBufferedReader(path, charset)) {

      String lineOfText;
      while ((lineOfText = reader.readLine()) != null) {
        allLines.add(lineOfText);
      }
    } catch (IOException e) {
      System.out.println("File not found. Couldn't find file"); //TODO
    }
    return allLines;
  }

}

