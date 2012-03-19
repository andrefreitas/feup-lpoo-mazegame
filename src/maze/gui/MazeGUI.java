package maze.gui;

import java.awt.*;

import javax.swing.*;

public class MazeGUI {
	private static JFrame frame;
	private static  JPanel painel;

	public static void init() {
		frame = new JFrame("Maze Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(800, 600));
		frame.getContentPane().setLayout(new GridLayout(3, 2));
		// Criar "widgets" e adicionar à área de conteúdo
		createWidgets();
		addWidgets(frame.getContentPane());
		// Redimensionar e mostrar a janela
		frame.pack();
		frame.setVisible(true);

	}

	private static  void addWidgets(Container contentPane) {

	}

	private static  void createWidgets() {
		
		
	}
}
