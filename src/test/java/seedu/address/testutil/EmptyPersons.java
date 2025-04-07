package seedu.address.testutil;

import seedu.address.model.AddressBook;

/**
 * A utility class to mimic an empty AddressBook.
 */
public class EmptyPersons {

    private EmptyPersons() {} //prevent instantiation

    /**
     * Returns an {@code AddressBook} with empty contact list.
     */
    public static AddressBook getEmptyAddressBook() {
        return new AddressBook();
    }
}
