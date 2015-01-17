package de.hhn.labswps.wefactor.web;

import java.security.Principal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import de.hhn.labswps.wefactor.domain.AccountRepository;
import de.hhn.labswps.wefactor.domain.Group;
import de.hhn.labswps.wefactor.domain.GroupRepository;
import de.hhn.labswps.wefactor.domain.ObjectIdentification;
import de.hhn.labswps.wefactor.domain.TimelineEvent;
import de.hhn.labswps.wefactor.domain.TimelineEventRepository;
import de.hhn.labswps.wefactor.domain.UserProfile;
import de.hhn.labswps.wefactor.domain.UserProfileRepository;
import de.hhn.labswps.wefactor.specification.WeFactorValues.EventType;
import de.hhn.labswps.wefactor.web.DataObjects.EntryList;
import de.hhn.labswps.wefactor.web.DataObjects.GroupDataObject;
import de.hhn.labswps.wefactor.web.DataObjects.ScreenMessageObject;
import de.hhn.labswps.wefactor.web.DataObjects.SearchBoxDataObject;
import de.hhn.labswps.wefactor.web.util.DataUtils;

@Controller
public class GroupController {

    public static final String GROUP_DETAILS_LINK = "/user/group";

    @Autowired
    private TimelineEventRepository timelineEventRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private GroupRepository groupRepository;

    @RequestMapping(GROUP_DETAILS_LINK)
    public String goToGroup(@RequestParam("id") Long id, ModelMap model,
            Principal currentUser) {

        // Laden der groupe
        Group group = this.groupRepository.findOne(id);
        model.addAttribute("group", group);

        // Laden der events
        Pageable topFive = new PageRequest(0,
                TimelineEventRepository.DEFAULT_PAGE_SIZE);
        model.addAttribute("events", this.timelineEventRepository
                .findByTargetGroupOrderByEventDateDesc(group, topFive));

        EntryList entries = new EntryList(group.getEntries());
        Collections.sort(entries);
        model.addAttribute("entries", entries);

        return "group";

    }

    @RequestMapping("/user/group/join")
    public String joinGroup(@RequestParam("id") Long id, ModelMap model,
            Principal currentUser) {

        String secUser = currentUser.getName();
        UserProfile profile = this.userProfileRepository
                .findByUsername(secUser);

        // Laden der groupe
        Group group = this.groupRepository.findOne(id);
        group.addMember(profile.getAccount());

        this.groupRepository.save(group);
        this.accountRepository.save(profile.getAccount());

        ObjectIdentification oid = DataUtils.createObjectIdentification(group,
                Group.class.getSimpleName());
        TimelineEvent event = new TimelineEvent(new Date(),
                profile.getAccount(), group, EventType.USER_JOINED_GROUP, oid);

        this.timelineEventRepository.save(event);

        return goToGroupBrowser(model, currentUser);

    }

    @RequestMapping("/user/group/leave")
    public String leaveGroup(@RequestParam("id") Long id, ModelMap model,
            Principal currentUser) {

        String secUser = currentUser.getName();
        UserProfile profile = this.userProfileRepository
                .findByUsername(secUser);

        // Laden der groupe
        Group group = this.groupRepository.findOne(id);
        group.removeMember(profile.getAccount());

        this.groupRepository.save(group);
        this.accountRepository.save(profile.getAccount());

        ObjectIdentification oid = DataUtils.createObjectIdentification(group,
                Group.class.getSimpleName());
        TimelineEvent event = new TimelineEvent(new Date(),
                profile.getAccount(), group, EventType.USER_LEFT_GROUP, oid);

        this.timelineEventRepository.save(event);

        return goToGroupBrowser(model, currentUser);

    }

    @RequestMapping("/user/groupbrowser")
    public String goToGroupBrowser(ModelMap model, Principal currentUser) {

        String secUser = currentUser.getName();
        UserProfile profile = this.userProfileRepository
                .findByUsername(secUser);

        model.addAttribute("groups",
                this.groupRepository.findByMembers(profile.getAccount()));

        return "groupbrowser";
    }

    @RequestMapping("/user/group/add")
    public String goToGroupEdit(ModelMap model, Principal currentUser) {

        String secUser = currentUser.getName();
        UserProfile profile = this.userProfileRepository
                .findByUsername(secUser);

        model.addAttribute("groupDataObject", new GroupDataObject());

        return "groupedit";
    }

    @RequestMapping(value = "/user/group/save", method = RequestMethod.POST)
    public String submitGroupEditForm(@Valid GroupDataObject groupDataObject,
            BindingResult result, ModelMap m, Principal currentUser) {
        if (result.hasErrors()) {
            return "groupedit";
        }

        UserProfile up = this.userProfileRepository.findByUsername(currentUser
                .getName());

        Group group = new Group(groupDataObject.getName(),
                groupDataObject.getDescription());

        group.addMember(up.getAccount());

        this.groupRepository.save(group);
        this.accountRepository.save(up.getAccount());

        return goToGroupBrowser(m, currentUser);
    }

    @RequestMapping(value = "/search/groups", method = RequestMethod.POST)
    public String executeSearch(@Valid SearchBoxDataObject searchBoxDataObject,
            BindingResult result, Model m, Principal currentUser) {
        if (result.hasErrors()) {
            return "editprofile";
        }

        List<Group> groups = this.groupRepository
                .findByNameContaining(searchBoxDataObject.getSearchtext());

        ScreenMessageObject sm = new ScreenMessageObject("We've found "
                + groups.size() + " results for "
                + searchBoxDataObject.getSearchtext());
        sm.setStrong(searchBoxDataObject.getSearchtext());
        m.addAttribute(ScreenMessageObject.MODEL_NAME, sm);

        m.addAttribute("groups", groups);

        return "groupbrowser";
    }

}
