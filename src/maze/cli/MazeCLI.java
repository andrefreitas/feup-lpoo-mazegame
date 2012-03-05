package maze.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MazeCLI {

	// read keyboard char input
	public static char readKeyboardArrow() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		char chars[] = new char[1];
		do {
			chars[0] = Character.toUpperCase((char) br.read());
		} while (chars[0] != 'W' && chars[0] != 'A' && chars[0] != 'S'
				&& chars[0] != 'D');
		return chars[0];
	}

}
