package maze.logic;
/**
 * ObjectIOMove.java - An abstract class for representing an object that moves by the user input
 * @author André Freitas, Vasco Gonçalves
 * @version 1.0
 * @see GameObject
 */
public abstract class ObjectIOMove extends GameObject {
    
    /** 
     * The constructor.
     * @param state the state of the object that is the representation in the maze
     * @param x the x position
     * @param y the y position
     */
    public ObjectIOMove(char state, int x, int y) {
        super(state, x, y);
    }
    /**
     * Move function that need to be implemented.
     * Receives a direction by the user and decides what to do.
     * @param direction the direction of the movement.
     */
    abstract void move(char direction);
}
