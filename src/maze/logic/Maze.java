package maze.logic;
/********************************************************
 * This class defines a generic maze
 ********************************************************/
public class Maze {
	/* Attributes ================== */
	public static int mazeDim[] = { 10, 10 };
	public static char[][] mazeMap = {
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
	public static ObjectSelfMove dragon;
	public static ObjectIOMove hero;
	public static GameObject exit;
	public static GameObject sword;

}
