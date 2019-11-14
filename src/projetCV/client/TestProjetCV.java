package projetCV.client;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Calendar;

import javax.ejb.EJB;
import javax.ejb.NoSuchEJBException;
import javax.ejb.embeddable.EJBContainer;
import javax.ejb.NoSuchEJBException;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import projetCV.entities.Person;
import projetCV.services.ConnectedUser;
import projetCV.services.PersonManager;

public class TestProjetCV {

	@EJB
    PersonManager personManager;
	
	@EJB
	ConnectedUser connectedUser;
	
	@EJB
	ConnectedUser connectedUser2;

    public TestProjetCV() throws Exception {
        // Nous injectons le test dans le container pour que 
        // l'annotation @EJB soit traitée
    	EJBContainer.createEJBContainer().getContext().bind("inject", this);
        assertNotNull(personManager);
    }

    @Test
    public void testAddPerson() throws Exception {
    	Person p = new Person();
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
    	personManager.createPerson(p);
    }
    
    @Test
    public void testAdd2Person() throws Exception {
    	Person p = new Person();
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
    	
    	Person p2 = new Person();
    	Calendar cal2 = Calendar.getInstance();
    	cal2.set(Calendar.YEAR, 1998);
    	cal2.set(Calendar.MONTH, Calendar.NOVEMBER);
    	cal2.set(Calendar.DAY_OF_MONTH, 24);
    	p2.setFirstName("aaa");
        p2.setLastName("bbb");
        p2.setEmail("aaa.bbb@hotmail.fr");
        p2.setWebSite("google.fr");
        p2.setBirthDate(cal2.getTime());
        p2.setPassWord("1234");
        
        personManager.createPerson(p);
        personManager.createPerson(p2);
    }
    
    @Test
    public void testLoginPerson() throws Exception {
    	connectedUser.login("mohamed.chaabane@hotmail.fr", "1234");
    	connectedUser.logout();
    }
    
    @Test(expected = NoSuchEJBException.class)
    public void testLoginEJBClosed() throws Exception {
    	connectedUser.login("mohamed.chaabane@hotmail.fr", "1234");
    	String log1 = connectedUser.getLogin();
    	connectedUser.logout();
    	connectedUser.login("aaa.bbb@hotmail.fr", "1234");
    	String log2 = connectedUser.getLogin();
    	connectedUser.logout();
    }
    
    @Test
    public void testLogin2Person() throws Exception {
    	connectedUser.login("mohamed.chaabane@hotmail.fr", "1234");
    	String log1 = connectedUser.getLogin();
    	connectedUser.logout();
    	connectedUser2.login("aaa.bbb@hotmail.fr", "1234");
    	String log2 = connectedUser2.getLogin();
    	connectedUser2.logout();
    	Assertions.assertNotEquals(log1, log2);
    }

}