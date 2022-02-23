package no.ntnu.idata2001.wargames.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import no.ntnu.idata2001.wargames.data.CavalryUnit;
import no.ntnu.idata2001.wargames.data.InfantryUnit;
import no.ntnu.idata2001.wargames.data.Unit;
import org.junit.jupiter.api.Test;

class UnitTest {

  public UnitTest() {
  }

  @Test
  void testCreationOfInstanceWithBlankName() {
    assertThrows(IllegalArgumentException.class, () ->
      new InfantryUnit("",100,12,5));
  }

  @Test
  void testCreationOfInstanceWithBlankName2() {
    try {
      InfantryUnit unit = new InfantryUnit("",100,12,5);
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
  }

  @Test
  void testCreationOfInstanceWithNameNull() {
    assertThrows(IllegalArgumentException.class, () ->
        new InfantryUnit(null,100,12,5));
  }

  @Test
  void testCreationOfInstanceWithZeroHealth() {
    assertThrows(IllegalArgumentException.class, () ->
        new InfantryUnit("Name1",0,12,5));
  }

  @Test
  void testCreationOfInstanceWithNegativeAttack() {
    assertThrows(IllegalArgumentException.class, () ->
        new InfantryUnit("Name1",100,-12,5));
  }

  @Test
  void testCreationOfInstanceWithNegativeArmor() {
    assertThrows(IllegalArgumentException.class, () ->
        new InfantryUnit("Name1",100,12,-5));
  }

  @Test
  void testSetHealth() {
    Unit unit = new CavalryUnit("ExampleName", 100);
    unit.setHealth(50);
    assertEquals(50, unit.getHealth());
  }

  @Test
  void testSetNegativeHealth() {
    Unit unit = new CavalryUnit("ExampleName", 100);
    unit.setHealth(-50);
    assertEquals(0, unit.getHealth());
  }

  @Test
  void testAttack() {
    Unit unit1 = new CavalryUnit("ExampleName", 100);
    Unit unit2 = new InfantryUnit("ExampleName", 50);

    unit1.attack(unit2);
    assertEquals(35, unit2.getHealth()); // 50-(20+6)+10+1 = 35

    unit1.attack(unit2);
    assertEquals(24, unit2.getHealth()); // 35-(20+2)+10+1 = 24

    unit1.attack(unit2);
    assertEquals(13, unit2.getHealth()); // 24-(20+2)+10+1 = 13

    unit1.attack(unit2);
    assertEquals(2, unit2.getHealth()); // 13-(20+2)+10+1 = 2

    unit1.attack(unit2);
    assertEquals(0, unit2.getHealth()); // 2-(20+2)+10+1 = -9 --> sets to 0

  }
}
