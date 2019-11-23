package projetCV.services;

import javax.ejb.Local;

@Local
public interface ConnectedUser {
   void login(String login, String pwd, int id);
   void logout();
   String getLogin();
}