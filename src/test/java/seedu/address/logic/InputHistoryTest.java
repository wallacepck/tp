package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class InputHistoryTest {
    public static final String TEST_COMMAND = "abc";
    public static final String TEST_COMMAND_2 = "def";

    @Test
    public void navigate_emptyHistory_returnsNull() {
        InputHistory history = new InputHistory();
        assertNull(history.navigateBackward());
    }

    @Test
    public void navigate_history_returnsValue() {
        InputHistory history = new InputHistory();

        history.enterCommand(TEST_COMMAND);
        assertEquals(TEST_COMMAND, history.navigateBackward());
    }

    @Test
    public void navigateBackward_backwardEnd_returnsValue() {
        InputHistory history = new InputHistory();
        history.enterCommand(TEST_COMMAND);
        history.navigateBackward();
        assertEquals(TEST_COMMAND, history.navigateBackward());
    }

    @Test
    public void navigateForward_forwardEnd_returnsValue() {
        InputHistory history = new InputHistory();
        history.enterCommand(TEST_COMMAND);
        assertEquals(TEST_COMMAND, history.navigateForward());
    }

    @Test
    public void navigator_equivalentToSelf() {
        InputHistory history = new InputHistory();
        history.enterCommand(TEST_COMMAND);
        history.enterCommand(TEST_COMMAND_2);

        InputHistory.HistoryNavigator navigator = history.getNavigator();
        assertEquals(TEST_COMMAND, navigator.backward());
        assertEquals(TEST_COMMAND_2, navigator.forward());
    }
}
