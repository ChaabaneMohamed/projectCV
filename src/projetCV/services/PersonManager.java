package projetCV.services;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import projetCV.entities.Activity;
import projetCV.entities.Person;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class PersonManager {

    @PersistenceContext(unitName = "myBase")
    EntityManager em;

    public Person getPerson(String email) {
        return em.find(Person.class, email);
    }

    public void removePerson(String email) {
    	Person c = em.find(Person.class, email);
        if (c != null) {
            em.remove(c);
        }
    }

    public void createPerson(Person p) {
        removePerson(p.getEmail());
        Person c = new Person();
        c.setFirstName(p.getFirstName());
        c.setLastName(p.getLastName());
        c.setEmail(p.getEmail());
        c.setWebSite(p.getWebSite());
        c.setBirthDate(p.getBirthDate());
        c.setPassWord(p.getPassWord());
        c.setCv(new ArrayList<Activity>());
        em.persist(c);
    }
    
    public void updatePerson(String email, Person p) {
        Person c = getPerson(email);
        c.setFirstName(p.getFirstName());
        c.setLastName(p.getLastName());
        c.setEmail(p.getEmail());
        c.setWebSite(p.getWebSite());
        c.setBirthDate(p.getBirthDate());
        c.setPassWord(p.getPassWord());
        em.merge(c);
    }
    
    public void createCv(Person p) {
    	ArrayList<Activity> Cv = new ArrayList<Activity>();
    	
    }

}
