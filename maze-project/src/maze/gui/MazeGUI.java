package maze.gui;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
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
    public JButton exit;

    public Image loadImage(String path){
        return Toolkit.getDefaultToolkit().getImage(getClass().getResource(path));
        
    }
    public void init() {
        frame = new JFrame("Maze Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int winSize = (MazeGame.maze.mazeDim +1)* 30;
        frame.setMinimumSize(new Dimension(winSize-30, winSize+30));
        frame.setContentPane(new GamePanel());  
        exit=new JButton("Exit Game");
        // Exit button
        frame.add(exit);
        frame.setVisible(true);
        // Game icons
        wallIcon = loadImage("/resources/wallIcon.png");
        heroIcon = loadImage("/resources/heroIcon.png");
        heroArmedIcon = loadImage("/resources/heroArmedIcon.png");
        dragonIcon = loadImage("/resources/dragonIcon.png");
        dragonSleepIcon = loadImage("/resources/dragonSleepIcon.png");
        swordIcon = loadImage("/resources/swordIcon.png");
        exitIcon = loadImage("/resources/exitIcon.png");
        sandIcon = loadImage("/resources/sandIcon.png");

        // Exit button
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
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
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

                }
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
