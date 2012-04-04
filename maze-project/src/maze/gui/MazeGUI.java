package maze.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import maze.logic.MazeGame;

public class MazeGUI {

    public JFrame frame;

    public void init() {
        frame = new JFrame("Maze Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int winSize = MazeGame.maze.mazeDim * 23;
        frame.setMinimumSize(new Dimension(winSize, winSize+15));
        frame.setContentPane(new GamePanel());
        frame.pack();
        frame.setVisible(true);
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
        }

        public GamePanel(LayoutManager l) {
            super(l);
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            for (int i = 1; i <= MazeGame.maze.mazeDim; i++) {
                for (int j = 1; j <= MazeGame.maze.mazeDim; j++) {
                    g.drawChars(MazeGame.maze.mazeMap[i - 1], j - 1, 1, j * 20,
                            i * 20);
                }
            }

        }
    }
}
