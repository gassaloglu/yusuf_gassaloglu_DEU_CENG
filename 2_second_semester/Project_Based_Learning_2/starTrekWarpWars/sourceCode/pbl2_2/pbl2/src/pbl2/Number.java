package pbl2;

public class Number {
	private Coordinate numberCoordinate;
	private int nValue;
	private int nScore;
	private boolean isMovingNumber;
	
	public Number(Coordinate numberCoordinate, int value, int nScore, boolean isMovingNumber) {
		this.numberCoordinate = numberCoordinate;
		this.nValue = value;
		this.nScore = nScore;
		this.isMovingNumber = isMovingNumber;
	}

	public boolean isMovingNumber() {
		return isMovingNumber;
	}

	public void setMovingNumber(boolean isMovingNumber) {
		this.isMovingNumber = isMovingNumber;
	}

	public Coordinate getNumberCoordinate() {
		return numberCoordinate;
	}

	public void setNumberCoordinate(Coordinate numberCoordinate) {
		this.numberCoordinate = numberCoordinate;
	}

	public int getnValue() {
		return nValue;
	}

	public void setnValue(int nValue) {
		this.nValue = nValue;
	}

	public int getnScore() {
		return nScore;
	}

	public void setnScore(int nScore) {
		this.nScore = nScore;
	}

}
