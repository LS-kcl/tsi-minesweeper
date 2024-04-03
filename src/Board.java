import java.util.Arrays;
import java.util.Random;

public class Board {
  private int size;
  private int numOfMines;
  private Square[][] board;
  private Random rng = new Random();

  public Board(int size) {
    this.size = size;

    // For now, mines take up 20% of board space (i.e. 1/5 squares)
    this.numOfMines = size * size / 5;

    // Initialise board and fill with default square values
    board = new Square[size][size];
    for (int col=0; col<size; col++){
      for (int row=0; row < size; row++) {
        board[col][row] = new Square();
      }
    }
  }

  public void printBoard() {
    // Print first header:
    System.out.print("  ");
    for (int i = 0; i < size; i++) {
      System.out.print(i + " ");
    }
    System.out.println(""); // New line to start
    
    for (int col=0; col<size; col++){
      System.out.print(col + " ");
      for (int row=0; row < size; row++) {
        System.out.print(board[row][col].getVisible() + " ");
      }
      System.out.println(""); // New line for next row
    }
  }

  public void generateBoard() {
    int minesLeft = numOfMines;

    while (minesLeft > 0) {
      // Generate random location for a mine
      int randRow = rng.nextInt(size);
      int randCol = rng.nextInt(size);
      
      Square square = board[randRow][randCol];
      // Only place mine if no existing mine
      if (!square.getMine()){
        square.setMine(true);
        minesLeft--;
      }
    }
    
  }

  /*
   * Checks if square is a mine, and returns true if it is
   */
  public boolean checkSquare(int row, int col) {
    Square square = board[row][col];
    
    // Do nothing if flagged
    if (square.getFlagged()){
      System.out.println("You cannot check a square you have flagged!");
      return false;
    }

    // Otherwise check and reveal
    return square.checkSquare();
  }

  /*
   * Moves the mine that would be hit to another square
   */
  public void protectedCheck(int row, int col) {
    Square square = board[row][col];

    // If would reveal a mine
    if (square.getMine()){
      boolean minePlaced = false;

      while (!minePlaced) {
        // Generate random location for a mine
        int randRow = rng.nextInt(size);
        int randCol = rng.nextInt(size);
        
        Square moveTo = board[randRow][randCol];
        // Only place mine if no existing mine
        if (!moveTo.getMine()){
          square.setMine(true);
          minePlaced = true;
        }
      }

    }

    // Perform original reveal
    revealSquares(row, col);
    
  }

  /*
   * Will reveal the current square, and then cascade try
   * to cascade to other cells nearby
   */
  public void revealSquares(int row, int col) {
    Square square = board[row][col];
    // Base cases, we stop if:
    // square is already revealed
    // square is a mine
    // square is a number
    // square is flagged
    if (square.getRevealed() || square.getMine() || square.getMinesAround() > 0 || square.getFlagged()){
      return;
    }

    // Reveal square
    square.revealSquare();

    // Recursively run on adjacent squares for "waterfall effect"
    for (int i = col-1; i <= col+1; i++) {
      // Check bounds
      if (i >= 0 && i < size){
        for (int j = row-1; j < row+1; j++) {
          if (j >= 0 && j < size){
            revealSquares(i, j);
          }
        }
      }
    }
  }

  public void flagSquare(int row, int col) {
    Square square = board[row][col];
    square.toggleFlag();
  }
}
