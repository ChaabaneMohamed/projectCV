package projetCV.client;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.NoSuchEJBException;
import javax.ejb.embeddable.EJBContainer;
import javax.faces.bean.ManagedProperty;
import javax.ejb.NoSuchEJBException;

import org.hibernate.Hibernate;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import monapp.PersonController;
import monapp.UserController;
import projetCV.entities.Activity;
import projetCV.entities.Person;
import projetCV.services.ConnectedUser;
import projetCV.services.PersonManager;

public class TestProjetCV {

	@EJB
    PersonManager personManager;
	
	@ManagedProperty("#{user}")
	private UserController userController;
	
	@ManagedProperty("#{person}")
	private PersonController personController;
	
	@EJB
	ConnectedUser connectedUser;
	
	@EJB
	ConnectedUser connectedUser2;
	
	Person p  = new Person();
	Person p2  = new Person();
	Person p3  = new Person();
	
	
	@Before public void intialisation() {
		personManager.removeAllPersons();
		
		List<Activity> activities1 = new ArrayList<Activity>();
		List<Activity> activities2 = new ArrayList<Activity>();
		List<Activity> activities3 = new ArrayList<Activity>();
		
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
        p.setCv(activities1);
        
        
        p2.setFirstName("aaa");
        p2.setLastName("bbb");
        p2.setEmail("aaa.bbb@hotmail.fr");
        p2.setWebSite("google.fr");
        p2.setBirthDate(cal.getTime());
        p2.setPassWord("1234");
        p2.setCv(activities2);
        
        p3.setFirstName("ccc");
        p3.setLastName("ddd");
        p3.setEmail("ccc.ddd@hotmail.fr");
        p3.setWebSite("google.fr");
        p3.setBirthDate(cal.getTime());
        p3.setPassWord("1234");
        p3.setCv(activities3);
	}


    public TestProjetCV() throws Exception {
        // Nous injectons le test dans le container pour que 
        // l'annotation @EJB soit traitée
    	EJBContainer.createEJBContainer().getContext().bind("inject", this);
        assertNotNull(personManager);
    }

    @Test
    public void testAddPerson() throws Exception {
    	personManager.createPerson(p);
    	personManager.getPerson(p.getId());
    }
    
    @Test
    public void testAddActivity() throws Exception {
    	personManager.createPerson(p);
    	
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
    	
    	Assertions.assertEquals(2, p.getCv().size());
    }
    
    @Test
    public void testUpdatePerson() throws Exception {
    	personManager.createPerson(p);
    	String web1 = personManager.getPerson(p.getId()).toString();
        p.setWebSite("google.com");
    	personManager.updatePerson(p);
    	String web2 = personManager.getPerson(p.getId()).toString();
    	Assertions.assertNotEquals(web1, web2);
    }
    
    @Test
    public void testAdd2Person() throws Exception {
        personManager.createPerson(p);
        personManager.createPerson(p2);
        
        personManager.getPerson(p.getId());
        personManager.getPerson(p2.getId());
    }
    
    @Test
    public void testLoginPerson() throws Exception {
    	userController.setEmail("mohamed.chaabane@hotmail.fr");
    	userController.setPassword("1234");
    	userController.login();
    	personController.logout();
    }
    
    @Test(expected = NoSuchEJBException.class)
    public void testLoginEJBClosed() throws Exception {
    	connectedUser.login("mohamed.chaabane@hotmail.fr", "1234");
    	boolean log1 = connectedUser.isLogged();
    	connectedUser.logout();
    	connectedUser.login("aaa.bbb@hotmail.fr", "1234");
    	boolean log2 = connectedUser.isLogged();
    	connectedUser.logout();
    }
    
    @Test
    public void testCreateCv() throws Exception {
    	personManager.createPerson(p); 
    	
    	Activity activity = new Activity();
    	activity.setPerson(p);
    	activity.setYear(2019);
    	activity.setTitle("Titre");
    	
    	personManager.addActivity(p, activity);
       	
    	Assertions.assertEquals(1, personManager.getPerson(p.getId()).getCv().size());
    }
    
    @Test
    public void testFindAllPersons() throws Exception {
    	personManager.createPerson(p);
    	personManager.createPerson(p2);
    	personManager.createPerson(p3);
    	
    	List<Person> all = personManager.findAllPersons();
    	
    	for (Person person : all) {
			System.out.println(person + "::"+ person.getId());
		}

    	Assertions.assertEquals(3, personManager.findAllPersons().size());
    }
    
    @Test
    public void testRemoveAllPersons() throws Exception {
    	personManager.createPerson(p);
    	personManager.createPerson(p2);
    	personManager.createPerson(p3);

    	personManager.removeAllPersons();
    	
    	Assertions.assertEquals(0, personManager.findAllPersons().size());
    }
    
    @Test
    public void testFindOnePersonByFirstName() throws Exception {
    	personManager.createPerson(p);
    	personManager.createPerson(p2);
    	personManager.createPerson(p3);
    	
    	Assertions.assertEquals(1, personManager.searchPersons("Moham").size());
    }
    
    @Test
    public void testFindOnePersonByLastName() throws Exception {
    	personManager.createPerson(p);
    	personManager.createPerson(p2);
    	personManager.createPerson(p3);
    	
    	Assertions.assertEquals(1, personManager.searchPersons("CHAA").size());
    }
    
    @Test
    public void testFindOnePersonByActivity() throws Exception {
    	Activity activity = new Activity();		
    	activity.setPerson(p);
    	activity.setYear(2019);
    	activity.setTitle("Titre"); 
    	
    	Activity activity2 = new Activity();
    	activity2.setPerson(p2);
    	activity2.setYear(2019);
    	activity2.setTitle("Titreazdazd");  
    	
    	personManager.createPerson(p);
    	personManager.addActivity(p, activity);
    	
    	personManager.createPerson(p2);
    	personManager.addActivity(p2, activity2);
    	
    	personManager.createPerson(p3);
    	
    	
    	Assertions.assertEquals(2, personManager.searchPersons("Tit").size());
    }

}