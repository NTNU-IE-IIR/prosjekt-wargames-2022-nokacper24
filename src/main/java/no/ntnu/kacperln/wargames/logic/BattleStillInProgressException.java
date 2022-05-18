package no.ntnu.kacperln.wargames.logic;

/**
 * BattleStillInProgressException is to be thrown when
 * getWinner() method in Battle class is called when battle
 * is still in progress, i.e. both armies have some units.
 */
public class BattleStillInProgressException extends RuntimeException {

  /**
   * Constructor for BattleStillInProgressException with null as message.
   */
  public BattleStillInProgressException() {
    super();
  }

  /**
   * Constructor for BattleStillInProgressException with provided message.
   *
   * @param message the message with details
   */
  public BattleStillInProgressException(String message) {
    super(message);
  }
}
