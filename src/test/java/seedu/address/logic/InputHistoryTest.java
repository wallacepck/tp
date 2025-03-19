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
        assertNull(history.navigateCurrent());
        assertNull(history.navigateBackward());
        assertNull(history.navigateForward());
    }

    @Test
    public void navigate_history_returnsValue() {
        InputHistory history = new InputHistory();

        history.enterCommand(TEST_COMMAND);
        assertEquals(TEST_COMMAND, history.navigateCurrent());
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
    public void navigate_navigator_equivalentToSelf() {
        InputHistory history = new InputHistory();
        history.enterCommand(TEST_COMMAND);
        history.enterCommand(TEST_COMMAND_2);

        HistoryNavigator navigator = history.getNavigator();
        assertEquals(TEST_COMMAND_2, navigator.current());
        assertEquals(TEST_COMMAND, navigator.backward());
        assertEquals(TEST_COMMAND_2, navigator.forward());
    }

    @Test
    public void enterCommand_overSize_dropsLast() {
        InputHistory history = new InputHistory();
        for (int i = 0; i < InputHistory.MAX_SIZE + 1; i++) {
            history.enterCommand(String.format("%d", i));
        }
        String result = "";
        for (int i = 0; i < InputHistory.MAX_SIZE + 1; i++) {
            result = history.navigateBackward();
        }
        assertEquals("1", result);
    }
}
