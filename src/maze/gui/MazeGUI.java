package maze.gui;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import maze.logic.MazeGame;

public class MazeGUI {
	private static JFrame frame;
	private static ArrayList<JLabel> maze = new ArrayList<JLabel>();

	public static void init() {
		frame = new JFrame("Maze Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(MazeGame.maze.mazeDim[0], MazeGame.maze.mazeDim[1]));
		// Criar "widgets" e adicionar à área de conteúdo
		createWidgets();
		addWidgets(frame.getContentPane());
		// Redimensionar e mostrar a janela
		frame.pack();
		frame.setVisible(true);

	}

	private static  void addWidgets(Container contentPane) {
		for(int i=0;i<10;i++)
			contentPane.add(maze.get(i));
	}

	private static  void createWidgets() {
		StringBuffer tempString = new StringBuffer();
		for(int i=0;i<MazeGame.maze.mazeDim[0];i++)
		{
			for(int j=0;j<MazeGame.maze.mazeDim[1];j++)
			{
				if(MazeGame.maze.mazeMap[i][j]==' ')
					tempString.append("  ");
				else
					tempString.append(MazeGame.maze.mazeMap[i][j]);
			}
			maze.add(new JLabel(tempString.toString()));
			tempString.delete(0, tempString.length());
		}
	}
}
