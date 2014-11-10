package de.hhn.labswps.wefactor.web.util;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class UserControllerAdvice {

    @Autowired
    private SocialControllerUtil util;

    @ModelAttribute
    public void setModel(HttpServletRequest request, Principal currentUser,
            Model model) {
        util.setModel(request, currentUser, model);

    }
}