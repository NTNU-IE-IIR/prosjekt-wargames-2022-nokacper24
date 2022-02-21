package no.ntnu.idata2001.data;

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
    // TODO exceptions
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
   * Calculates and sets health of the unit attacked by this unit.
   *
   * @param opponent unit to be attacked
   */
  public void attack(Unit opponent) {
    int newHealth = opponent.getHealth() - (this.attack + this.getAttackBonus())
        + (opponent.getArmor() + opponent.getResistBonus());

    opponent.setHealth(newHealth);
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
