package projetCV.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name= "Person")
public class Person implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY	)
	@Column(name="person_id")
	private int id;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "person", cascade=CascadeType.ALL)
	List<Activity> cv;

	@Column(name = "firstName")
	private String firstName;											

	@Column(name = "lastName")
	private String lastName;

	@Column(name = "email")
	private String email;

	@Column(name = "webSite")
	private String webSite;

	@Column(name = "birthDate")
	private Date birthDate; 

	@Column(name = "passWord")
	private String passWord;


	/// Getters AND Setters ////////////////

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Activity> getCv() {
		return cv;
	}

	public void setCv(List<Activity> cv) {
		this.cv = cv;
	}
	
	@Override
	public String toString() {
		return "Person: " + this.getId() + ":" + this.getFirstName() + ":" + this.getLastName() + ":" + this.getEmail() + ":" + this.getWebSite()+ ":CV="+this.getCv();  
	}

}
