package maze.logic;
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
	public ObjectSelfMove dragon;
	public ObjectIOMove hero;
	public GameObject exit;
	public GameObject sword;

}
