package no.ntnu.kacperln.wargames.logic;

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
import no.ntnu.kacperln.wargames.data.Army;
import no.ntnu.kacperln.wargames.data.Unit;

/**
 * Class representing a FileHandler for Army files.
 * Army files are text files with specific formatting.
 * First line is interpreted as army name,
 * every following line is interpreted as units in the army.
 * Correct formatting for the unit lines is as follows:
 * UnitType,unitName,unitHealth
 * Note that unit type must be supported, name cannot be empty, and health must be a number.
 *
 * @author Kacper L. Nowicki
 * @version 08-05-2022
 */
public class ArmyFileHandler {

  /**
   * Saves provided army in the provided file.
   *
   * @param army to be saved in file
   * @param file file to be saved
   * @throws IOException when IOException occurs
   */
  public void saveArmyToFile(Army army, File file) throws IOException {

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
    }
  }

  /**
   * Loads and returns army from file provided.
   *
   * @param file file
   * @return army from file
   * @throws IllegalUnitsFileException if file format is incorrect
   * @throws IOException               when IOException occurs
   */
  public Army loadArmyFromFile(File file) throws IllegalUnitsFileException, IOException {
    UnitFactory unitFactory = new UnitFactory();
    List<String> lines = this.loadLinesFromFile(file);

    String armyName;
    ArrayList<Unit> collectionOfUnits = new ArrayList<>();

    // file must have at least 1 line (which is the army name), it cannot be blank
    // it can have no units
    if (lines.isEmpty() || (lines.get(0).isBlank())) {
      throw new IllegalUnitsFileException("file has no army name");
    } else {
      armyName = lines.get(0);
    }

    // for every line in file (skips first line - army name)
    for (int i = 1; i < lines.size(); i++) {
      String[] line = lines.get(i).split(",");

      // throw exception iif line does not have 3 elements and is not a blank line
      if (line.length != 3 && !(line.length == 1 && line[0].isBlank())) {
        throw new IllegalUnitsFileException(
            "number of elements on line nr " + (i + 1) + " other than three");

        // if line has 3 elements parse them, else (blank line) skip and do nothing
      } else if (line.length == 3) {
        String unitType = line[0];
        String unitName = line[1];
        int unitHealth;

        // try to parse health, if not valid throw IllegalUnitsFileException
        try {
          unitHealth = Integer.parseInt(line[2]);
        } catch (NumberFormatException e) {
          throw new IllegalUnitsFileException(
              "health number provided on line nr "
                  + (i + 1) + " has incorrect format");
        }

        Unit newUnit;
        // try creating new unit from data in line
        // if illegal arguments found throw IllegalUnitsFileException
        try {
          newUnit = unitFactory.createUnit(unitType, unitName, unitHealth);
        } catch (IllegalArgumentException e) {
          throw new IllegalUnitsFileException(
              "Something went wrong when creating unit on line nr " + (i + 1)
                  + "\nDetails: " + e.getMessage());
        }
        collectionOfUnits.add(newUnit);
      }
    }
    return new Army(armyName, collectionOfUnits);
  }

  /**
   * Reads a file and returns the List of lines from file.
   *
   * @param file file to be read
   * @return List of lines from file
   * @throws IOException when IOException occurs
   */
  private List<String> loadLinesFromFile(File file) throws IOException {
    Charset charset = StandardCharsets.UTF_8;
    Path path = Path.of(file.getAbsolutePath());

    ArrayList<String> allLines = new ArrayList<>();

    try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
      String lineOfText;
      while ((lineOfText = reader.readLine()) != null) {
        allLines.add(lineOfText);
      }
    }
    return allLines;
  }
}