package com.viewer.Session;

import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JcrSessionManagerImpl implements JcrSessionManager {

    private final Repository repository;
    private Session session;


    @Autowired
    public JcrSessionManagerImpl(Repository repository) {
        this.repository = repository;
    }

    @Override
    public Session getSession(String username, String password) {
        try {
            session= repository.login(new SimpleCredentials(username, password.toCharArray()), "default");
            return session;
        } catch (RepositoryException e) {
            // Gérer l'exception appropriée ici.
            return null;
        }
    }
    
    @Override
    public void setSession(Session session) {
        this.session = session;
    }
    
}
