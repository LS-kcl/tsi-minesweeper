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

    // While there remains squares to be found
    while (board.squaresRemaining() > 0) {
      board.printBoard();
      board.printSquaresRemaining();
      board.printDebuggingBoard();

      int col = -1;
      int row = -1;
      do{
        System.out.println("Enter the column of the square you would like to select");
        col = InputHelper.getIntInput();
        System.out.println("Enter the row of the square you would like to select");
        row = InputHelper.getIntInput();
      } while (!isWithinBounds(col, boardSize) || !isWithinBounds(row, boardSize));

      String option = "";
      do{
        System.out.println("What would you like to do for square "
          + "col: " + col + ", "
          + "row: " + row + "?"
        );
        System.out.println("F: Flag the square");
        System.out.println("R: Reveal the square");
        option = InputHelper.getStringInput();
      } while (!option.equals("F") && !option.equals("R"));

      switch (option) {
        case "F":
          board.flagSquare(row, col);
          break;

        case "R":
          board.revealSquares(row, col);
          break;

        default:
          System.out.println("Invalid input");
          break;
      }
    }

    System.out.println("You won!");
  }

  private boolean isWithinBounds(int val, int maxValExclusive) {
    return (val >= 0 && val < maxValExclusive);
  }

}
