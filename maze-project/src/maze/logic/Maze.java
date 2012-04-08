package maze.logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Maze.java - A class for representing the Maze matrix and its objects.
 *
 * @author André Freitas, Vasco Gonçalves
 * @version 1.0
 */
public class Maze implements Serializable {
    // the dimension of the maze: default is 10

    public int mazeDim = 10;
    // the maze array of chars: default is this
    public char[][] mazeMap = {
        {'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'},
        {'X', 'H', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
        {'X', ' ', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X'},
        {'X', 'D', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X'},
        {'X', ' ', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X'},
        {'X', ' ', ' ', ' ', ' ', ' ', ' ', 'X', ' ', 'S'},
        {'X', ' ', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X'},
        {'X', ' ', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X'},
        {'X', 'E', 'X', 'X', ' ', ' ', ' ', ' ', ' ', 'X'},
        {'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'}};
    // the list of dragons
    public ArrayList<DragonObject> dragons = new ArrayList<DragonObject>(Arrays.asList(new DragonObject('D', 1, 3)));
    // an hero
    public HeroObject hero = new HeroObject('H', 1, 1);
    // an exit
    public GameObject exit = new GameObject('S', 9, 5);
    // a sword
    public GameObject sword = new GameObject('E', 1, 8);
    public char moveChars[] = {'W', 'A', 'S', 'D'};
    // The number of dragons
    public int nDragons = 1;
    // The option about the dragon type
    public int dragonOption;
    public boolean dragonsCanSleep = false;
    public boolean dragonsCanMove = false;

    /**
     * Call all the dragons to move.
     */
    public void moveDragons() {
        for (int i = 0; i < dragons.size(); i++) {
            dragons.get(i).move();
        }
    }

    /**
     * Set the hero in the maze.
     *
     * @param h the hero.
     */
    public void setHero(HeroObject h) {
        hero = h;
    }

    /**
     * Set the exit in the maze.
     *
     * @param e
     */
    public void setExit(GameObject e) {
        exit = e;
    }

    /**
     * Set the sword in the maze.
     *
     * @param s
     */
    public void setSword(GameObject s) {
        sword = s;
    }

    /**
     * Set the dragons in the maze.
     *
     * @param d
     */
    public void setDragons(ArrayList<DragonObject> d) {
        dragons = d;
    }

    /**
     * Set the array of chars that represents the maze.
     *
     * @param mazeM
     */
    public void setMazeMap(char[][] mazeM) {
        mazeMap = mazeM;
    }

    /**
     * Set the mase dimension.
     *
     * @param dim the dimension
     */
    public void setDim(int dim) {
        mazeDim = dim;
    }
}
