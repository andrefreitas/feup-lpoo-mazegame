package maze.logic;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class defines a maze that have a map and its objects
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
    public ArrayList<DragonObject> dragons;
    // an hero
    public heroObject hero;
    // an exit
    public GameObject exit;
    // a sword
    public GameObject sword;
    public char moveChars[] = {'W', 'A', 'S', 'D'};
    // The number of dragons
    public int nDragons;
    // The option about the dragon type
    public int dragonOption;
    public boolean dragonsCanSleep=false;
    public boolean dragonsCanMove=false;

    // call every dragon to move
    public void moveDragons() {
        for (int i = 0; i < dragons.size(); i++) {
            dragons.get(i).move();
        }
    }

    public void setDim(int dim) {
        mazeDim = dim;
    }
}
