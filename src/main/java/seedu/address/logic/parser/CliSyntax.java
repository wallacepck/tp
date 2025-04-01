package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_TAG = new Prefix("tag/");
    public static final Prefix PREFIX_ROLE = new Prefix("r/");
    public static final Prefix PREFIX_MODULE = new Prefix("m/");
    public static final Prefix PREFIX_FAVOURITE = new Prefix("f/");
    public static final Prefix PREFIX_MULTIPLE_MODULES = new Prefix("mm/");
    public static final Prefix PREFIX_TELEGRAM = new Prefix("t/");
}
