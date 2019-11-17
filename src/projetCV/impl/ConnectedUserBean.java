package projetCV.impl;

import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.ejb.Stateful;

import projetCV.entities.Person;
import projetCV.services.ConnectedUser;
import projetCV.services.PersonManager;

@Stateful(name="connectedUser")
public class ConnectedUserBean implements ConnectedUser {
        
	@EJB
	private PersonManager personManager;
	
    private String login = "";

    public void login(String login, String pwd) {
       
       Person p = personManager.getPerson(login);
       if (p != null) {
            this.login = login;
            System.out.printf("Login user %s on %s\n", login, this);
        }
    }

    @Remove
    public void logout() {
        this.login = "";
        System.out.printf("Logout on %s\n", this);
    }

    public String getLogin() {
        return login;
    }

}