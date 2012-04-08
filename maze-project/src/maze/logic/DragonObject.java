package maze.logic;

import java.io.Serializable;

/**
 * DragonObject.java - a class for representing a Dragon that moves by itself.
 * This class implements the abstract class ObjectSelfMove
 *
 * @author André Freitas, Vasco Gonçalves
 * @version 1.0
 * @see ObjectSelfMove
 */
public class DragonObject extends ObjectSelfMove implements Serializable {

    protected boolean isSleeping = false;
    protected int sleepSteps;

    /**
     * Constructor that receives the data
     *
     * @param state the representation of the Dragon
     * @param x the x-axis position
     * @param y the y-axis position
     */
    public DragonObject(char state, int x, int y) {
        super(state, x, y);
    }

    /**
     * Move the dragon randomly. This is pured random, so the Dragon isn't
     * intelligent! Remember that the Dragon can sleep sometimes, so the Dragon
     * behaviour given by the user is beeing considered here.
     *
     */
    @Override
    public void move() {
        if (this.getState() == 'K' || MazeGame.maze.dragonsCanMove == false) {
            return; // solves bug1 which would make the dragon continue moving even after killed
        }
        int xpos = 0, ypos = 0;
        int deltaX[] = {0, 0, -1, 1, 0};
        int deltaY[] = {-1, 1, 0, 0, 0};
        java.util.Random r = new java.util.Random();

        // Handle sleeping
        int sleep;
        if (MazeGame.maze.dragonsCanSleep) {
            if (isSleeping == false) {
                sleep = r.nextInt(100);
                if (sleep < 20 && !samePosition(this, MazeGame.maze.sword)) {
                    isSleeping = true;
                    MazeGame.maze.mazeMap[this.getY()][this.getX()] = 'd';
                    state = 'd';
                    sleepSteps = 2;
                    return; // exit move
                }
            }
            if (isSleeping == true) {
                sleepSteps--;
                if (sleepSteps <= 0) {
                    isSleeping = false;
                    state = 'D';
                } else {
                    return;  // exit move
                }
            }
        }
        // Handles the movement
        do {
            int i = r.nextInt(5);
            xpos = deltaX[i];
            ypos = deltaY[i];
        } while (MazeGame.maze.mazeMap[this.getY() + ypos][this.getX() + xpos] == 'X'
                || MazeGame.maze.mazeMap[this.getY() + ypos][this.getX() + xpos] == 'S');

        // the dragon only moves if not adjacent (if he is, then the game is over)
        if (!GameObject.adjacentPosition(this, MazeGame.maze.hero)) {
            // blank the previous cell and move the dragon to the new one
            MazeGame.maze.mazeMap[this.getY()][this.getX()] = ' ';
            MazeGame.maze.mazeMap[this.getY() + ypos][this.getX() + xpos] = this.getState();
            MazeGame.updateObject(this, this.getX() + xpos, this.getY() + ypos,
                    this.getState());
            // if the dragon goes to the cell which has the sword the status changes to F
            if (GameObject.samePosition(this, MazeGame.maze.sword)) {
                MazeGame.maze.mazeMap[this.getY()][this.getX()] = 'F';
                MazeGame.updateObject(this, this.getX(), this.getY(), 'F');
                // when the dragon returns to a position other than the sword's the status go back to normal
            } else if (GameObject.adjacentPosition(this, MazeGame.maze.sword)
                    && this.getState() == 'F') {
                MazeGame.updateObject(this, this.getX(), this.getY(), 'D');
                MazeGame.maze.mazeMap[this.getY()][this.getX()] = 'D';
                MazeGame.maze.mazeMap[MazeGame.maze.sword.getY()][MazeGame.maze.sword.getX()] = 'E';
            }
        }
    }
}
