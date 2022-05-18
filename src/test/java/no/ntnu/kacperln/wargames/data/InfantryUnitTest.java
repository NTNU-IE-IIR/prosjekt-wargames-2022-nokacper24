package no.ntnu.kacperln.wargames.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import no.ntnu.kacperln.wargames.logic.TerrainType;
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
    unit.setCurrentTerrain(TerrainType.PLAINS); // no extras
    assertEquals(1, unit.getResistBonus()); // 1 if not forest
    assertEquals(1, unit.getResistBonus());
  }

  @Test
  void testGetAttackBonus() {
    InfantryUnit unit = new InfantryUnit("ExampleName", 100, 12, 5);
    unit.setCurrentTerrain(TerrainType.PLAINS); // no extras
    assertEquals(2, unit.getAttackBonus()); // 2 if not forest
    assertEquals(2, unit.getAttackBonus());
  }

  @Test
  void testGetResistBonusForest() {
    InfantryUnit unit = new InfantryUnit("ExampleName", 100, 12, 5);
    unit.setCurrentTerrain(TerrainType.FOREST);
    assertEquals(3, unit.getResistBonus()); // 1+2=3 if forest
    assertEquals(3, unit.getResistBonus());
  }

  @Test
  void testGetAttackBonusForest() {
    InfantryUnit unit = new InfantryUnit("ExampleName", 100, 12, 5);
    unit.setCurrentTerrain(TerrainType.FOREST);
    assertEquals(4, unit.getAttackBonus()); // 2+2=4 if forest
    assertEquals(4, unit.getAttackBonus());
  }

  @Test
  void testCopy() {
    InfantryUnit unit = new InfantryUnit("ExampleName", 100, 12, 5);
    InfantryUnit copy = unit.copy();
    assertEquals(unit, copy);
    assertNotSame(unit, copy);
  }

}
