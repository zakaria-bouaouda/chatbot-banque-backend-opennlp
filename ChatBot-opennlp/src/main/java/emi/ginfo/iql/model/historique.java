package emi.ginfo.iql.model;
//
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Table;

import org.springframework.data.annotation.Id;
//import javax.persistence.Entity;

//@Entity
public class historique {
	@Id 
	
	//
	public int id;
	public String question;
	public historique(String question) {
		super();
		this.question = question;
	}
	public historique() {
		super();
		// TODO Auto-generated constructor stub
	}
	public historique(int id, String question) {
		super();
		this.id = id;
		this.question = question;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}

}
