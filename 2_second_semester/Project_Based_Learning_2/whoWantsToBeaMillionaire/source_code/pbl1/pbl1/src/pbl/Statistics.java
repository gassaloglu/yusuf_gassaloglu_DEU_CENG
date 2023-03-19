package pbl;

public class Statistics {
	private int question_id;
	private int contestant_id;
	private String answer;
	public Statistics(int question_id, int contestant_id, String answer) {
		super();
		this.question_id = question_id;
		this.contestant_id = contestant_id;
		this.answer = answer;
	}
	public int getQuestion_id() {
		return question_id;
	}
	public void setQuestion_id(int question_id) {
		this.question_id = question_id;
	}
	public int getContestant_id() {
		return contestant_id;
	}
	public void setContestant_id(int contestant_id) {
		this.contestant_id = contestant_id;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}

	
	
}
