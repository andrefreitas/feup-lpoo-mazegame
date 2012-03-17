package maze.logic;

import java.io.IOException;
import java.lang.Math;
import java.util.Scanner;

import maze.cli.MazeCLI;
/********************************************************
 * This class defines a maze game
 ********************************************************/
public class MazeGame {
	public static Maze maze = new Maze();
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
			maze.mazeDim[0] = x;
			maze.mazeDim[1] = y;
			MazeBuilder.generateMaze(maze.mazeDim[0], maze.mazeDim[1]);
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
		maze.hero =new ObjectIOMove('H', 1, 1);
		maze.mazeMap[maze.hero.getY()][maze.hero.getX()] = maze.hero.getState();

		maze.sword = new GameObject('E', 1, 8);
		maze.mazeMap[maze.sword.getY()][maze.sword.getX()] = maze.sword.getState();

		maze.dragon = new ObjectSelfMove('D', 1, 3);
		maze.mazeMap[maze.dragon.getY()][maze.dragon.getX()] = maze.dragon.getState();

		maze.exit = new GameObject('S', maze.mazeDim[0] - 1, maze.mazeDim[1] - 5);
		maze.mazeMap[maze.exit.getY()][maze.exit.getX()] = maze.exit.getState();
	}

	// this function evaluates if the game is over by checking if the hero is
	// adjacent to the dragon and unarmed or if he's armed and exited the
	// dungeon
	public static boolean gameOver() {
		return ((GameObject.samePosition(maze.hero, maze.exit) && maze.hero.getState() == 'A') || (GameObject
				.adjacentPosition(maze.hero, maze.dragon) && maze.hero.getState() == 'H'));
	}

	// Update a gameobject calling the set functions
	public static void updateObject(GameObject a, int x, int y, char state) {
		a.setY(y);
		a.setX(x);
		a.setState(state);

	}

}