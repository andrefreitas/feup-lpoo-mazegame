package maze.logic;

import java.io.Serializable;

/**
 * ******************************************************
 * This class defines a generic object in the maze
 * ******************************************************
 */
public class GameObject implements Serializable {

    /*
     * Attributes ==================
     */
    public char state; // the state that appears in the maze cell
    public int x; // the x-axis position
    public int y; // the y-axis position

    /*
     * Constructor *==================
     */
    public GameObject(char state, int x, int y) {
        this.state = state;
        this.x = x;
        this.y = y;
    }

    /*
     * Get Methods ==================
     */
    // get the state
    public char getState() {
        return state;
    }
    // get x position

    public int getX() {
        return x;
    }
    // get y position

    public int getY() {
        return y;
    }

    /*
     * Set Methods ==================
     */
    // set the state
    public void setState(char state) {
        this.state = state;
    }
    // set the x

    public void setX(int x) {
        this.x = x;
    }
    // set the y

    public void setY(int y) {
        this.y = y;
    }
    /*
     * Others ==================
     */
    // this function evaluates if 2 objects are in the same position

    public static boolean samePosition(GameObject a, GameObject b) {
        return (a.getX() == b.getX() && a.getY() == b.getY());
    }
    // this function evaluates if 2 objects are adjacent to each other

    public static boolean adjacentPosition(GameObject a, GameObject b) {
        if (java.lang.Math.abs(a.getX() - b.getX()) == 1) {
            return (a.getY() - b.getY() == 0);
        }
        if (a.getX() - b.getX() == 0) {
            return (java.lang.Math.abs(a.getY() - b.getY()) == 1);
        }
        if (java.lang.Math.abs(a.getY() - b.getY()) == 1) {
            return (a.getX() - b.getX() == 0);
        }
        if (a.getY() - b.getY() == 0) {
            return (java.lang.Math.abs(a.getX() - b.getX()) == 1);
        }

        return false;
    }
}