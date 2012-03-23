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
	static int d; // dragon option
	static int nDragons; // number of dragons
	/* main() ================== */
	public static void main(String args[]) throws IOException {
		Scanner in = new Scanner(System.in);
		char opt;
		int dim = 0;
		do {  //ask if the user wants a random maze or the default one
		
			System.out.print("Generate random maze (Y-N): ");
			opt = in.nextLine().toCharArray()[0];
			opt = Character.toUpperCase(opt);
		} while (opt != 'Y' && opt != 'N');
		if (opt == 'Y') { //ask the maze dimension
			System.out.print("X and Y Dimension: ");
			dim = in.nextInt();
			dim = Math.abs(dim);
			maze.mazeDim = dim;
			MazeBuilder.generateMaze(maze.mazeDim);
		}
		// Evaluate if the player wants a static dragon, a dragon that moves or a dragon that moves and sleeps
		System.out.println("Choose the type of dragon that you want:");
		System.out.print("1 - Static Dragon\n2 - Dragon that moves\n3 - Dragon that moves and sleep\n");
		System.out.print("Option:");
		d = in.nextInt();
		
		// How many dragons?
		do{
			System.out.println("How many Dragons? (>=1 and <=3):");
			nDragons=in.nextInt();
		}
		while(nDragons<1 || nDragons>3);
		new MazeGUI().init();
		play();
	}

	// this function is called by main and is the game cycle itself
	public static void play() throws IOException {
		setupObjects();
		do {
			MazeCLI.printMaze();
			maze.hero.move(MazeCLI.readKeyboardArrow());
			maze.moveDragons();
		} while (!gameOver());
		MazeCLI.printMaze();
	}

	// setup the objects with their initial positions and states
	public static void setupObjects() {
		maze.hero =new heroObject('H', 1, 1);
		maze.mazeMap[maze.hero.getY()][maze.hero.getX()] = maze.hero.getState();

		maze.sword = new GameObject('E', 1, 8);
		maze.mazeMap[maze.sword.getY()][maze.sword.getX()] = maze.sword.getState();


		maze.exit = new GameObject('S', maze.mazeDim - 1, maze.mazeDim - 5);
		maze.mazeMap[maze.exit.getY()][maze.exit.getX()] = maze.exit.getState();
		
		// Dragons --->
		maze.dragons=new ArrayList<DragonObject>();
		maze.dragons.add(new DragonObject('D', 1, 3));
		maze.mazeMap[maze.dragons.get(0).getY()][maze.dragons.get(0).getX()] = maze.dragons.get(0).getState();
		if(d==2) DragonObject.enableCanMove();
		if(d==3) {
			DragonObject.enableCanMove();
			DragonObject.enableCanSleep();
		}
		if(nDragons>1){
			for(int i=1; i<nDragons; i++){
				maze.dragons.add(new DragonObject('D', 1+3+i, 3+3+i));
				maze.mazeMap[maze.dragons.get(i).getY()][maze.dragons.get(i).getX()] = maze.dragons.get(i).getState();
			}
		}
		// -->
		
	}

	// this function evaluates if the game is over by checking if the hero is
	// adjacent to the dragon and unarmed or if he's armed and exited the
	// dungeon
	public static boolean gameOver() {
		if (GameObject.samePosition(maze.hero, maze.exit) && maze.hero.getState() == 'A')  return true;
		for(int i=0; i<maze.dragons.size(); i++)
			if (GameObject.adjacentPosition(maze.hero, maze.dragons.get(i)) && (maze.hero.getState() == 'H' && (maze.dragons.get(i).getState()=='D' || maze.dragons.get(i).getState()=='F')) ) return true;
				
		return false;
	}

	// Update a gameobject calling the set functions
	public static void updateObject(GameObject a, int x, int y, char state) {
		a.setY(y);
		a.setX(x);
		a.setState(state);

	}

}