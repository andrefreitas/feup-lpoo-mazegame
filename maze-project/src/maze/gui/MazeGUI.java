package maze.gui;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import maze.logic.MazeGame;

public class MazeGUI {

    private Image wallIcon;
    private Image heroIcon;
    private Image heroArmedIcon;
    private Image dragonIcon;
    private Image dragonSleepIcon;
    private Image swordIcon;
    private Image exitIcon;
    private Image sandIcon;
    public JFrame frame;

    public void init() {
        frame = new JFrame("Maze Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int winSize = (MazeGame.maze.mazeDim +1)* 30;
        frame.setMinimumSize(new Dimension(winSize-30, winSize+30));
        frame.setContentPane(new GamePanel());           
        frame.setVisible(true);
        // Game icons
        wallIcon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/wallIcon.png"));
        heroIcon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/heroIcon.png"));
        heroArmedIcon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/heroArmedIcon.png"));
        dragonIcon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/dragonIcon.png"));
        dragonSleepIcon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/dragonSleepIcon.png"));
        swordIcon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/swordIcon.png"));
        exitIcon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/exitIcon.png"));
        sandIcon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/sandIcon.png"));
        KeyListener keyList = new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                // TODO Auto-generated method stub
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                MazeGame.maze.hero.move(Character.toUpperCase(e.getKeyChar()));
                frame.repaint();
                if (MazeGame.gameOver()) {
                    gameOver();
                }

            }
        };




        frame.getContentPane().addKeyListener(keyList);
        frame.getContentPane().setFocusable(true);

    }

    public void gameOver() {
        System.exit(0);

    }

    // Game Panel that show the maze
    public class GamePanel extends JPanel {

        /**
         *
         */
        private static final long serialVersionUID = 1L;

        public GamePanel() {
            super();
            this.setBackground(Color.WHITE);
        }

        public GamePanel(LayoutManager l) {
            super(l);
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            for (int i = 1; i <= MazeGame.maze.mazeDim; i++) {
                for (int j = 1; j <= MazeGame.maze.mazeDim; j++) { //
                    // g.drawChars(MazeGame.maze.mazeMap[i - 1], j - 1, 1, j * 20, i * 20);
                    switch (MazeGame.maze.mazeMap[j - 1][i - 1]) {
                        case 'X':
                            g.drawImage(wallIcon, (i - 1) * 30, (j - 1) * 30, null);
                            break;
                        case 'H':
                            g.drawImage(heroIcon, (i - 1) * 30, (j - 1) * 30, null);
                            break;
                        case 'D':
                            g.drawImage(dragonIcon, (i - 1) * 30, (j - 1) * 30, null);
                            break;
                        case 'd':
                            g.drawImage(dragonSleepIcon, (i - 1) * 30, (j - 1) * 30, null);
                            break;
                        case 'E':
                            g.drawImage(swordIcon, (i - 1) * 30, (j - 1) * 30, null);
                            break;
                        case 'F':
                            g.drawImage(swordIcon, (i - 1) * 30, (j - 1) * 30, null);
                            break; //TODO: change later
                        case 'S':
                            g.drawImage(exitIcon, (i - 1) * 30, (j - 1) * 30, null);
                            break;
                        case 'A':
                            g.drawImage(heroArmedIcon, (i - 1) * 30, (j - 1) * 30, null);
                            break;
                        default:
                            g.drawImage(sandIcon, (i - 1) * 30, (j - 1) * 30, null);
                           
                    }

                }
            }


        }
    }
}
