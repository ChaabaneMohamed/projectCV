package monapp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.junit.Before;

import projetCV.entities.Activity;
import projetCV.entities.Person;
import projetCV.services.ConnectedUser;
import projetCV.services.PersonManager;

@ManagedBean(name = "person")
@SessionScoped
public class PersonController {

	@EJB
	PersonManager personManager;

	Person p  = new Person();
	
	@PostConstruct
	public void init() {
		personManager.removeAllPersons();
		
		System.out.println("Create " + this);
		if (personManager.findAllPersons().size() == 0) {
			personManager.removeAllPersons();

			List<Activity> activities = new ArrayList<Activity>();

			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, 1997);
			cal.set(Calendar.MONTH, Calendar.NOVEMBER);
			cal.set(Calendar.DAY_OF_MONTH, 7);

			p.setFirstName("Mohamed");
			p.setLastName("CHAABANE");
			p.setEmail("mohamed.chaabane@hotmail.fr");
			p.setWebSite("google.fr");
			p.setBirthDate(cal.getTime());
			p.setPassWord("1234");
			p.setCv(activities);
			
			save();
		}

	}

	public List<Person> getPersons() {
		return personManager.findAllPersons();
	}

	public Person getPerson() {
		return p;
	}

	public Person getPerson(int id) {
		p = personManager.getPerson(id);
		return p;
	}

	public void save() {
		personManager.createPerson(p);
	}

	public String newPerson() {
		p = new Person();
		return "editPerson?faces-redirect=true";
	}


}
