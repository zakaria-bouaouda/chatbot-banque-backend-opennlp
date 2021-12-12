package emi.ginfo.iql.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="reponse")
public class reponse {
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String id;
	private String categorie;
	private String reponse;
	
	public reponse(String categorie, String reponse) {
		
		this.categorie = categorie;
		this.reponse = reponse;
	}

	public reponse() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public String getReponse() {
		return reponse;
	}

	public void setReponse(String reponse) {
		this.reponse = reponse;
	}

}
