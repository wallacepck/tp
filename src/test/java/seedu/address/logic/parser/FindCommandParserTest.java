package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.PersonContainsKeywordsPredicate;

public class FindCommandParserTest {

    private final FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingKeywords_throwsParseException() {
        assertParseFailure(parser, "n/ ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "p/ ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPrefix_throwsParseException() {
        assertParseFailure(parser, "e/ fiona@test.com",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "a/ Blk 123 Street",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validNameArgs_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(
                        PersonContainsKeywordsPredicate.SearchField.NAME, Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "n/ Alice Bob", expectedFindCommand);

        assertParseSuccess(parser, " \n n/ Alice \n \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_validPhoneArgs_returnsFindCommand() {
        // Single phone number
        FindCommand expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(
                        PersonContainsKeywordsPredicate.SearchField.PHONE, Collections.singletonList("91234567")));
        assertParseSuccess(parser, "p/ 91234567", expectedFindCommand);

        // Multiple phone numbers
        expectedFindCommand = new FindCommand(new PersonContainsKeywordsPredicate(
                PersonContainsKeywordsPredicate.SearchField.PHONE, Arrays.asList("91234567", "98765432")));
        assertParseSuccess(parser, "p/ 91234567 98765432", expectedFindCommand);
    }

    @Test
    public void parse_caseInsensitivePrefix_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(
                        PersonContainsKeywordsPredicate.SearchField.NAME, Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "N/ Alice Bob", expectedFindCommand);
        assertParseSuccess(parser, "n/ Alice Bob", expectedFindCommand);
    }

    @Test
    public void parse_extraSpaces_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(
                        PersonContainsKeywordsPredicate.SearchField.NAME, Arrays.asList("Charlie", "David")));
        assertParseSuccess(parser, "  n/  Charlie   David  ", expectedFindCommand);
    }

    @Test
    public void parse_specialCharactersInName_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(
                        PersonContainsKeywordsPredicate.SearchField.NAME, Arrays.asList("O'Connor", "Jean-Luc")));
        assertParseSuccess(parser, "n/ O'Connor Jean-Luc", expectedFindCommand);
    }

    @Test
    public void parse_specialCharactersInPhone_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(
                        PersonContainsKeywordsPredicate.SearchField.PHONE, Collections.singletonList("9123-4567")));
        assertParseSuccess(parser, "p/ 9123-4567", expectedFindCommand);
    }
}
