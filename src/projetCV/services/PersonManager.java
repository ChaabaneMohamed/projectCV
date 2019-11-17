package projetCV.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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
    
    public void createCv(Person p, ArrayList<Activity> cv) {
    	p.setCv(cv);
    	em.persist(em);
    }
    
    public void updateCv(Person p, ArrayList<Activity> cv) {
       p.setCv(cv);
        em.merge(p);
    }
    
    public List<Person> findAllPersons() {
    	CriteriaBuilder cb = em.getCriteriaBuilder();
    	CriteriaQuery<Person> cq = cb.createQuery(Person.class);
    	Root<Person> rootEntry = cq.from(Person.class);
    	CriteriaQuery<Person> all = cq.select(rootEntry);
    	TypedQuery<Person> allQuery = em.createQuery(all);
        return allQuery.getResultList();
     }
    
    public List<Person> searchPersons(String part) {
    	List<Person> all = findAllPersons();
    	List<Person> find = new ArrayList<Person>();
        for (Person person : all) {
			if(person.getFirstName().contains(part)
					|| person.getLastName().contains(part)) {
				find.add(person);
				break;
			}
			else {
				for (Activity activity : person.getCv()) {
					if(activity.getTitle().contains(part)) {
						find.add(person);
						break;
					}
					
				}
			} 
		}
        return find;
     }

}
