package de.hhn.labswps.wefactor.web;

import java.security.Principal;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.hhn.labswps.wefactor.web.util.SocialControllerUtil;

@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    private SocialControllerUtil util;

    @RequestMapping(method = RequestMethod.GET)
    public String index(HttpServletRequest request, HttpSession session,
            Principal currentUser, Locale locale, Model model) {
        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,
                DateFormat.LONG, locale);
        String formattedDate = dateFormat.format(date);
        model.addAttribute("currentTime", formattedDate);

        util.setModel(request, currentUser, model);
        return "index";
    }
}