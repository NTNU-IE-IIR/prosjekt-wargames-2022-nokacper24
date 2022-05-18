package no.ntnu.kacperln.wargames.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class RangedUnitTest {

  public RangedUnitTest() {
  }

  @Test
  void testCreationOfInstance() {
    RangedUnit unit = new RangedUnit("ExampleName", 100, 12, 5);

    assertEquals("ExampleName", unit.getName());
    assertEquals(100, unit.getHealth());
    assertEquals(12, unit.getAttack());
    assertEquals(5, unit.getArmor());
  }

  @Test
  void testGetResistBonus() {
    RangedUnit unit = new RangedUnit("ExampleName", 100, 12, 5);
    assertEquals(6, unit.getResistBonus()); // first 6
    assertEquals(4, unit.getResistBonus()); // second 4
    assertEquals(2, unit.getResistBonus()); // then always 2
    assertEquals(2, unit.getResistBonus());
  }

  @Test
  void testGetAttackBonus() {
    RangedUnit unit = new RangedUnit("ExampleName", 100, 12, 5);
    assertEquals(3, unit.getAttackBonus()); // always 3
    assertEquals(3, unit.getAttackBonus());
  }
}
