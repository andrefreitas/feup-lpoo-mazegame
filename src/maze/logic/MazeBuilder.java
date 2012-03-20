package maze.logic;

public abstract class MazeBuilder {

	// random maze generation
	public static void generateMaze(int dim) {
		// attention: m x n matrix => array[n][m]
		MazeGame.maze.mazeMap = new char[dim][dim];
		// fill with blank spaces
		for (int i = 0; i < dim; i++)
			for (int j = 0; j < dim; j++)
				MazeGame.maze.mazeMap[i][j] = ' ';
		// fill with walls
		for (int i = 0; i < dim; i++) {
			MazeGame.maze.mazeMap[0][i] = 'X';
			MazeGame.maze.mazeMap[i][0] = 'X';
			MazeGame.maze.mazeMap[dim - 1][i] = 'X';
			MazeGame.maze.mazeMap[i][dim - 1] = 'X';
		}
		MazeBuilder.recursiveMazeGen(MazeGame.maze.mazeMap, 0, dim - 1, 0, dim - 1);
	}

	// recursive algorithm for maze generation
	public static void recursiveMazeGen(char[][] maze, int xi, int xf, int yi,
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
	
}
