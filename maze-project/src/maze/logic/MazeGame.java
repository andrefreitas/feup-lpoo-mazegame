package maze.logic;

import java.io.IOException;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Scanner;

import maze.cli.MazeCLI;
import maze.gui.*;

/********************************************************
 * This class defines a maze game
 ********************************************************/
public class MazeGame {

	public static Maze maze = new Maze();
	static int dragonOption; // dragon option
	static int nDragons; // number of dragons
	static boolean ui = true;
	static MazeGUI gui;

	/* main() ================== */
	public static void main(String args[]) throws IOException {
		Scanner in = new Scanner(System.in);
		char opt;
		int dim = 0;
		do { // ask if the user wants a random maze or the default one

			System.out.print("Generate random maze (Y-N): ");
			opt = in.nextLine().toCharArray()[0];
			opt = Character.toUpperCase(opt);
		} while (opt != 'Y' && opt != 'N');
		if (opt == 'Y') { // ask the maze dimension
			System.out.print("X and Y Dimension: ");
			dim = in.nextInt();
			dim = Math.abs(dim);
			maze.mazeDim = dim;
			MazeBuilder.generateMaze(maze.mazeDim, maze);
		}
		// Evaluate if the player wants a static dragon, a dragon that moves or
		// a dragon that moves and sleeps
		System.out.println("Choose the type of dragon that you want:");
		System.out
				.print("1 - Static Dragon\n2 - Dragon that moves\n3 - Dragon that moves and sleep\n");
		System.out.print("Option:");
		dragonOption = in.nextInt();

		// How many dragons?
		do {
			System.out.println("How many Dragons? (>=1 and <=3):");
			nDragons = in.nextInt();
		} while (nDragons < 1 || nDragons > 3);

		// create a new Mazegui instance
		if (ui) {
			gui = new MazeGUI();
			gui.init();
		}
		play();
	}

	// Wait implementation
	public static void wait(int n) {
		long t0, t1;
		t0 = System.currentTimeMillis();
		do {
			t1 = System.currentTimeMillis();
		} while (t1 - t0 < 1000);
	}

	// this function is called by main and is the game cycle itself
	public static void play() throws IOException {
		setupObjects();
		if (!ui) {
			// CLI interface
			do {
				MazeCLI.printMaze();
				maze.hero.move(MazeCLI.readKeyboardArrow());
				maze.moveDragons();
			} while (!gameOver());
			MazeCLI.printMaze();
		} else {
			// GUI interface
			do {
				maze.moveDragons();
				gui.frame.repaint();
				wait(2);

			} while (!gameOver());
			gui.gameOver();
		}
	}

	// setup the objects with their initial positions and states
	public static void setupObjects() {
		java.util.Random r = new java.util.Random();
		// Setup hero
		int limitGen = maze.mazeDim / 4;
		int x, y;
		do {
			x = r.nextInt(limitGen);
			y = r.nextInt(limitGen);

		} while (maze.mazeMap[x][y] == 'X');
		maze.hero = new heroObject('H', x, y);
		maze.mazeMap[maze.hero.getY()][maze.hero.getX()] = maze.hero.getState();

		// Setup sword
		do {
			x = r.nextInt(maze.mazeDim);
			y = r.nextInt(maze.mazeDim);

		} while (maze.mazeMap[x][y] == 'X' || maze.mazeMap[x][y] == 'H');
		maze.sword = new GameObject('E', x, y);
		maze.mazeMap[maze.sword.getY()][maze.sword.getX()] = maze.sword
				.getState();

		// Setup exit

		do {
			y = r.nextInt((maze.mazeDim - 1) / 2 - 1) + (maze.mazeDim - 1) / 2;
		} while (maze.mazeMap[y][maze.mazeDim - 2] == 'X');
		maze.exit = new GameObject('S', maze.mazeDim - 1, y);
		maze.mazeMap[maze.exit.getY()][maze.exit.getX()] = maze.exit.getState();

		// Dragons --->
		if (dragonOption == 2)
			DragonObject.enableCanMove();
		if (dragonOption == 3) {
			DragonObject.enableCanMove();
			DragonObject.enableCanSleep();
		}
		maze.dragons = new ArrayList<DragonObject>();

		for (int i = 0; i < nDragons; i++) {
			int n = 0;
			do {
				n++;
				x = r.nextInt(maze.mazeDim);
				y = r.nextInt(maze.mazeDim);
				if (n == 1)
					maze.dragons.add(new DragonObject('D', x, y));
				else
					maze.dragons.set(i, new DragonObject('D', x, y));

			} while (maze.mazeMap[x][y] == 'X'
					|| maze.mazeMap[x][y] == 'E'
					|| maze.mazeMap[x][y] == 'D'
					|| GameObject.adjacentPosition(maze.dragons.get(i),
							maze.hero) || maze.mazeMap[x][y] == 'H');
			maze.mazeMap[maze.dragons.get(i).getY()][maze.dragons.get(i).getX()] = maze.dragons
					.get(i).getState();
		}
		// -->

	}

	// this function evaluates if the game is over by checking if the hero is
	// adjacent to the dragon and unarmed or if he's armed and exited the
	// dungeon
	public static boolean gameOver() {
		if (GameObject.samePosition(maze.hero, maze.exit)
				&& maze.hero.getState() == 'A')
			return true;
		for (int i = 0; i < maze.dragons.size(); i++)
			if (GameObject.adjacentPosition(maze.hero, maze.dragons.get(i))
					&& (maze.hero.getState() == 'H' && (maze.dragons.get(i)
							.getState() == 'D' || maze.dragons.get(i)
							.getState() == 'F')))
				return true;

		return false;
	}

	// Update a gameobject calling the set functions
	public static void updateObject(GameObject a, int x, int y, char state) {
		a.setY(y);
		a.setX(x);
		a.setState(state);

	}

}