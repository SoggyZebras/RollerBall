#### ClientTests

The Client is the intermediary interface between the GUI and the server. The client sends messages to the server and processes messages from the server. Messages from the server get passed to the GUI through public function calls.

### List of Tests
1. testConstructorNullArg
2. testMakeMove
3. testCheckValidMove
4. testGameState

## Test Case ID

#### TC1

#### Test Case Summary
This test makes sure that the constructor for the client is throwing the correct exception when passed a null argument.

#### Test Procedure
1. Create a Client object with null as the ip address parameter
2. Check if the constructor throws an IO exception

#### Expected Result
The Client object will not be created and an IO exception will be thrown


## Test Case ID

#### TC2

#### Test Case Summary
This test makes sure the client will construct and send a message to the server

#### Test Procedure
1. Create a thread t1 constructed with a Server object
2. Start the thread t1
3. Create a thread t2 constructed with a Client object
4. Start the thread t2
5. Call the MakeMove function

#### Expected Result
The Client will return true if the client constructed and sent a make move message to the server


## Test Case ID

#### TC3

#### Test Case Summary
This test makes sure the client will construct and send a message to the server

#### Test Procedure
1. Create a thread t1 constructed with a Server object
2. Start the thread t1
3. Create a thread t2 constructed with a Client object
4. Start the thread t2
5. Call the checkValidMove function

#### Expected Result
The Client will return true if the client constructed and sent a check valid move message to the server


## Test Case ID

#### TC4

#### Test Case Summary
This test makes sure the client will construct and send a message to the server

#### Test Procedure
1. Create a thread t1 constructed with a Server object
2. Start the thread t1
3. Create a thread t2 constructed with a Client object
4. Start the thread t2
5. Call the GameState function

#### Expected Result
The Client will return true if the client constructed and sent a get game state message to the server
