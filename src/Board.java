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
   * Goes through all squares on the board, if mine found, increment number
   * of all squares around it
   */
  public void updateBoardNumbers() {
    // Loop through all squares
    for (int col=0; col<size; col++){
      for (int row=0; row < size; row++) {
        // If the square has a mine
        if (board[col][row].getMine()){
          // increment number values of all adjacent squares
          for (int i = col-1; i <= col+1; i++) {
            // Check bounds
            if (i >= 0 && i < size){
              for (int j = row-1; j <= row+1; j++) {
                if (j >= 0 && j < size){
                  int prevVal = board[i][j].getMinesAround();
                  board[i][j].setMinesAround(prevVal+1);
                }
              }
            }
          }
        }
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
   * Will reveal the current square, then call the cascading reveals
   */
  public void revealSquares(int row, int col) {
    Square square = board[row][col];
    
    // Reveal square
    square.revealSquare();

    // Recursively run on adjacent squares for "waterfall effect"
    for (int i = col-1; i <= col+1; i++) {
      // Check bounds
      if (i >= 0 && i < size){
        for (int j = row-1; j <= row+1; j++) {
          if (j >= 0 && j < size){
            revealCascade(i, j);
          }
        }
      }
    }
  }

  /*
   * Will reveal the current square, and then cascade try
   * to cascade to other cells nearby
   */
  public void revealCascade(int row, int col) {
    Square square = board[row][col];
    // Base cases, we stop if:
    // square is already revealed
    // square is a mine
    // square is a number
    // square is flagged
    if (square.getRevealed() || square.getMinesAround() > 0 || square.getMine() || square.getFlagged()){
      return;
    }

    // Reveal square
    square.revealSquare();

    // Recursively run on adjacent squares for "waterfall effect"
    for (int i = col-1; i <= col+1; i++) {
      // Check bounds
      if (i >= 0 && i < size){
        for (int j = row-1; j <= row+1; j++) {
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
