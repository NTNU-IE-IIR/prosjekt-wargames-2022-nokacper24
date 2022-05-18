package no.ntnu.kacperln.wargames.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class InfantryUnitTest {

  public InfantryUnitTest() {
  }

  @Test
  void testCreationOfInstance() {
    InfantryUnit unit = new InfantryUnit("ExampleName", 100, 12, 5);

    assertEquals("ExampleName", unit.getName());
    assertEquals(100, unit.getHealth());
    assertEquals(12, unit.getAttack());
    assertEquals(5, unit.getArmor());
  }

  @Test
  void testGetResistBonus() {
    InfantryUnit unit = new InfantryUnit("ExampleName", 100, 12, 5);
    assertEquals(1, unit.getResistBonus()); // always 1
    assertEquals(1, unit.getResistBonus());
  }

  @Test
  void testGetAttackBonus() {
    InfantryUnit unit = new InfantryUnit("ExampleName", 100, 12, 5);
    assertEquals(2, unit.getAttackBonus()); // always 2
    assertEquals(2, unit.getAttackBonus());
  }

  @Test
  void testCopy() {
    InfantryUnit unit = new InfantryUnit("ExampleName", 100, 12, 5);
    InfantryUnit copy = unit.copy();
    assertEquals(unit, copy);
    assertNotSame(unit, copy);
  }

}
