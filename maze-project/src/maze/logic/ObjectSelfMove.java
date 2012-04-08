package maze.logic;

/**
 * ObjectSelfMove.java - a class for representing an object that moves by itself
 *
 * @author André Freitas, Vasco Gonçalves
 * @version 1.0
 * @see GameObject
 */
public abstract class ObjectSelfMove extends GameObject {

    /**
     * The constructor.
     *
     * @param state the state of the object that is the representation in the
     * maze
     * @param x the x position
     * @param y the y position
     */
    public ObjectSelfMove(char state, int x, int y) {
        super(state, x, y);
    }

    /**
     * The move function that needs to be implemented. An object that moves by
     * itself can move based in a proper algorithm, that should be further
     * defined in the classes that implement this function.
     */
    abstract void move();
}
