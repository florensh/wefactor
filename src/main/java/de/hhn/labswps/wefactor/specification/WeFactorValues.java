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

        /** The Java. */
        Java("java", "java"),
        /** The html. */
        HTML("html", "html"),
        /** The css. */
        CSS("css", "css"),
        /** The xml. */
        XML("xml", "xml")
        // /** The C_ sharp. */
        // C_Sharp("C#"),
        // /** The C_ plus plus. */
        // C_PlusPlus("C++"),
        // /** The sql. */
        // SQL("sql"),
        // /** The php. */
        // PHP("PHP"),
        // /** The Bash. */
        // Bash("Bash"),
        // /** The c. */
        // C("C"),
        // /** The Python. */
        // Python("Python"),
        // /** The Groovy. */
        // Groovy("Groovy"),
        // /** The Ruby. */
        // Ruby("Ruby"),
        // /** The Scala. */
        // Scala("Scala"),
        // /** The Haskell. */
        // Haskell("Haskell"),
        // /** The Objective_ c. */
        // Objective_C("Objectiv-C")
        ;

        private String mode;
        private String displayName;

        ProgrammingLanguage(String displayName, String mode) {
            this.mode = mode;
            this.displayName = displayName;
        }

        public String getMode() {
            return mode;
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
}
