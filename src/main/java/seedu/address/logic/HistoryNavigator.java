package seedu.address.logic;

/**
 * Represents a function that navigates a history based on input
 */
public interface HistoryNavigator {
    /**
     * Returns the current entry in the history
     */
    String current();

    /**
     * Navigates the history in the backward direction and returns the result.
     */
    String backward();

    /**
     * Navigates the history in the forward direction and returns the result.
     */
    String forward();
}
