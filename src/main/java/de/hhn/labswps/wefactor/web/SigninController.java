package de.hhn.labswps.wefactor.web;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SigninController {

    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public String showSigninPage(HttpServletRequest request,
            Principal currentUser, Model model) {
        return "signin";
    }

}