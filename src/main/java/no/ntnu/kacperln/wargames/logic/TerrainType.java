package no.ntnu.kacperln.wargames.logic;

/**
 * Enum for terrain types.
 */
public enum TerrainType {
  /**
   * Hill terrain type.
   */
  HILL,
  /**
   * Plains terrain type.
   */
  PLAINS,
  /**
   * Forest terrain type.
   */
  FOREST;

  /**
   * Returns the TerrainType in form of a string, all lowercase.
   *
   * @return TerrainType in lowercase
   */
  @Override
  public String toString() {
    return this.name().toLowerCase();
  }
}
