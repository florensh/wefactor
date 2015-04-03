package de.hhn.labswps.wefactor.web.util;

import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import de.hhn.labswps.wefactor.web.DataObjects.ScreenMessageObject;

public class ViewUtil {

    public static void showMessage(String string, String strong, Model m) {

        final ScreenMessageObject sm = new ScreenMessageObject(string);
        sm.setStrong(strong);
        m.addAttribute(ScreenMessageObject.MODEL_NAME, sm);

    }

    public static void showMessage(String string, String strong, ModelMap m) {
        final ScreenMessageObject sm = new ScreenMessageObject(string);
        sm.setStrong(strong);
        m.addAttribute(ScreenMessageObject.MODEL_NAME, sm);

    }

}
