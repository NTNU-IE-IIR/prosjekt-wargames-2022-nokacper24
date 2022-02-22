package no.ntnu.idata2001.wargames.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import no.ntnu.idata2001.wargames.data.Army;
import no.ntnu.idata2001.wargames.data.CavalryUnit;
import no.ntnu.idata2001.wargames.data.CommanderUnit;
import no.ntnu.idata2001.wargames.data.InfantryUnit;
import no.ntnu.idata2001.wargames.data.RangedUnit;
import no.ntnu.idata2001.wargames.data.Unit;
import org.junit.jupiter.api.Test;

class ArmyTest {
  public ArmyTest() {
  }

  @Test
  void testCreationOfInstanceEmpty() {
    Army army = new Army("Army1");
    assertEquals("Army1",army.getName());
    assertEquals(0, army.getAllUnits().size());
  }

  @Test
  void testCreationOfInstanceWithUnits() {
    ArrayList<Unit> collectionOfUnits = new ArrayList<>();
    Unit unit1 = new CavalryUnit("name1", 100);
    Unit unit2 = new RangedUnit("name2", 120);
    Unit unit3 = new CommanderUnit("name3", 90);
    Unit unit4 = new InfantryUnit("name4", 70);
    collectionOfUnits.add(unit1);
    collectionOfUnits.add(unit2);
    collectionOfUnits.add(unit3);
    collectionOfUnits.add(unit4);
    Army army = new Army("Army1", collectionOfUnits);

    assertEquals("Army1",army.getName());
    assertEquals(4, army.getAllUnits().size());
  }

  @Test
  void testAdd() {
    Unit unit1 = new CavalryUnit("name1", 100);
    Army army = new Army("Army1");

    assertEquals(0, army.getAllUnits().size());
    army.add(unit1);
    assertEquals(1, army.getAllUnits().size());
  }

  @Test
  void testAddAll() {
    ArrayList<Unit> collectionOfUnits = new ArrayList<>();
    Unit unit1 = new CavalryUnit("name1", 100);
    Unit unit2 = new RangedUnit("name2", 120);
    Unit unit3 = new CommanderUnit("name3", 90);
    Unit unit4 = new InfantryUnit("name4", 70);
    collectionOfUnits.add(unit1);
    collectionOfUnits.add(unit2);
    collectionOfUnits.add(unit3);
    collectionOfUnits.add(unit4);
    Army army = new Army("Army1");

    assertEquals(0, army.getAllUnits().size());
    army.addAll(collectionOfUnits);
    assertEquals(4, army.getAllUnits().size());
  }

  @Test
  void testRemove() {
    ArrayList<Unit> collectionOfUnits = new ArrayList<>();
    Unit unit1 = new CavalryUnit("name1", 100);
    Unit unit2 = new RangedUnit("name2", 120);
    Unit unit3 = new CommanderUnit("name3", 90);
    Unit unit4 = new InfantryUnit("name4", 70);
    collectionOfUnits.add(unit1);
    collectionOfUnits.add(unit2);
    collectionOfUnits.add(unit3);
    collectionOfUnits.add(unit4);
    Army army = new Army("Army1", collectionOfUnits);

    assertEquals(4, army.getAllUnits().size());
    assertTrue(army.remove(unit4));
    assertEquals(3, army.getAllUnits().size());
  }
  @Test
  void testRemoveNonexistentUnit() {
    ArrayList<Unit> collectionOfUnits = new ArrayList<>();
    Unit unit1 = new CavalryUnit("name1", 100);
    Unit unit2 = new RangedUnit("name2", 120);
    Unit unit3 = new CommanderUnit("name3", 90);
    Unit unit4 = new InfantryUnit("name4", 70);
    collectionOfUnits.add(unit1);
    collectionOfUnits.add(unit2);
    collectionOfUnits.add(unit3);

    Army army = new Army("Army1", collectionOfUnits);

    assertEquals(3, army.getAllUnits().size());
    assertFalse(army.remove(unit4));
    assertEquals(3, army.getAllUnits().size());
  }

  @Test
  void testHasUnits() {
    Unit unit1 = new CavalryUnit("name1", 100);
    Army army = new Army("Army1");

    assertFalse(army.hasUnits());
    army.add(unit1);
    assertTrue(army.hasUnits());
  }

  @Test
  void testGetRandom() {
    ArrayList<Unit> collectionOfUnits = new ArrayList<>();
    Unit unit1 = new CavalryUnit("name1", 100);
    Unit unit2 = new RangedUnit("name2", 120);
    collectionOfUnits.add(unit1);
    collectionOfUnits.add(unit2);

    Army army = new Army("Army1", collectionOfUnits);
    assertNotNull(army.getRandom());
  }

}
