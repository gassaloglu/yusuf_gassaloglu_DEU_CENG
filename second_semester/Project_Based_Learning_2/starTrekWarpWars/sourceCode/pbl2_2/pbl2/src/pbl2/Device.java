package pbl2;

public class Device {
	private Coordinate dCoordinate;
	private char dtype;
	private int dScore;
	private int activationTime;
	
	public Device(Coordinate dCoordinate, char dtype, int dScore, int activationTime) {
		this.dCoordinate = dCoordinate;
		this.dtype = dtype;
		this.dScore = dScore;
		this.activationTime = activationTime;
	}

	public Coordinate getdCoordinate() {
		return dCoordinate;
	}

	public void setdCoordinate(Coordinate dCoordinate) {
		this.dCoordinate = dCoordinate;
	}

	public char getDtype() {
		return dtype;
	}

	public void setDtype(char dtype) {
		this.dtype = dtype;
	}

	public int getdScore() {
		return dScore;
	}

	public void setdScore(int dScore) {
		this.dScore = dScore;
	}

	public int getActivationTime() {
		return activationTime;
	}

	public void setActivationTime(int activationTime) {
		this.activationTime = activationTime;
	}
	
}
