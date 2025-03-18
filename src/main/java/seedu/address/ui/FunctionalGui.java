package seedu.address.ui;

/**
 * Represents an interface for a switchable window.
 * Implementing classes should provide functionality to switch the window placeholder.
 */
public interface FunctionalGui {

    /**
     * Sets the placeholder content of the switchable window.
     *
     * @param url The identifier for the window content to switch to.
     */
    void setSwitchWindowPlaceholder(String url);

    void filterListByGui(String keyword);
}
