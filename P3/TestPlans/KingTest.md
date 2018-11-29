#### TP2 KingTests

King is a piece in the game, it can only move to valid locations, and if it reaches the other end of
the board the game is over. 

### List of Tests
1. Test the validMoves at different locations

## Test Case ID

#### TC1

#### Test Case Summary
The King will return a list of all valid moves based on surrounding pieces. 

#### Variations
1. Valid/Invalid Moves
2. Checkmate possible
3. Game winning move

#### Test Procedure
1. Create a king object with a given location and color
2. Call validMoves with the current state of the game
3. Check if a move exists where game will be won

#### Expected Result
The validMoves will return an ArrayList with all the possible moves
