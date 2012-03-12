package maze.test;
import maze.logic.Maze;
import maze.logic.MazeGame;
import maze.logic.ObjectIOMove;
import static org.junit.Assert.*;

import org.junit.Test;

public class MazeGameTest {

	@Test
	public void testHeroBasicMoves() {
		MazeGame.setupObjects();
		// test hero impossible moves
		ObjectIOMove.moveHero('W');
		assertEquals(Maze.hero.getX(), 1);
		assertEquals(Maze.hero.getY(), 1);
		ObjectIOMove.moveHero('A');
		assertEquals(Maze.hero.getX(), 1);
		assertEquals(Maze.hero.getY(), 1);
		// test possible moves
		ObjectIOMove.moveHero('D');
		assertEquals(Maze.hero.getX(), 2);
		assertEquals(Maze.hero.getY(), 1);
		ObjectIOMove.moveHero('D');
		ObjectIOMove.moveHero('D');
		ObjectIOMove.moveHero('S');
		assertEquals(Maze.hero.getX(), 4);
		assertEquals(Maze.hero.getY(), 2);
	}
	@Test
	public void testHeroDie(){
		MazeGame.setupObjects();
		ObjectIOMove.moveHero('S');
		assertEquals(true,MazeGame.gameOver());
	}
	
	@Test
	public void testHeroCantExit(){
		MazeGame.setupObjects();
		for(int i=0; i<7;i++)
			ObjectIOMove.moveHero('D');
		for(int i=0; i<4;i++)
			ObjectIOMove.moveHero('S');
		ObjectIOMove.moveHero('D');
		assertEquals(Maze.hero.getX(),8);
		assertEquals(Maze.hero.getY(),5);
	}
	@Test
	public void testHeroKillDragon(){
		MazeGame.setupObjects();
		for(int i=0; i<3;i++)
			ObjectIOMove.moveHero('D');
		for(int i=0; i<4;i++)
			ObjectIOMove.moveHero('S');
		for(int i=0; i<4;i++)
			ObjectIOMove.moveHero('A');
		for(int i=0; i<4;i++)
			ObjectIOMove.moveHero('S');
		// go the sword, now kill the dragon
		for(int i=0; i<4;i++)
			ObjectIOMove.moveHero('W');
		assertEquals(Maze.dragon.getState(),'K');
		assertEquals(Maze.mazeMap[3][1],' ');
	}
	@Test
	public void testHeroArmed(){
		MazeGame.setupObjects();
		assertEquals(Maze.hero.getState(),'H');
		for(int i=0; i<3;i++)
			ObjectIOMove.moveHero('D');
		for(int i=0; i<4;i++)
			ObjectIOMove.moveHero('S');
		for(int i=0; i<4;i++)
			ObjectIOMove.moveHero('A');
		for(int i=0; i<4;i++)
			ObjectIOMove.moveHero('S');
		assertEquals(Maze.hero.getState(),'A');
	}
	
	@Test
	public void testHeroCanExit(){
		MazeGame.updateObject(Maze.hero, 8, 5, 'A');
		ObjectIOMove.moveHero('D');
		assertEquals(MazeGame.gameOver(),true);
	}
}
