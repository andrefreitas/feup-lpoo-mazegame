import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.CharBuffer;

/**
 * @version 1.0
 * @created 27-Fev-2012 14:58:57
 */
public class MazeGame {

	private static GameObject dragon;
	private static GameObject hero;
	private static GameObject exit;
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
	private static GameObject sword;

	public MazeGame() {

	}

	public static void main(String args[]) throws IOException {
		play();
	}

	public void finalize() throws Throwable {

	}

	private static boolean gameOver() {
		return ((GameObject.samePosition(hero, exit) && hero.getState() == 'A') || (GameObject
				.adjacentPosition(hero, dragon) && hero.getState() == 'H'));
	}

	private void generateMaze() {

	}

	private static void moveDragon() {
		int x = 0, y = 0;
		java.util.Random r = new java.util.Random();
		do {
			int i = r.nextInt(5);

			switch (i) {
			case 0:
				x = 0;
				y = -1;
				break;
			case 1:
				x = 0;
				y = 1;
				break;
			case 2:
				x = -1;
				y = 0;
				break;
			case 3:
				x = 1;
				y = 0;
				break;
			case 4:
				x = 0;
				y = 0;
				break;
			}
		} while (mazeMap[dragon.getY() + y][dragon.getX() + x] == 'X'
				|| mazeMap[dragon.getY() + y][dragon.getX() + x] == 'S');
		if (!GameObject.adjacentPosition(dragon, hero)) {
			mazeMap[dragon.getY()][dragon.getX()] = ' ';
			mazeMap[dragon.getY() + y][dragon.getX() + x] = dragon.getState();
			updateObject(dragon, dragon.getX() + x, dragon.getY() + y,
					dragon.getState());
			if (GameObject.samePosition(dragon, sword)) {
				mazeMap[dragon.getY()][dragon.getX()] = 'F';
				updateObject(dragon, dragon.getX(), dragon.getY(), 'F');
			} else if (GameObject.adjacentPosition(dragon, sword)
					&& dragon.getState() == 'F') {
				updateObject(dragon, dragon.getX(), dragon.getY(), 'D');
				mazeMap[dragon.getY()][dragon.getX()] = 'D';
				mazeMap[sword.getY()][sword.getX()] = 'E';
			}
		}
	}

	/**
	 * 
	 * @param char
	 */
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
		if (mazeMap[hero.getY() + y][hero.getX() + x] != 'X'
				&& !(hero.getX() + x == exit.getX()
						&& hero.getY() + y == exit.getY() && hero.getState() == 'H')) {
			mazeMap[hero.getY()][hero.getX()] = ' ';
			mazeMap[hero.getY() + y][hero.getX() + x] = hero.getState();
			updateObject(hero, hero.getX() + x, hero.getY() + y,
					hero.getState());

		}
		if (GameObject.samePosition(hero, sword)) {
			updateObject(hero, hero.getX(), hero.getY(), 'A');
			mazeMap[hero.getY()][hero.getX()] = hero.getState();
		}
		if (GameObject.adjacentPosition(hero, dragon) && hero.getState() == 'A') {
			mazeMap[dragon.getY()][dragon.getX()] = ' ';
		}
	}

	private static void play() throws IOException {
		setupObjects();
		do {
			printMaze();
			moveHero(readKeyboardArrow());
			moveDragon();
		} while (!gameOver());
		printMaze();
	}

	private static void printMaze() {
		for (int i = 0; i < 10; i++) {
			for (int n = 0; n < 10; n++) {
				System.out.print(mazeMap[i][n] + " ");
			}
			System.out.print('\n');
		}
	}

	private static char readKeyboardArrow() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		char chars[] = new char[1];
		do {
			chars[0] = Character.toUpperCase((char) br.read());
		} while (chars[0] != 'W' && chars[0] != 'A' && chars[0] != 'S'
				&& chars[0] != 'D');
		return chars[0];
	}

	private static void setupObjects() {
		hero = new GameObject('H', 1, 1);
		exit = new GameObject('S', 9, 5);
		sword = new GameObject('E', 1, 8);
		dragon = new GameObject('D', 1, 3);
	}

	/**
	 * 
	 * @param a
	 * @param x
	 * @param y
	 * @param state
	 */
	private static void updateObject(GameObject a, int x, int y, char state) {
		a.setY(y);
		a.setX(x);
		a.setState(state);

	}

}