package no.ntnu.idata2001.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
  void testGetResistBonus() {
    CommanderUnit unit = new CommanderUnit("ExampleName", 100, 12, 5);
    assertEquals(1, unit.getResistBonus()); // always 1
    assertEquals(1, unit.getResistBonus());
  }

  @Test
  void testGetAttackBonus() {
    CommanderUnit unit = new CommanderUnit("ExampleName", 100, 12, 5);
    assertEquals(6, unit.getAttackBonus()); // first 6
    assertEquals(2, unit.getAttackBonus()); // later only 2
    assertEquals(2, unit.getAttackBonus());
  }
}
