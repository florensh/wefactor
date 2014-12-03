package de.hhn.labswps.wefactor.specification;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class WeFactorValues.
 * 
 * @author Patrick Wohlgemuth
 */
public class WeFactorValues {

    public static final String DEFAULT_IMAGE_URL = "http://www.localcrimenews.com/wp-content/uploads/2013/07/default-user-icon-profile.png";

    /**
     * The Enum ProgrammingLanguage.
     */
    public enum ProgrammingLanguage {

        //@formatter:off
        Java("Java", "java"),
        HTML("HTML", "html"),
        CSS("CSS", "css"),
        XML("XML", "xml")
        // C_Sharp("C#"),
        // C_PlusPlus("C++"),
        // SQL("sql"),
        // PHP("PHP"),
        // Bash("Bash"),
        // C("C"),
        // Python("Python"),
        // Groovy("Groovy"),
        // Ruby("Ruby"),
        // Scala("Scala"),
        // Haskell("Haskell"),
        // Objective_C("Objectiv-C")
        ;
        //@formatter:on

        private String mode;
        private String displayName;

        ProgrammingLanguage(String displayName, String mode) {
            this.mode = mode;
            this.displayName = displayName;
        }

        public String getMode() {
            return mode;
        }

        public String getDisplayName() {
            return displayName;
        }

        public static ProgrammingLanguage getLanguageForDisplayname(String name) {
            for (ProgrammingLanguage pl : ProgrammingLanguage.values()) {
                if (pl.displayName.equalsIgnoreCase(name)) {
                    return pl;
                }
            }
            return null;
        }

        public static List<String> valuesAsEditorMode() {
            List<String> retVal = new ArrayList<String>();
            for (ProgrammingLanguage pl : ProgrammingLanguage.values()) {
                retVal.add(pl.mode);
            }
            return retVal;
        }
    }

    public enum ProviderIdentification {
        GOOGLE, WEFACTOR;
    }

    public enum Role {
        USER, ADMIN, MODERATOR
    }

    public enum EventType {

        //@formatter:off
        MADE_PROPOSAL("made a new proposal"),
        PROPOSAL_ACCEPTED("accepted your proposal"),
        PROPOSAL_REJECTED("rejected your proposal"),
        ;
        //@formatter:on

        private String text;

        EventType(String text) {
            this.text = text;

        }

        public String getText() {
            return this.text;
        }
    }
}
