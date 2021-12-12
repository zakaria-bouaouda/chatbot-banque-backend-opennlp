package emi.ginfo.iql.model;

public class chatbot {
	private String question;
	private String reponse;
	
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getReponse() {
		return reponse;
	}
	public void setReponse(String reponse) {
		this.reponse = reponse;
	}
	public chatbot(String question, String reponse) {
		super();
		this.question = question;
		this.reponse = reponse;
	}
	public chatbot() {
		super();
		// TODO Auto-generated constructor stub
	}
}
