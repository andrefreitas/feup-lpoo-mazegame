package maze.test;

import maze.logic.GameObject;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * GameObjectTest.java - A class for testing the GameObject in the Maze Game
 *
 * @author André Freitas, Vasco Gonçalves
 * @see GameObject
 */
public class GameObjectTest {

    /**
     * Test the creation of a Game Object.
     */
    @Test
    public void testGameObject() {
        GameObject g1 = new GameObject('A', 1, 2);
        assertEquals('A', g1.state);
        assertEquals(1, g1.x);
        assertEquals(2, g1.y);
    }

    /**
     * Test getting the state.
     */
    @Test
    public void testGetState() {
        GameObject g1 = new GameObject('A', 1, 2);
        assertEquals('A', g1.getState());
    }

    /**
     * Test getting the x position.
     */
    @Test
    public void testGetX() {
        GameObject g1 = new GameObject('A', 1, 2);
        assertEquals(1, g1.getX());
    }

    /**
     * Test getting the y position.
     */
    @Test
    public void testGetY() {
        GameObject g1 = new GameObject('A', 1, 2);
        assertEquals(2, g1.getY());
    }
    
    /**
     * Test setting the state.
     */
    @Test
    public void testSetState() {
        GameObject g1 = new GameObject('A', 1, 2);
        g1.setState('B');
        assertEquals('B', g1.state);
    }
    
    /** 
     * Test setting the x position.
     */
    @Test
    public void testSetX() {
        GameObject g1 = new GameObject('A', 1, 2);
        g1.setX(3);
        assertEquals(3, g1.x);
    }

    /**
     * Test setting the y position.
     */
    @Test
    public void testSetY() {
        GameObject g1 = new GameObject('A', 1, 2);
        g1.setY(3);
        assertEquals(3, g1.y);
    }

    /** 
     * Test if the samePosition function works.
     */
    @Test
    public void testSamePosition() {
        GameObject g1 = new GameObject('A', 1, 2);
        GameObject g2 = new GameObject('B', 1, 2);
        assertEquals(true, GameObject.samePosition(g1, g2));
    }
     /** 
     * Test if the AdjacentPosition function works.
     */
    @Test
    public void testAdjacentPosition() {
        GameObject g1 = new GameObject('A', 1, 2);
        GameObject g2 = new GameObject('B', 2, 2);
        assertEquals(true, GameObject.adjacentPosition(g1, g2));
        g1.setX(3);
        assertEquals(true, GameObject.adjacentPosition(g1, g2));
        g1.setX(2);
        g1.setY(1);
        assertEquals(true, GameObject.adjacentPosition(g1, g2));
        g1.setY(3);
        assertEquals(true, GameObject.adjacentPosition(g1, g2));
    }
}
