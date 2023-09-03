package com.viewer.Session;

import javax.jcr.Session;

public interface JcrSessionManager {
    Session getSession(String username, String password);
    void setSession(Session session);
}
