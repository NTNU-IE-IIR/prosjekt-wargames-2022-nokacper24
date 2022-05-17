package no.ntnu.idata2001.wargames.data;

import java.util.Objects;

/**
 * Abstract class representing a single unit in the Wargames app.
 *
 * @author Kacper L. Nowicki
 * @version 20.02.2022
 */
public abstract class Unit {



  /**
   * Enum for all unit types.
   * When adding a new unit type make sure to add new enum.
   */
  public enum UnitType {
    INFANTRY,
    CAVALRY,
    RANGED,
    COMMANDER;
  }
  private String name;

  private int health;
  private final int attack;
  private final int armor;
  /**
   * Constructor of the abstract class Unit.
   *
   * @param name   Name of the unit
   * @param health Initial health of the unit
   * @param attack Base attack damage
   * @param armor  Base defence value
   * @throws IllegalArgumentException when name empty or null,
   *                                  health equal or less than 0,
   *                                  attack or armor equal or less than 0
   */
  protected Unit(String name, int health, int attack, int armor) {
    this.setName(name);
    if (health <= 0) {
      throw new IllegalArgumentException("Health cannot be equal or less than 0.");
    }
    if (attack < 0 || armor < 0) {
      throw new IllegalArgumentException("Neither attack or armor can be less than 0.");
    }

    this.health = health;
    this.attack = attack;
    this.armor = armor;
  }

  /**
   * Constructor for copy of Unit.
   *
   * @param unit to be copied
   */
  protected Unit(Unit unit) {
    this.name = unit.name;
    this.health = unit.health;
    this.attack = unit.attack;
    this.armor = unit.armor;
  }


  /**
   * Returns a copy of the unit object.
   *
   * @return copy of the unit object
   */
  public abstract Unit copy();

  /**
   * Sets the name of the unit.
   *
   * @parm name of the unit
   * @throws IllegalArgumentException when name empty or null
   */
  public void setName(String name) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Name cannot be empty.");
    } else {
      this.name = name;
    }
  }

  /**
   * Returns name of the unit.
   *
   * @return name
   */
  public String getName() {
    return this.name;
  }

  /**
   * Returns current health of the unit.
   *
   * @return health
   */
  public int getHealth() {
    return this.health;
  }

  /**
   * Returns attack damage.
   *
   * @return attack
   */
  public int getAttack() {
    return attack;
  }

  /**
   * Returns armor value of the unit.
   *
   * @return armor
   */
  public int getArmor() {
    return this.armor;
  }

  /**
   * Sets health of the unit.
   * If parameter is negative, health is set to 0.
   *
   * @param health to be set
   */
  public void setHealth(int health) {
    this.health = health < 0 ? 0 : health;
  }

  /**
   * Returns whether unit is alive or not.
   * True if health is greater than 0.
   *
   * @return true if alive, false if not
   */
  public boolean isAlive() {
    return this.health > 0;
  }

  /**
   * Attacks given unit.
   *
   * @param opponent unit to be attacked
   */
  public void attack(Unit opponent) {
    int attackDamage = this.attack + this.getAttackBonus();
    opponent.takeDamage(attackDamage);
  }

  /**
   * Reduce unit's health, consider armor and resistBonus.
   *
   * @param attackDamage to take
   */
  public void takeDamage(int attackDamage) {
    int damageToTake = attackDamage - this.armor - this.getResistBonus();

    if (damageToTake > 0) {
      this.reduceHealthBy(damageToTake);
    }
  }

  /**
   * Reduces unit's health by given amount.
   *
   * @param healthPoints to be reduced
   */
  private void reduceHealthBy(int healthPoints) {
    int newHealth = this.health - healthPoints;
    this.setHealth(newHealth);
  }

  /**
   * Return attack bonus of the unit.
   *
   * @return attack bonus
   */
  abstract int getAttackBonus();

  /**
   * Returns resist bonus of the unit.
   *
   * @return resist bonus
   */
  abstract int getResistBonus();

  /**
   * Returns type of the unit.
   *
   * @return UnitType enum
   */
  public abstract UnitType getUnitType();

  /**
   * Returns unit's fields in form of a string.
   *
   * @return name, health, attack and armor of the unit
   */
  @Override
  public String toString() {
    return this.name + "\n Health " + this.health
        + "\n Attack " + this.attack + "\n Armor " + this.armor;
  }

  /**
   * Test if two Units are equal.
   *
   * @param o object to be compared to this
   * @return true if units are equal
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Unit unit = (Unit) o;
    return health == unit.health && attack == unit.attack && armor == unit.armor &&
        name.equals(unit.name);
  }

  /**
   * Returns a hashCode of the object.
   *
   * @return a hashcode for Unit object
   */
  @Override
  public int hashCode() {
    return Objects.hash(name, health, attack, armor);
  }
}
