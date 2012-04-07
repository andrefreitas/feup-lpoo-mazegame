/*
 * Maze Interactive construction
 */
package maze.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

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

    public static void main(String[] args) {
        MazeInteractiveBuilder builder = new MazeInteractiveBuilder(20);

    }

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
        populateMatrix();
        populateMazeButtons();

        // Setup the container
        container.add(mazeMatrix);
        container.add(mazeButtons);
        window.add(container);

        // Set the Window visible
        window.setVisible(true);

    }

    private void populateMatrix() {

        // Populate the array of chars walls
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                mazeCells[i][j] = new mazeCell(j, i);
                mazeCells[i][j].setValue(' ');
                maze[i][j] = ' ';
            }
        }
        // Populate walls

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
        // Populate the jPanel
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                mazeMatrix.add(mazeCells[i][j]);
            }
        }

    }

    private void populateMazeButtons() {

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

        //Create Exit button
        putExit = new JButton("Put Exit");
        putExit.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                valueSelected = 'S';
            }
        });

        exitButton = new JButton("Exit and Start Game");
        exitButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < dimension; i++) {
                    for (int j = 0; j < dimension; j++) {

                        System.out.print(maze[i][j] + " ");
                    }
                    System.out.println("");
                }
            }
        });
        // add to jpanel
        mazeButtons.add(putWall);
        mazeButtons.add(putSand);
        mazeButtons.add(putDragon);
        mazeButtons.add(putHero);
        mazeButtons.add(putSword);
        mazeButtons.add(putExit);
        mazeButtons.add(exitButton);

    }
    // Class for each cell

    private void setupIcons() {
        wallIcon = new ImageIcon(getClass().getResource("/resources/wallIcon.png"));
        heroIcon = new ImageIcon(getClass().getResource("/resources/heroIcon.png"));
        dragonIcon = new ImageIcon(getClass().getResource("/resources/dragonIcon.png"));
        swordIcon = new ImageIcon(getClass().getResource("/resources/swordIcon.png"));
        exitIcon = new ImageIcon(getClass().getResource("/resources/exitIcon.png"));
        sandIcon = new ImageIcon(getClass().getResource("/resources/sandIcon.png"));
    }

    public class mazeCell extends JButton {

        public final int x;
        public final int y;
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
            this.setSize(30, 30);
            this.setMaximumSize(new Dimension(30,30));
            this.setMinimumSize(new Dimension(30,30));
            this.setPreferredSize(new Dimension(30,30));
            addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    int x = ((mazeCell) e.getSource()).x;
                    int y = ((mazeCell) e.getSource()).y;
                    ((mazeCell) e.getSource()).setIcon(selectIcon(valueSelected));
                    ((mazeCell) e.getSource()).setValue(valueSelected);
                    maze[y][x] = valueSelected;
                }
            });

        }
    }

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
