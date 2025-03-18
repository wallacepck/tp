package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FavCommand;

public class FavCommandParserTest {
    private FavCommandParser parser = new FavCommandParser();
    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new FavCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FavCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleArgs_throwsParseException() {
        assertParseFailure(parser, "1 2", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FavCommand.MESSAGE_USAGE));
    }
}
