package de.hhn.labswps.wefactor.web;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import de.hhn.labswps.wefactor.domain.AccountRepository;
import de.hhn.labswps.wefactor.domain.Group;
import de.hhn.labswps.wefactor.domain.GroupRepository;
import de.hhn.labswps.wefactor.domain.TimelineEventRepository;
import de.hhn.labswps.wefactor.domain.UserProfileRepository;

@Controller
public class GroupController {

    @Autowired
    private TimelineEventRepository timelineEventRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private GroupRepository groupRepository;

    @RequestMapping("/user/group")
    public String goToGroup(@RequestParam("id") Long id, ModelMap model,
            Principal currentUser) {

        // Laden der groupe
        Group group = this.groupRepository.findOne(id);
        model.addAttribute("group", group);

        // Laden der events
        Pageable topFive = new PageRequest(0, 6);
        model.addAttribute("events", this.timelineEventRepository
                .findByTargetGroupOrderByEventDateDesc(group, topFive));

        // entries sind ueber group.getEntries() abrufbar bzw in thymeleaf
        // group.entries
        // in group.html verwendest du am besten aus entries das fragment
        // th:fragment="entryList (entries)" (siehe zeile 58 entries) mit
        // th:replace

        return "group";

    }

    @RequestMapping("/user/groupbrowser")
    public String goToGroupBrowser(ModelMap model, Principal currentUser) {

        model.addAttribute("groups", this.groupRepository.findAll());

        return "groupBrowser";
    }

}
