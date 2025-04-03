package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.Name;
import seedu.address.model.person.PersonContainsKeywordsPredicate;
import seedu.address.model.person.Role;

public class FindCommandParserTest {

    private final FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingKeywords_throwsParseException() {
        assertParseFailure(parser, " n/ ", Name.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " p/ ",
                "Phone number must only contain digits.");
        assertParseFailure(parser, " e/ ",
                "Email keyword cannot be empty.");
        assertParseFailure(parser, " r/ ", Role.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " f/ ",
                "f/ field only accepts 'y' or 'n' (case-insensitive).");
        assertParseFailure(parser, " t/ ",
                "Telegram keyword cannot be empty.");
        assertParseFailure(parser, " mm/ ",
                "Module keyword cannot be empty.");
    }

    @Test
    public void parse_invalidPrefix_throwsParseException() {
        assertParseFailure(parser, " l/fiona@test.com",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " m/ 2101",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_caseSensitivePrefix_throwsParseException() {
        assertParseFailure(parser, " N/Darren",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " Mm/ 3230",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " T/@d",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " E/Ddd, ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validNameArgs_returnsFindCommand() {
        Map<PersonContainsKeywordsPredicate.SearchField, List<String>> fieldKeywordMap = new HashMap<>();
        fieldKeywordMap.put(PersonContainsKeywordsPredicate.SearchField.NAME, Arrays.asList("Alice", "Bob"));
        FindCommand expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(fieldKeywordMap));
        assertParseSuccess(parser, " n/Alice Bob", expectedFindCommand);
        assertParseSuccess(parser, " n/ Alice Bob", expectedFindCommand);
        assertParseSuccess(parser, " \n n/ Alice \n \t Bob  \t", expectedFindCommand);
        assertParseSuccess(parser, "  n/  Alice   Bob  ", expectedFindCommand);
    }

    @Test
    public void parse_specialCharactersInName_throwsParseException() {
        assertParseFailure(parser, " n/ O'Connor", Name.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " n/Daniel Lucas Jean-Luc", Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_validModuleArgs_returnsFindCommand() {
        Map<PersonContainsKeywordsPredicate.SearchField, List<String>> fieldKeywordMap = new HashMap<>();

        // Full module code
        fieldKeywordMap.put(PersonContainsKeywordsPredicate.SearchField.MODULE, Collections.singletonList("CS2103T"));
        FindCommand expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(fieldKeywordMap));
        assertParseSuccess(parser, " mm/CS2103T", expectedFindCommand);
        // Partial module code
        fieldKeywordMap.clear();
        fieldKeywordMap.put(PersonContainsKeywordsPredicate.SearchField.MODULE, Collections.singletonList("2103"));
        expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(fieldKeywordMap));
        assertParseSuccess(parser, " mm/2103", expectedFindCommand);
        // Multiple full module codes
        fieldKeywordMap.clear();
        fieldKeywordMap.put(PersonContainsKeywordsPredicate.SearchField.MODULE, Arrays.asList("CS2103T", "CS3230"));
        expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(fieldKeywordMap));
        assertParseSuccess(parser, " mm/CS2103T CS3230", expectedFindCommand);
        // Multiple partial module codes
        fieldKeywordMap.clear();
        fieldKeywordMap.put(PersonContainsKeywordsPredicate.SearchField.MODULE, Arrays.asList("2101", "3230", "IS"));
        expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(fieldKeywordMap));
        assertParseSuccess(parser, " mm/2101 3230 IS", expectedFindCommand);
    }

    @Test
    public void parse_invalidModuleKeywords_throwsParseException() {
        assertParseFailure(parser, " mm/CS2103T%^&#*@",
                "Module keywords must contain only alphanumeric characters.");
    }

    @Test
    public void parse_validPhoneArgs_returnsFindCommand() {
        Map<PersonContainsKeywordsPredicate.SearchField, List<String>> fieldKeywordMap = new HashMap<>();

        // Single phone number
        fieldKeywordMap.put(PersonContainsKeywordsPredicate.SearchField.PHONE, List.of("91234567"));
        FindCommand expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(fieldKeywordMap));
        assertParseSuccess(parser, " p/ 91234567", expectedFindCommand);

        // Multiple phone numbers
        fieldKeywordMap.clear();
        fieldKeywordMap.put(PersonContainsKeywordsPredicate.SearchField.PHONE, Arrays.asList("91234567", "98765432"));
        expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(fieldKeywordMap));
        assertParseSuccess(parser, " p/ 91234567 98765432", expectedFindCommand);

        // Multiple partial phone numbers
        fieldKeywordMap.clear();
        fieldKeywordMap.put(PersonContainsKeywordsPredicate.SearchField.PHONE, Arrays.asList("9123", "4567", "5432"));
        expectedFindCommand = new FindCommand(new PersonContainsKeywordsPredicate(fieldKeywordMap));
        assertParseSuccess(parser, " p/ 9123 4567 5432", expectedFindCommand);
    }

    @Test
    public void parse_invalidPhone_throwsParseException() {
        assertParseFailure(parser, " p/9123_4567",
                "Phone number must only contain digits.");
        assertParseFailure(parser, " p/charlie",
                "Phone number must only contain digits.");
    }

    @Test
    public void parse_favourite_returnsFindCommand() {
        Map<PersonContainsKeywordsPredicate.SearchField, List<String>> fieldKeywordMap = new HashMap<>();
        fieldKeywordMap.put(PersonContainsKeywordsPredicate.SearchField.FAVOURITE, List.of("y"));
        FindCommand expectedFindCommand = new FindCommand(new PersonContainsKeywordsPredicate(fieldKeywordMap));
        assertParseSuccess(parser, " f/y", expectedFindCommand);
        assertParseSuccess(parser, " f/ y", expectedFindCommand);
    }

    @Test
    public void parse_invalidFavouriteKeyword_throwsParseException() {
        assertParseFailure(parser, " f/ maybe",
                "f/ field only accepts 'y' or 'n' (case-insensitive).");
        assertParseFailure(parser, " f/maybe n",
                "f/ field must contain exactly one keyword: 'y' or 'n' (case-insensitive).");
        assertParseFailure(parser, " f/y y",
                "f/ field must contain exactly one keyword: 'y' or 'n' (case-insensitive).");
        assertParseFailure(parser, " f/ y n",
                "f/ field must contain exactly one keyword: 'y' or 'n' (case-insensitive).");
    }

    @Test
    public void parse_validRoleKeyword_returnsFindCommand() {
        Map<PersonContainsKeywordsPredicate.SearchField, List<String>> fieldKeywordMap = new HashMap<>();
        fieldKeywordMap.put(PersonContainsKeywordsPredicate.SearchField.ROLE, List.of("TA"));
        FindCommand expectedFindCommand = new FindCommand(new PersonContainsKeywordsPredicate(fieldKeywordMap));
        assertParseSuccess(parser, " r/TA", expectedFindCommand);
        assertParseSuccess(parser, " r/ \n TA", expectedFindCommand);
    }

    @Test
    public void parse_invalidRoleKeyword_throwsParseException() {
        assertParseFailure(parser, " r/prof ta",
                "r/ field must contain exactly one keyword: 'prof' or 'TA' (case-insensitive).");
        assertParseFailure(parser, " r/student",
                Role.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_telegram_returnsFindCommand() {
        Map<PersonContainsKeywordsPredicate.SearchField, List<String>> fieldKeywordMap = new HashMap<>();
        fieldKeywordMap.put(PersonContainsKeywordsPredicate.SearchField.TELEGRAM, List.of("@darren", "fiona"));
        FindCommand expectedFindCommand = new FindCommand(new PersonContainsKeywordsPredicate(fieldKeywordMap));
        assertParseSuccess(parser, " t/@darren fiona", expectedFindCommand);
        assertParseSuccess(parser, " t/ @darren fiona", expectedFindCommand);
        assertParseSuccess(parser, " t/ \n @darren \n fiona", expectedFindCommand);
        assertParseSuccess(parser, " t/      @darren        fiona   ", expectedFindCommand);
    }

    @Test
    public void parse_invalidTelegram_throwsParseException() {
        assertParseFailure(parser, " t/@O'neil",
                "Telegram handle should only contain alphabets, digits, underscores or '@'.");
        assertParseFailure(parser, " t/ @darren_*()#",
                "Telegram handle should only contain alphabets, digits, underscores or '@'.");
    }

    @Test
    public void parse_validEmail_returnsFindCommand() {
        Map<PersonContainsKeywordsPredicate.SearchField, List<String>> fieldKeywordMap = new HashMap<>();
        fieldKeywordMap.put(PersonContainsKeywordsPredicate.SearchField.EMAIL, List.of("darren@example.com"));
        FindCommand expectedFindCommand = new FindCommand(new PersonContainsKeywordsPredicate(fieldKeywordMap));
        assertParseSuccess(parser, " e/darren@example.com", expectedFindCommand);

        fieldKeywordMap.clear();
        fieldKeywordMap.put(PersonContainsKeywordsPredicate.SearchField.EMAIL, List.of("darren", "fio@example.com"));
        expectedFindCommand = new FindCommand(new PersonContainsKeywordsPredicate(fieldKeywordMap));
        assertParseSuccess(parser, " e/darren fio@example.com", expectedFindCommand);
    }

    @Test
    public void parse_namePhoneModuleFavouriteEmailAndRole_returnsFindCommand() {
        Map<PersonContainsKeywordsPredicate.SearchField, List<String>> fieldKeywordMap = new HashMap<>();
        fieldKeywordMap.put(PersonContainsKeywordsPredicate.SearchField.NAME, List.of("Darren"));
        fieldKeywordMap.put(PersonContainsKeywordsPredicate.SearchField.PHONE, List.of("91234567"));
        fieldKeywordMap.put(PersonContainsKeywordsPredicate.SearchField.MODULE, List.of("CS3230"));
        fieldKeywordMap.put(PersonContainsKeywordsPredicate.SearchField.FAVOURITE, List.of("y"));
        fieldKeywordMap.put(PersonContainsKeywordsPredicate.SearchField.ROLE, List.of("TA"));
        fieldKeywordMap.put(PersonContainsKeywordsPredicate.SearchField.EMAIL, List.of("darren@example.com"));
        FindCommand expectedFindCommand = new FindCommand(new PersonContainsKeywordsPredicate(fieldKeywordMap));
        assertParseSuccess(parser, " n/Darren p/91234567 mm/CS3230 f/y r/TA e/darren@example.com",
                expectedFindCommand);
    }

    @Test
    public void parse_multipleInvalidKeywords_throwsParseException() {
        assertParseFailure(parser, " n/$$ mm/%%", Name.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " mm/$$ n/$$", Name.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " t/%% mm/$$",
                "Module keywords must contain only alphanumeric characters.");
    }

    @Test
    public void parse_multipleValidKeywordsAndOneInvalidKeyword_throwsParseException() {
        assertParseFailure(parser, " n/Darren $$$", Name.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " n/Darren mm/3230 $$",
                "Module keywords must contain only alphanumeric characters.");
        assertParseFailure(parser, " t/@dd mm/dd e/dd p/dd",
                "Phone number must only contain digits.");
    }
}
