package maze.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import maze.cli.MazeCLI;
import maze.gui.*;

/**
 * ******************************************************
 * This class defines a maze game
 * ******************************************************
 */
public class MazeGame {
    // The maze of the game

    public static Maze maze = new Maze();
    // The option about the dragon type
    static int dragonOption;
    // The options have been set?
    static boolean optionSet=false;
    // The number of dragons
    static int nDragons;
    // Enables the gui
    static boolean enableGui = true;
    // The game gui
    static MazeGUI gameGui;
    // The home screen gui
    static HomeGUI homeGui;

    public static void main(String args[]) throws IOException {
        // Variables for options
        int mazeDim = 30;

        // Without GUI
        if (!enableGui) {
            MazeCLI.askOptions(mazeDim, dragonOption, nDragons);
            maze.setDim(mazeDim);
            optionSet=true;
            MazeBuilder.generateMaze(mazeDim, maze);
            play();
        } // With GUI
        else {
            homeGui.showGui();
        }
       
    }
    
    public static void setOptions(int mazeDim, int dragonOp, int nDrag){
        optionSet=true;
        maze.setDim(mazeDim);
        dragonOption=dragonOp;
        nDragons=nDrag;
    }
    
    public static void startGui() throws IOException{
            gameGui = new MazeGUI();
            gameGui.init();
            MazeBuilder.generateMaze(maze.mazeDim, maze);
            play();
    }

    // Wait implementation
    public static void wait(int n) {
        long t0, t1;
        t0 = System.currentTimeMillis();
        do {
            t1 = System.currentTimeMillis();
        } while (t1 - t0 < 1000);
    }

    // this function is called by main and is the game cycle itself
    public static void play() throws IOException {
        setupObjects();
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
        if (dragonOption == 2) {
            DragonObject.enableCanMove();
        }
        if (dragonOption == 3) {
            DragonObject.enableCanMove();
            DragonObject.enableCanSleep();
        }
        maze.dragons = new ArrayList<DragonObject>();

        for (int i = 0; i < nDragons; i++) {
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