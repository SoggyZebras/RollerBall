#### TP1 BishopTests

Bishop is a piece of the game, where it is able to make moves to specific locations on the board, including bouncing off the edges 


### List of Tests
1. Test the validMoves method for each quadrant 

### Test Case ID

#### TC1

#### Test Case Summary
The Bishop is able to give a list of valid moves based upon the given quadrant. 

#### Variations
1. Quadrants 1-4
2. Valid/Invalid Moves

#### Test Procedure
1. Create a Bishop object for a given location and color 
2. Call validMoves with the current state of the game

#### Expected Result
The validMoves will return an ArrayList with all the possible moves
