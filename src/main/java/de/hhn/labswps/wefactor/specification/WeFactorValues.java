package de.hhn.labswps.wefactor.specification;

import java.util.ArrayList;
import java.util.List;

import de.hhn.labswps.wefactor.web.EntryController;
import de.hhn.labswps.wefactor.web.GroupController;

/**
 * The Class WeFactorValues.
 *
 * @author Patrick Wohlgemuth
 */
public class WeFactorValues {

    /** The Constant DEFAULT_IMAGE_URL. */
    public static final String DEFAULT_IMAGE_URL = "http://www.localcrimenews.com/wp-content/uploads/2013/07/default-user-icon-profile.png";

    /** The Constant DEFAULT_GROUP_IMAGE_URL. */
    public static final String DEFAULT_GROUP_IMAGE_URL = "http://cdn.flaticon.com/png/256/32441.png";

    /**
     * The Enum ProgrammingLanguage.
     */
    public enum ProgrammingLanguage {

        // @formatter:off
        Java("Java", "java"),
        HTML("HTML", "html"),
        CSS("CSS", "css"),
        XML("XML", "xml"),
        CLOJURE("Clojure", "clojure"),
        COBOL("Cobol","cobol"),
        C_Sharp("C#", "csharp"),
        HASKELL("Haskell", "haskell"),
        JSON("Json", "json"),
        JSP("JSP", "jsp"),
        JAVASCRIPT("JavaScript", "javascript"),
        OBJECTIVE_C("Objectiv-C", "objectivec"),
        PASCAL("Pascal", "pascal"),
        PYTHON("Python", "python"),
        RUBY("Ruby", "ruby"),
        C_PP("C / C++", "c_pp"),
        PERL("Perl", "perl"),
        PHP("PHP", "php"),
        PROLOG("Prolog", "prolog"),
        SQL("SQL", "sql"),
        GROOVY("Groovy", "groovy"),
        ;
        // @formatter:on

        /** The acc editor mode of the language. */
        private final String mode;

        /** The display name. */
        private final String displayName;

        /**
         * Instantiates a new programming language.
         *
         * @param theDisplayName
         *            the display name
         * @param theMode
         *            the mode
         */
        ProgrammingLanguage(final String theDisplayName, final String theMode) {
            this.mode = theMode;
            this.displayName = theDisplayName;
        }

        /**
         * Gets the mode.
         *
         * @return the mode
         */
        public String getMode() {
            return this.mode;
        }

        /**
         * Gets the display name.
         *
         * @return the display name
         */
        public String getDisplayName() {
            return this.displayName;
        }

        /**
         * Gets the language for displayname.
         *
         * @param name
         *            the name
         * @return the language for displayname
         */
        public static ProgrammingLanguage getLanguageForDisplayname(
                final String name) {
            for (final ProgrammingLanguage pl : ProgrammingLanguage.values()) {
                if (pl.displayName.equalsIgnoreCase(name)) {
                    return pl;
                }
            }
            return null;
        }

        /**
         * Gets the language for mode.
         *
         * @param mode
         *            the mode
         * @return the language for mode
         */
        public static ProgrammingLanguage getLanguageForMode(final String mode) {
            for (final ProgrammingLanguage pl : ProgrammingLanguage.values()) {
                if (pl.mode.equalsIgnoreCase(mode)) {
                    return pl;
                }
            }
            return null;
        }

        /**
         * Values as editor mode.
         *
         * @return the list
         */
        public static List<String> valuesAsEditorMode() {
            final List<String> retVal = new ArrayList<String>();
            for (final ProgrammingLanguage pl : ProgrammingLanguage.values()) {
                retVal.add(pl.mode);
            }
            return retVal;
        }
    }

    /**
     * The Enum ProviderIdentification.
     */
    public enum ProviderIdentification {

        GOOGLE, WEFACTOR;
    }

    /**
     * The Enum Role.
     */
    public enum Role {

        USER, ADMIN, MODERATOR
    }

    /**
     * The Enum EventType.
     */
    public enum EventType {

        // @formatter:off
        MADE_PROPOSAL("made a new proposal",EntryController.ENTRY_DETAILS_LINK),
        PROPOSAL_ACCEPTED("accepted your proposal",EntryController.ENTRY_DETAILS_LINK),
        PROPOSAL_REJECTED("rejected your proposal",EntryController.ENTRY_DETAILS_LINK),
        USER_JOINED_GROUP("joined the group",GroupController.GROUP_DETAILS_LINK),
        USER_LEFT_GROUP("left the group",GroupController.GROUP_DETAILS_LINK),
        MADE_ENTRY("made an entry",EntryController.ENTRY_DETAILS_LINK)
        ;
        // @formatter:on

        /** The event text. */
        private final String text;

        /** The event link. */
        private final String link;

        /**
         * Instantiates a new event type.
         *
         * @param theText
         *            the text
         * @param theLink
         *            the link
         */
        EventType(final String theText, final String theLink) {
            this.text = theText;
            this.link = theLink;

        }

        /**
         * Gets the text.
         *
         * @return the text
         */
        public String getText() {
            return this.text;
        }

        /**
         * Gets the link.
         *
         * @return the link
         */
        public String getLink() {
            return this.link;
        }
    }
}
