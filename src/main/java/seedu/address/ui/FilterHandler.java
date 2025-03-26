package seedu.address.ui;

/**
 * Represents an interface for panes that allow filtering of person List with GUI interaction events
 * such as button press.
 */
public interface FilterHandler {

    /**
     * Filters the contact list with the module code received via label in Module Folder.
     *
     * @param moduleName module code in string.
     */
    void filterListByModuleName(String moduleName);

}
