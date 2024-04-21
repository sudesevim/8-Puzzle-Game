import java.awt.event.KeyEvent; // for the constants of the keys on the keyboard
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

// A program that partially implements the 8 puzzle.
public class EightPuzzle {
	private static Board board;
   // The main method is the entry point where the program starts execution.
   public static void main(String[] args) {
      // StdDraw setup
      // -----------------------------------------------------------------------
      // set the size of the canvas (the drawing area) in pixels
      StdDraw.setCanvasSize(500, 500);
      // set the range of both x and y values for the drawing canvas
      StdDraw.setScale(0.5, 3.5);
      // enable double buffering to animate moving the tiles on the board
      StdDraw.enableDoubleBuffering();

      // create a random board for the 8 puzzle
      board = new Board();
      
      //Generate a random initial puzzle state
      int[][] initialState=EightPuzzleSolver.generateRandomState();
      int[][] solution=EightPuzzleSolver.solvePuzzleWithHeuristic(initialState,true);
      
      if(solution!=null) {
    	  //Set the board state to the solution
    	  board.setBoardState(solution);
    	  //Draw the initial board state
    	  board.draw();
    	  StdDraw.show();
      
    	  List<String> manhattanMoves = EightPuzzleSolver.solvePuzzleWithHeuristicAndGetMoves(initialState, true);

          // Solve puzzle with Misplaced Tiles heuristic and get moves
          List<String> misplacedTilesMoves = EightPuzzleSolver.solvePuzzleWithHeuristicAndGetMoves(initialState, false);

          if (manhattanMoves != null && misplacedTilesMoves != null) {
              // Set the initial board state
              board.setBoardState(initialState);

              // Draw the initial board state
              board.draw();
              StdDraw.show();

              // Display and execute movements for Manhattan Distance solution
              displayAndExecuteMoves(manhattanMoves);

          } else {
              System.out.println("No solution found!");
          }}
          }
          
          private static void displayAndExecuteMoves(List<String> moves) {
              for (String move : moves) {
                  switch (move) {
                      case "R":
                          board.moveRight();
                          break;
                      case "L":
                          board.moveLeft();
                          break;
                      case "U":
                          board.moveUp();
                          break;
                      case "D":
                          board.moveDown();
                          break;
                      default:
                          System.out.println("Invalid move: " + move);
                  }

                  // Draw the updated board state
                  board.draw();
                  StdDraw.show();
                  StdDraw.pause(500); // Pause for a short time between moves
              }
          

      // The main animation and user interaction loop
   
      // -----------------------------------------------------------------------
      while (true) {
         // draw the board, show the resulting drawing and pause for a short time
         board.draw();
         StdDraw.show();
         StdDraw.pause(100); // 100 ms
         // if the user has pressed the right arrow key on the keyboard
         if (StdDraw.isKeyPressed(KeyEvent.VK_RIGHT))
            // move the empty cell right
            board.moveRight();
         // if the user has pressed the left arrow key on the keyboard
         if (StdDraw.isKeyPressed(KeyEvent.VK_LEFT))
            // move the empty cell left
            board.moveLeft();
         // if the user has pressed the up arrow key on the keyboard
         if (StdDraw.isKeyPressed(KeyEvent.VK_UP))
            // move the empty cell up
            board.moveUp();
         // if the user has pressed the down arrow key on the keyboard
         if (StdDraw.isKeyPressed(KeyEvent.VK_DOWN))
            // move the empty cell down
            board.moveDown();
         if(Arrays.deepEquals(board.getBoardState(), EightPuzzleSolver.goalState)) {
        	 System.out.println("Puzzle Solved!");
        	 break;
         }
         else {
        	 System.out.println("No solution found!");
         }
      }
   }
}