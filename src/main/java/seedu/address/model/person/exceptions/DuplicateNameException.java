package seedu.address.model.person.exceptions;

/**
 * Signals that the operation will result in duplicate names.
 */
public class DuplicateNameException extends RuntimeException {
    public DuplicateNameException() {
        super("Operation would result in contacts with duplicate names.");
    }
}
