package maze.logic;

public class ObjectIOMove extends GameObject {

	public ObjectIOMove(char state, int x, int y) {
		super(state, x, y);
	}

	// move the hero by receiving an input
	public static void moveHero(char direction) {
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
		if (Maze.mazeMap[Maze.hero.getY() + y][Maze.hero.getX() + x] != 'X'
				&& !(Maze.hero.getX() + x == Maze.exit.getX()
						&& Maze.hero.getY() + y == Maze.exit.getY() && Maze.hero.getState() == 'H')) {
			Maze.mazeMap[Maze.hero.getY()][Maze.hero.getX()] = ' ';
			Maze.mazeMap[Maze.hero.getY() + y][Maze.hero.getX() + x] = Maze.hero.getState();
			MazeGame.updateObject(Maze.hero, Maze.hero.getX() + x, Maze.hero.getY() + y,
					Maze.hero.getState());
	
		}
		// if the hero is in the same cell of the sword, changes his state to
		// "Armed"
		if (GameObject.samePosition(Maze.hero, Maze.sword)) {
			MazeGame.updateObject(Maze.hero, Maze.hero.getX(), Maze.hero.getY(), 'A');
			Maze.mazeMap[Maze.hero.getY()][Maze.hero.getX()] = Maze.hero.getState();
		}
		// if the hero is near the dragon and armed, the dragon dies
		if (GameObject.adjacentPosition(Maze.hero, Maze.dragon) && Maze.hero.getState() == 'A') {
			Maze.mazeMap[Maze.dragon.getY()][Maze.dragon.getX()] = ' ';
			// bug fix 1
			MazeGame.updateObject(Maze.dragon, -1, -1, 'K');
		}
	}
	

}
