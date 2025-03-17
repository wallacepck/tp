package seedu.address.model.tag;

/**
 * Represents a Role that a person may have.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Role extends Tag {
    /**
     * Constructs a {@code Role}.
     *
     * @param roleName A valid role name.
     */
    public Role(String roleName) {
        super(roleName);
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return "[Role: " + tagName + ']';
    }
}
