package maze.logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import javax.swing.JOptionPane;
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
    //TODO Verify CLI interface
    static boolean enableGui = true;
    // The game gui
    static MazeGUI gameGui;
    // The home screen gui
    public static HomeGUI homeGui = new HomeGUI();
    public static boolean optionsSet = false;
    public static boolean gameOver = false;

    public static void main(String args[]) throws IOException, ClassNotFoundException, InterruptedException {
        // Variables for options
        int mazeDim = 30;
        boolean saveExists = true;
        ObjectInputStream is = null;
        try {
            is = new ObjectInputStream(
                    new FileInputStream("save.dat"));
        } catch (IOException e) {
            saveExists = false;
        } finally {
            homeGui.setVisible(true);
            if (is != null) {
                int n = JOptionPane.showConfirmDialog(homeGui, "Do you want to load the previous game?", "Load Game", JOptionPane.YES_NO_OPTION);
                if (n == 0) {
                    maze = (Maze) is.readObject();
                    homeGui.setVisible(false);
                } else {
                    //if user doesn't want to load, delete the save file
                    is.close();
                    File f = new File("save.dat");
                    f.delete();
                    saveExists = false;
                }
                is.close();
            }
        }
        if (!saveExists) {
            maze = new Maze();
        }
        do {
            if (!saveExists) {
                // Without GUI
                if (!enableGui) {
                    MazeCLI.askOptions(mazeDim, maze.dragonOption, maze.nDragons);
                    maze.setDim(mazeDim);
                    MazeBuilder.generateMaze(mazeDim, maze);
                } // With GUI
                else {
                    do {
                        Thread.sleep(500);
                    } while (!optionsSet);
                }
                if (maze.dragonOption == 2) {
                    maze.dragonsCanMove = true;
                }
                if (maze.dragonOption == 3) {
                    maze.dragonsCanMove = true;
                    maze.dragonsCanSleep = true;
                }

            } else {
                optionsSet = true;
            }
            if (!enableGui) {
                // CLI interface
                do {
                    MazeCLI.printMaze();
                    maze.hero.move(MazeCLI.readKeyboardArrow());
                    maze.moveDragons();
                } while (gameOver() == 0);
                MazeCLI.printMaze();
            } else {
                startGui();
                // GUI interface
                do {
                    Thread.sleep(1000);
                    maze.moveDragons();
                    gameGui.repaint();
                } while (gameOver() == 0);
                File f = new File("save.dat");
                f.delete();
                if (!gameOver) {
                    gameGui.gameOver(gameOver());
                }
                gameOver = false;
                optionsSet = false;
            }


        } while (true);


    }

    public static void setOptions(int mazeDim, int dragonOp, int nDrag, char moveChars[]) {
        maze.setDim(mazeDim);
        maze.dragonOption = dragonOp;
        maze.nDragons = nDrag;
        maze.moveChars = moveChars;
    }

    public static void startGui() throws IOException {
        gameGui = new MazeGUI();
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
            x = r.nextInt(maze.mazeDim-6)+3;
            y = r.nextInt(maze.mazeDim-6)+3;

        } while (maze.mazeMap[x][y] == 'X' || maze.mazeMap[x][y] == 'H');
        maze.sword = new GameObject('E', x, y);
        maze.mazeMap[maze.sword.getY()][maze.sword.getX()] = maze.sword.getState();

        // Setup exit
        limitGen=maze.mazeDim /8;
        do {
            x = r.nextInt(limitGen)+(maze.mazeDim/2-maze.mazeDim/16);
            y = r.nextInt(limitGen)+(maze.mazeDim/2-maze.mazeDim/16);
        } while (maze.mazeMap[y][x] == 'X');
        maze.exit = new GameObject('S', maze.mazeDim/2, maze.mazeDim/2);
        maze.mazeMap[maze.exit.getY()][maze.exit.getX()] = maze.exit.getState();

        maze.dragons = new ArrayList<DragonObject>();

        for (int i = 0; i < maze.nDragons; i++) {
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

    public static int gameOver() {
        if (GameObject.samePosition(maze.hero, maze.exit)
                && maze.hero.getState() == 'A') {
            return 2;
        }
        for (int i = 0; i < maze.dragons.size(); i++) {
            if (GameObject.adjacentPosition(maze.hero, maze.dragons.get(i))
                    && (maze.hero.getState() == 'H' && (maze.dragons.get(i).getState() == 'D' || maze.dragons.get(i).getState() == 'F'))) {
                return 1;
            }
        }

        return 0;
    }

    // Update a gameobject calling the set functions
    public static void updateObject(GameObject a, int x, int y, char state) {
        a.setY(y);
        a.setX(x);
        a.setState(state);

    }
}
