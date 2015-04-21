package de.hhn.labswps.wefactor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import de.hhn.labswps.wefactor.domain.JournalEntry.EventType;
import de.hhn.labswps.wefactor.service.JournalService;

public class CustomAuthenticationSuccessHandler extends
        SimpleUrlAuthenticationSuccessHandler implements
        AuthenticationSuccessHandler {

    @Autowired
    protected JournalService journalService;

    protected void writeEventToJournal(String username, EventType eventType) {
        this.journalService.writeEntry(username, eventType);

    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        writeEventToJournal(authentication.getName(), EventType.LOGIN);
        super.onAuthenticationSuccess(request, response, authentication);
    }

}
