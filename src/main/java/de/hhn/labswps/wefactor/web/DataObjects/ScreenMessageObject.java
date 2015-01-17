package de.hhn.labswps.wefactor.web.DataObjects;

/**
 * The Class ScreenMessageObject.
 */
public class ScreenMessageObject {

    /** The Constant MODEL_NAME. */
    public static final String MODEL_NAME = "screenMessage";

    /** The message. */
    private final String message;

    /** The part. */
    private String part;

    /**
     * Instantiates a new screen message object.
     *
     * @param message
     *            the message
     */
    public ScreenMessageObject(final String message) {
        this.message = message;
    }

    /**
     * Sets the strong.
     *
     * @param part
     *            the new strong
     */
    public void setStrong(final String part) {
        this.part = part;
    }

    /**
     * Gets the html.
     *
     * @return the html
     */
    public String getHtml() {
        String retVal = "<h4>".concat(this.message).concat("</h4>");

        if ((this.part != null) && !this.part.isEmpty()) {
            retVal = retVal.replace(this.part, "<strong>".concat(this.part)
                    .concat("</strong>"));
        }

        return retVal;
    }
}
