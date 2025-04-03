package seedu.address.model.person.exceptions;

/**
 * Signals that the operation will result in duplicate Telegram handles.
 */
public class DuplicateTelegramException extends RuntimeException {
    public DuplicateTelegramException() {
        super("Operation would result in contacts with duplicate telegram handles.");
    }
}
