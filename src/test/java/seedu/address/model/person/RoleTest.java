package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RoleTest {

    @Test
    public void get_validRole_success() {
        assertDoesNotThrow(() -> Role.getRole(Role.TA.roleName));
    }
    @Test
    public void get_invalidRole_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, Role.MESSAGE_CONSTRAINTS, () -> Role.getRole("chocolate_cake"));
    }

    @Test
    public void print_role_returnsPrettyName() {
        assertEquals("TA", Role.TA.toString());
    }
}
