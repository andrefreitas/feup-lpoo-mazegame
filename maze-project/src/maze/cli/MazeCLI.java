package maze.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import maze.logic.MazeBuilder;
import maze.logic.MazeGame;

/**
 * MazeCLI.java - a class for representing the maze in command line interface
 * @author André Freitas, Vasco Gonçalves
 * @version 1.0
 */
public class MazeCLI {

    /**
     * Read a character pressed from the keyboard. 
     * This function only returns a char if the key pressed id W, A, S or D.
     * @throws IOException
     * @return a char type
     */
    public static char readKeyboardArrow() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        char chars[] = new char[1];
        do {
            chars[0] = Character.toUpperCase((char) br.read());
        } while (chars[0] != 'W' && chars[0] != 'A' && chars[0] != 'S'
                && chars[0] != 'D');
        return chars[0];
    }

    /**
     * Prints the maze in the console.
     */
    public static void printMaze() {
        for (int i = 0; i < MazeGame.maze.mazeDim; i++) {
            for (int n = 0; n < MazeGame.maze.mazeDim; n++) {
                System.out.print(MazeGame.maze.mazeMap[i][n] + " ");
            }
            System.out.print('\n');
        }
    }

    /**
     * Ask the user the options about if he want a random maze 
     * and its dimension and the number and type of dragons.
     */
    public static void askOptions() {
        char opt;
        Scanner in = new Scanner(System.in);
        // Do you want a random maze?
        do {
            System.out.print("Generate random maze (Y-N): ");
            opt = in.nextLine().toCharArray()[0];
            opt = Character.toUpperCase(opt);
        } while (opt != 'Y' && opt != 'N');

        // If yes, what are the dimensons?
        if (opt == 'Y') {
            System.out.print("X and Y Dimension: ");
            MazeGame.maze.setDim(Math.abs(in.nextInt()));
            MazeBuilder.generateMaze(MazeGame.maze.mazeDim, MazeGame.maze);
        }

        // What kind of dragons you want?
        System.out.println("Choose the type of dragon that you want:");
        System.out.print("1 - Static Dragon\n2 - Dragon that moves\n3 - Dragon that moves and sleep\n");
        System.out.print("Option:");
        MazeGame.maze.dragonOption = in.nextInt();
        if (MazeGame.maze.dragonOption == 2) {
            MazeGame.maze.dragonsCanMove = true;
        }
        if (MazeGame.maze.dragonOption == 3) {
            MazeGame.maze.dragonsCanMove = true;
            MazeGame.maze.dragonsCanSleep = true;
        }

        // How many dragons?
        if (opt == 'Y') {
            do {
                System.out.println("How many Dragons? (>=1 and <=3):");
                MazeGame.maze.nDragons = in.nextInt();
            } while (MazeGame.maze.nDragons < 1 || MazeGame.maze.nDragons > 3);
            MazeGame.setupObjects();
        }

    }
/**
 * This function prints a message depending the way the game end and asks
 * the user if he want to play again. If option is no, the game ends.
 * @param overStatus the status returned by gameOver()
 * @see MazeGame
 */
    public static void playAgain(int overStatus) {
        char opt;
        Scanner in = new Scanner(System.in);
        if (overStatus == 2) {
            System.out.println("CONGRATULATIONS! You Won!");
        } else {
            System.out.println("Too bad! You Died!");
        }
        do {
            System.out.print("Play again (Y-N)? ");
            opt = in.nextLine().toCharArray()[0];
            opt = Character.toUpperCase(opt);
        } while (opt != 'Y' && opt != 'N');
        if (opt == 'N') {
            System.exit(0);
        }
    }
}
