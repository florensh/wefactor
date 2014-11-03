package de.hhn.labswps.wefactor.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SignupController {

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String showSignupPage() {
        return "signup";
    }

}
