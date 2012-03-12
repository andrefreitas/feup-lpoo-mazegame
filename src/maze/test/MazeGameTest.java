package maze.test;
import maze.logic.MazeGame;
import maze.cli.MazeCLI;

import static org.junit.Assert.*;

import org.junit.Test;

public class MazeGameTest {

	@Test
	public void testHeroBasicMoves() {
		MazeGame.setupObjects();
		// test hero impossible moves
		MazeGame.moveHero('W');
		assertEquals(MazeGame.hero.getX(), 1);
		assertEquals(MazeGame.hero.getY(), 1);
		MazeGame.moveHero('A');
		assertEquals(MazeGame.hero.getX(), 1);
		assertEquals(MazeGame.hero.getY(), 1);
		// test possible moves
		MazeGame.moveHero('D');
		assertEquals(MazeGame.hero.getX(), 2);
		assertEquals(MazeGame.hero.getY(), 1);
		MazeGame.moveHero('D');
		MazeGame.moveHero('D');
		MazeGame.moveHero('S');
		assertEquals(MazeGame.hero.getX(), 4);
		assertEquals(MazeGame.hero.getY(), 2);
	}
	@Test
	public void testHeroDie(){
		MazeGame.setupObjects();
		MazeGame.moveHero('S');
		assertEquals(true,MazeGame.gameOver());
	}
	
	@Test
	public void testHeroCantExit(){
		MazeGame.setupObjects();
		for(int i=0; i<7;i++)
			MazeGame.moveHero('D');
		for(int i=0; i<4;i++)
			MazeGame.moveHero('S');
		MazeGame.moveHero('D');
		assertEquals(MazeGame.hero.getX(),8);
		assertEquals(MazeGame.hero.getY(),5);
	}
	@Test
	public void testHeroKillDragon(){
		MazeGame.setupObjects();
		for(int i=0; i<3;i++)
			MazeGame.moveHero('D');
		for(int i=0; i<4;i++)
			MazeGame.moveHero('S');
		for(int i=0; i<4;i++)
			MazeGame.moveHero('A');
		for(int i=0; i<4;i++)
			MazeGame.moveHero('S');
		// go the sword, now kill the dragon
		for(int i=0; i<4;i++)
			MazeGame.moveHero('W');
		assertEquals(MazeGame.dragon.getState(),'K');
		assertEquals(MazeGame.mazeMap[3][1],' ');
	}
	@Test
	public void testHeroArmed(){
		MazeGame.setupObjects();
		assertEquals(MazeGame.hero.getState(),'H');
		for(int i=0; i<3;i++)
			MazeGame.moveHero('D');
		for(int i=0; i<4;i++)
			MazeGame.moveHero('S');
		for(int i=0; i<4;i++)
			MazeGame.moveHero('A');
		for(int i=0; i<4;i++)
			MazeGame.moveHero('S');
		assertEquals(MazeGame.hero.getState(),'A');
	}
	
	@Test
	public void testHeroCanExit(){
		MazeGame.updateObject(MazeGame.hero, 8, 5, 'A');
		MazeGame.moveHero('D');
		assertEquals(MazeGame.gameOver(),true);
	}
}
