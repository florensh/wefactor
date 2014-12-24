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
    public static final String DEFAULT_GROUP_IMAGE_URL = "http://cdn.flaticon.com/png/256/32441.png";

    /**
     * The Enum ProgrammingLanguage.
     */
    public enum ProgrammingLanguage {

        // @formatter:off
        Java("Java", "java"), HTML("HTML", "html"), CSS("CSS", "css"), XML(
                "XML", "xml"), CLOJURE("Clojure", "clojure"), COBOL("Cobol",
                "cobol"), C_Sharp("C#", "csharp"), HASKELL("Haskell", "haskell"), JSON(
                "Json", "json"), JSP("JSP", "jsp"), JAVASCRIPT("JavaScript",
                "javascript"), OBJECTIVE_C("Objectiv-C", "objectivec"), PASCAL(
                "Pascal", "pascal"), PYTHON("Python", "python"), RUBY("Ruby",
                "ruby"), C_PP("C / C++", "c_pp"), PERL("Perl", "perl"), PHP(
                "PHP", "php"), PROLOG("Prolog", "prolog"), SQL("SQL", "sql"), GROOVY(
                "Groovy", "groovy"),

        ;
        // @formatter:on

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

        public static ProgrammingLanguage getLanguageForMode(String mode) {
            for (ProgrammingLanguage pl : ProgrammingLanguage.values()) {
                if (pl.mode.equalsIgnoreCase(mode)) {
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

        // @formatter:off
        MADE_PROPOSAL("made a new proposal"), PROPOSAL_ACCEPTED(
                "accepted your proposal"), PROPOSAL_REJECTED(
                "rejected your proposal"), ;
        // @formatter:on

        private String text;

        EventType(String text) {
            this.text = text;

        }

        public String getText() {
            return this.text;
        }
    }
}
