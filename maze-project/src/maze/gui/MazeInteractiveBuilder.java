package maze.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import maze.logic.DragonObject;
import maze.logic.GameObject;
import maze.logic.HeroObject;
import maze.logic.MazeGame;

/**
 * MazeInteractiveBuilder.java - A class for creating a Maze Construction tool
 * that uses the mouse
 *
 * @author André Freitas, Vasco Gonçalves
 * @version 1.0
 */
public class MazeInteractiveBuilder {
    // Window and containers

    private JFrame window;
    private JPanel mazeMatrix;
    private JPanel mazeButtons;
    private JPanel container;
    private final int controlsWidth = 500;
    // Internal Maze Data
    private int dimension;
    private char[][] maze;
    private char valueSelected;
    private mazeCell[][] mazeCells;
    private int dragonOption = 3;
    // Maze Icons
    private Icon wallIcon;
    private Icon heroIcon;
    private Icon dragonIcon;
    private Icon swordIcon;
    private Icon exitIcon;
    private Icon sandIcon;
    // Maze Buttons with Icons
    private JButton putWall;
    private JButton putSand;
    private JButton putDragon;
    private JButton putHero;
    private JButton putSword;
    private JButton putExit;
    //Exit button
    private JButton exitButton;
    // Max ocurrences
    private int exitMax = 1;
    private int heroMax = 1;
    private int dragonMax = 3;
    private int swordMax = 1;

    /**
     * The main() function.
     */
    public static void main(String[] args) {
        MazeInteractiveBuilder builder = new MazeInteractiveBuilder(10);

    }

    /**
     * Asks the user the Maze Dimension and creates a new MazeInteractiveBuilder
     * instance.
     */
    static void run() {
        String mazeDimString;
        int mazeDim;
        do {
            mazeDimString = JOptionPane.showInputDialog(null, "Enter the Maze Dimension\n(between 10 and 35): ", 20);
            mazeDim = Integer.parseInt(mazeDimString);
        } while (mazeDim < 10 | mazeDim > 35);
        MazeInteractiveBuilder builder = new MazeInteractiveBuilder(mazeDim);
    }

    /**
     * Constructor of the interactive builder. Setup all the necessary data,
     * like maze dimension, the icons, the panel and containers dimensions.
     *
     * @param d the dimension of the maze
     */
    public MazeInteractiveBuilder(int d) {
        // Maze dimensions
        dimension = d;
        valueSelected = ' ';
        maze = new char[d][d];
        mazeCells = new mazeCell[d][d];

        // Setup the icons
        setupIcons();
        // Main Window
        window = new JFrame("Maze Builder");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setMinimumSize(new Dimension(30 * d + controlsWidth, 30 * d));
        window.setMaximumSize(new Dimension(30 * d + controlsWidth, 30 * d));

        // Panels and containers
        mazeMatrix = new JPanel(new GridLayout(d, d, 0, 0));
        mazeButtons = new JPanel(new GridLayout(7, 1, 0, 10));
        container = new JPanel(new GridLayout(1, 2, 10, 0));
        populateMaze();
        populateBuildingButtons();

        // Setup the container
        container.add(mazeMatrix);
        container.add(mazeButtons);
        window.add(container);

        // Set the Window visible
        window.setVisible(true);

    }

    /**
     * Populate the Maze array of chars and Maze Cell Buttons with Sand and
     * Walls. Note that the maze data is internally represented in a 2D array of
     * chars. In the other hand, in the GUI the maze is represented by using
     * buttons.
     */
    private void populateMaze() {

        // Populate all the maze with sand
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                mazeCells[i][j] = new mazeCell(j, i);
                mazeCells[i][j].setValue(' ');
                maze[i][j] = ' ';
            }
        }
        // Populate all the walls
        for (int i = 0; i < dimension; i++) {
            // Top
            mazeCells[0][i].setValue('X');
            maze[0][i] = 'X';
            // Bottom
            mazeCells[dimension - 1][i].setValue('X');
            maze[dimension - 1][i] = 'X';
            // Left
            mazeCells[i][0].setValue('X');
            maze[i][0] = 'X';
            // Right
            mazeCells[i][dimension - 1].setValue('X');
            maze[i][dimension - 1] = 'X';

        }
        // Populate the jPanel with the mazeCells
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                mazeMatrix.add(mazeCells[i][j]);
            }
        }

    }

    /**
     * Populate the Building Buttons and setup the actionlisteners.
     */
    private void populateBuildingButtons() {

        // Create Wall button
        putWall = new JButton("Put Wall");
        putWall.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                valueSelected = 'X';
            }
        });

        // Create sand button
        putSand = new JButton("Put Sand");
        putSand.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                valueSelected = ' ';
            }
        });

        // Create dragon button
        putDragon = new JButton("Put Dragon");
        putDragon.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                valueSelected = 'D';
            }
        });

        // Create hero button
        putHero = new JButton("Put Hero");
        putHero.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                valueSelected = 'H';
            }
        });

        // Create Sword button
        putSword = new JButton("Put Sword");
        putSword.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                valueSelected = 'E';
            }
        });

        // Create Exit button
        putExit = new JButton("Put Exit");
        putExit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                valueSelected = 'S';
            }
        });

        exitButton = new JButton("Exit and Start Game");
        exitButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {


                if (isMinimalComplete()) {

                    /*
                     * for (int i = 0; i < dimension; i++) { for (int j = 0; j <
                     * dimension; j++) { System.out.print(maze[i][j] + " "); }
                     * System.out.println(""); }
                     */

                    // prepazeMaze Data and go
                    prepareMazeData();
                    window.setVisible(false);
                } // Maze is not complete!
                else {
                    JOptionPane.showMessageDialog(window, "The maze isn't complete!");
                }



            }
        });

        // Add to jpanel
        mazeButtons.add(putWall);
        mazeButtons.add(putSand);
        mazeButtons.add(putDragon);
        mazeButtons.add(putHero);
        mazeButtons.add(putSword);
        mazeButtons.add(putExit);
        mazeButtons.add(exitButton);

    }

    /**
     * Configure all the maze parameters, like keyboard controls, the game
     * objects and dragons behaviour. This is called when the start game button
     * is pressed in the Window.
     *
     */
    private void prepareMazeData() {
        // (1) Set the maze options
        char moveChars[] = {'W', 'A', 'S', 'D'};
        MazeGame.setOptions(dimension, dragonOption, 3 - dragonMax, moveChars);

        // (2) Fetch all the objects
        ArrayList<DragonObject> dragons = new ArrayList<DragonObject>();
        HeroObject hero = null;
        GameObject exit = null;
        GameObject sword = null;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                switch (maze[i][j]) {
                    case 'D':
                        dragons.add(new DragonObject('D', j, i));
                        break;
                    case 'H':
                        hero = new HeroObject('H', j, i);
                        break;
                    case 'S': {
                        exit = new GameObject('S', j, i);
                    }
                    break;
                    case 'E':
                        sword = new GameObject('E', j, i);
                        break;
                }
            }
        }

        // (3) set that objects
        MazeGame.maze.setDragons(dragons);
        MazeGame.maze.setHero(hero);
        MazeGame.maze.setExit(exit);
        MazeGame.maze.setSword(sword);
        MazeGame.maze.setMazeMap(maze);
        // Say the options are set and the game is ready to start!
        MazeGame.optionsSet = true;

    }

    /**
     * Check if the maze is closed by walls and have the minimum objects
     * numbers. Must have at least 1 hero, 1 dragon, 1 exit and 1 sword.
     *
     * @return true when the maze is minimal complete
     */
    private boolean isMinimalComplete() {
        //  Check limit walls
        for (int i = 0; i < dimension; i++) {
            // Top
            if (maze[0][i] == ' ') {
                return false;
            }
            // Bottom
            if (maze[dimension - 1][i] == ' ') {
                return false;
            }
            // Left
            if (maze[i][0] == ' ') {
                return false;
            }
            // Right
            if (maze[i][dimension - 1] == ' ') {
                return false;
            }
        }

        // Check minimal objects
        if (exitMax == 1 || swordMax == 1 || heroMax == 1 || dragonMax == 3) {
            return false;
        }


        return true;
    }

    /**
     * Setup all the icons of the maze representation in GUI.
     */
    private void setupIcons() {
        wallIcon = new ImageIcon(getClass().getResource("/resources/wallIcon.png"));
        heroIcon = new ImageIcon(getClass().getResource("/resources/heroIcon.png"));
        dragonIcon = new ImageIcon(getClass().getResource("/resources/dragonIcon.png"));
        swordIcon = new ImageIcon(getClass().getResource("/resources/swordIcon.png"));
        exitIcon = new ImageIcon(getClass().getResource("/resources/exitIcon.png"));
        sandIcon = new ImageIcon(getClass().getResource("/resources/sandIcon.png"));
    }

    /**
     * This class represent a Cell int the maze, that is a Button. This is only
     * for the visual representation of the Maze in the GUI. Remember that all
     * the internal data is stored in the array of chars
     *
     */
    public class mazeCell extends JButton {

        public int x;
        public int y;
        public char val;

        /**
         * This function sets the value of the cell
         *
         * @param val the value to be stored
         */
        public void setValue(char val) {
            this.val = val;
            setIcon(selectIcon(val));
        }

        /**
         * Constructor that receives the position of the Button. It also setups
         * the listener, when the button is clicked, to set the Maze Cell Value.
         * Example: putting an hero in the cell.
         *
         * @param x the x-axis position
         * @param y the y-axis position
         */
        public mazeCell(int x, int y) {
            this.x = x;
            this.y = y;
            this.val = ' ';
            this.setIcon(sandIcon);
            addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    // Get the button position
                    int x = ((mazeCell) e.getSource()).x;
                    int y = ((mazeCell) e.getSource()).y;
                    if (x != dimension - 1 && x != 0 && y != dimension - 1 && y != 0) {
                        // Evaluate the ocurrence
                        int oldVal = ((mazeCell) e.getSource()).val;
                        int newVal = valueSelected;
                        if (checkOcurrence(oldVal, newVal)) {

                            // Set the icon and value in the button
                            ((mazeCell) e.getSource()).setIcon(selectIcon(valueSelected));
                            ((mazeCell) e.getSource()).setValue(valueSelected);

                            // Update the value in the maze array of chars
                            maze[y][x] = valueSelected;
                        }
                    }
                }
            });

        }
    }

    /**
     * Check the ocurrence of a specified object and evaluates by the limits, if
     * it's possible or not.
     *
     * @param oldVal the existing value
     * @param newVal the new value
     * @return true if the limits of objects are respected
     */
    private boolean checkOcurrence(int oldVal, int newVal) {
        // Evaluates the new value
        switch (newVal) {
            // Dragon
            case 'D':
                if (oldVal != 'D') {
                    if (dragonMax > 0) {
                        dragonMax--;
                    } else {
                        return false;
                    }
                }
                break;
            // Sword
            case 'E':
                if (oldVal != 'E') {
                    if (swordMax > 0) {
                        swordMax--;
                    } else {
                        return false;
                    }
                }
                break;
            // Hero 
            case 'H':
                if (oldVal != 'H') {
                    if (heroMax > 0) {
                        heroMax--;
                    } else {
                        return false;
                    }
                }
                break;
            // Exit 
            case 'S':
                if (oldVal != 'S') {
                    if (exitMax > 0) {
                        exitMax--;
                    } else {
                        return false;
                    }
                }
                break;
        }
        //-->
        // Evaluates the old value
        switch (oldVal) {
            // Dragon
            case 'D':
                if (newVal != 'D') {
                    dragonMax++;
                }
                break;
            // Sword
            case 'E':
                if (newVal != 'E') {
                    swordMax++;
                }
                break;
            // Hero 
            case 'H':
                if (newVal != 'H') {
                    heroMax++;
                }
                break;
            // Exit 
            case 'S':
                if (newVal != 'S') {
                    exitMax++;
                }
                break;
        }


        return true;
    }

    /**
     * This function returns an ImageIcon depending on the value given. Example:
     * if the val given is 'H', this returns an Hero icon.
     *
     * @param val the character value of the icon
     */
    public ImageIcon selectIcon(char val) {
        ImageIcon aux;
        aux = (ImageIcon) sandIcon;
        switch (val) {
            case 'X':
                aux = (ImageIcon) wallIcon;
                break;
            case 'D':
                aux = (ImageIcon) dragonIcon;
                break;
            case 'H':
                aux = (ImageIcon) heroIcon;
                break;
            case 'E':
                aux = (ImageIcon) swordIcon;
                break;
            case 'S':
                aux = (ImageIcon) exitIcon;
                break;
        }
        return aux;
    }
}
