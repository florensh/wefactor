package de.hhn.labswps.wefactor.specification;

import java.util.ArrayList;
import java.util.List;

import de.hhn.labswps.wefactor.web.EntryController;
import de.hhn.labswps.wefactor.web.GroupController;

/**
 * The Class WeFactorValues contains enums and constants.
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
        /** The language Java. */
        Java("Java", "java"),
        
        /** The language html. */
        HTML("HTML", "html"),
        
        /** The language css. */
        CSS("CSS", "css"),
        
        /** The language xml. */
        XML("XML", "xml"),
        
        /** The language clojure. */
        CLOJURE("Clojure", "clojure"),
        
        /** The language cobol. */
        COBOL("Cobol","cobol"),
        
        /** The language C_ sharp. */
        C_Sharp("C#", "csharp"),
        
        /** The language haskell. */
        HASKELL("Haskell", "haskell"),
        
        /** The language json. */
        JSON("Json", "json"),
        
        /** The language jsp. */
        JSP("JSP", "jsp"),
        
        /** The language javascript. */
        JAVASCRIPT("JavaScript", "javascript"),
        
        /** The language objective c. */
        OBJECTIVE_C("Objectiv-C", "objectivec"),
        
        /** The language pascal. */
        PASCAL("Pascal", "pascal"),
        
        /** The language python. */
        PYTHON("Python", "python"),
        
        /** The language ruby. */
        RUBY("Ruby", "ruby"),
        
        /** The language c pp. */
        C_PP("C / C++", "c_pp"),
        
        /** The language perl. */
        PERL("Perl", "perl"),
        
        /** The language php. */
        PHP("PHP", "php"),
        
        /** The language prolog. */
        PROLOG("Prolog", "prolog"),
        
        /** The language sql. */
        SQL("SQL", "sql"),
        
        /** The language groovy. */
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

        /** The google. */
        GOOGLE, /** The wefactor. */
        WEFACTOR, LDAP;
    }

    /**
     * The Enum Role.
     */
    public enum Role {

        /** The user. */
        USER, /** The admin. */
        ADMIN, /** The moderator. */
        MODERATOR
    }

    /**
     * The Enum EventType.
     */
    public enum EventType {

        // @formatter:off
        /** The made proposal. */
        MADE_PROPOSAL("made a new proposal",EntryController.ENTRY_DETAILS_LINK),
        
        /** The proposal accepted. */
        PROPOSAL_ACCEPTED("accepted your proposal",EntryController.ENTRY_DETAILS_LINK),
        
        /** The proposal rejected. */
        PROPOSAL_REJECTED("rejected your proposal",EntryController.ENTRY_DETAILS_LINK),
        
        /** The user joined group. */
        USER_JOINED_GROUP("joined the group",GroupController.GROUP_DETAILS_LINK),
        
        /** The user left group. */
        USER_LEFT_GROUP("left the group",GroupController.GROUP_DETAILS_LINK),
        
        /** The made entry. */
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
