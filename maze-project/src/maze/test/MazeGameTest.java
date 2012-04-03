package maze.test;

import maze.logic.MazeGame;
import static org.junit.Assert.*;
import org.junit.Test;

public class MazeGameTest {

    @Test
    public void testHeroBasicMoves() {
        MazeGame.setupObjects();
        // test hero impossible moves
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

    @Test
    public void testHeroDie() {
        MazeGame.setupObjects();
        MazeGame.maze.hero.move('S');
        assertEquals(true, MazeGame.gameOver());
    }

    @Test
    public void testHeroCantExit() {
        MazeGame.setupObjects();
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

    @Test
    public void testHeroKillDragon() {
        MazeGame.setupObjects();
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

    @Test
    public void testHeroArmed() {
        MazeGame.setupObjects();
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

    @Test
    public void testHeroCanExit() {
        MazeGame.updateObject(MazeGame.maze.hero, 8, 5, 'A');
        MazeGame.maze.hero.move('D');
        assertEquals(MazeGame.gameOver(), true);
    }

    @Test
    public void testDragonSleepingKill() {
        MazeGame.setupObjects();
        MazeGame.maze.hero.move('S');
        MazeGame.maze.dragons.get(0).setState('d');
        assertEquals(false, MazeGame.gameOver());
    }
}
