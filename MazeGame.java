import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.CharBuffer;
/********************************************************
 * This class defines a maze game
 ********************************************************/
public class MazeGame {
	/* Attributes ================== */
	private static GameObject dragon;
	private static GameObject hero;
	private static GameObject exit;
	private static GameObject sword;
	private static char[][] mazeMap = {
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
	
	/* main()  ================== */
	public static void main(String args[]) throws IOException {
		play();
	}
	
	// this function is called by main and is the game cycle itself
	private static void play() throws IOException {
		setupObjects();
		do {
			printMaze();
			moveHero(readKeyboardArrow());
			moveDragon();
		} while (!gameOver());
		printMaze();
	}
	
	// setup the object with their initial postion and states
	private static void setupObjects() {
		hero = new GameObject('H', 1, 1);
		exit = new GameObject('S', 9, 5);
		sword = new GameObject('E', 1, 8);
		dragon = new GameObject('D', 1, 3);
	}
	
	// print the maze in the standard output
	private static void printMaze() {
		for (int i = 0; i < 10; i++) {
			for (int n = 0; n < 10; n++) {
				System.out.print(mazeMap[i][n] + " ");
			}
			System.out.print('\n');
		}
	}

	// this function evaluates if the game is over by checking if the hero is unarmed or is near a dragon
	private static boolean gameOver() {
		return ((GameObject.samePosition(hero, exit) && hero.getState() == 'A') || (GameObject
				.adjacentPosition(hero, dragon) && hero.getState() == 'H'));
	}
	
	// random maze generation
	private void generateMaze() {

	}
	
	// move the dragon randomly
	private static void moveDragon() {
		if (dragon.getState()=='K') return ; // bug fix 1
		int x = 0, y = 0;
		int deltaX[]={0,0,-1,1,0};
		int deltaY[]={-1,1,0,0,0};
		java.util.Random r = new java.util.Random();
		do {
			int i = r.nextInt(5);
			x = deltaX[i];
			y = deltaY[i];
		} while (mazeMap[dragon.getY() + y][dragon.getX() + x] == 'X'
				|| mazeMap[dragon.getY() + y][dragon.getX() + x] == 'S');
		
		// the dragon moves if only is not near the hero
		if (!GameObject.adjacentPosition(dragon, hero)) {
			// delete the old cell and move the dragon to the new cell
			mazeMap[dragon.getY()][dragon.getX()] = ' ';
			mazeMap[dragon.getY() + y][dragon.getX() + x] = dragon.getState();
			updateObject(dragon, dragon.getX() + x, dragon.getY() + y,
					dragon.getState());
			// if the dragon goes to a cell with a sword the status changes to F
			if (GameObject.samePosition(dragon, sword)) {
				mazeMap[dragon.getY()][dragon.getX()] = 'F';
				updateObject(dragon, dragon.getX(), dragon.getY(), 'F');
			// if the new position of the dragon is without the sword, he returns to state D
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
		// only moves the heroe if the new cell isn't a wall and if he's not unarmed and going to the exit
		if (mazeMap[hero.getY() + y][hero.getX() + x] != 'X'
				&& !(hero.getX() + x == exit.getX()
						&& hero.getY() + y == exit.getY() && hero.getState() == 'H')) {
			mazeMap[hero.getY()][hero.getX()] = ' ';
			mazeMap[hero.getY() + y][hero.getX() + x] = hero.getState();
			updateObject(hero, hero.getX() + x, hero.getY() + y,
					hero.getState());

		}
		// if the hero is in the same cell of the sword, goes to the state "Armed"
		if (GameObject.samePosition(hero, sword)) {
			updateObject(hero, hero.getX(), hero.getY(), 'A');
			mazeMap[hero.getY()][hero.getX()] = hero.getState();
		}
		// if the hero is near the dragon and is armed, the dragon dies
		if (GameObject.adjacentPosition(hero, dragon) && hero.getState() == 'A') {
			mazeMap[dragon.getY()][dragon.getX()] = ' ';
			// bug fix 1
			updateObject(dragon, -1, -1, 'K');
		}
	}


	// read keyboard char input
	private static char readKeyboardArrow() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		char chars[] = new char[1];
		do {
			chars[0] = Character.toUpperCase((char) br.read());
		} while (chars[0] != 'W' && chars[0] != 'A' && chars[0] != 'S'
				&& chars[0] != 'D');
		return chars[0];
	}

	

	// Update a gameobject calling the set functions
	private static void updateObject(GameObject a, int x, int y, char state) {
		a.setY(y);
		a.setX(x);
		a.setState(state);

	}

}