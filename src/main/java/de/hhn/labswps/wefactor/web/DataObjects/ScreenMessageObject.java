package de.hhn.labswps.wefactor.web.DataObjects;

public class ScreenMessageObject {

    public static final String MODEL_NAME = "screenMessage";

    private final String message;
    private String part;

    public ScreenMessageObject(String message) {
        this.message = message;
    }

    public void setStrong(String part) {
        this.part = part;
    }

    public String getHtml() {
        String retVal = "<h4>".concat(this.message).concat("</h4>");

        if (part != null && !part.isEmpty()) {
            retVal = retVal.replace(part,
                    "<strong>".concat(this.part).concat("</strong>"));
        }

        return retVal;
    }
}
