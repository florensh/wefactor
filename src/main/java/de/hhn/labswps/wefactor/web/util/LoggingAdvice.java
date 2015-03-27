package de.hhn.labswps.wefactor.web.util;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class LoggingAdvice {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @ModelAttribute
    public void setModel(final HttpServletRequest request,
            final Principal currentUser, final Model model) {

        String user = request.getRemoteUser();
        String req = request.getServletPath();

        log.info("User " + user + " requested " + req);

    }

}
