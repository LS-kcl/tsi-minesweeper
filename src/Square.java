public class Square {
  private boolean flagged;
  private boolean isMine;
  private boolean revealed;
  private int minesAround;

  public Square() {
    // Initialise to defaults
    flagged = false;
    isMine = false;
    revealed = false;
    minesAround = 0;
  }

  public void toggleFlag() {
    flagged = !flagged;
  }

  /*
   * Reveals a square and returns if it was a mine
   */
  public boolean checkSquare() {
    // Do not reveal if flagged
    if (flagged) {
      return false;
    }
    revealed = true;
    return isMine;
  }

  public boolean getMine() {
    return isMine;
  }

  public boolean getRevealed() {
    return revealed;
  }

  public void revealSquare() {
    revealed = true;
  }

  public int getMinesAround() {
    return minesAround;
  }

  public void setMinesAround(int val) {
    minesAround = val;
  }

  public boolean getFlagged() {
    return flagged;
  }

  /*
   * A method to return a command line
   * indication of what it looks like on the
   * board
   */
  public String getVisible() {
    if (flagged) {
      return "F";
    }

    // Mine always returns as blank
    if (isMine){
      return "X";
    }

    if (revealed){
      return "" + minesAround;
    }

    // All else fails (not revealed or flagged)
    return "X";
  }

  /*
   * A method to return a debugging string of what the 
   * square looks like
   */
  public String getDebuggingVisible() {
    // Mine always returns as blank
    if (isMine){
      return "M";
    }

    if (flagged) {
      return "F";
    }

    return "" + minesAround;

  }

  public void setMine(boolean value) {
    isMine = value;
  }
}
