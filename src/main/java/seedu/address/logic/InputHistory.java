package seedu.address.logic;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.util.ArrayDequeWithRandomAccess;

/**
 * Manages input history of the command line
 */
public class InputHistory {
    public static final int MAX_SIZE = 16;
    private final ArrayDequeWithRandomAccess<String> history;
    private String activeInput = "";

    /**
     * A value of
     * -1 : activeInput
     * >= 0 : history
     */
    private int indexInHistory = -1;

    /**
     * Creates a {@code InputHistory} with blank history
     */
    public InputHistory() {
        this.history = new ArrayDequeWithRandomAccess<>();
    }

    /**
     * Adds {@code commandText} to history and shifts the history to the active input.
     * If the history is over {@link InputHistory.MAX_SIZE}, the oldest entry is deleted.
     *
     * @param commandText The new command to add to history, cannot be null
     */
    public void enterInput(String commandText) {
        requireNonNull(commandText);
        indexInHistory = -1;
        history.addFirst(commandText);
        activeInput = commandText;

        if (history.size() > MAX_SIZE) {
            history.removeLast();
        }
    }

    /**
     * If the current entry in history does not match {@code commandText}, sets the {@code activeInput}
     * to {@code commandText} and sets the current entry to the active input.
     *
     * @param commandText The input command text, cannot be null
     */
    public void checkActiveText(String commandText) {
        requireNonNull(commandText);
        if (!getCurrentEntry().equals(commandText)) {
            indexInHistory = -1;
            activeInput = commandText;
        }
    }

    /**
     * Shifts the history one command back if there are commands behind and returns the newly pointed command,
     * else returns the currently pointed command.
     *
     * @return The pointed command
     */
    public String navigateBackward() {
        if (indexInHistory < history.size() - 1) {
            indexInHistory++;
        }
        return getCurrentEntry();
    }

    /**
     * Shifts the history one command forward if there are commands in front and returns the newly pointed command,
     * else returns the currently pointed command.
     *
     * @return The pointed command
     */
    public String navigateForward() {
        if (indexInHistory >= 0) {
            indexInHistory--;
        }
        return getCurrentEntry();
    }

    /**
     * Returns the currently pointed command.
     *
     * @return The pointed command
     */
    public String getCurrentEntry() {
        if (history.isEmpty() || indexInHistory == -1) {
            return activeInput;
        }

        assert indexInHistory >= 0 && indexInHistory < history.size();
        return history.get(indexInHistory);
    }
}
