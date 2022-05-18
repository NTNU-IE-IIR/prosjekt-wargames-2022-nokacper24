package no.ntnu.kacperln.wargames.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import no.ntnu.kacperln.wargames.logic.TerrainType;
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
    unit.setCurrentTerrain(TerrainType.PLAINS); // PLAINS, default
    assertEquals(6, unit.getResistBonus()); // first 6
    assertEquals(4, unit.getResistBonus()); // second 4
    assertEquals(2, unit.getResistBonus()); // then always 2
    assertEquals(2, unit.getResistBonus());
  }

  @Test
  void testGetAttackBonus() {
    RangedUnit unit = new RangedUnit("ExampleName", 100, 12, 5);
    unit.setCurrentTerrain(TerrainType.PLAINS); // PLAINS, default
    assertEquals(3, unit.getAttackBonus()); // always 3
    assertEquals(3, unit.getAttackBonus());
  }

  @Test
  void testGetAttackBonusHill() {
    RangedUnit unit = new RangedUnit("ExampleName", 100, 12, 5);
    unit.setCurrentTerrain(TerrainType.HILL); // HILL, advantage
    assertEquals(5, unit.getAttackBonus()); // always 3 + 2 = 5
    assertEquals(5, unit.getAttackBonus());
  }

  @Test
  void testGetAttackBonusForest() {
    RangedUnit unit = new RangedUnit("ExampleName", 100, 12, 5);
    unit.setCurrentTerrain(TerrainType.FOREST); // FOREST, disadvantage
    assertEquals(1, unit.getAttackBonus()); // always 3 - 2 = 1
    assertEquals(1, unit.getAttackBonus());
  }

  @Test
  void testCopy() {
    RangedUnit unit = new RangedUnit("ExampleName", 100, 12, 5);
    RangedUnit copy = unit.copy();
    assertEquals(unit, copy);
    assertNotSame(unit, copy);
  }

  @Test
  void testCopyNegative() {
    RangedUnit unit = new RangedUnit("ExampleName", 100, 12, 5);
    RangedUnit copy = unit.copy();
    unit.setHealth(50);
    assertNotEquals(unit, copy);
    assertNotSame(unit, copy);
  }

}
