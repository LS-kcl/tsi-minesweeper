public class Game {

  public void run() {
    System.out.println("Welcome to Minesweeper!");
    System.out.println("Please enter a board size between 5 and 9");
    System.out.println("User input: " + InputHelper.stringInput());
  }

}
