package maze.logic;

import java.util.ArrayList;

/**
 * ******************************************************
 * This class defines a maze builder
 * ******************************************************
 */
public abstract class MazeBuilder {
    /*
     * Receives the maze and the dimension and generates the chambers @param dim
     * the dimension n x n of the maze @param maze the maze object
     */

    public static void generateMaze(int dim, Maze maze) {
        maze.mazeMap = new char[dim][dim];
        maze.mazeMap = dfs(dim, 0.75, 0.75);
    }

    /*
     * This is the DGS algorithm, that starts with the maze full of walls and
     * starts opening paths. All Chambers have access to all the maze. Credit
     * goes to http://en.wikipedia.org/wiki/Maze_generation_algorithm @param
     * size the size of the maze @param complexity the complexity of the maze
     * @param density the density of the maze @return an array of chars
     *
     */
    public static char[][] dfs(int size, double complexity, double density) {
        // Convert size to odd number
        size = ((size / 2) * 2 + 1);
        complexity = (int) (complexity * (5 * (size + size)));
        density = (int) (density * (size / 2 * size / 2));
        boolean maze[][] = new boolean[size][size];
        MazeGame.maze.mazeDim = size;

        // fill the borders
        for (int i = 0; i < size; i++) {
            maze[0][i] = true;
            maze[size - 1][i] = true;
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
                ArrayList<int[]> neighbours = new ArrayList<>();
                if (x > 1) {
                    int aux[] = {0, 0};
                    aux[0] = y;
                    aux[1] = x - 2;
                    neighbours.add(aux);
                }
                if (x < size - 2) {
                    int aux[] = {0, 0};
                    aux[0] = y;
                    aux[1] = x + 2;
                    neighbours.add(aux);
                }
                if (y > 1) {
                    int aux[] = {0, 0}; // attention: m x n matrix =>
                    // array[n][m]
                    aux[0] = y - 2;
                    aux[1] = x;
                    neighbours.add(aux);
                }
                if (y < size - 2) {
                    int aux[] = {0, 0};
                    aux[0] = y + 2;
                    aux[1] = x;
                    neighbours.add(aux);
                }

                if (neighbours.size() > 0) {
                    int randNeighbour = r.nextInt(neighbours.size());
                    int _y = neighbours.get(randNeighbour)[0];
                    int _x = neighbours.get(randNeighbour)[1];
                    if (maze[_y][_x] == false) {
                        maze[_y][_x] = true;
                        maze[_y + (y - _y) / 2][_x + (x - _x) / 2] = true;
                        x = _x;
                        y = _y;
                    }
                }

            }

        }
        // move to array of chars
        char[][] mazeChar = new char[size][size];
        for (int l = 0; l < size; l++) {
            for (int m = 0; m < size; m++) {
                if (maze[l][m]) {
                    mazeChar[l][m] = 'X';
                } else {
                    mazeChar[l][m] = ' ';
                }
            }
        }
        return mazeChar;

    }
}
