import java.util.*;

class PuzzleState {
    int[][] board; // 2D array representing the puzzle board
    int g; // cost from start to current state
    int h; // heuristic
    int f; // f = g + h
    String moves; // String representing moves made

 // Constructor to initialize the PuzzleState object
    public PuzzleState(int[][] board, int g, int h, String moves) {
        this.board = board;
        this.g = g;
        this.h = h;
        this.f = g + h;
        this.moves = moves;
    }

 // Getter method for the puzzle board
    public int[][] getBoard() {
        return board;
    }

 // Getter method for the total cost f
    public int getF() {
        return f;
    }

 // Getter method for the moves made
    public String getMoves() {
        return moves;
    }
}

//Class to solve the 8-puzzle problem
public class EightPuzzleSolver {
	// Static variables representing the goal state, movements, and their corresponding characters
    static int[][] goalState = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
    static int[] dx = {0, 0, 1, -1}; // Movements in the x-direction
    static int[] dy = {1, -1, 0, 0}; // Movements in the y-direction
    static char[] moveChars = {'R', 'L', 'D', 'U'}; // Characters representing movements (Right, Left, Down, Up)
    
    // Method to print the puzzle board
    public static void printPuzzle(int[][] puzzle) {
        for (int[] row : puzzle) { 
            for (int cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }
  
    // Method to print the puzzle board with a message
    public static void printPuzzleWithMessage(String message, int[][] puzzle) {
        System.out.println(message);
        printPuzzle(puzzle);
    }
    
    // Method to check if the puzzle is solved
    public static boolean isSolved(int[][] puzzle) {
        return Arrays.deepEquals(puzzle, goalState);
    }
    
    
    public static int[][] solveAndPrintPuzzle(String message, int[][] initialState, boolean useManhattanDistance) {
        // Print the message indicating the start of solving the puzzle
        System.out.println(message);
        // Solve the puzzle with the specified initial state and heuristic function
        int[][] solution = solvePuzzleWithHeuristic(initialState, useManhattanDistance);
        // If a solution is found
        if (solution != null) {
            // Print the solution along with a message
            printPuzzleWithMessage("Solution:", solution);
            // Check if the puzzle is fully solved
            if (isSolved(solution)) {
                System.out.println("Puzzle Solved!");
            } else {
                System.out.println("Puzzle Not Solved!");
            }
        } else {
            // If no solution is found, print a corresponding message
            System.out.println("No solution found!");
        }
        // Return the solution array or null if no solution is found
        return solution;
    }


    // Method to calculate the Manhattan distance heuristic
    public static int calculateManhattanDistance(int[][] state) {
        int distance = 0;
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                int value = state[i][j];
                if (value != 0) {
                    int targetX = (value - 1) / 3; // target row
                    int targetY = (value - 1) % 3; // target column
                    distance += Math.abs(targetX - i) + Math.abs(targetY - j);
                }
            }
        }
        return distance;
    }
    
    // Method to calculate the number of misplaced tiles heuristic
    public static int calculateMisplacedTiles(int[][] state) {
        int count = 0;
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                if (state[i][j] != goalState[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }

 // Method to get the neighboring states of a given puzzle state
    public static List<PuzzleState> getNeighbors(PuzzleState state, boolean useManhattanDistance) {
        List<PuzzleState> neighbors = new ArrayList<>();
        int[][] currentBoard = state.getBoard();
        int zeroX = -1, zeroY = -1;
        // Find the position of zero (empty tile)
        for (int i = 0; i < currentBoard.length; i++) {
            for (int j = 0; j < currentBoard[i].length; j++) {
                if (currentBoard[i][j] == 0) {
                    zeroX = i;
                    zeroY = j;
                    break;
                }
            }
        }
        // Generate neighbors by moving the zero tile
        for (int i = 0; i < 4; i++) {
            int newX = zeroX + dx[i];
            int newY = zeroY + dy[i];
            if (newX >= 0 && newX < 3 && newY >= 0 && newY < 3) {
                int[][] newBoard = new int[3][3];
                for (int k = 0; k < 3; k++) {
                    newBoard[k] = Arrays.copyOf(currentBoard[k], 3);
                }
                // Swap zero tile with the neighbor tile
                newBoard[zeroX][zeroY] = currentBoard[newX][newY];
                newBoard[newX][newY] = 0;
                String moves = state.getMoves() + moveChars[i];
                int hValue = useManhattanDistance ? calculateManhattanDistance(newBoard) : calculateMisplacedTiles(newBoard);
                neighbors.add(new PuzzleState(newBoard, state.g + 1, hValue, moves));
            }
        }
        return neighbors;
    }

    // Method to solve the puzzle using A* algorithm with a specified heuristic
    public static int[][] solvePuzzleWithHeuristic(int[][] initialState, boolean useManhattanDistance) {
        PriorityQueue<PuzzleState> open = new PriorityQueue<>(Comparator.comparingInt(PuzzleState::getF));
        Set<String> visited = new HashSet<>();
        int initialH = useManhattanDistance ? calculateManhattanDistance(initialState) : calculateMisplacedTiles(initialState);
        PuzzleState startState = new PuzzleState(initialState, 0, initialH, "");
        open.add(startState);

        while (!open.isEmpty()) {
            PuzzleState currentState = open.poll();
            int[][] currentBoard = currentState.getBoard();
            if (Arrays.deepEquals(currentBoard, goalState)) {
                System.out.println("Moves: " + currentState.getMoves());
                return currentBoard;
            }
            visited.add(Arrays.deepToString(currentBoard));
            List<PuzzleState> neighbors = getNeighbors(currentState, useManhattanDistance);
            for (PuzzleState neighbor : neighbors) {
                if (!visited.contains(Arrays.deepToString(neighbor.getBoard()))) {
                    open.add(neighbor);
                }
            }
        }
        return null; // No solution found
    }
    
    public static List<String> solvePuzzleWithHeuristicAndGetMoves(int[][] initialState, boolean useManhattanDistance) {
        // Open states are stored in priority order
        PriorityQueue<PuzzleState> open = new PriorityQueue<>(Comparator.comparingInt(PuzzleState::getF));
        // Used to keep track of visited states
        Set<String> visited = new HashSet<>();
        // Calculate heuristic cost based on the initial state
        int initialH = useManhattanDistance ? calculateManhattanDistance(initialState) : calculateMisplacedTiles(initialState);
        // Create the initial state: 0 cost, initial heuristic cost, empty moves list
        PuzzleState startState = new PuzzleState(initialState, 0, initialH, "");
        // Add the initial state to the open states list
        open.add(startState);
        // Continue as long as the open states list is not empty
        while (!open.isEmpty()) {
            // Get the best state according to priority order
            PuzzleState currentState = open.poll();
            int[][] currentBoard = currentState.getBoard();
            // If the current state represents the goal state
            if (Arrays.deepEquals(currentBoard, goalState)) {
                // Retrieve the moves made, split them into characters, and add them to a list
                return Arrays.asList(currentState.getMoves().split(""));
            }
            // Mark the current board as visited
            visited.add(Arrays.deepToString(currentBoard));
            // Get the neighboring states
            List<PuzzleState> neighbors = getNeighbors(currentState, useManhattanDistance);
            // Check each neighbor
            for (PuzzleState neighbor : neighbors) {
                // Add it to the open list if it has not been visited before
                if (!visited.contains(Arrays.deepToString(neighbor.getBoard()))) {
                    open.add(neighbor);
                }
            }
        }
        // If no solution is found, return null
        return null;
    }


 // Method to generate a random initial state for the puzzle
    public static int[][] generateRandomState() {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);
        int[][] state = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                state[i][j] = numbers.get(i * 3 + j);
            }
        }
        return state;
    }

 // Method to print the puzzle grid represented by a 2D array
    public static void printPuzzle1(int[][] puzzle) {
        // Loop through each row in the puzzle
        for (int[] row : puzzle) {
            // Loop through each cell in the current row
            for (int cell : row) {
                // Print the value of the cell followed by a space
                System.out.print(cell + " ");
            }
            // Move to the next line after printing each row
            System.out.println();
        }
    }

 // Method to check if a puzzle is solvable
    public static boolean isSolvable(int[][] state) {
        int inversions = 0;
        int[] flattened = new int[state.length * state[0].length];
        int index = 0;
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                flattened[index++] = state[i][j];
            }
        }
        for (int i = 0; i < flattened.length; i++) {
            for (int j = i + 1; j < flattened.length; j++) {
                if (flattened[i] != 0 && flattened[j] != 0 && flattened[i] > flattened[j]) {
                    inversions++;
                }
            }
        }
        return inversions % 2 == 0; // Puzzle is solvable if number of inversions is even
    }

    public static void main(String[] args) {
    	
    	// Generate a random initial state for the puzzle
        int[][] initialState = generateRandomState();
        System.out.println("Initial Puzzle:");
        printPuzzle(initialState);
        
        // Check if the initial puzzle is solvable
        if (!isSolvable(initialState)) {
            System.out.println("The initial puzzle is not solvable. Regenerating a new puzzle...");
            // Regenerate a new puzzle if the initial one is not solvable
            initialState = generateRandomState();
            System.out.println("New Initial Puzzle:");
            printPuzzle(initialState);
        }
        // Solve the puzzle using Manhattan Distance heuristic
        solveAndPrintPuzzle("Solving puzzle with Manhattan Distance heuristic...", initialState, true);
        
        System.out.println("\n----------------------------------------------\n");
        
        // Solve the puzzle using Misplaced Tiles heuristic
        solveAndPrintPuzzle("Solving puzzle with Misplaced Tiles heuristic...", initialState, false);
    }
 }