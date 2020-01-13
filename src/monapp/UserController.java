package monapp;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import projetCV.entities.Person;
import projetCV.impl.ConnectedUserBean;
import projetCV.services.ConnectedUser;
import projetCV.services.PersonManager;

@ManagedBean(name = "user")
@SessionScoped
public class UserController {

	@ManagedProperty("#{person}")
	private PersonController personController;

	@EJB
	private PersonManager personManager;
	
	private boolean logged = false;

	public boolean getLogged() {
		return logged;
	}

	public void setLogged(boolean logged) {
		this.logged = logged;
	}

	private String email;
	private String password;

	public String login() {
		System.out.println("LOGIN TEST ==============================================");
		Person p = personManager.getByEmailAndPassword(email, password);

		if(p == null) {
			return null;
		}
		System.out.println("USER FOUND ----------------------------------------------------");

		personController.setConnectedPerson(p);
		setLogged(true);
		return "persons?faces-redirect=true";
	}
	
	public PersonController getPersonController() {
		return personController;
	}

	public void setPersonController(PersonController personController) {
		this.personController = personController;
	}

	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public PersonController getPersonViewController() {
		return personController;
	}

	public void setPersonViewController(PersonController personViewController) {
		this.personController = personViewController;
	}


}
