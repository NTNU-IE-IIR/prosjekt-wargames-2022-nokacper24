package no.ntnu.kacperln.wargames.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import no.ntnu.kacperln.wargames.data.Unit;
import org.junit.jupiter.api.Test;

public class UnitFactoryTest {

  @Test
  void testCreateUnitString() {
    UnitFactory factory = new UnitFactory();
    Unit unit = factory.createUnit("cavalry", "name", 100);
    assertEquals(Unit.UnitType.CAVALRY, unit.getUnitType());
    assertEquals(100, unit.getHealth());
    assertEquals("name", unit.getName());
  }

  @Test
  void testCreateUnitEnum() {
    UnitFactory factory = new UnitFactory();
    Unit unit = factory.createUnit(Unit.UnitType.CAVALRY, "name", 100);
    assertEquals(Unit.UnitType.CAVALRY, unit.getUnitType());
    assertEquals(100, unit.getHealth());
    assertEquals("name", unit.getName());
  }

  @Test
  void testCreateUnitStringIncorrect() {
    UnitFactory factory = new UnitFactory();
    assertThrows(IllegalArgumentException.class, () -> {
      factory.createUnit("aaaa", "name1", 100);
    });
  }

  @Test
  void testCreateUnitsString() {
    UnitFactory factory = new UnitFactory();
    List<Unit> units = factory.createUnits(25, "commander", "name1", 100);
    assertEquals(25, units.size());
    assertEquals(Unit.UnitType.COMMANDER, units.get(0).getUnitType());
  }

  @Test
  void testCreateUnitsEnum() {
    UnitFactory factory = new UnitFactory();
    List<Unit> units = factory.createUnits(25, Unit.UnitType.COMMANDER, "name1", 100);
    assertEquals(25, units.size());
    assertEquals(Unit.UnitType.COMMANDER, units.get(0).getUnitType());
  }

  @Test
  void testCreateUnitsStringIncorrect() {
    UnitFactory factory = new UnitFactory();
    assertThrows(IllegalArgumentException.class, () -> {
      factory.createUnits(25, "aaaa", "name1", 100);
    });
  }
}
