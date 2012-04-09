package maze.logic;

public class HeroObject extends ObjectIOMove {

    public HeroObject(char state, int x, int y) {
        super(state, x, y);
    }

    // move the hero by receiving an input
    @Override
    public void move(char direction) {
        int xpos, ypos;
        if (direction == MazeGame.maze.moveChars[0]) {
            xpos = 0;
            ypos = -1;
        } else if (direction == MazeGame.maze.moveChars[1]) {
            xpos = -1;
            ypos = 0;
        } else if (direction == MazeGame.maze.moveChars[2]) {
            xpos = 0;
            ypos = 1;
        } else if (direction == MazeGame.maze.moveChars[3]) {
            xpos = 1;
            ypos = 0;
        } else {
            return;
        }
        // only moves the hero if the new cell isn't a wall, if he's
        // unarmed and going to the exit or if a dragon is next to him
        if (MazeGame.maze.mazeMap[MazeGame.maze.hero.getY() + ypos][MazeGame.maze.hero.getX() + xpos] != 'X'
                && MazeGame.maze.mazeMap[MazeGame.maze.hero.getY() + ypos][MazeGame.maze.hero.getX() + xpos] != 'd'
                && MazeGame.maze.mazeMap[MazeGame.maze.hero.getY() + ypos][MazeGame.maze.hero.getX() + xpos] != 'D'
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
        // Makes the hero disappear when entering the exit portal
        if (GameObject.samePosition(MazeGame.maze.hero, MazeGame.maze.exit)) {
            MazeGame.maze.mazeMap[MazeGame.maze.hero.getY()][MazeGame.maze.hero.getX()] = 'S';
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
