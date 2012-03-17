package maze.logic;

public class heroObject extends ObjectIOMove {

	public heroObject(char state, int x, int y) {
		super(state, x, y);
	}

	// move the hero by receiving an input
	public void move(char direction) {
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
		if (MazeGame.maze.mazeMap[MazeGame.maze.hero.getY() + y][MazeGame.maze.hero.getX() + x] != 'X'
				&& !(MazeGame.maze.hero.getX() + x == MazeGame.maze.exit.getX()
						&& MazeGame.maze.hero.getY() + y == MazeGame.maze.exit.getY() && MazeGame.maze.hero.getState() == 'H')) {
			MazeGame.maze.mazeMap[MazeGame.maze.hero.getY()][MazeGame.maze.hero.getX()] = ' ';
			MazeGame.maze.mazeMap[MazeGame.maze.hero.getY() + y][MazeGame.maze.hero.getX() + x] = MazeGame.maze.hero.getState();
			MazeGame.updateObject(MazeGame.maze.hero, MazeGame.maze.hero.getX() + x, MazeGame.maze.hero.getY() + y,
					MazeGame.maze.hero.getState());
	
		}
		// if the hero is in the same cell of the sword, changes his state to
		// "Armed"
		if (GameObject.samePosition(MazeGame.maze.hero, MazeGame.maze.sword)) {
			MazeGame.updateObject(MazeGame.maze.hero, MazeGame.maze.hero.getX(), MazeGame.maze.hero.getY(), 'A');
			MazeGame.maze.mazeMap[MazeGame.maze.hero.getY()][MazeGame.maze.hero.getX()] = MazeGame.maze.hero.getState();
		}
		// if the hero is near the dragon and armed, the dragon dies
		if (GameObject.adjacentPosition(MazeGame.maze.hero, MazeGame.maze.dragon) && MazeGame.maze.hero.getState() == 'A') {
			MazeGame.maze.mazeMap[MazeGame.maze.dragon.getY()][MazeGame.maze.dragon.getX()] = ' ';
			// bug fix 1
			MazeGame.updateObject(MazeGame.maze.dragon, -1, -1, 'K');
		}
	}

}
