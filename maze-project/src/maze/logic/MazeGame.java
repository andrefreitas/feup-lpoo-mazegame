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
 * MazeGame.java - A class for representing the game of the maze. The game: Our
 * hero wakes up after listening the voices of the dragons. Now, he need to get
 * out of the maze with a sword and kill all the dragons.
 *
 * @author André Freitas, Vasco Gonçalves
 * @version 1.0
 */
public class MazeGame {
    // The maze of the game

    public static Maze maze;
    // Enables the gui
    static boolean enableGui = true;
    // The game gui
    static MazeGUI gameGui;
    // The home screen gui
    public static HomeGUI homeGui = new HomeGUI();
    public static boolean optionsSet = false;
    public static boolean gameOver = false;

    /**
     * The main function
     *
     * @param args the arguments for the main
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws InterruptedException
     */
    public static void main(String args[]) throws IOException, ClassNotFoundException, InterruptedException {
        // Variables for options
        boolean saveExists = true;
        ObjectInputStream is = null;
        try {
            is = new ObjectInputStream(
                    new FileInputStream("save.dat"));
        } catch (IOException e) {
            saveExists = false;
        } finally {
            if (enableGui) {
                homeGui.setVisible(true);
            }
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
                    MazeCLI.askOptions();
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


            // Start the game
            if (!enableGui) {
                // CLI interface
                do {
                    MazeCLI.printMaze();
                    maze.hero.move(MazeCLI.readKeyboardArrow());
                    maze.moveDragons();
                } while (gameOver() == 0);
                MazeCLI.playAgain(gameOver());
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

    /**
     * Set all the necessary game options. Options like the Maze Dimension, the
     * Dragon Behaviour, the keyboard controls, etc
     *
     * @param mazeDim the maze Dimension
     * @param dragonOp the dragon behaviour
     * @param nDrag the number of dragons
     * @param moveChars the keyboard controls
     */
    public static void setOptions(int mazeDim, int dragonOp, int nDrag, char moveChars[]) {
        maze.setDim(mazeDim);
        maze.dragonOption = dragonOp;
        maze.nDragons = nDrag;
        maze.moveChars = moveChars;
    }

    /**
     * Starts a new Game GUI.
     *
     * @throws IOException
     */
    public static void startGui() throws IOException {
        gameGui = new MazeGUI();
    }

    /**
     * Setup all the objects in the maze about their position and initial state.
     * Note that this function behaves based in the user options given.
     */
    public static void setupObjects() {
        java.util.Random r = new java.util.Random();
        // Setup hero

        int limitGen = maze.mazeDim / 4;
        int x, y;
        do {
            x = r.nextInt(limitGen);
            y = r.nextInt(limitGen);

        } while (maze.mazeMap[x][y] == 'X');
        maze.hero = new HeroObject('H', x, y);
        maze.mazeMap[maze.hero.getY()][maze.hero.getX()] = maze.hero.getState();

        // Setup sword
        do {
            x = r.nextInt(maze.mazeDim - 6) + 3;
            y = r.nextInt(maze.mazeDim - 6) + 3;

        } while (maze.mazeMap[x][y] == 'X' || maze.mazeMap[x][y] == 'H');
        maze.sword = new GameObject('E', x, y);
        maze.mazeMap[maze.sword.getY()][maze.sword.getX()] = maze.sword.getState();

        // Setup exit
        maze.exit = new GameObject('S', maze.mazeDim / 2, maze.mazeDim / 2);
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
                    || maze.mazeMap[x][y] == 'S'
                    || GameObject.adjacentPosition(maze.dragons.get(i),
                    maze.hero) || maze.mazeMap[x][y] == 'H');
            maze.mazeMap[maze.dragons.get(i).getY()][maze.dragons.get(i).getX()] = maze.dragons.get(i).getState();
        }


    }

    /**
     * Evaluate if the Game is Over. The game can end if the hero is killed by a
     * Dragon or if the hero reached the exit with a sword.
     *
     * @return true if hero is killed or reached exit
     */
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

    /**
     * Update a specified object in the maze Game.
     *
     * @param a the object
     * @param x the new x position
     * @param y the new y position
     * @param state the new state
     */
    public static void updateObject(GameObject a, int x, int y, char state) {
        a.setY(y);
        a.setX(x);
        a.setState(state);

    }
}
