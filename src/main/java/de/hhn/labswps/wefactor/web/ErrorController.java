package de.hhn.labswps.wefactor.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/error")
public class ErrorController {

    @RequestMapping(method = RequestMethod.GET)
    public String error(final HttpServletRequest request,
            final HttpSession session) {

        return "error";

    }
}
