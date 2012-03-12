package maze.logic;

import java.io.IOException;
import java.lang.Math;
import java.util.Scanner;

import maze.cli.MazeCLI;
/********************************************************
 * This class defines a maze game
 ********************************************************/
public class MazeGame {
	/* main() ================== */
	public static void main(String args[]) throws IOException {
		Scanner in = new Scanner(System.in);
		char opt;
		int x = 0, y = 0;
		do {  //ask if the user wants a random maze or the default one
			System.out.print("Generate random maze (Y-N): ");
			opt = in.nextLine().toCharArray()[0];
			opt = Character.toUpperCase(opt);
		} while (opt != 'Y' && opt != 'N');
		if (opt == 'Y') { //ask the maze dimension
			System.out.print("X Dimension: ");
			x = in.nextInt();
			System.out.print("Y Dimension: ");
			y = in.nextInt();
			x = Math.abs(x);
			y = Math.abs(y);
			Maze.mazeDim[0] = x;
			Maze.mazeDim[1] = y;
			generateMaze(Maze.mazeDim[0], Maze.mazeDim[1]);
		}
		play();
	}

	// this function is called by main and is the game cycle itself
	public static void play() throws IOException {
		setupObjects();
		do {
			MazeCLI.printMaze();
			ObjectIOMove.moveHero(MazeCLI.readKeyboardArrow());
			ObjectSelfMove.moveDragon();
		} while (!gameOver());
		MazeCLI.printMaze();
	}

	// setup the objects with their initial positions and states
	public static void setupObjects() {
		Maze.hero =new ObjectIOMove('H', 1, 1);
		Maze.mazeMap[Maze.hero.getY()][Maze.hero.getX()] = Maze.hero.getState();

		Maze.sword = new GameObject('E', 1, 8);
		Maze.mazeMap[Maze.sword.getY()][Maze.sword.getX()] = Maze.sword.getState();

		Maze.dragon = new ObjectSelfMove('D', 1, 3);
		Maze.mazeMap[Maze.dragon.getY()][Maze.dragon.getX()] = Maze.dragon.getState();

		Maze.exit = new GameObject('S', Maze.mazeDim[0] - 1, Maze.mazeDim[1] - 5);
		Maze.mazeMap[Maze.exit.getY()][Maze.exit.getX()] = Maze.exit.getState();
	}

	// this function evaluates if the game is over by checking if the hero is
	// adjacent to the dragon and unarmed or if he's armed and exited the
	// dungeon
	public static boolean gameOver() {
		return ((GameObject.samePosition(Maze.hero, Maze.exit) && Maze.hero.getState() == 'A') || (GameObject
				.adjacentPosition(Maze.hero, Maze.dragon) && Maze.hero.getState() == 'H'));
	}

	// random maze generation
	public static void generateMaze(int m, int n) {
		// attention: m x n matrix => array[n][m]
		Maze.mazeMap = new char[n][m];
		// fill with blank spaces
		for (int i = 0; i < n; i++)
			for (int j = 0; j < m; j++)
				Maze.mazeMap[i][j] = ' ';
		// fill with walls
		for (int i = 0; i < m; i++) {
			Maze.mazeMap[0][i] = 'X';
			Maze.mazeMap[i][0] = 'X';
			Maze.mazeMap[n - 1][i] = 'X';
			Maze.mazeMap[i][m - 1] = 'X';
		}
		recursiveMazeGen(Maze.mazeMap, 0, m - 1, 0, n - 1);
	}

	// recursive algorithm for maze generation
	public static void recursiveMazeGen(char[][] maze, int xi, int xf, int yi,
			int yf) {
		if (Math.abs(xf - xi) < 4 | Math.abs(yf - yi) < 4)
			return; // if the wall is impossible to create, does nothing
		// (1) Generate the walls intersection point
		java.util.Random r = new java.util.Random();
		int x = r.nextInt(xf - (xi + 2) - 1) + xi + 2;
		int y = r.nextInt(yf - (yi + 2) - 1) + yi + 2;

		// (2) Fill the walls
		// ***********************************************************************************************
		// READ THIS PLEASE: this fixes a problem in which some chambers would
		// be closed
		// This could happen because some crossing walls close chamber exits.
		// Try to change the variable moreRandomWall to test the difference
		// ***********************************************************************************************
		int moreRandomWall = r.nextInt(2);
		for (int i = xi + 1 + moreRandomWall; i < xf - 1; i++)
			maze[y][i] = 'X';
		for (int i = yi + 1 + moreRandomWall; i < yf - moreRandomWall; i++)
			maze[i][x] = 'X';
		// ***********************************************************************************************

		// (3) Draw 3 exits in walls, but without a guaranteed exit from the
		// chamber
		int delta = r.nextInt(x - (xi + 1)) + xi + 1; // x-left
		maze[y][delta] = ' ';

		delta = r.nextInt(xf - (x + 1)) + x + 1; // x-right
		maze[y][delta] = ' ';

		delta = r.nextInt(yf - (y + 1)) + y + 1; // x-right
		maze[delta][x] = ' ';

		// (4) Recursive call
		recursiveMazeGen(maze, xi, x, yi, y);
		recursiveMazeGen(maze, x, xf, yi, y);
		recursiveMazeGen(maze, xi, x, y, yf);
		recursiveMazeGen(maze, x, xf, y, yf);
	}

	// Update a gameobject calling the set functions
	public static void updateObject(GameObject a, int x, int y, char state) {
		a.setY(y);
		a.setX(x);
		a.setState(state);

	}

}