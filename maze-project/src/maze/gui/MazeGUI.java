package maze.gui;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

import maze.logic.MazeGame;

public class MazeGUI {
	private JFrame frame;

	public void init() {
		frame = new JFrame("Maze Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension((MazeGame.maze.mazeDim + 1) * 20,
				(MazeGame.maze.mazeDim + 2) * 20));
		frame.setContentPane(new GamePanel());
		// Criar "widgets" e adicionar à área de conteúdo
		createWidgets();
		addWidgets(frame.getContentPane());
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
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				switch (Character.toUpperCase(e.getKeyChar())) {
				case 'W':
					System.out.println("W");
				case 'A':
					System.out.println("A");
				case 'S':
					System.out.println("S");
				case 'D':
					System.out.println("D");
				}

			}
		};
		frame.getContentPane().addKeyListener(keyList);
		frame.getContentPane().setFocusable(true);

	}

	private void addWidgets(Container contentPane) {
	}

	private void createWidgets() {

	}

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
