package columns;

public class ColumnNode {
	private String columnNumber;
	private ColumnNode down;
	private CardNumberNode right;
	private CardNumberNode place;
	public ColumnNode(String dataToAdd) {
		columnNumber = dataToAdd;
		down = null;
		right = null;
	}

	public String getColumnNumber() {
		return columnNumber;
	}

	public void setColumnNumber(String columnNumber) {
		this.columnNumber = columnNumber;
	}

	public ColumnNode getDown() {
		return down;
	}

	public void setDown(ColumnNode down) {
		this.down = down;
	}

	public CardNumberNode getRight() {
		return right;
	}

	public void setRight(CardNumberNode right) {
		this.right = right;
	}

	public CardNumberNode getPlace() {
		return place;
	}

	public void setPlace(CardNumberNode place) {
		this.place = place;
	}
}
