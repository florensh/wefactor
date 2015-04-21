package de.hhn.labswps.wefactor.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.hhn.labswps.wefactor.domain.JournalEntry.EventType;
import de.hhn.labswps.wefactor.service.JournalService;
import de.hhn.labswps.wefactor.service.JournalService.StatisticValue;
import de.hhn.labswps.wefactor.web.DataObjects.AdminStatisticsDataObject;
import de.hhn.labswps.wefactor.web.util.ViewUtil;

@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/administration")
public class AdminController extends BaseController {

    @Autowired
    private JournalService journalService;

    @RequestMapping(method = RequestMethod.GET)
    public String admin(final Model model,
            @Valid AdminStatisticsDataObject adminStatisticsDataObject,
            final BindingResult result) {

        if (result.hasErrors()) {
            return "administration";
        }

        ViewUtil.showMessage(
                "Manage weFactor! There are currently ? users online.", "?",
                model);

        List<StatisticValue> l = this.journalService.getStatistic(
                adminStatisticsDataObject.getDay(),
                adminStatisticsDataObject.getEventType());

        String[] hours = new String[24];
        Long[] values = new Long[24];
        Long[] users = new Long[24];

        for (int i = 0; i < l.size(); i++) {
            hours[i] = ((StatisticValue) l.get(i)).key;
            values[i] = ((StatisticValue) l.get(i)).value;
            users[i] = ((StatisticValue) l.get(i)).users;
        }

        model.addAttribute("hours", hours);
        model.addAttribute("data", values);
        model.addAttribute("users", users);

        model.addAttribute("types", EventType.values());
        model.addAttribute("adminStatisticsDataObject",
                adminStatisticsDataObject);
        return "administration";
    }

}
