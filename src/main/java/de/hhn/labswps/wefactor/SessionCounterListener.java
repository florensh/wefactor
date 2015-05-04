package de.hhn.labswps.wefactor;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionCounterListener implements HttpSessionListener {

    // private static int totalActiveSessions;
    private static Set<String> sessions = new HashSet<String>();

    @Override
    public void sessionCreated(HttpSessionEvent arg0) {
        // totalActiveSessions++;
        sessions.add(arg0.getSession().getId());
        System.out.println("sessionCreated - add one session into counter");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent arg0) {
        // totalActiveSessions--;
        sessions.remove(arg0.getSession().getId());
        System.out
                .println("sessionDestroyed - deduct one session from counter");
    }

    public static int getTotalActiveSessions() {
        // return totalActiveSessions;
        return sessions.size();
    }
}