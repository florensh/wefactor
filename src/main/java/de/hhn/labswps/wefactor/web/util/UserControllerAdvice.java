package de.hhn.labswps.wefactor.web.util;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import de.hhn.labswps.wefactor.domain.GroupRepository;
import de.hhn.labswps.wefactor.web.DataObjects.SearchBoxDataObject;

@ControllerAdvice
public class UserControllerAdvice {

    @Autowired
    private SocialControllerUtil util;

    @Autowired
    private GroupRepository groupRepository;

    @ModelAttribute
    public void setModel(HttpServletRequest request, Principal currentUser,
            Model model) {
        util.setModel(request, currentUser, model);

        SearchBoxDataObject sbda = new SearchBoxDataObject();
        model.addAttribute("searchBoxDataObject", sbda);
        model.addAttribute("groups", groupRepository.findAll());// TODO nur die
                                                                // gruppen des
                                                                // angemeldeten
                                                                // users laden

    }
}
