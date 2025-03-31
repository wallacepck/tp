package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class InputHistoryTest {
    public static final String TEST_COMMAND = "abc";
    public static final String TEST_COMMAND_2 = "def";

    @Test
    public void navigate_emptyHistory_returnsEmptyString() {
        InputHistory history = new InputHistory();
        assertEquals("", history.getCurrentEntry());
        assertEquals("", history.navigateBackward());
        assertEquals("", history.navigateForward());
    }

    @Test
    public void navigate_current_returnsCurrentEntry() {
        InputHistory history = new InputHistory();

        history.enterInput(TEST_COMMAND);
        assertEquals(TEST_COMMAND, history.getCurrentEntry());
    }

    @Test
    public void edit_navigatedEntry_navigatesToLatest() {
        InputHistory history = new InputHistory();
        history.enterInput(TEST_COMMAND);
        history.navigateBackward();
        history.checkActiveText(TEST_COMMAND_2);

        assertEquals(TEST_COMMAND_2, history.getCurrentEntry());
        assertEquals(TEST_COMMAND, history.navigateBackward());
    }

    @Test
    public void navigate_backwardEnd_returnsValue() {
        InputHistory history = new InputHistory();
        history.enterInput(TEST_COMMAND);
        history.navigateBackward();
        assertEquals(TEST_COMMAND, history.navigateBackward());
    }

    @Test
    public void navigate_forwardEnd_returnsValue() {
        InputHistory history = new InputHistory();
        history.enterInput(TEST_COMMAND);
        assertEquals(TEST_COMMAND, history.navigateForward());
    }

    @Test
    public void navigate_multiple_correctOrder() {
        InputHistory history = new InputHistory();
        history.enterInput(TEST_COMMAND);
        history.enterInput(TEST_COMMAND_2);
        assertEquals(TEST_COMMAND_2, history.navigateBackward());
        assertEquals(TEST_COMMAND, history.navigateBackward());
        assertEquals(TEST_COMMAND_2, history.navigateForward());
    }

    @Test
    public void enterCommand_overSize_dropsLast() {
        InputHistory history = new InputHistory();
        for (int i = 0; i < InputHistory.MAX_SIZE + 1; i++) {
            history.enterInput(String.format("%d", i));
        }
        String result = "";
        for (int i = 0; i < InputHistory.MAX_SIZE + 1; i++) {
            result = history.navigateBackward();
        }
        assertEquals("1", result);
    }
}
