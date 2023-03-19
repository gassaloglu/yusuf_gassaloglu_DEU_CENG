package pbl;
// question class it retrieves questions
public class Question {
	//attributes
    private String category;   //questions category	   
    private String text;		  // question
    private String choice1;	  // a choice	
    private String choice2;	  // b choice
    private String choice3;	  // c choice
    private String choice4;    // d choice
    private String answer;     // answer
    private String difficulty; // question difficulty  
    
    //constructor
	public Question(String category, String text, String choice1, String choice2, String choice3, String choice4,
			String answer, String difficulty) {
		
		this.category = category;
		this.text = text;
		this.choice1 = choice1;
		this.choice2 = choice2;
		this.choice3 = choice3;
		this.choice4 = choice4;
		this.answer = answer;
		this.difficulty = difficulty;
	}
	
	// getters and setters
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getQuestion() {
		return text;
	}
	public void setQuestion(String question) {
		this.text = question;
	}	
	public String getChoice1() {
		return choice1;
	}
	public void setChoice1(String choice1) {
		this.choice1 = choice1;
	}
	public String getChoice2() {
		return choice2;
	}
	public void setChoice2(String choice2) {
		this.choice2 = choice2;
	}
	public String getChoice3() {
		return choice3;
	}
	public void setChoice3(String choice3) {
		this.choice3 = choice3;
	}
	public String getChoice4() {
		return choice4;
	}
	public void setChoice4(String choice4) {
		this.choice4 = choice4;
	}	
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}	
}
