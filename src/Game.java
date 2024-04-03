public class Game {

  public void run() {
    System.out.println("Welcome to Minesweeper!");

    int boardSize = -1;
    do{
      System.out.println("Please enter a board size between 5 and 9");
      boardSize = InputHelper.getIntInput();
    }while(boardSize < 5 || boardSize > 9);

    Board board = new Board(boardSize);
    board.generateBoard();
    board.updateBoardNumbers();

    boolean running = true;
    // Placeholder while true
    while (running) {
      board.printBoard();

      int col = -1;
      int row = -1;
      do{
        System.out.println("Enter the column of the square you would like to select");
        col = InputHelper.getIntInput();
        System.out.println("Enter the row of the square you would like to select");
        row = InputHelper.getIntInput();
      } while (!isWithinBounds(col, boardSize) || !isWithinBounds(row, boardSize));

      board.revealSquares(col, row);
    }
  }

  private boolean isWithinBounds(int val, int maxValExclusive) {
    return (val >= 0 && val < maxValExclusive);
  }

}
