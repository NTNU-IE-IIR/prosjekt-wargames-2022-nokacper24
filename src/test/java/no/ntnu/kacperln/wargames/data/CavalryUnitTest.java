package no.ntnu.kacperln.wargames.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import no.ntnu.kacperln.wargames.logic.TerrainType;
import org.junit.jupiter.api.Test;

class CavalryUnitTest {

  public CavalryUnitTest() {
  }

  @Test
  void testCreationOfInstance() {
    CavalryUnit unit = new CavalryUnit("ExampleName", 100, 12, 5);

    assertEquals("ExampleName", unit.getName());
    assertEquals(100, unit.getHealth());
    assertEquals(12, unit.getAttack());
    assertEquals(5, unit.getArmor());
  }

  @Test
  void testGetResistBonus() {
    CavalryUnit unit = new CavalryUnit("ExampleName", 100, 12, 5);
    unit.setCurrentTerrain(TerrainType.HILL); // HILL, no extras
    assertEquals(1, unit.getResistBonus()); // always 1
    assertEquals(1, unit.getResistBonus());
  }

  @Test
  void testGetAttackBonus() {
    CavalryUnit unit = new CavalryUnit("ExampleName", 100, 12, 5);
    unit.setCurrentTerrain(TerrainType.HILL); // HILL, default
    assertEquals(6, unit.getAttackBonus()); // first attack 6
    assertEquals(2, unit.getAttackBonus()); // later only 2
    assertEquals(2, unit.getAttackBonus());
  }

  @Test
  void testGetResistBonusForest() {
    CavalryUnit unit = new CavalryUnit("ExampleName", 100, 12, 5);
    unit.setCurrentTerrain(TerrainType.FOREST); // FOREST, resist = 0
    assertEquals(0, unit.getResistBonus());
    assertEquals(0, unit.getResistBonus());
  }

  @Test
  void testGetAttackBonusPlains() {
    CavalryUnit unit = new CavalryUnit("ExampleName", 100, 12, 5);
    unit.setCurrentTerrain(TerrainType.PLAINS); // PLAINS, advantage
    assertEquals(8, unit.getAttackBonus()); // first attack 6 + 2 = 8
    assertEquals(4, unit.getAttackBonus()); // later only 2 + 2 = 4
    assertEquals(4, unit.getAttackBonus()); // 2 + 2 = 4
  }

  @Test
  void testCopy() {
    CavalryUnit unit = new CavalryUnit("ExampleName", 100, 12, 5);
    CavalryUnit copy = unit.copy();
    assertEquals(unit, copy);
    assertNotSame(unit, copy);
  }

}
