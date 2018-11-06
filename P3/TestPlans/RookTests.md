
### TP3 RookTests
The Rook class represents a piece in the game that can move and bounce in straight lines.

### List of Tests
1. Test friendly piece in the way (TC1)
2. Test enemy piece stops movement (TC2)
3. Test valid moves on outer ring (TC3)
4. Test vald moves on inner ring (TC4)


### Test Case ID

#### TC1

#### Test Case Summary
Test that the rook's movement is stopped by a friendly piece.

#### Variations
1. Different quadrants of the board.

#### Preconditions
1. There is a rook on the board.
2. There is another piece (same color as the rook) on the board in a position that will stop the rook's movement.

#### Test Procedure
1. Create a board with a rook and some other piece with the same color as the rook.
2. Get the valid moves of the rook and make sure that the friendly piece's spot and any spots past that are not included in the list of valid moves.

#### Expected Result
A list of valid moves that does not contain the friendly piece's spot and any spots past that.


### Test Case ID

#### TC2

#### Test Case Summary
Test that the rook's movement is stopped by an enemy piece.

#### Variations
1. Different quadrants of the board.

#### Preconditions
1. There is a rook on the board.
2. There is another piece (opposite color of the rook) on the board in a position that will stop the rook's movement.

#### Test Procedure
1. Create a board with a rook and some other piece with the opposite color of the rook.
2. Get the valid moves of the rook and make sure that any spots past the enemy's spot are not included in the list of valid moves.

#### Expected Result
A list of valid moves that does not contain the friendly piece's spot and any spots past that.

### Test Case ID

#### TC3

#### Test Case Summary
Test that the rook's movement works as expected on the outer ring.

#### Variations
1. Different quadrants of the board.

#### Preconditions
1. There is a rook on the board in the outer ring.
2. There are no other pieces on the board that would stop the rook's movement.

#### Test Procedure
1. Create a board with a rook on the outer ring.
2. Get the valid moves of the rook and make sure that all the correct locations are there.

#### Expected Result
A list of correct valid moves.

### Test Case ID

#### TC4

#### Test Case Summary
Test that the rook's movement works as expected on the inner ring.

#### Variations
1. Different quadrants of the board.

#### Preconditions
1. There is a rook on the board in the inner ring.
2. There are no other pieces on the board that would stop the rook's movement.

#### Test Procedure
1. Create a board with a rook on the inner ring.
2. Get the valid moves of the rook and make sure that all the correct locations are there.

#### Expected Result
A list of correct valid moves.

