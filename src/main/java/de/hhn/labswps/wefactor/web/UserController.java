package de.hhn.labswps.wefactor.web;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.hhn.labswps.wefactor.service.SignUpService;
import de.hhn.labswps.wefactor.web.DataObjects.RegisterFormDataObject;

@Controller
public class UserController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SignUpService signUpService;

    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public String showSigninPage(HttpServletRequest request,
            Principal currentUser, Model model) {
        return "signin";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String showSignupPage(Model model) {
        model.addAttribute("registerFormDataObject",
                new RegisterFormDataObject());
        return "registration";
    }

    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    public String submitForm(
            @Valid RegisterFormDataObject registerFormDataObject,
            BindingResult result, Model m) {
        if (result.hasErrors()) {
            return "registration";
        }

        this.signUpService.execute(registerFormDataObject.getUsername(),
                registerFormDataObject.getEmail(),
                registerFormDataObject.getPassword());

        UserDetails user = userDetailsService
                .loadUserByUsername(registerFormDataObject.getUsername());

        if (user == null) {
            return "registration";
        }

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(user, null, user
                        .getAuthorities()));

        return "redirect:/";
    }

}