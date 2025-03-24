package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Role that a person may have.
 * Guarantees: immutable; name is one of the valid enum values as declared in {@link Role}
 */
public enum Role {
    TA("ta"),
    PROFESSOR("prof");

    public static final String MESSAGE_CONSTRAINTS = "Roles should be one of the following: ta, prof";
    private static final Map<String,Role> lookupTable = new HashMap<>();
    static {
        for(Role r : EnumSet.allOf(Role.class)) {
            lookupTable.put(r.roleName, r);
        }
    }

    public final String roleName;

    /**
     * Constructs a {@code Role}.
     *
     * @param roleName A valid role name.
     */
    Role(String roleName) {
        this.roleName = roleName;
    }

    /**
     * Returns true if the given string is a valid role name
     */
    public static boolean isValidRole(String roleName) {
        return lookupTable.containsKey(roleName);
    }

    /**
     * Returns the {@link Role} from a given {@code roleName}.
     *
     * @param roleName A valid role name.
     * @return The respective {@code Role}.
     * @throws IllegalArgumentException if the role name is not valid.
     */
    public static Role getRole(String roleName) {
        requireNonNull(roleName);
        checkArgument(isValidRole(roleName), MESSAGE_CONSTRAINTS);
        return lookupTable.get(roleName);
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return "[Role: " + roleName + ']';
    }
}
