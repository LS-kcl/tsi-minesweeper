# A minesweeper implementation

## Command line interface

## GUI interface
*To be implemented once command line interface MVP is completed* 

## Project Structure Draft
### Features to implement
- Cannot lose on first click
- Flagging
- Wave of empty non-number spaces revealed on selection
- 

### How to implement this

#### A sample flow through the program
- User starts up the game
- User is prompted with board size
- Program checks board size is within valid bounds
- Number of mines is calculated as a factor of board size
- com.kclls.github.Board is generated
- User selects first square
- com.kclls.github.Game runs protected check to ensure they do not lose first choice
- com.kclls.github.Main loop of game begins:
    - com.kclls.github.Board is printed
    - User prompted to select square
    - com.kclls.github.Board checks that square is valid
    - If square is valid, user is prompted "C" to check square, and "F" to flag
        - Case 1: invalid input, user is sent back to dialog above
        - Case 2: User selects "C", check if mine using checkSquare(), board reveals or user loses
        - Case 3: User selects "F", flag is toggled on chosen square

#### Cascade rules:
This is implemented using a recursive function:
- Reveal first square
- Check all squares around it for "0"s (i.e. no surrounding mines)
- If there's a zero around, reveal all squares around the zero that are not mines, if the revealed square is also a zero, recursively call on that zero square
    - recursive call of 
- Base case is when it's called on a non "0", it reveals the square and returns

#### Classes
com.kclls.github.Game:
- Attributes:
    - board : com.kclls.github.Board
- Methods
    - runGame() : void

com.kclls.github.Board:
- Attributes:
    - size: int
    - numOfMines: int
    - squares: Array<com.kclls.github.Square>[][]
- Methods:
    - selectSquare() : void
    - printBoard() : void
    - generateBoard() : void
    - checkSquare() : boolean
    - protectedReveal() : void
    - flagSquare() : void
    - revealSquares() : void

com.kclls.github.Square:
- Attributes:
    - flagged: boolean
    - isMine: boolean
    - revealed: boolean
    - minesAround: int
- Methods:
    - toggleFlag() : void
    - checkSquare() : boolean
    - peekMine() : boolean
