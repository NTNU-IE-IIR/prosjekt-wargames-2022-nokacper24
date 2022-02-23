package no.ntnu.idata2001.wargames.data;

/**
 * Abstract class representing a single unit in the Wargames app.
 *
 * @author Kacper L. Nowicki
 * @version 20.02.2022
 */
public abstract class Unit {

  private final String name;
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
   */
  protected Unit(String name, int health, int attack, int armor) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Name cannot be empty.");
    }
    if (health <= 0) {
      throw new IllegalArgumentException("Health cannot be equal or less than 0.");
    }
    if (attack < 0 || armor < 0) {
      throw new IllegalArgumentException("Neither attack or armor can be less than 0.");
    }

    this.name = name;
    this.health = health;
    this.attack = attack;
    this.armor = armor;
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
  public void reduceHealthBy(int healthPoints) {
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
   * Returns unit's fields in form of a string.
   *
   * @return name, health, attack and armor of the unit
   */
  @Override
  public String toString() {
    return this.name + "\n Health " + this.health
        + "\n Attack " + this.attack + "\n Armor " + this.armor;
  }

}
