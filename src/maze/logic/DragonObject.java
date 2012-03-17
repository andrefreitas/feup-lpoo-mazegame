package maze.logic;

public class DragonObject extends ObjectSelfMove{
	protected boolean isSleeping=false;
	protected int sleepSteps;
	protected static boolean canSleep=false;
	protected static boolean canMove=false;
	public DragonObject(char state, int x, int y) {
		super(state, x, y);
	}
	public static void enableCanSleep(){
		canSleep=true;
	}
	public static void enableCanMove(){
		canMove=true;
	}
	// move the dragon randomly
	public void move() {
		if (this.getState() == 'K' || canMove==false)
			return; // solves bug1 which would make the dragon continue moving
					// even after killed
		int x = 0, y = 0;
		int deltaX[] = { 0, 0, -1, 1, 0 };
		int deltaY[] = { -1, 1, 0, 0, 0 };
		java.util.Random r = new java.util.Random();
		// Handle sleeping
		int sleep;
		if(canSleep){
			if (isSleeping==false){
				sleep =r.nextInt(100);
				if(sleep<20) {
					isSleeping=true;
					MazeGame.maze.mazeMap[this.getY()][this.getX()] = 'd';
					state='d';
					sleepSteps=2;
					return; // exit move
				}
			}
			if(isSleeping==true){
				sleepSteps--;
				if(sleepSteps<=0) {
					isSleeping=false;
					state='D';
				}
				else return;  // exit move
			}
		}
		//--->
		
		do {
			int i = r.nextInt(5);
			x = deltaX[i];
			y = deltaY[i];
		} while (MazeGame.maze.mazeMap[this.getY() + y][this.getX() + x] == 'X'
				|| MazeGame.maze.mazeMap[this.getY() + y][this.getX() + x] == 'S');
	
		// the dragon only moves if not adjacent (if he is, then the game is
		// over)
		if (!GameObject.adjacentPosition(this, MazeGame.maze.hero)) {
			// blank the previous cell and move the dragon to the new one
			MazeGame.maze.mazeMap[this.getY()][this.getX()] = ' ';
			MazeGame.maze.mazeMap[this.getY() + y][this.getX() + x] = this.getState();
			MazeGame.updateObject(this, this.getX() + x, this.getY() + y,
					this.getState());
			// if the dragon goes to the cell which has the sword the status
			// changes to F
			if (GameObject.samePosition(this, MazeGame.maze.sword)) {
				MazeGame.maze.mazeMap[this.getY()][this.getX()] = 'F';
				MazeGame.updateObject(this, this.getX(), this.getY(), 'F');
				// when the dragon returns to a position other than the sword's
				// the status go back to normal
			} else if (GameObject.adjacentPosition(this, MazeGame.maze.sword)
					&& this.getState() == 'F') {
				MazeGame.updateObject(this, this.getX(), this.getY(), 'D');
				MazeGame.maze.mazeMap[this.getY()][this.getX()] = 'D';
				MazeGame.maze.mazeMap[MazeGame.maze.sword.getY()][MazeGame.maze.sword.getX()] = 'E';
			}
		}
	}

}
