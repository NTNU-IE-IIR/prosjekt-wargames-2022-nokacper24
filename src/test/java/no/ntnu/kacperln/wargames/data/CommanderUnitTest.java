package no.ntnu.kacperln.wargames.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import no.ntnu.kacperln.wargames.logic.TerrainType;
import org.junit.jupiter.api.Test;

class CommanderUnitTest {

  public CommanderUnitTest() {
  }

  @Test
  void testCreationOfInstance() {
    CommanderUnit unit = new CommanderUnit("ExampleName", 100, 12, 5);

    assertEquals("ExampleName", unit.getName());
    assertEquals(100, unit.getHealth());
    assertEquals(12, unit.getAttack());
    assertEquals(5, unit.getArmor());
  }

  @Test
  void testCopy() {
    CommanderUnit unit = new CommanderUnit("ExampleName", 100, 12, 5);
    CommanderUnit copy = unit.copy();
    assertEquals(unit, copy);
    assertNotSame(unit, copy);
  }

  @Test
  void testCopyNegative() {
    CommanderUnit unit = new CommanderUnit("ExampleName", 100, 12, 5);
    CommanderUnit copy = unit.copy();
    unit.setHealth(50);
    assertNotEquals(unit, copy);
    assertNotSame(unit, copy);
  }
}
