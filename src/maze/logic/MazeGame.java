package maze.logic;

import java.io.IOException;
import java.lang.Math;
import java.util.Scanner;

import maze.cli.MazeCLI;
/********************************************************
 * This class defines a maze game
 ********************************************************/
public class MazeGame {
	/* Attributes ================== */
	private static GameObject dragon;
	private static GameObject hero;
	private static GameObject exit;
	private static GameObject sword;
	public static int mazeDim[] = { 10, 10 };
	public static char[][] mazeMap = {
			{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' },
			{ 'X', 'H', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
			{ 'X', ' ', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X' },
			{ 'X', 'D', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X' },
			{ 'X', ' ', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X' },
			{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', 'X', ' ', 'S' },
			{ 'X', ' ', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X' },
			{ 'X', ' ', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X' },
			{ 'X', 'E', 'X', 'X', ' ', ' ', ' ', ' ', ' ', 'X' },
			{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' } };
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
			mazeDim[0] = x;
			mazeDim[1] = y;
			generateMaze(mazeDim[0], mazeDim[1]);
		}
		play();
	}

	// this function is called by main and is the game cycle itself
	private static void play() throws IOException {
		setupObjects();
		do {
			MazeCLI.printMaze();
			moveHero(MazeCLI.readKeyboardArrow());
			moveDragon();
		} while (!gameOver());
		MazeCLI.printMaze();
	}

	// setup the objects with their initial positions and states
	private static void setupObjects() {
		hero = new GameObject('H', 1, 1);
		mazeMap[hero.getY()][hero.getX()] = hero.getState();

		sword = new GameObject('E', 1, 8);
		mazeMap[sword.getY()][sword.getX()] = sword.getState();

		dragon = new GameObject('D', 1, 3);
		mazeMap[dragon.getY()][dragon.getX()] = dragon.getState();

		exit = new GameObject('S', mazeDim[0] - 1, mazeDim[1] - 5);
		mazeMap[exit.getY()][exit.getX()] = exit.getState();
	}

	// this function evaluates if the game is over by checking if the hero is
	// adjacent to the dragon and unarmed or if he's armed and exited the
	// dungeon
	private static boolean gameOver() {
		return ((GameObject.samePosition(hero, exit) && hero.getState() == 'A') || (GameObject
				.adjacentPosition(hero, dragon) && hero.getState() == 'H'));
	}

	// random maze generation
	private static void generateMaze(int m, int n) {
		// attention: m x n matrix => array[n][m]
		mazeMap = new char[n][m];
		// fill with blank spaces
		for (int i = 0; i < n; i++)
			for (int j = 0; j < m; j++)
				mazeMap[i][j] = ' ';
		// fill with walls
		for (int i = 0; i < m; i++) {
			mazeMap[0][i] = 'X';
			mazeMap[i][0] = 'X';
			mazeMap[n - 1][i] = 'X';
			mazeMap[i][m - 1] = 'X';
		}
		recursiveMazeGen(mazeMap, 0, m - 1, 0, n - 1);
	}

	// recursive algorithm for maze generation
	private static void recursiveMazeGen(char[][] maze, int xi, int xf, int yi,
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

	// move the dragon randomly
	private static void moveDragon() {
		if (dragon.getState() == 'K')
			return; // solves bug1 which would make the dragon continue moving
					// even after killed
		int x = 0, y = 0;
		int deltaX[] = { 0, 0, -1, 1, 0 };
		int deltaY[] = { -1, 1, 0, 0, 0 };
		java.util.Random r = new java.util.Random();
		do {
			int i = r.nextInt(5);
			x = deltaX[i];
			y = deltaY[i];
		} while (mazeMap[dragon.getY() + y][dragon.getX() + x] == 'X'
				|| mazeMap[dragon.getY() + y][dragon.getX() + x] == 'S');

		// the dragon only moves if not adjacent (if he is, then the game is
		// over)
		if (!GameObject.adjacentPosition(dragon, hero)) {
			// blank the previous cell and move the dragon to the new one
			mazeMap[dragon.getY()][dragon.getX()] = ' ';
			mazeMap[dragon.getY() + y][dragon.getX() + x] = dragon.getState();
			updateObject(dragon, dragon.getX() + x, dragon.getY() + y,
					dragon.getState());
			// if the dragon goes to the cell which has the sword the status
			// changes to F
			if (GameObject.samePosition(dragon, sword)) {
				mazeMap[dragon.getY()][dragon.getX()] = 'F';
				updateObject(dragon, dragon.getX(), dragon.getY(), 'F');
				// when the dragon returns to a position other than the sword's
				// the status go back to normal
			} else if (GameObject.adjacentPosition(dragon, sword)
					&& dragon.getState() == 'F') {
				updateObject(dragon, dragon.getX(), dragon.getY(), 'D');
				mazeMap[dragon.getY()][dragon.getX()] = 'D';
				mazeMap[sword.getY()][sword.getX()] = 'E';
			}
		}
	}

	// move the hero by receiving an input
	private static void moveHero(char direction) {
		int x = 0, y = 0;
		switch (direction) {
		case 'W':
			x = 0;
			y = -1;
			break;
		case 'S':
			x = 0;
			y = 1;
			break;
		case 'A':
			x = -1;
			y = 0;
			break;
		case 'D':
			x = 1;
			y = 0;
			break;
		}
		// only moves the hero if the new cell isn't a wall and if he's not
		// unarmed and going to the exit
		if (mazeMap[hero.getY() + y][hero.getX() + x] != 'X'
				&& !(hero.getX() + x == exit.getX()
						&& hero.getY() + y == exit.getY() && hero.getState() == 'H')) {
			mazeMap[hero.getY()][hero.getX()] = ' ';
			mazeMap[hero.getY() + y][hero.getX() + x] = hero.getState();
			updateObject(hero, hero.getX() + x, hero.getY() + y,
					hero.getState());

		}
		// if the hero is in the same cell of the sword, changes his state to
		// "Armed"
		if (GameObject.samePosition(hero, sword)) {
			updateObject(hero, hero.getX(), hero.getY(), 'A');
			mazeMap[hero.getY()][hero.getX()] = hero.getState();
		}
		// if the hero is near the dragon and armed, the dragon dies
		if (GameObject.adjacentPosition(hero, dragon) && hero.getState() == 'A') {
			mazeMap[dragon.getY()][dragon.getX()] = ' ';
			// bug fix 1
			updateObject(dragon, -1, -1, 'K');
		}
	}

	// Update a gameobject calling the set functions
	private static void updateObject(GameObject a, int x, int y, char state) {
		a.setY(y);
		a.setX(x);
		a.setState(state);

	}

}