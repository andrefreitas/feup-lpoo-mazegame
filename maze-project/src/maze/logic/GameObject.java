package maze.logic;

import java.io.Serializable;

/**
 * GameObject.java - A class for representing a generic object in the Maze.
 *
 * @author André Freitas, Vasco Gonçalves
 * @version 1.0
 */
public class GameObject implements Serializable {

    public char state; // the state that appears in the maze cell
    public int x; // the x-axis position
    public int y; // the y-axis position

    /**
     * The constructor of the GameObject
     *
     * @param state the representation of that object in the maze
     * @param x the x-axis position
     * @param y the y-axis position
     */
    public GameObject(char state, int x, int y) {
        this.state = state;
        this.x = x;
        this.y = y;
    }

    /**
     * Get the object state
     *
     * @return a char type
     */
    public char getState() {
        return state;
    }

    /**
     * Get the x-axis position
     *
     * @return an integer type
     */
    public int getX() {
        return x;
    }

    /**
     * Get the y-axis position
     *
     * @return an integer type
     */
    public int getY() {
        return y;
    }

    /**
     * Set the state of the object
     *
     * @param state the new state
     */
    public void setState(char state) {
        this.state = state;
    }

    /**
     * Set the x-axis position
     *
     * @param x the new x position
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Set the y-axis position
     *
     * @param y the new y position
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Evaluates if 2 objects are in the same position
     *
     * @param a an object
     * @param b other object
     * @return true if they are int the same position
     */
    public static boolean samePosition(GameObject a, GameObject b) {
        return (a.getX() == b.getX() && a.getY() == b.getY());
    }

    /**
     * This function evaluates if 2 objects are adjacent to each other.
     * Diagonals are not adjacent.
     *
     * @param a an object
     * @param b other object
     * @return true if the objects are adjacent to each other
     */
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