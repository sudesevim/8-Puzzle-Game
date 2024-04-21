# 8-Puzzle-Game
 
 I implement a Java-based project into my base code for my Programming Studio Lesson’s Project
My Java-based project aims to solve the 8-puzzle game to reach the goal state with as few moves
as possible. This project involves the implementation of a widely used algorithm which is A* to find
the solution. I used Manhattan distance and Misplaced Tiles heuristics to guide the search process.
And I used the StdDraw library for graphical visualization.
My purpose of this project is the find two possible solutions with Manhattan Distance and
Misplaced Tiles heuristic which used in A* algorithm to guide the search process and show the
solutions of the 8-puzzle game


This project involves the implementation of a widely used algorithm which is A* to find the
solution. I used Manhattan distance and Misplaced Tiles heuristics to guide the search process. And I
used the StdDraw library for graphical visualization. Here are the classes I used in my code:
1. Board.java
2. Tile.java
3. EightPuzzle.java
EightPuzzle class represents the state of the puzzle; puzzle board, the cost from the start state, the
heuristic value, the total cost and the sequence of the moves. It provides get methods to access the
puzzle board, total cost and moves.
4. EightPuzzleSolver.java
The EightPuzzleSolver class contains the main logic of the puzzle. It has static methods for print the
puzzle, generating a random puzzle, checking if it is solvable and A* search algorithm with
heuristics.
