package maze.gui;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

import maze.cli.MazeCLI;
import maze.logic.MazeGame;

public class MazeGUI {
	public JFrame frame;

	public void init() {
		frame = new JFrame("Maze Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		int winSize = ((MazeGame.maze.mazeDim / 2) * 2 + 2) * 20;
		frame.setMinimumSize(new Dimension(winSize, winSize));
		frame.setContentPane(new GamePanel());
		// Redimensionar e mostrar a janela
		frame.pack();
		frame.setVisible(true);
		KeyListener keyList = new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				MazeGame.maze.hero.move(Character.toUpperCase(e.getKeyChar()));
				frame.repaint();
				MazeCLI.printMaze();
				if (MazeGame.gameOver())
					gameOver();

			}

			@Override
			public void keyPressed(KeyEvent e) {

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
		public GamePanel() {
			super();
		}

		public GamePanel(LayoutManager l) {
			super(l);
		}

		public void paint(Graphics g) {
			super.paint(g);
			for (int i = 1; i <= MazeGame.maze.mazeDim; i++)
				for (int j = 1; j <= MazeGame.maze.mazeDim; j++)
					g.drawChars(MazeGame.maze.mazeMap[i - 1], j - 1, 1, j * 20,
							i * 20);

		}
	}

}
