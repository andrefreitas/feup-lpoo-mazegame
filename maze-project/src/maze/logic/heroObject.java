package maze.logic;

public class heroObject extends ObjectIOMove {

    public heroObject(char state, int x, int y) {
        super(state, x, y);
    }

    // move the hero by receiving an input
    @Override
    public void move(char direction) {
        int xpos = 0, ypos = 0;
        switch (direction) {
            case 'W':
                xpos = 0;
                ypos = -1;
                break;
            case 'S':
                xpos = 0;
                ypos = 1;
                break;
            case 'A':
                xpos = -1;
                ypos = 0;
                break;
            case 'D':
                xpos = 1;
                ypos = 0;
                break;
        }
        // only moves the hero if the new cell isn't a wall and if he's not
        // unarmed and going to the exit
        if (MazeGame.maze.mazeMap[MazeGame.maze.hero.getY() + ypos][MazeGame.maze.hero.getX() + xpos] != 'X'
                && !(MazeGame.maze.hero.getX() + xpos == MazeGame.maze.exit.getX()
                && MazeGame.maze.hero.getY() + ypos == MazeGame.maze.exit.getY() && MazeGame.maze.hero.getState() == 'H')) {
            MazeGame.maze.mazeMap[MazeGame.maze.hero.getY()][MazeGame.maze.hero.getX()] = ' ';
            MazeGame.maze.mazeMap[MazeGame.maze.hero.getY() + ypos][MazeGame.maze.hero.getX() + xpos] = MazeGame.maze.hero.getState();
            MazeGame.updateObject(MazeGame.maze.hero, MazeGame.maze.hero.getX()
                    + xpos, MazeGame.maze.hero.getY() + ypos,
                    MazeGame.maze.hero.getState());

        }
        // if the hero is in the same cell of the sword, changes his state to
        // "Armed"
        if (GameObject.samePosition(MazeGame.maze.hero, MazeGame.maze.sword)) {
            MazeGame.updateObject(MazeGame.maze.hero,
                    MazeGame.maze.hero.getX(), MazeGame.maze.hero.getY(), 'A');
            MazeGame.maze.mazeMap[MazeGame.maze.hero.getY()][MazeGame.maze.hero.getX()] = MazeGame.maze.hero.getState();
        }
        // if the hero is near the dragon and armed, the dragon dies
        for (int i = 0; i < MazeGame.maze.dragons.size(); i++) {
            if (GameObject.adjacentPosition(MazeGame.maze.hero,
                    MazeGame.maze.dragons.get(i))
                    && MazeGame.maze.hero.getState() == 'A') {
                MazeGame.maze.mazeMap[MazeGame.maze.dragons.get(i).getY()][MazeGame.maze.dragons.get(i).getX()] = ' ';
                // bug fix 1
                MazeGame.updateObject(MazeGame.maze.dragons.get(i), -1, -1, 'K');
            }
        }
    }
}
