package maze.logic;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import maze.cli.MazeCLI;
import maze.gui.HomeGUI;
import maze.gui.MazeGUI;

/**
 * ******************************************************
 * This class defines a maze game
 * ******************************************************
 */
public class MazeGame {
    // The maze of the game

    public static Maze maze;
    // Enables the gui
    static boolean enableGui = true;
    // The game gui
    static MazeGUI gameGui;
    // The home screen gui
    static HomeGUI homeGui;
    public static boolean optionsSet = false;

    public static void main(String args[]) throws IOException, ClassNotFoundException {
        // Variables for options
        int mazeDim = 30;
        boolean saveExists = true;
        ObjectInputStream is = null;
        try {
            is = new ObjectInputStream(
                    new FileInputStream("save.dat"));
            maze = (Maze) is.readObject();
        } catch (IOException e) {
            saveExists = false;
        } finally {
            if (is != null) {
                is.close();
            }
        }

        if (!saveExists) {
            maze = new Maze();
            // Without GUI
            if (!enableGui) {
                MazeCLI.askOptions(mazeDim, Maze.dragonOption, Maze.nDragons);
                maze.setDim(mazeDim);
                MazeBuilder.generateMaze(mazeDim, maze);
            } // With GUI
            else {
                HomeGUI.showGui();
                do {
                    wait(1);
                } while (!optionsSet);
            }
            setupObjects();
        } else {
            optionsSet = true;
        }
        if (!enableGui) {
            // CLI interface
            do {
                MazeCLI.printMaze();
                maze.hero.move(MazeCLI.readKeyboardArrow());
                maze.moveDragons();
            } while (!gameOver());
            MazeCLI.printMaze();
        } else {
            // GUI interface
            do {
                maze.moveDragons();
                gameGui.frame.repaint();
                wait(2);

            } while (!gameOver());
            gameGui.gameOver();
        }

    }

    public static void setOptions(int mazeDim, int dragonOp, int nDrag, char moveChars[]) {
        maze.setDim(mazeDim);
        Maze.dragonOption = dragonOp;
        Maze.nDragons = nDrag;
        Maze.moveChars = moveChars;
    }

    public static void startGui() throws IOException {
        MazeBuilder.generateMaze(maze.mazeDim, maze);
        gameGui = new MazeGUI();
        gameGui.init();
    }

    // Wait implementation
    public static void wait(int n) {
        long t0, t1;
        t0 = System.currentTimeMillis();
        do {
            t1 = System.currentTimeMillis();
        } while (t1 - t0 < 1000);
    }

    // setup the objects with their initial positions and states
    public static void setupObjects() {
        java.util.Random r = new java.util.Random();
        // Setup hero

        int limitGen = maze.mazeDim / 4;
        int x, y;
        do {
            x = r.nextInt(limitGen);
            y = r.nextInt(limitGen);

        } while (maze.mazeMap[x][y] == 'X');
        maze.hero = new heroObject('H', x, y);
        maze.mazeMap[maze.hero.getY()][maze.hero.getX()] = maze.hero.getState();

        // Setup sword
        do {
            x = r.nextInt(maze.mazeDim);
            y = r.nextInt(maze.mazeDim);

        } while (maze.mazeMap[x][y] == 'X' || maze.mazeMap[x][y] == 'H');
        maze.sword = new GameObject('E', x, y);
        maze.mazeMap[maze.sword.getY()][maze.sword.getX()] = maze.sword.getState();

        // Setup exit

        do {
            y = r.nextInt((maze.mazeDim - 1) / 2 - 1) + (maze.mazeDim - 1) / 2;
        } while (maze.mazeMap[y][maze.mazeDim - 2] == 'X');
        maze.exit = new GameObject('S', maze.mazeDim - 1, y);
        maze.mazeMap[maze.exit.getY()][maze.exit.getX()] = maze.exit.getState();

        // Dragons --->
        if (Maze.dragonOption == 2) {
            DragonObject.enableCanMove();
        }
        if (Maze.dragonOption == 3) {
            DragonObject.enableCanMove();
            DragonObject.enableCanSleep();
        }
        maze.dragons = new ArrayList<>();

        for (int i = 0; i < Maze.nDragons; i++) {
            int n = 0;
            do {
                n++;
                x = r.nextInt(maze.mazeDim);
                y = r.nextInt(maze.mazeDim);
                if (n == 1) {
                    maze.dragons.add(new DragonObject('D', x, y));
                } else {
                    maze.dragons.set(i, new DragonObject('D', x, y));
                }

            } while (maze.mazeMap[x][y] == 'X'
                    || maze.mazeMap[x][y] == 'E'
                    || maze.mazeMap[x][y] == 'D'
                    || GameObject.adjacentPosition(maze.dragons.get(i),
                    maze.hero) || maze.mazeMap[x][y] == 'H');
            maze.mazeMap[maze.dragons.get(i).getY()][maze.dragons.get(i).getX()] = maze.dragons.get(i).getState();
        }


    }

    public static boolean gameOver() {
        if (GameObject.samePosition(maze.hero, maze.exit)
                && maze.hero.getState() == 'A') {
            return true;
        }
        for (int i = 0; i < maze.dragons.size(); i++) {
            if (GameObject.adjacentPosition(maze.hero, maze.dragons.get(i))
                    && (maze.hero.getState() == 'H' && (maze.dragons.get(i).getState() == 'D' || maze.dragons.get(i).getState() == 'F'))) {
                return true;
            }
        }

        return false;
    }

    // Update a gameobject calling the set functions
    public static void updateObject(GameObject a, int x, int y, char state) {
        a.setY(y);
        a.setX(x);
        a.setState(state);

    }
}