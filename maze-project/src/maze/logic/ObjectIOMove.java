package maze.logic;

public abstract class ObjectIOMove extends GameObject {

    public ObjectIOMove(char state, int x, int y) {
        super(state, x, y);
    }

    abstract void move(char direction);
}
