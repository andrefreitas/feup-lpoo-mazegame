package maze.logic;

public abstract class ObjectSelfMove extends GameObject {

	public ObjectSelfMove(char state, int x, int y) {
		super(state, x, y);
	}
	
	abstract void move();

}
