package no.ntnu.kacperln.wargames.logic;

/**
 * IllegalUnitsFileException is to be thrown when
 * something went wrong while loading file that should contain units,
 * for example corrupt or incorrect file.
 * <p>
 * Possible issues that could trigger a throw: Incorrect file format, no units in the file,
 * when details for unit creation are illegal (incorrect type, blank name or negative health)
 */
public class IllegalUnitsFileException extends Exception {

  /**
   * Constructor for IllegalUnitsFileException with null as message.
   */
  public IllegalUnitsFileException() {
    super();
  }

  /**
   * Constructor for IllegalUnitsFileException with provided message.
   *
   * @param message the message with details
   */
  public IllegalUnitsFileException(String message) {
    super(message);
  }
}
