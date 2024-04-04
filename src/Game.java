

public class Game {
  Board board;
  int boardSize;
  boolean hitMine = false;
  boolean firstMoveMade = false;

  public void run() {
    System.out.println("Welcome to Minesweeper!");
    initialiseBoard();

    // While there remains squares to be found
    while (board.squaresRemaining() > 0 && hitMine == false) {
      board.printBoard();
      board.printSquaresRemaining();
      board.printDebuggingBoard();

      Coord coords = takeRowAndColInput();
      performAction(coords);

    }

    if (!hitMine){
      System.out.println("You won!");
    } else {
      System.out.println("You lost, play again?");
    }
  }

  /*
   * Perform some action on coordinates
   */
  public void performAction(Coord c) {
      String option = "";
      do{
        System.out.printf("What would you like to do for square (%s,%s)%n", c.row, c.col);
        System.out.println("F: Flag the square");
        System.out.println("R: Reveal the square");
        option = InputHelper.getStringInput();
      } while (!option.equals("F") && !option.equals("R"));

      switch (option) {
        case "F":
          board.flagSquare(c.row, c.col);
          break;

        case "R":
          // If first move not made
          if (!firstMoveMade){
            board.safeRevealSquares(c.row, c.col);
            firstMoveMade = true;
          } else {
            hitMine = board.revealSquares(c.row, c.col);
            if (hitMine){
              System.out.printf("There was a mine at (%s,%s)%n", c.row, c.col);
            }
          }
          break;

        default:
          System.out.println("Invalid input");
          break;
      }
  }

  public Coord takeRowAndColInput() {
      int row = -1;
      int col = -1;
      do{
        System.out.println("Enter the row of the square you would like to select");
        row = InputHelper.getIntInput();
        System.out.println("Enter the column of the square you would like to select");
        col = InputHelper.getIntInput();
      } while (!isWithinBounds(col, boardSize) || !isWithinBounds(row, boardSize));

    return new Coord(row, col);
  }

  public void initialiseBoard() {
    do{
      System.out.println("Please enter a board size between 5 and 9");
      boardSize = InputHelper.getIntInput();
    }while(boardSize < 5 || boardSize > 9);

    board = new Board(boardSize);
    board.generateBoard();
    board.updateBoardNumbers();
  }

  private boolean isWithinBounds(int val, int maxValExclusive) {
    return (val >= 0 && val < maxValExclusive);
  }

}
