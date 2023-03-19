package columns;

public class CardNumberNode {
	private int cardNumber;
	private CardNumberNode next;
	
	public CardNumberNode(int dataToAdd) {
		cardNumber = dataToAdd;
		next = null;
	}

	public int getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(int cardNumber) {
		this.cardNumber = cardNumber;
	}

	public CardNumberNode getNext() {
		return next;
	}

	public void setNext(CardNumberNode next) {
		this.next = next;
	}
	
}
