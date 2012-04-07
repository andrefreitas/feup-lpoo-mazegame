/*
 * Maze Interactive construction
 */
package maze.gui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;
import maze.logic.*;

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

    // This main exists for test purpose
    public static void main(String[] args) {
        MazeInteractiveBuilder builder = new MazeInteractiveBuilder(10);

    }

    static void run() {
        String mazeDimString;
        int mazeDim;
        do {
            mazeDimString = JOptionPane.showInputDialog(null, "Enter the Maze Dimension\n(between 10 and 35): ", 20);
            mazeDim = Integer.parseInt(mazeDimString);
        } while (mazeDim < 10 | mazeDim > 35);
        MazeInteractiveBuilder builder = new MazeInteractiveBuilder(mazeDim);
    }
    /*
     * Constructor of the interactive builder @param d the dimension of the maze
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
    /*
     * Populate the basic maze with walls and sand
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

    /*
     * Populate the buttons to Build objects in the maze
     */
    private void populateBuildingButtons() {

        // Create Wall button
        putWall = new JButton("Put Wall");
        putWall.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                valueSelected = 'X';
            }
        });

        // Create sand button
        putSand = new JButton("Put Sand");
        putSand.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                valueSelected = ' ';
            }
        });

        // Create dragon button
        putDragon = new JButton("Put Dragon");
        putDragon.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                valueSelected = 'D';
            }
        });

        // Create hero button
        putHero = new JButton("Put Hero");
        putHero.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                valueSelected = 'H';
            }
        });

        // Create Sword button
        putSword = new JButton("Put Sword");
        putSword.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                valueSelected = 'E';
            }
        });

        // Create Exit button
        putExit = new JButton("Put Exit");
        putExit.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                valueSelected = 'S';
            }
        });

        exitButton = new JButton("Exit and Start Game");
        exitButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {


                if (isMinimalComplete()) {

                    for (int i = 0; i < dimension; i++) {
                        for (int j = 0; j < dimension; j++) {
                            System.out.print(maze[i][j] + " ");
                        }
                        System.out.println("");
                    }

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

    /*
     * This functions fetch all the Maze Objects and the configuration values of
     * the game
     */
    private void prepareMazeData() {
        // (1) Set the maze options
        char moveChars[] = {'W', 'A', 'S', 'D'};
        MazeGame.setOptions(dimension, dragonOption, 3 - dragonMax, moveChars);

        // (2) Fetch all the objects
        ArrayList<DragonObject> dragons = new ArrayList<DragonObject>();
        heroObject hero=null;
        GameObject exit=null;
        GameObject sword=null;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                switch (maze[i][j]) {
                    case 'D':
                        dragons.add(new DragonObject('D', j, i));
                        break;
                    case 'H':
                        hero = new heroObject('H', j, i);
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

        if (exitMax == 1) {
            return false;
        }
        if (swordMax == 1) {
            return false;
        }
        if (heroMax == 1) {
            return false;
        }
        if (dragonMax == 3) {
            return false;
        }
        // Check minimal objects
        return true;
    }

    /*
     * Setup the icons of the maze
     */
    private void setupIcons() {
        wallIcon = new ImageIcon(getClass().getResource("/resources/wallIcon.png"));
        heroIcon = new ImageIcon(getClass().getResource("/resources/heroIcon.png"));
        dragonIcon = new ImageIcon(getClass().getResource("/resources/dragonIcon.png"));
        swordIcon = new ImageIcon(getClass().getResource("/resources/swordIcon.png"));
        exitIcon = new ImageIcon(getClass().getResource("/resources/exitIcon.png"));
        sandIcon = new ImageIcon(getClass().getResource("/resources/sandIcon.png"));
    }

    /*
     * Class for a button of type MazeCell This Button have 3 adittional
     * parameters:the x, y and the value of the cell, that is the object that is
     * beeing store in that position.
     */
    public class mazeCell extends JButton {

        public int x;
        public int y;
        public char val;

        public void setValue(char val) {
            this.val = val;
            setIcon(selectIcon(val));
        }

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
            });

        }
    }

    /*
     * Check the ocurrence of a specified object and evaluates, basing in the
     * limits, if it's possible or not
     *
     * @param oldVal the existing value @param newVal the new value @return true
     * if the limits of objects are respected
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
    /*
     * This function returns an ImageIcon depending on the value given @param
     * val the character value of the icon
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

    /*
     * Loads an image and return it as a buffered image @param path the full
     * path of the image
     */
    private BufferedImage loadImage(String path) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(getClass().getResource(path));
        } catch (IOException ex) {
            System.exit(1);
        }
        return img;

    }
}
