package com.kclls.github;

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

      System.out.println("### DEBUGGING BOARD ###");
      board.printBoardSolution();

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
              board.printBoardSolution();
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
      boolean validInput = false;
      do{
        System.out.println("Enter row of the square to select");
        row = InputHelper.getIntInput();
        System.out.println("Enter column of the square to select");
        col = InputHelper.getIntInput();

        if (!isWithinBounds(col, boardSize) || !isWithinBounds(row, boardSize)){
          System.out.println("");
        }else{
          validInput = true;
        }
      } while (!validInput);

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
