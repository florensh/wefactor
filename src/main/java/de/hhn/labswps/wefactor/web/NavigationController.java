package de.hhn.labswps.wefactor.web;

import java.security.Principal;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NavigationController {

    @RequestMapping("/user/timeline")
    public String goToTimeline(HttpServletRequest request, HttpSession session,
            Principal currentUser, Locale locale, Model model) {

        return "timeline";

    }

}
