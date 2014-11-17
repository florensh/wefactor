package de.hhn.labswps.wefactor.specification;

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
        Java,
        /** The html. */
        HTML,
        /** The css. */
        CSS,
        /** The xml. */
        XML,
        /** The C_ sharp. */
        C_Sharp,
        /** The C_ plus plus. */
        C_PlusPlus,
        /** The sql. */
        SQL,
        /** The php. */
        PHP,
        /** The Bash. */
        Bash,
        /** The c. */
        C,
        /** The Python. */
        Python,
        /** The Groovy. */
        Groovy,
        /** The Ruby. */
        Ruby,
        /** The Scala. */
        Scala,
        /** The Haskell. */
        Haskell,
        /** The Objective_ c. */
        Objective_C
    }

    public enum ProviderIdentification {
        GOOGLE, WEFACTOR;
    }

    public enum Role {
        USER, ADMIN, MODERATOR
    }
}
