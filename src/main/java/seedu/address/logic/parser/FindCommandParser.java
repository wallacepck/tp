package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonContainsKeywordsPredicate;
import seedu.address.model.person.PersonContainsKeywordsPredicate.SearchField;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {
    private static final Pattern PREFIX_PATTERN =
            Pattern.compile("(?<prefix>[npm]/)(?<keywords>.*?)(?=\\s+[npm]/|$)",
                    Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        detectInvalidPrefixes(trimmedArgs);

        Matcher matcher = PREFIX_PATTERN.matcher(trimmedArgs);
        Map<SearchField, List<String>> fieldKeywordsMap = new HashMap<>();
        boolean foundAnyPrefix = false;

        while (matcher.find()) {
            foundAnyPrefix = true;
            String prefix = matcher.group("prefix").trim();
            String keywordStr = matcher.group("keywords").trim();

            if (keywordStr.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }

            PersonContainsKeywordsPredicate.SearchField field = getSearchField(prefix);
            List<String> keywords = Arrays.asList(keywordStr.split("\\s+"));
            fieldKeywordsMap.put(field, keywords);
        }

        if (!foundAnyPrefix || fieldKeywordsMap.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        return new FindCommand(new PersonContainsKeywordsPredicate(fieldKeywordsMap));
    }

    /**
     * Ensures only valid prefix(es) is present.
     * @throws ParseException if invalid prefix(es) are found.
     */
    private void detectInvalidPrefixes(String args) throws ParseException {
        long invalidPrefixCount = Arrays.stream(args.split("\\s+"))
                .map(String::toLowerCase)
                .filter(token -> token.matches("^[a-z]+/$"))
                .filter(token -> !token.matches("^(n/|m/|p/)$"))
                .count();
        if (invalidPrefixCount > 0) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
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
