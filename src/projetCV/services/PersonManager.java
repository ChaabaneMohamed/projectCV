package projetCV.services;

import java.util.ArrayList;
import java.util.Iterator;
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

import org.hibernate.Hibernate;

import projetCV.entities.Activity;
import projetCV.entities.Person;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class PersonManager {
 
    @PersistenceContext(unitName = "myBase")
    EntityManager em;

    public Person getPerson(int id) {
        return em.find(Person.class, id);
    }

    public void removePerson(int id) {
    	Person c = em.find(Person.class, id);
        if (c != null) {
            em.remove(c);
        }
    }

    public void createPerson(Person p) {
        //if(getPerson(p.getId()) == null)
        	em.persist(p);
    }
    
    public void updatePerson(Person updatedP) {
        em.merge(updatedP);
    }
    
    public void addActivity(Person p, Activity activity) {
    	List<Activity> activities = p.getCv();
    	activities.add(activity);
    	p.setCv(activities);
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
    
    public void removeAllPersons() {
    	List<Person> all = findAllPersons();
    	
    	for (Person person : all) {
			removePerson(person.getId());
		}
     }
    
    public List<Person> searchPersons(String part) {
    	List<Person> all = findAllPersons();
    	List<Person> find = new ArrayList<Person>();
        for (Person person : all) {
        	System.out.println(person.getFirstName() + ":" + person.getLastName()+ "/////"+ part);
			if(person.getFirstName().contains(part)
					|| person.getLastName().contains(part)) {
				find.add(person);
				break;
			}
			else {
				System.out.println("ELSE----------------------------------------");
				if(person.getCv() != null) {
					System.out.println("CV FOUND -----------------------------------");
					System.out.println("NB ACT = "+ person.getCv().size());
					for (Activity activity : person.getCv()) {
						
						System.out.println("|||||||||||IF "+ activity.getTitle()+ " = "+ part);
						if(activity.getTitle().contains(part)) {
							System.out.println("TITLE FOUND -----------------------------------");
							find.add(person);
							break;
						}
						
					}
				}
			} 
		}
        return find;
     }

}
