package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        detectInvalidNumberOfPrefixes(trimmedArgs);

        // Extract and validate prefix & keywords
        String[] splitArgs = extractPrefixAndKeywords(trimmedArgs);
        String prefix = splitArgs[0].trim();
        String keywordString = validateKeywordString(splitArgs[1].trim());

        PersonContainsKeywordsPredicate.SearchField searchField = getSearchField(prefix);
        List<String> keywords = Arrays.asList(keywordString.split("\\s+"));
        return new FindCommand(new PersonContainsKeywordsPredicate(searchField, keywords));
    }

    /**
     * Ensures only one valid prefix is present.
     * @throws ParseException if multiple or missing prefixes are found.
     */
    private void detectInvalidNumberOfPrefixes(String args) throws ParseException {
        if (args.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        // Ensure only one prefix exists
        long prefixCount = Arrays.stream(args.split("\\s+"))
                .filter(token -> token.matches("^(n/|p/|m/|N/|P/|M/)$"))
                .count();
        // If multiple prefix exists, invalid prefix used, or no specified prefix
        if (prefixCount != 1) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Extracts the prefix and keyword string from the input.
     * @throws ParseException if the format is incorrect.
     */
    private String[] extractPrefixAndKeywords(String trimmedArgs) throws ParseException {
        String[] splitArgs = trimmedArgs.split("\\s+", 2);
        if (splitArgs.length < 2 || splitArgs[1].trim().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        return splitArgs;
    }

    /**
     * Ensures that the keyword string is not empty.
     * @throws ParseException if the keyword string is empty.
     */
    private String validateKeywordString(String keywordString) throws ParseException {
        if (keywordString.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        return keywordString;
    }

    /**
     * Converts a prefix string into the corresponding SearchField.
     * @throws ParseException if the prefix is invalid.
     */
    private PersonContainsKeywordsPredicate.SearchField getSearchField(String prefix) throws ParseException {
        return switch (prefix.toLowerCase()) {
        case "n/" -> PersonContainsKeywordsPredicate.SearchField.NAME;
        case "p/" -> PersonContainsKeywordsPredicate.SearchField.PHONE;
        case "m/" -> PersonContainsKeywordsPredicate.SearchField.MODULE;
        default -> throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindCommand.MESSAGE_USAGE));
        };
    }
}
