package maze.logic;

public class ObjectSelfMove extends GameObject {

	public ObjectSelfMove(char state, int x, int y) {
		super(state, x, y);
	}

	// move the dragon randomly
	public static void moveDragon() {
		if (MazeGame.maze.dragon.getState() == 'K')
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
		} while (MazeGame.maze.mazeMap[MazeGame.maze.dragon.getY() + y][MazeGame.maze.dragon.getX() + x] == 'X'
				|| MazeGame.maze.mazeMap[MazeGame.maze.dragon.getY() + y][MazeGame.maze.dragon.getX() + x] == 'S');
	
		// the dragon only moves if not adjacent (if he is, then the game is
		// over)
		if (!GameObject.adjacentPosition(MazeGame.maze.dragon, MazeGame.maze.hero)) {
			// blank the previous cell and move the dragon to the new one
			MazeGame.maze.mazeMap[MazeGame.maze.dragon.getY()][MazeGame.maze.dragon.getX()] = ' ';
			MazeGame.maze.mazeMap[MazeGame.maze.dragon.getY() + y][MazeGame.maze.dragon.getX() + x] = MazeGame.maze.dragon.getState();
			MazeGame.updateObject(MazeGame.maze.dragon, MazeGame.maze.dragon.getX() + x, MazeGame.maze.dragon.getY() + y,
					MazeGame.maze.dragon.getState());
			// if the dragon goes to the cell which has the sword the status
			// changes to F
			if (GameObject.samePosition(MazeGame.maze.dragon, MazeGame.maze.sword)) {
				MazeGame.maze.mazeMap[MazeGame.maze.dragon.getY()][MazeGame.maze.dragon.getX()] = 'F';
				MazeGame.updateObject(MazeGame.maze.dragon, MazeGame.maze.dragon.getX(), MazeGame.maze.dragon.getY(), 'F');
				// when the dragon returns to a position other than the sword's
				// the status go back to normal
			} else if (GameObject.adjacentPosition(MazeGame.maze.dragon, MazeGame.maze.sword)
					&& MazeGame.maze.dragon.getState() == 'F') {
				MazeGame.updateObject(MazeGame.maze.dragon, MazeGame.maze.dragon.getX(), MazeGame.maze.dragon.getY(), 'D');
				MazeGame.maze.mazeMap[MazeGame.maze.dragon.getY()][MazeGame.maze.dragon.getX()] = 'D';
				MazeGame.maze.mazeMap[MazeGame.maze.sword.getY()][MazeGame.maze.sword.getX()] = 'E';
			}
		}
	}

}
