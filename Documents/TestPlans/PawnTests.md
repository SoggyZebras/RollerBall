#### PawnTests:

The pawn class encompasses 4 initial pawn pieces on the board 

### List of Tests
1. testQuads
2. testMoveBlankBoard
3. testFriendlyMove
4. testEnemyMove

## Test Case ID

#### TC1 - testQuads

#### Test Case Summary
This test makes sure that when the pawn pieces are initialized that they are in the correct quadrants

#### Test Procedure
1. Create 4 location objects, one for each quadrant (quadrants are numbered from 1 on the BOTTOM and then increasing clockwise until reaching 4 on the RIGHT)
2. Check wether or not the pawns are placed in the correct quadrants following the SetQuadrant() logic 

#### Expected Result
The pawn pieces will be created in the correct quadrants 


## Test Case ID

#### TC2 - testMoveBlankBoard

#### Test Case Summary
This test ensures that each piece moves the correct way for their quadrant and do not move to an invalid position

#### Test Procedure
1. Create location objects specific to the expected validMoves() result for each pawn piece quadrant move
2. Calculate the validMoves() array of locations for each pawn piece in their respective quadrant
3. Calculate the validMoves() array for the pawn that is in one of the 4 locations on the board that allows for 3 valid moves
4. Check the result of the validMoves() array

#### Expected Result
The returned vector of Location objects in validMove() for each piece will be correct and valid for the rollerball game board


## Test Case ID

#### TC3 - testFriendlyMove

#### Test Case Summary
This test ensures that a pawn piece will not move to, and thereby capture, a "friendly" piece of the same color by testing both validMoves() and checkFriendly() function(s) in pawn class

#### Test Procedure
1. Create location objects that will populate the hashMap of <location, piece> map for the board state
2. Create Piece object(s) with specified same color as pawn piece(s) in the test
3. Calculate validMoves() vector of valid Locations for the pawn piece in question
4. Ensure that the location that the friendly piece inhabits is not in the validMoves() vector list

#### Expected Result
The validMoves() vector list of allowed moves for the particular pawn piece being currently tests does not contain the Location position of the friendly piece

## Test Case ID

#### TC4 - testEnemyMove

#### Test Case Summary
This test ensures that a pawn piece will recognize an enemy piece inhabiting a currently valid move at the pawn current position and include this move in validMoves() list

#### Test Procedure
1. Create location objects that will populate the hashMap of <location, piece> map for the board state
2. Create Piece object(s) with specified different color as pawn piece(s) in the test
3. Calculate validMoves() vector of valid Locations for the pawn piece in question
4. Ensure that the location that the friendly piece inhabits is included in the validMoves() vector list, thereby allowing this pawn piece in question to 'capture' the enemy piece

#### Expected Result
The validMoves() vector list of allowed moves for the particular pawn piece being currently tests does contain the Location position of the enemy piece
