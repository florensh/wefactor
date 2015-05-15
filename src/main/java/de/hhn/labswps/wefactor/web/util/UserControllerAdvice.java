package de.hhn.labswps.wefactor.web.util;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
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

/**
 * The Class UserControllerAdvice.
 */
@ControllerAdvice
public class UserControllerAdvice {

    /** The util. */
    @Autowired
    private SocialControllerUtil util;

    @Autowired
    private ApplicationContext applicationContext;

    /** The group repository. */
    @Autowired
    private GroupRepository groupRepository;

    /** The user profile repository. */
    @Autowired
    private UserProfileRepository userProfileRepository;

    /** The timeline event repository. */
    @Autowired
    private TimelineEventRepository timelineEventRepository;

    @Value("${feedback_mail_link}")
    String feedbackMailLink;

    /**
     * Sets the model.
     *
     * @param request
     *            the request
     * @param currentUser
     *            the current user
     * @param model
     *            the model
     */
    @ModelAttribute
    public void setModel(final HttpServletRequest request,
            final Principal currentUser, final Model model) {
        this.util.setModel(request, currentUser, model);
        List<Group> groups = new ArrayList<Group>();

        Environment env = applicationContext.getEnvironment();
        model.addAttribute("env", env);

        int countEvents = 0;
        if (currentUser != null) {
            final String secUser = currentUser.getName();
            final UserProfile profile = this.userProfileRepository
                    .findByUsername(secUser);

            groups = this.groupRepository.findByMembers(profile.getAccount());

            List<TimelineEvent> events = null;

            if ((profile.getAccount().getGroups() == null)
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

        final Map<Group, Integer> gr = new HashMap<Group, Integer>();
        for (final Group g : groups) {
            int count = 0;
            final List<TimelineEvent> events = this.timelineEventRepository
                    .findByTargetGroupAndReadByUser(g, false);
            if (events != null) {
                count = events.size();
            }
            gr.put(g, count);
        }
        model.addAttribute("usergroups", gr);

        if (!model.containsAttribute("searchBoxDataObject")) {
            final SearchBoxDataObject sbda = new SearchBoxDataObject();
            model.addAttribute("searchBoxDataObject", sbda);

        }

        if (this.feedbackMailLink != null && !this.feedbackMailLink.isEmpty()) {
            model.addAttribute("feedbackLink", this.feedbackMailLink);
        }

    }
}
