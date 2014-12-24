package de.hhn.labswps.wefactor.web.util;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import de.hhn.labswps.wefactor.domain.Group;
import de.hhn.labswps.wefactor.domain.GroupRepository;
import de.hhn.labswps.wefactor.domain.UserProfile;
import de.hhn.labswps.wefactor.domain.UserProfileRepository;
import de.hhn.labswps.wefactor.web.DataObjects.SearchBoxDataObject;

@ControllerAdvice
public class UserControllerAdvice {

    @Autowired
    private SocialControllerUtil util;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @ModelAttribute
    public void setModel(HttpServletRequest request, Principal currentUser,
            Model model) {
        util.setModel(request, currentUser, model);
        List<Group> groups = new ArrayList<Group>();

        if (currentUser != null) {
            String secUser = currentUser.getName();
            UserProfile profile = this.userProfileRepository
                    .findByUsername(secUser);

            groups = groupRepository.findByMembers(profile.getAccount());

        }
        model.addAttribute("usergroups", groups);

        SearchBoxDataObject sbda = new SearchBoxDataObject();
        model.addAttribute("searchBoxDataObject", sbda);

    }
}
