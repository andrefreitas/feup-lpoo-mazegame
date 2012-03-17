package maze.logic;

import java.util.ArrayList;

/********************************************************
 * This class defines a generic maze
 ********************************************************/
public class Maze {
	/* Attributes ================== */
	public int mazeDim[] = { 10, 10 };
	public char[][] mazeMap = {
	{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' },
	{ 'X', 'H', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
	{ 'X', ' ', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X' },
	{ 'X', 'D', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X' },
	{ 'X', ' ', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X' },
	{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', 'X', ' ', 'S' },
	{ 'X', ' ', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X' },
	{ 'X', ' ', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X' },
	{ 'X', 'E', 'X', 'X', ' ', ' ', ' ', ' ', ' ', 'X' },
	{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' } };
	public DragonObject dragon;
	public heroObject hero;
	public GameObject exit;
	public GameObject sword;

}
