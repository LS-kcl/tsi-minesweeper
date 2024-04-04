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
- Board is generated
- User selects first square
- Game runs protected check to ensure they do not lose first choice
- Main loop of game begins:
    - Board is printed
    - User prompted to select square
    - Board checks that square is valid
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
Game:
- Attributes:
    - board : Board
- Methods
    - runGame() : void

Board:
- Attributes:
    - size: int
    - numOfMines: int
    - squares: Array<Square>[][]
- Methods:
    - selectSquare() : void
    - printBoard() : void
    - generateBoard() : void
    - checkSquare() : boolean
    - protectedReveal() : void
    - flagSquare() : void
    - revealSquares() : void

Square:
- Attributes:
    - flagged: boolean
    - isMine: boolean
    - revealed: boolean
    - minesAround: int
- Methods:
    - toggleFlag() : void
    - checkSquare() : boolean
    - peekMine() : boolean
