package maze.logic;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class MazeBuilder {

	// random maze generation
	public static void generateMaze(int dim, Maze maze) {
		// attention: m x n matrix => array[n][m]
		maze.mazeMap = new char[dim][dim];
		// fill with blank spaces
		for (int i = 0; i < dim; i++)
			for (int j = 0; j < dim; j++)
				maze.mazeMap[i][j] = ' ';
		// fill with walls
		for (int i = 0; i < dim; i++) {
			maze.mazeMap[0][i] = 'X';
			maze.mazeMap[i][0] = 'X';
			maze.mazeMap[dim - 1][i] = 'X';
			maze.mazeMap[i][dim - 1] = 'X';
		}
		// recursiveMazeGen(maze.mazeMap, 0, dim - 1, 0, dim - 1);
		maze.mazeMap = dfs(dim, 0.75, 0.75);
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

	// Base from the python example in
	// http://en.wikipedia.org/wiki/Maze_generation_algorithm
	public static char[][] dfs(int size, double complexity, double density) {
		//Convert size to odd number
		size = ((size / 2) * 2 + 1);
		complexity = (int) (complexity * (5 * (size + size)));
		density = (int) (density * (size / 2 * size / 2));
		boolean maze[][] = new boolean[size][size];
		MazeGame.maze.mazeDim = size;

		// populate matrix
		/*
		 * for (int i=0; i<height; i++) for (int j=0; j<width; j++)
		 * maze[i][j]=false;
		 */

		// fill the horizontal borders
		for (int i = 0; i < size; i++) {
			maze[0][i] = true;
			maze[size - 1][i] = true;
		}

		// fill the vertical borders
		for (int i = 0; i < size; i++) {
			maze[i][0] = true;
			maze[i][size - 1] = true;
		}
		// Make the isles
		java.util.Random r = new java.util.Random();
		for (int i = 0; i < density; i++) {
			int x = r.nextInt(size / 2 + 1) * 2;
			int y = r.nextInt(size / 2 + 1) * 2;
			maze[y][x] = true;
			for (int j = 0; j < complexity; j++) {
				ArrayList<int[]> neighbours = new ArrayList<int[]>();
				if (x > 1) {
					int aux[] = { 0, 0 };
					aux[0] = y;
					aux[1] = x - 2;
					neighbours.add(aux);
				}
				if (x < size - 2) {
					int aux[] = { 0, 0 };
					aux[0] = y;
					aux[1] = x + 2;
					neighbours.add(aux);
				}
				if (y > 1) {
					int aux[] = { 0, 0 };
					aux[0] = y - 2;
					aux[1] = x;
					neighbours.add(aux);
				}
				if (y < size - 2) {
					int aux[] = { 0, 0 };
					aux[0] = y + 2;
					aux[1] = x;
					neighbours.add(aux);
				}

				if (neighbours.size() > 0) {
					int _y = neighbours.get(r.nextInt(neighbours.size()))[0];
					int _x = neighbours.get(r.nextInt(neighbours.size()))[1];
					if (maze[_y][_x] == false) {
						maze[_y][_x] = true;
						maze[_y + (y - _y) / 2][_x + (x - _x) / 2] = true;
						x = _x;
						y = _y;
					}
				}

			}

		}
		// move to an array of chars
		char[][] mazeChar = new char[size][size];
		for (int l = 0; l < size; l++)
			for (int m = 0; m < size; m++) {
				if (maze[l][m])
					mazeChar[l][m] = 'X';
				else
					mazeChar[l][m] = ' ';
			}
		return mazeChar;

	}

}
