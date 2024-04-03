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
    board.printBoard();
  }

}
