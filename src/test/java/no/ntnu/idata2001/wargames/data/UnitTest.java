package no.ntnu.idata2001.wargames.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class UnitTest {

  public UnitTest() {
  }

  @Test
  void testCreationOfInstanceWithBlankName() {
    assertThrows(IllegalArgumentException.class, () ->
      new DummyUnit("",100,12,5));
  }

  @Test
  void testCreationOfInstanceWithBlankName2() {
    try {
      DummyUnit unit = new DummyUnit("",100,12,5);
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
  }

  @Test
  void testCreationOfInstanceWithNameNull() {
    assertThrows(IllegalArgumentException.class, () ->
        new DummyUnit(null,100,12,5));
  }

  @Test
  void testCreationOfInstanceWithZeroHealth() {
    assertThrows(IllegalArgumentException.class, () ->
        new DummyUnit("Name1",0,12,5));
  }

  @Test
  void testCreationOfInstanceWithNegativeAttack() {
    assertThrows(IllegalArgumentException.class, () ->
        new DummyUnit("Name1",100,-12,5));
  }

  @Test
  void testCreationOfInstanceWithNegativeArmor() {
    assertThrows(IllegalArgumentException.class, () ->
        new DummyUnit("Name1",100,12,-5));
  }

  @Test
  void testSetHealth() {
    Unit unit = new DummyUnit("ExampleName", 100);
    unit.setHealth(50);
    assertEquals(50, unit.getHealth());
  }

  @Test
  void testSetNegativeHealth() {
    Unit unit = new DummyUnit("ExampleName", 100);
    unit.setHealth(-50);
    assertEquals(0, unit.getHealth());
  }

  @Test
  void testAttack() {
    Unit unit1 = new DummyUnit("ExampleName", 100);
    Unit unit2 = new DummyUnit("ExampleName", 25);

    unit1.attack(unit2);
    assertEquals(19, unit2.getHealth()); // 25-(15+2)+10+1 = 19

    unit1.attack(unit2);
    assertEquals(13, unit2.getHealth()); // 19-(15+2)+10+1 = 13

    unit1.attack(unit2);
    assertEquals(7, unit2.getHealth()); // 13-(15+2)+10+1 = 7

    unit1.attack(unit2);
    assertEquals(1, unit2.getHealth()); // 7-(15+2)+10+1 = 1

    unit1.attack(unit2);
    assertEquals(0, unit2.getHealth()); //1-(15+2)+10+1 = -5 --> sets to 0

  }
}
