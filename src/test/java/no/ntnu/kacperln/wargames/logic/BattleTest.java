package no.ntnu.kacperln.wargames.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import no.ntnu.kacperln.wargames.data.Army;
import no.ntnu.kacperln.wargames.data.CavalryUnit;
import no.ntnu.kacperln.wargames.data.CommanderUnit;
import no.ntnu.kacperln.wargames.data.InfantryUnit;
import no.ntnu.kacperln.wargames.data.RangedUnit;
import no.ntnu.kacperln.wargames.data.Unit;
import org.junit.jupiter.api.Test;

class BattleTest {

  public BattleTest() {
  }

  @Test
  void testCreationOfInstance(){
    Army army1 = new Army("Army1");
    Army army2 = new Army("Army2");
    Battle battle = new Battle(army1, army2, TerrainType.values()[0]);

    assertEquals(army1,battle.getArmyOne());
    assertEquals(army2,battle.getArmyTwo());
  }

  @Test
  void testIsInProgress() {
    ArrayList<Unit> collectionOfUnits1 = new ArrayList<>();
    Unit unit1 = new CavalryUnit("name1", 100);
    Unit unit2 = new RangedUnit("name2", 120);
    collectionOfUnits1.add(unit1);
    collectionOfUnits1.add(unit2);

    ArrayList<Unit> collectionOfUnits2 = new ArrayList<>();
    Unit unit3 = new CommanderUnit("name3", 90);
    Unit unit4 = new InfantryUnit("name4", 70);
    collectionOfUnits2.add(unit3);
    collectionOfUnits2.add(unit4);

    Army army1 = new Army("Army1", collectionOfUnits1);
    Army army2 = new Army("Army2", collectionOfUnits2);
    Battle battle = new Battle(army1, army2, TerrainType.values()[0]);

    assertTrue(battle.isInProgress());
  }

  @Test
  void testIsInProgressEmpty() {
    ArrayList<Unit> collectionOfUnits1 = new ArrayList<>();
    ArrayList<Unit> collectionOfUnits2 = new ArrayList<>();

    Army army1 = new Army("Army1", collectionOfUnits1);
    Army army2 = new Army("Army2", collectionOfUnits2);
    Battle battle = new Battle(army1, army2, TerrainType.values()[0]);

    assertFalse(battle.isInProgress());
  }

  @Test
  void testSimulate() {
    ArrayList<Unit> collectionOfUnits1 = new ArrayList<>();
    Unit unit1 = new CavalryUnit("name1", 100);
    Unit unit2 = new RangedUnit("name2", 120);
    collectionOfUnits1.add(unit1);
    collectionOfUnits1.add(unit2);

    ArrayList<Unit> collectionOfUnits2 = new ArrayList<>();
    Unit unit3 = new CommanderUnit("name3", 10);
    Unit unit4 = new InfantryUnit("name4", 10);
    collectionOfUnits2.add(unit3);
    collectionOfUnits2.add(unit4);

    Army army1 = new Army("Army1", collectionOfUnits1);
    Army army2 = new Army("Army2", collectionOfUnits2);

    Battle battle = new Battle(army1, army2, TerrainType.values()[0]);

    assertNotNull(battle.simulate());
  }

  @Test
  void testSimulateDraw() {
    ArrayList<Unit> collectionOfUnits1 = new ArrayList<>();
    Unit unit1 = new CavalryUnit("name1", 1);
    collectionOfUnits1.add(unit1);

    ArrayList<Unit> collectionOfUnits2 = new ArrayList<>();
    Unit unit2 = new CommanderUnit("name3", 1);
    collectionOfUnits2.add(unit2);

    Army army1 = new Army("Army1", collectionOfUnits1);
    Army army2 = new Army("Army2", collectionOfUnits2);

    Battle battle = new Battle(army1, army2, TerrainType.values()[0]);

    assertNull(battle.simulate());
  }

  @Test
  void testGetWInner() {
    ArrayList<Unit> collectionOfUnits1 = new ArrayList<>();
    Unit unit1 = new CavalryUnit("name1", 100);
    Unit unit2 = new RangedUnit("name2", 120);
    collectionOfUnits1.add(unit1);
    collectionOfUnits1.add(unit2);

    ArrayList<Unit> collectionOfUnits2 = new ArrayList<>();
    Unit unit3 = new CommanderUnit("name3", 10);
    Unit unit4 = new InfantryUnit("name4", 10);
    collectionOfUnits2.add(unit3);
    collectionOfUnits2.add(unit4);

    Army army1 = new Army("Army1", collectionOfUnits1);
    Army army2 = new Army("Army2", collectionOfUnits2);

    Battle battle = new Battle(army1, army2, TerrainType.values()[0]);
    battle.simulate();

    assertEquals(army1, battle.getWinner());
  }

  @Test
  void testGetWinnerBattleInProgress() {
    ArrayList<Unit> collectionOfUnits1 = new ArrayList<>();
    Unit unit1 = new CavalryUnit("name1", 100);
    collectionOfUnits1.add(unit1);

    ArrayList<Unit> collectionOfUnits2 = new ArrayList<>();
    Unit unit2 = new CommanderUnit("name2", 100);
    collectionOfUnits2.add(unit2);

    Army army1 = new Army("Army1", collectionOfUnits1);
    Army army2 = new Army("Army2", collectionOfUnits2);

    Battle battle = new Battle(army1, army2, TerrainType.values()[0]);
    battle.simulateStep();

    assertThrows(BattleStillInProgressException.class, () -> battle.getWinner());
  }
}
