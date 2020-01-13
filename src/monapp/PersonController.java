package monapp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import org.junit.Before;

import projetCV.entities.Activity;
import projetCV.entities.Person;
import projetCV.services.ConnectedUser;
import projetCV.services.PersonManager;

@ManagedBean(name = "person")
@SessionScoped
public class PersonController {

	@EJB
	private PersonManager personManager;

	@EJB
	private ConnectedUser connectedUser;

	Person p  = new Person();
	Person p2  = new Person();


	@PostConstruct
	public void init() {	
		System.out.println("Create " + this);
		//personManager.removeAllPersons();
		System.out.println("=======================================================NB PERS" + personManager.findAllPersons().size());

		if (personManager.findAllPersons().size() == 0) {

			List<Activity> activities = new ArrayList<Activity>();
			List<Activity> activities2 = new ArrayList<Activity>();

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

			p2.setFirstName("aaa");
			p2.setLastName("bbb");
			p2.setEmail("aaa.bbb@hotmail.fr");
			p2.setWebSite("google.fr");
			p2.setBirthDate(cal.getTime());
			p2.setPassWord("1234");
			p2.setCv(activities2);

			personManager.createPerson(p);
			personManager.createPerson(p2);

			Activity a1 = new Activity();
			a1.setId(0);
			a1.setNature("nature");
			a1.setTitle("title");
			a1.setWebSite("webSite");
			a1.setYear(2020);

			Activity a2 = new Activity();
			a2.setId(1);
			a2.setNature("nature");
			a2.setTitle("title");
			a2.setWebSite("webSite");
			a2.setYear(2020);

			personManager.addActivity(p, a1);
			personManager.addActivity(p, a2);

			List<Activity> act = personManager.getPerson(p.getId()).getCv();
			System.out.println("iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiNB ACT = " + activities.size() + "|||" + p.getId());
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

	public String save() {
		if(personManager.getPerson(p.getId()) == null) {
			personManager.createPerson(p);
			System.out.println("ADDED ============================");
		}
		else {
			personManager.updatePerson(p);
			System.out.println("UPDATED ============================");
		}
		return "persons";
	}

	public List<Activity> getCv() {
		List<Activity> activities = p.getCv();
		return activities;
	}

	public String show(int id) {
		p = personManager.getPerson(id);
		List<Activity> a = p.getCv();
		System.out.println("NB ACT = " + a.size() + "=======================================================" + p.getId() + "|" + id);
		System.out.println("name = " + p.getFirstName());
		return "showActivities";
	}

	public String newPerson() {
		p = new Person();
		return "editPerson?faces-redirect=true";
	}

	public String editPerson(int id) {
		p = personManager.getPerson(id);
		List<Activity> a = p.getCv();
		System.out.println("NB ACT = " + a.size() + "=======================================================" + p.getId() + "|" + id);
		System.out.println("name = " + p.getFirstName());
		return "editPerson";
	}

	public void setConnectedPerson(Person p) {	
		if(p == null) {
			System.out.println("rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
			return;
		}
		connectedUser.setPersonLogged(p);
	}

	public String logout() {
		connectedUser.setPersonLogged(null);
		return "persons?faces-redirect=true";
	}

	public boolean isLogged()  {
		return connectedUser.getPersonLogged() != null;
	}

}
