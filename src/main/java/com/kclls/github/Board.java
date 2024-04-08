package com.kclls.github;

import java.util.ArrayList;
import java.util.Random;

public class Board {
  private int size;
  private int numOfMines;
  private Square[][] board;
  private Random rng = new Random();

  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_YELLOW = "\u001B[33m";
  public static final String ANSI_RESET = "\u001B[0m";

  public Board(int size) {
    this.size = size;

    // For now, mines take up 10% of board space (i.e. 1/10 squares)
    this.numOfMines = size * size / 10;

    // Initialise board and fill with default square values
    board = new Square[size][size];
    for (int col=0; col<size; col++){
      for (int row=0; row < size; row++) {
        board[row][col] = new Square();
      }
    }
  }

  public void printBoard() {
    // Print first header:
    System.out.print("  ");
    for (int i = 0; i < size; i++) {
      System.out.print(ANSI_YELLOW);
      System.out.print(i + " ");
      System.out.print(ANSI_RESET);
    }
    System.out.println(""); // New line to start
    
    for (int col=0; col<size; col++){
      System.out.print(ANSI_YELLOW);
      System.out.print(col + " ");
      System.out.print(ANSI_RESET);
      for (int row=0; row < size; row++) {
        System.out.print(board[col][row].getVisible() + " ");
      }
      System.out.println(""); // New line for next row
    }
  }

  public void printBoardSolution() {
    // Print first header:
    System.out.print("  ");
    for (int i = 0; i < size; i++) {
      System.out.print(ANSI_YELLOW);
      System.out.print(i + " ");
      System.out.print(ANSI_RESET);
    }
    System.out.println(""); // New line to start
    
    for (int col=0; col<size; col++){
      System.out.print(ANSI_YELLOW);
      System.out.print(col + " ");
      System.out.print(ANSI_RESET);
      for (int row=0; row < size; row++) {
        System.out.print(board[col][row].getDebuggingVisible() + " ");
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
        if (board[row][col].getMine()){
          // Get valid squares around this one
          ArrayList<Coord> coords = getValidCoords(row, col);

          for (Coord coord : coords){
            int prevVal = board[coord.row][coord.col].getMinesAround();
            board[coord.row][coord.col].setMinesAround(prevVal+1);
          }
        }
      }
    }
  }

  public void resetBoardNumbers() {
    for (Square[] nestedList : board){
      for (Square square : nestedList){
          // Reset the board number
          square.setMinesAround(0);
      }
    }
  }

  /*
   * Moves the mine that would be hit to another square
   */
  public void safeRevealSquares(int row, int col) {
    Square square = board[row][col];

    // If would reveal a mine
    if (square.getMine()){
      boolean minePlaced = false;

      while (!minePlaced) {
        // Generate random location for a mine
        int randRow = rng.nextInt(size);
        int randCol = rng.nextInt(size);
        
        Square moveTo = board[randCol][randRow];
        // Only place mine if no existing mine
        if (!moveTo.getMine()){
          square.setMine(false);
          moveTo.setMine(true);
          minePlaced = true;
        }
      }

      // Generate new set of numbers on the board
      resetBoardNumbers();
      updateBoardNumbers();
    }

    // Perform original reveal
    revealSquares(row, col);
    
  }

  /*
   * Will reveal the current square, then call the cascading reveals
   * returns true if mine revealed, else false
   */
  public boolean revealSquares(int row, int col) {
    Square square = board[row][col];
    
    // If it was a mine returned, don't cascade:
    if (square.getMine()) {
      return true;
    }

    // Don't reveal a flag
    if (square.getFlagged()) {
      System.out.println("You cannot check a square you have flagged!");
      return false; // False as no mine found
    }

    // Reveal square in cascade
    revealCascade(row, col);

    return false;

  }

  /*
   * Will reveal the current square, and then cascade try
   * to cascade to other cells nearby
   */
  private void revealCascade(int row, int col) {
    Square square = board[row][col];
    // Base cases, we stop if:
    // square is already revealed1
    // square is a mine
    // square is flagged

    // Do not execute on squares already revealed or flagged
    if (square.getRevealed() || square.getFlagged()){
      return;
    }

    // Reveal square
    square.revealSquare();

    // Get valid squares around this one
    ArrayList<Coord> coords = getValidCoords(row, col);

    for (Coord coord : coords){
      // Get relevant square
      Square nearbySquare = board[coord.row][coord.col];
      if (nearbySquare.getFlagged() || nearbySquare.getMine()){
        // do not reveal mines or flagged squares
        // do nothing
      }else if (nearbySquare.getMinesAround() == 0){
        // if no mines around, cascade around
        revealCascade(coord.row, coord.col);
      } else{
        // This is just a regular number
        // We reveal if the square is a number, but we don't cascade
        nearbySquare.revealSquare();
      }
    }
  }

  private ArrayList<Coord> getValidCoords(int row, int col) {
    int rowStart = Math.max(row-1, 0);
    int rowFinish = Math.min(row+1, size-1);
    int colStart = Math.max(col-1, 0);
    int colfinish = Math.min(col+1, size-1);

    ArrayList<Coord> validCoords = new ArrayList<>();
    for (int i = rowStart; i <= rowFinish; i++) {
        for (int j = colStart; j <= colfinish; j++) {
          validCoords.add(new Coord(i, j));
        }
    }
    return validCoords;
  }

  public void printSquaresRemaining(){
    System.out.println("There are " + squaresRemaining() + " squares left to find");
  }

  public int squaresRemaining(){
    int count = 0;
    for (Square[] nestedList : board){
      for (Square square : nestedList){
        // If square is not yet revealed, and is not a mine
        if (!square.getRevealed() && !square.getMine()){
          count++;
        }
      }
    }
    return count;
  }

  public void placeMine(int row, int col) {
    board[row][col].setMine(true);
  }

  public boolean getMine(int row, int col) {
    return board[row][col].getMine();
  }

  public int getMinesAround(int row, int col) {
    return board[row][col].getMinesAround();
  }

  public boolean getFlagged(int row, int col) {
    return board[row][col].getFlagged();
  }

  public boolean getRevealed(int row, int col) {
    return board[row][col].getRevealed();
  }
  
  public void flagSquare(int row, int col) {
    Square square = board[row][col];
    square.toggleFlag();
  }
}
