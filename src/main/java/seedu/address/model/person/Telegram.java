package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the telegram of a person in the addressbook. To be wrapped in an Optional object.
 * is valid as declared in {@link #isValidHandle(String)}.
 */
public class Telegram {
    private static final String UNDERSCORE = "_";
    public static final String MESSAGE_CONSTRAINTS = "Telegram should be of the format @handle "
            + "and adhere to the following constraints:\n"
            + "1. The telegram handle should start with '@'.\n"
            + "The handle may not start with any numbers.\n"
            + "2. The handle should only contain alphanumeric characters and " + UNDERSCORE + ". The handle may "
            + "not start or end with any special characters including _ .\n"
            + "The handle may not start with any numbers.\n"
            + "3. The handle should have between 5 - 32 characters excluding the leading @.";

    private static final String ALPHANUMERIC_WITH_UNDERSCORE = "a-zA-Z0-9_";
    private static final String NO_LEADING_NUMBER_UNDERSCORE_REGEX = "(?![_0-9])";
    private static final String NO_TRAILING_UNDERSCORE_REGEX = "(?<!_)";
    private static final String HANDLE_REGEX = NO_LEADING_NUMBER_UNDERSCORE_REGEX + "(["
            + ALPHANUMERIC_WITH_UNDERSCORE + "]){5,32}"
            + NO_TRAILING_UNDERSCORE_REGEX;

    public static final String VALIDATION_REGEX = "^@" + HANDLE_REGEX;

    private final String handle;

    /**
     * Constructs a {@code Telegram}.
     * @param handle A valid Telegram handle.
     */
    public Telegram(String handle) {
        requireNonNull(handle);
        checkArgument(isValidHandle(handle), MESSAGE_CONSTRAINTS);
        this.handle = handle;
    }

    /**
     * Returns if a string is a valid telegram.
     */
    public static boolean isValidHandle(String handle) {
        return handle.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return this.handle;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Telegram)) {
            return false;
        }

        Telegram otherTelegram = (Telegram) other;
        return handle.equalsIgnoreCase(otherTelegram.handle);
    }

    @Override
    public int hashCode() {
        return handle.hashCode();
    }

}
