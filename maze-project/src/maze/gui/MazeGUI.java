package maze.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import maze.logic.MazeGame;

public class MazeGUI extends JFrame {

    private BufferedImage wallIcon;
    private BufferedImage heroIcon;
    private BufferedImage heroArmedIcon;
    private BufferedImage dragonIcon;
    private BufferedImage dragonSleepIcon;
    private BufferedImage dragonSwordIcon;
    private BufferedImage swordIcon;
    private BufferedImage exitIcon;
    private BufferedImage sandIcon;
    public JButton exit;

    private BufferedImage loadImage(String path) {
        BufferedImage img=null;
        try {
            img = ImageIO.read(getClass().getResource(path));
        } catch (IOException ex) {
            System.exit(1);
        }
        return img;

    }

    public MazeGUI() {
        super("Maze Game");
        int winSize = MazeGame.maze.mazeDim * 30;
        setContentPane(new GamePanel());
        // Game icons
        wallIcon = loadImage("/resources/wallIcon.png");
        heroIcon = loadImage("/resources/heroIcon.png");
        heroArmedIcon = loadImage("/resources/heroArmedIcon.png");
        dragonIcon = loadImage("/resources/dragonIcon.png");
        dragonSleepIcon = loadImage("/resources/dragonSleepIcon.png");
        dragonSwordIcon = loadImage("/resources/dragonSwordIcon.png");
        swordIcon = loadImage("/resources/swordIcon.png");
        exitIcon = loadImage("/resources/exitIcon.png");
        sandIcon = loadImage("/resources/sandIcon.png");

        // Exit button
        KeyListener keyList = new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    closeGame();
                }
                MazeGame.maze.hero.move(Character.toUpperCase(e.getKeyChar()));
                repaint();
                if (MazeGame.gameOver() > 0) {
                    gameOver(MazeGame.gameOver());
                }

            }
        };
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                closeGame();
            }
        });

        getContentPane().addKeyListener(keyList);
        getContentPane().setFocusable(true);
        getContentPane().setPreferredSize(new Dimension(winSize, winSize));
        setResizable(false);
        setVisible(true);
        pack();
        getContentPane().requestFocus();


    }

    public void gameOver(int overStatus) {
        MazeGame.gameOver = true;
        if (overStatus == 2) {
            JOptionPane.showMessageDialog(this, "CONGRATULATIONS! You Won!", "YOU WON", JOptionPane.DEFAULT_OPTION);
        } else {
            JOptionPane.showMessageDialog(this, "Too bad! You Died!", "YOU DIED", JOptionPane.ERROR_MESSAGE);
        }
        int n = JOptionPane.showConfirmDialog(this, "Do you want to play again?", "Restart", JOptionPane.YES_NO_OPTION);
        if (n == 0) {
            this.setVisible(false);
            MazeGame.homeGui.setVisible(true);
        } else {
            System.exit(0);
        }

    }

    public void closeGame() {
        int n = JOptionPane.showConfirmDialog(this, "Do you want to save the current game?", "Save Game", JOptionPane.YES_NO_OPTION);
        if (n == 0) {
            ObjectOutputStream os = null;
            try {
                os = new ObjectOutputStream(
                        new FileOutputStream("save.dat"));
                os.writeObject(MazeGame.maze);
            } catch (IOException ex) {
            } finally {
                if (os != null) {
                    try {
                        os.close();
                    } catch (IOException ex) {
                        Logger.getLogger(MazeGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.exit(0);
                }
            }
        } else {
            System.exit(0);
        }
    }

    // Game Panel that show the maze
    public class GamePanel extends JPanel {

        private static final long serialVersionUID = 1L;

        public GamePanel() {
            super();
            setBackground(Color.WHITE);
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
                            g.drawImage(dragonSwordIcon, (i - 1) * 30, (j - 1) * 30, null);
                            break;
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
