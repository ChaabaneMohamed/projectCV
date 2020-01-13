package projetCV.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.ejb.Stateful;

import projetCV.entities.Person;
import projetCV.services.ConnectedUser;
import projetCV.services.PersonManager;

@Stateful(name="connectedUser")
public class ConnectedUserBean implements ConnectedUser {

	private Person personlogged;
	
	@Override
	public void setPersonLogged(Person person) {
		System.out.println("PERSON LOGGED " + person.getEmail() + "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		this.personlogged = person;
	}

	@Override
	public Person getPersonLogged() {
		System.out.println("LOGGED ? = " + personlogged.getEmail()+"$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		return personlogged;
	}	
	
}