package pbl2;

public class Computer {
	private Coordinate computerCoordinate;
	private char[][] maze;
	private boolean isMoving;
	
	public Computer(Coordinate computCoordinate, char[][] maze, boolean isMoving ) {
		this.computerCoordinate = computCoordinate;
		this.maze = maze;
		this.isMoving = isMoving;
	}

	public boolean isMoving() {
		return isMoving;
	}

	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}

	public char[][] getMaze() {
		return maze;
	}

	public void setMaze(char[][] maze) {
		this.maze = maze;
	}

	public Coordinate getComputerCoordinate() {
		return computerCoordinate;
	}

	public void setComputerCoordinate(Coordinate computerCoordinate) {
		this.computerCoordinate = computerCoordinate;
	}


	
	
}
