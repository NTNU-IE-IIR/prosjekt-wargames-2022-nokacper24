package no.ntnu.idata2001;

/**
 * This abstract class represents a single unit in the Wargames app.
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
   * Calculates and sets health of the unit attacked by this unit.
   *
   * @param opponent unit to be attacked
   */
  public void attack(Unit opponent) {
    opponent.health = opponent.health - (this.attack + this.getAttackBonus())
        + (opponent.armor + opponent.getResistBonus());
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

}
