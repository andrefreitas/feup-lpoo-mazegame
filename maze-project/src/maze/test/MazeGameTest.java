package maze.test;

import maze.cli.MazeCLI;
import maze.logic.Maze;
import maze.logic.MazeGame;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * MazeGameTest.java - a class for testing the game rules and objects movements.
 *
 * @author André Freitas, Vasco Gonçalves
 * @version 1.0
 * @see MazeGame
 */
public class MazeGameTest {

    /**
     * Test the possible and impossible moves of the hero.
     */
    @Test
    public void testHeroBasicMoves() {
        // test hero impossible moves
    	MazeGame.maze= new Maze();
        MazeGame.maze.hero.move('W');
        assertEquals(MazeGame.maze.hero.getX(), 1);
        assertEquals(MazeGame.maze.hero.getY(), 1);
        MazeGame.maze.hero.move('A');
        assertEquals(MazeGame.maze.hero.getX(), 1);
        assertEquals(MazeGame.maze.hero.getY(), 1);
        // test possible moves
        MazeGame.maze.hero.move('D');
        assertEquals(MazeGame.maze.hero.getX(), 2);
        assertEquals(MazeGame.maze.hero.getY(), 1);
        MazeGame.maze.hero.move('D');
        MazeGame.maze.hero.move('D');
        MazeGame.maze.hero.move('S');
        assertEquals(MazeGame.maze.hero.getX(), 4);
        assertEquals(MazeGame.maze.hero.getY(), 2);
    }

    /**
     * Test if the hero die when goes to the same position of the dragon.
     */
    @Test
    public void testHeroDie() {
    	MazeGame.maze= new Maze();
        MazeGame.maze.hero.move('S');
        assertEquals(1, MazeGame.gameOver()); // return is 1 when dies
    }

    /**
     * Test if the hero can't exit when he doesn't have a sword.
     */
    @Test
    public void testHeroCantExit() {
    	MazeGame.maze= new Maze();
        for (int i = 0; i < 7; i++) {
            MazeGame.maze.hero.move('D');
        }
        for (int i = 0; i < 4; i++) {
            MazeGame.maze.hero.move('S');
        }
        MazeGame.maze.hero.move('D');
        assertEquals(MazeGame.maze.hero.getX(), 8);
        assertEquals(MazeGame.maze.hero.getY(), 5);
    }

    /**
     * Test when the hero have a sword and kills the dragon.
     */
    @Test
    public void testHeroKillDragon() {
    	MazeGame.maze= new Maze();
        for (int i = 0; i < 3; i++) {
            MazeGame.maze.hero.move('D');
        }
        for (int i = 0; i < 4; i++) {
            MazeGame.maze.hero.move('S');
        }
        for (int i = 0; i < 4; i++) {
            MazeGame.maze.hero.move('A');
        }
        for (int i = 0; i < 4; i++) {
            MazeGame.maze.hero.move('S');
        }
        // go the sword, now kill the dragon
        for (int i = 0; i < 4; i++) {
            MazeGame.maze.hero.move('W');
        }
        assertEquals(MazeGame.maze.dragons.get(0).getState(), 'K');
        assertEquals(MazeGame.maze.mazeMap[3][1], ' ');
    }

    /**
     * Test when the hero get's a sword and becames Armed.
     */
    @Test
    public void testHeroArmed() {
    	MazeGame.maze= new Maze();
        assertEquals(MazeGame.maze.hero.getState(), 'H');
        for (int i = 0; i < 3; i++) {
            MazeGame.maze.hero.move('D');
        }
        for (int i = 0; i < 4; i++) {
            MazeGame.maze.hero.move('S');
        }
        for (int i = 0; i < 4; i++) {
            MazeGame.maze.hero.move('A');
        }
        for (int i = 0; i < 4; i++) {
            MazeGame.maze.hero.move('S');
        }
        assertEquals(MazeGame.maze.hero.getState(), 'A');
    }
    
    /**
     * Test when the hero can exit when he is armed.
     */

    @Test
    public void testHeroCanExit() {
        MazeGame.updateObject(MazeGame.maze.hero, 8, 5, 'A');
        MazeGame.maze.hero.move('D');
        assertEquals(MazeGame.gameOver(), 2); // success return is 2
    }

    /**
     * Test when the dragon is sleeping and can't kill the hero
     */
    @Test
    public void testDragonSleepingKill() {
    	MazeGame.maze= new Maze();
        MazeGame.maze.hero.move('S');
        MazeGame.maze.dragons.get(0).setState('d');
        assertEquals(0, MazeGame.gameOver()); // must be 0, because is sleeping
    }
}
