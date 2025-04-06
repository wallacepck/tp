package seedu.address.ui;

/**
 * Represents an interface that handles the display and functionality due to selection events.
 */
public interface GuiFunctionHandler {

    // List filter functionality

    /**
     * Filters the contact list with the module code received via label in Module Folder.
     *
     * @param moduleCode module code in string.
     */
    void filterListByModuleCode(String moduleCode);

    /**
     * Filters the contact list by those which are marked as favourite.
     */
    void filterListByFavourites();

    /**
     * Clears filter when switching to Contacts tab manually.
     */
    void clearFilter();

    // Display switch functionality

    /**
     * Sets the placeholder content of the switchable window.
     *
     * @param selectedButton The identifier for the window content to switch to.
     */
    void setSwitchWindowPlaceholder(String selectedButton);
}
