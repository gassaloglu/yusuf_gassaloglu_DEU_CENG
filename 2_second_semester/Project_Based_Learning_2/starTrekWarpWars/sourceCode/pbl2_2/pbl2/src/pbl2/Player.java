package pbl2;

public class Player {
	private int pScore;
	private int pLife;
	private int pEnergy;
	private Stack backpack;
	private Coordinate pCoordinate;
	
	public Player(Coordinate pCoordinate, int pScore, int pLife, int pEnergy, Stack backpack) {
		this.pScore = pScore;
		this.pLife = pLife;
		this.pEnergy = pEnergy;
		this.backpack = backpack;
		this.pCoordinate = pCoordinate;
	}

	public Coordinate getpCoordinate() {
		return pCoordinate;
	}

	public void setpCoordinate(Coordinate pCoordinate) {
		this.pCoordinate = pCoordinate;
	}

	public int getpScore() {
		return pScore;
	}

	public void setpScore(int pScore) {
		this.pScore = pScore;
	}

	public int getpLife() {
		return pLife;
	}

	public void setpLife(int pLife) {
		this.pLife = pLife;
	}

	public int getpEnergy() {
		return pEnergy;
	}

	public void setpEnergy(int pEnergy) {
		this.pEnergy = pEnergy;
	}

	public Stack getBackpack() {
		return backpack;
	}

	public void setBackpack(Stack backpack) {
		this.backpack = backpack;
	}
	
}
