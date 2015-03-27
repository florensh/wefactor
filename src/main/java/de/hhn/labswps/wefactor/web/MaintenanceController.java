package de.hhn.labswps.wefactor.web;

import java.security.Principal;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/maintenance")
public class MaintenanceController {

    @RequestMapping(method = RequestMethod.GET)
    public String maintenance(final HttpServletRequest request,
            final HttpSession session, final Principal currentUser,
            final Locale locale, final Model model) {

        return "maintenance";
    }

}
