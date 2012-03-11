package maze.test;

import static org.junit.Assert.*;

import maze.logic.GameObject;

import org.junit.Test;

public class GameObjectTest {

	@Test
	public void testGameObject() {
		GameObject g1 = new GameObject('A', 1, 2);
		assertEquals('A', g1.state);
		assertEquals(1, g1.x);
		assertEquals(2, g1.y);
	}

	@Test
	public void testGetState() {
		GameObject g1 = new GameObject('A', 1, 2);
		assertEquals('A', g1.getState());
	}

	@Test
	public void testGetX() {
		GameObject g1 = new GameObject('A', 1, 2);
		assertEquals(1, g1.getX());
	}

	@Test
	public void testGetY() {
		GameObject g1 = new GameObject('A', 1, 2);
		assertEquals(2, g1.getY());
	}

	@Test
	public void testSetState() {
		GameObject g1 = new GameObject('A', 1, 2);
		g1.setState('B');
		assertEquals('B', g1.state);
	}

	@Test
	public void testSetX() {
		GameObject g1 = new GameObject('A', 1, 2);
		g1.setX(3);
		assertEquals(3, g1.x);
	}

	@Test
	public void testSetY() {
		GameObject g1 = new GameObject('A', 1, 2);
		g1.setY(3);
		assertEquals(3, g1.y);
	}

	@Test
	public void testSamePosition() {
		GameObject g1 = new GameObject('A', 1, 2);
		GameObject g2 = new GameObject('B', 1, 2);
		assertEquals(true, GameObject.samePosition(g1, g2));
	}

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
