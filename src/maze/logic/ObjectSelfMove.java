package maze.logic;

public class ObjectSelfMove extends GameObject {

	public ObjectSelfMove(char state, int x, int y) {
		super(state, x, y);
	}

	// move the dragon randomly
	public static void moveDragon() {
		if (Maze.dragon.getState() == 'K')
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
		} while (Maze.mazeMap[Maze.dragon.getY() + y][Maze.dragon.getX() + x] == 'X'
				|| Maze.mazeMap[Maze.dragon.getY() + y][Maze.dragon.getX() + x] == 'S');
	
		// the dragon only moves if not adjacent (if he is, then the game is
		// over)
		if (!GameObject.adjacentPosition(Maze.dragon, Maze.hero)) {
			// blank the previous cell and move the dragon to the new one
			Maze.mazeMap[Maze.dragon.getY()][Maze.dragon.getX()] = ' ';
			Maze.mazeMap[Maze.dragon.getY() + y][Maze.dragon.getX() + x] = Maze.dragon.getState();
			MazeGame.updateObject(Maze.dragon, Maze.dragon.getX() + x, Maze.dragon.getY() + y,
					Maze.dragon.getState());
			// if the dragon goes to the cell which has the sword the status
			// changes to F
			if (GameObject.samePosition(Maze.dragon, Maze.sword)) {
				Maze.mazeMap[Maze.dragon.getY()][Maze.dragon.getX()] = 'F';
				MazeGame.updateObject(Maze.dragon, Maze.dragon.getX(), Maze.dragon.getY(), 'F');
				// when the dragon returns to a position other than the sword's
				// the status go back to normal
			} else if (GameObject.adjacentPosition(Maze.dragon, Maze.sword)
					&& Maze.dragon.getState() == 'F') {
				MazeGame.updateObject(Maze.dragon, Maze.dragon.getX(), Maze.dragon.getY(), 'D');
				Maze.mazeMap[Maze.dragon.getY()][Maze.dragon.getX()] = 'D';
				Maze.mazeMap[Maze.sword.getY()][Maze.sword.getX()] = 'E';
			}
		}
	}

}
