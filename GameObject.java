/**
 * @version 1.0
 * @created 27-Fev-2012 14:59:07
 */
public class GameObject {

	protected char state;
	protected int x;
	protected int y;

	public GameObject() {

	}

	public void finalize() throws Throwable {

	}

	/**
	 * 
	 * @param state
	 * @param y
	 * @param x
	 */
	public GameObject(char state, int x, int y) {
		this.state = state;
		this.x = x;
		this.y = y;
	}

	public char getState() {
		return state;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	/**
	 * 
	 * @param state
	 */
	public void setState(char state) {
		this.state = state;
	}

	/**
	 * 
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * 
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}

	public static boolean samePosition(GameObject a, GameObject b) {
		return (a.getX() == b.getX() && a.getY() == b.getY());
	}

	public static boolean adjacentPosition(GameObject a, GameObject b) {
		if (java.lang.Math.abs(a.getX() - b.getX()) == 1)
			return (a.getY() - b.getY() == 0);
		if (a.getX() - b.getX() == 0)
			return (java.lang.Math.abs(a.getY() - b.getY()) == 1);
		if (java.lang.Math.abs(a.getY() - b.getY()) == 1)
			return (a.getX() - b.getX() == 0);
		if (a.getY() - b.getY() == 0)
			return (java.lang.Math.abs(a.getX() - b.getX()) == 1);
		
		return false;
	}
}