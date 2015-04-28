package de.hhn.labswps.wefactor;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionCounterListener implements HttpSessionListener {

    private static int totalActiveSessions;

    @Override
    public void sessionCreated(HttpSessionEvent arg0) {
        totalActiveSessions++;
        System.out.println("sessionCreated - add one session into counter");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent arg0) {
        totalActiveSessions--;
        System.out
                .println("sessionDestroyed - deduct one session from counter");
    }

    public static int getTotalActiveSessions() {
        return totalActiveSessions;
    }
}