package projetCV.services;

import javax.ejb.Local;

import projetCV.entities.Person;

@Local
public interface ConnectedUser {
   void setPersonLogged(Person person);
   Person getPersonLogged();
}