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
    public void parse_multiplePrefixes_throwsParseException() {
        assertParseFailure(parser, "n/ Darren e/ fiona@test.com",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "n/ Darren n/ Fiona",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "n/ Darren p/ 91234567",
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
    public void parse_validModuleArgs_returnsFindCommand() {
        // Full module code
        FindCommand expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(
                        PersonContainsKeywordsPredicate.SearchField.MODULE, Collections.singletonList("CS2103T")
                ));
        assertParseSuccess(parser, "m/ CS2103T", expectedFindCommand);
        // Partial module code
        expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(
                        PersonContainsKeywordsPredicate.SearchField.MODULE, Collections.singletonList("2103")
                ));
        assertParseSuccess(parser, "m/ 2103", expectedFindCommand);
        // Multiple full module codes
        expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(
                        PersonContainsKeywordsPredicate.SearchField.MODULE, Arrays.asList("CS2103T", "CS3230")
                ));
        assertParseSuccess(parser, "m/ CS2103T CS3230", expectedFindCommand);
        // Multiple partial module codes
        expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(
                        PersonContainsKeywordsPredicate.SearchField.MODULE, Arrays.asList("2101", "3230", "IS")
                ));
        assertParseSuccess(parser, "m/ 2101 3230 IS", expectedFindCommand);
    }

    @Test
    public void parse_validPhoneArgs_returnsFindCommand() {
        // Single phone number
        FindCommand expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(
                        PersonContainsKeywordsPredicate.SearchField.PHONE, Collections.singletonList("91234567")));
        assertParseSuccess(parser, "p/ 91234567", expectedFindCommand);

        // Multiple phone numbers
        expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(
                        PersonContainsKeywordsPredicate.SearchField.PHONE, Arrays.asList("91234567", "98765432")));
        assertParseSuccess(parser, "p/ 91234567 98765432", expectedFindCommand);


        // Multiple partial phone numbers
        expectedFindCommand = new FindCommand(new PersonContainsKeywordsPredicate(
                PersonContainsKeywordsPredicate.SearchField.PHONE, Arrays.asList("9123", "4567", "5432")));
        assertParseSuccess(parser, "p/ 9123 4567 5432", expectedFindCommand);
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
}
