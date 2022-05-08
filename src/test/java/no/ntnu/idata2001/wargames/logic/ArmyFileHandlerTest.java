package no.ntnu.idata2001.wargames.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import no.ntnu.idata2001.wargames.data.Army;
import no.ntnu.idata2001.wargames.data.CavalryUnit;
import no.ntnu.idata2001.wargames.data.CommanderUnit;
import no.ntnu.idata2001.wargames.data.InfantryUnit;
import no.ntnu.idata2001.wargames.data.RangedUnit;
import no.ntnu.idata2001.wargames.data.Unit;
import org.junit.jupiter.api.Test;

/**
 * Tests for ArmyFileHandler.
 * To run these tests you need 3 files under /src/test/:
 * <p>- sampleArmy.csv
 * <p>- sampleArmyBlank.csv
 * <p>- sampleArmyCorrupt.csv
 *
 * <p>Important to note that these tests can fail due to IOExceptions.
 */
public class ArmyFileHandlerTest {

  @Test
  void LoadArmyFromFIleTest() throws IOException {
    ArmyFileHandler handler = new ArmyFileHandler();
    File file = new File("./src/test/sampleArmy.csv");
    try {
      Army army = handler.loadArmyFromFile(file);
      assertEquals("ArmyName1", army.getName());
      assertEquals(4, army.getAllUnits().size());
    } catch (IllegalUnitsFileException e) {
      //this should never get triggered, as long as sampleArmy.csv was not edited
    }
  }

  @Test
  void LoadArmyFromFIleTestBlankFile() throws IOException {
    ArmyFileHandler handler = new ArmyFileHandler();
    File file = new File("./src/test/sampleArmyBlank.csv");

    assertThrows(IllegalUnitsFileException.class, () ->
        handler.loadArmyFromFile(file));
  }

  @Test
  void LoadArmyFromFIleTestCorruptFile() throws IOException {
    ArmyFileHandler handler = new ArmyFileHandler();
    File file = new File("./src/test/sampleArmyCorrupt.csv");

    assertThrows(IllegalUnitsFileException.class, () ->
        handler.loadArmyFromFile(file));
  }

  /**
   * This test method tests both save and load methods.
   */
  @Test
  void saveArmyToFileTest() throws IOException {
    ArrayList<Unit> collectionOfUnits = new ArrayList<>();
    Unit unit1 = new InfantryUnit("name1", 70);
    Unit unit2 = new CavalryUnit("name2", 100);
    Unit unit3 = new RangedUnit("name3", 120);
    Unit unit4 = new CommanderUnit("name4", 90);
    collectionOfUnits.add(unit1);
    collectionOfUnits.add(unit2);
    collectionOfUnits.add(unit3);
    collectionOfUnits.add(unit4);
    Army army = new Army("Army", collectionOfUnits);

    ArmyFileHandler handler = new ArmyFileHandler();

    File file = new File("./src/test/testArmyFile.csv");
    handler.saveArmyToFile(army, file);

    try {
      Army loadedArmy = handler.loadArmyFromFile(file);
      assertEquals(army, loadedArmy);
    } catch (IllegalUnitsFileException e) {
      // this should not get triggered since file is created first
    }

  }

  //@Test
  void LoadArmyFromFIleTdwdwdwdwdwest() throws IllegalUnitsFileException, IOException {
    ArmyFileHandler handler = new ArmyFileHandler();
    UnitFactory factory = new UnitFactory();
    List<Unit> units = factory.createUnits(25, "commander", "name1", 100);
    units.addAll(factory.createUnits(50, Unit.UnitType.INFANTRY, "name232", 150));
    Army army = new Army("Super amazing army", units);

    File file = new File("./src/test/greatArmy.csv");
    handler.saveArmyToFile(army,file);
  }
}
