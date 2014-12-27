package de.hhn.labswps.wefactor.web.util;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import de.hhn.labswps.wefactor.domain.Group;
import de.hhn.labswps.wefactor.domain.GroupRepository;
import de.hhn.labswps.wefactor.domain.TimelineEvent;
import de.hhn.labswps.wefactor.domain.TimelineEventRepository;
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

    @Autowired
    private TimelineEventRepository timelineEventRepository;

    @ModelAttribute
    public void setModel(HttpServletRequest request, Principal currentUser,
            Model model) {
        util.setModel(request, currentUser, model);
        List<Group> groups = new ArrayList<Group>();

        int countEvents = 0;
        if (currentUser != null) {
            String secUser = currentUser.getName();
            UserProfile profile = this.userProfileRepository
                    .findByUsername(secUser);

            groups = groupRepository.findByMembers(profile.getAccount());

            List<TimelineEvent> events = null;

            if (profile.getAccount().getGroups() == null
                    || profile.getAccount().getGroups().isEmpty()) {
                events = this.timelineEventRepository
                        .findByTargetAccountAndReadByUser(profile.getAccount(),
                                false);

            } else {
                events = this.timelineEventRepository
                        .findByTargetAccountOrTargetGroupInAndReadByUser(
                                profile.getAccount(), profile.getAccount()
                                        .getGroups(), false);

            }

            if (events != null) {
                countEvents = events.size();
            }

        }
        model.addAttribute("unreadEvents", countEvents);

        Map<Group, Integer> gr = new HashMap<Group, Integer>();
        for (Group g : groups) {
            int count = 0;
            List<TimelineEvent> events = this.timelineEventRepository
                    .findByTargetGroupAndReadByUser(g, false);
            if (events != null) {
                count = events.size();
            }
            gr.put(g, count);
        }
        model.addAttribute("usergroups", gr);

        SearchBoxDataObject sbda = new SearchBoxDataObject();
        model.addAttribute("searchBoxDataObject", sbda);

    }
}
