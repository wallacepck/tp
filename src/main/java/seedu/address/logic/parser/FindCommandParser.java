package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MULTIPLE_MODULES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FAVOURITE;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonContainsKeywordsPredicate;
import seedu.address.model.person.PersonContainsKeywordsPredicate.SearchField;

/**
 * Parses input arguments and creates a new {@code FindCommand} object.
 * If no valid prefix is provided, or if any validation fails, a {@code ParseException}
 * is thrown.
 */
public class FindCommandParser implements Parser<FindCommand> {

    private static final List<String> ALLOWED_PREFIXES = List.of("n/", "p/", "mm/", "f/", "r/");

    @Override
    public FindCommand parse(String args) throws ParseException {
        detectInvalidPrefixes(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_NAME,
                PREFIX_PHONE,
                PREFIX_MULTIPLE_MODULES,
                PREFIX_FAVOURITE,
                PREFIX_ROLE);
        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_NAME,
                PREFIX_PHONE,
                PREFIX_MULTIPLE_MODULES,
                PREFIX_FAVOURITE,
                PREFIX_ROLE);

        Map<SearchField, List<String>> fieldKeywordMap = new HashMap<>();
        addFieldIfPresent(argMultimap, PREFIX_NAME, SearchField.NAME, fieldKeywordMap, null);
        addFieldIfPresent(argMultimap, PREFIX_PHONE, SearchField.PHONE, fieldKeywordMap, null);
        addFieldIfPresent(argMultimap, PREFIX_MULTIPLE_MODULES, SearchField.MODULE, fieldKeywordMap, null);
        addFieldIfPresent(argMultimap, PREFIX_FAVOURITE, SearchField.FAVOURITE, fieldKeywordMap,
                this::validateFavouriteKeywords);
        addFieldIfPresent(argMultimap, PREFIX_ROLE, SearchField.ROLE, fieldKeywordMap,
                this::validateRoleKeywords);
        if (fieldKeywordMap.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        return new FindCommand(new PersonContainsKeywordsPredicate(fieldKeywordMap));
    }

    private void detectInvalidPrefixes(String args) throws ParseException {
        Pattern prefixDetection = Pattern.compile("(?<=\\s|^)([a-zA-Z]+/)");
        Matcher matcher = prefixDetection.matcher(args);
        while (matcher.find()) {
            String prefix = matcher.group(1).toLowerCase();
            if (!ALLOWED_PREFIXES.contains(prefix)) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
        }
    }

    private void addFieldIfPresent(ArgumentMultimap argMultimap, Prefix prefix, SearchField searchField,
                                   Map<SearchField, List<String>> fieldKeywordMap,
                                   Validator<List<String>> validator) throws ParseException {
        if (isPrefixPresent(argMultimap, prefix)) {
            List<String> keywords = extractKeywords(argMultimap, prefix);
            if (validator != null) {
                validator.validate(keywords);
            }
            fieldKeywordMap.put(searchField, keywords);
        }
    }

    private List<String> extractKeywords(ArgumentMultimap argMultimap, Prefix prefix) {
        return argMultimap.getAllValues(prefix)
                .stream()
                .flatMap(s -> Arrays.stream(s.split("\\s+")))
                .collect(Collectors.toList());
    }

    private void validateFavouriteKeywords(List<String> keywords) throws ParseException {
        if (keywords.size() != 1) {
            throw new ParseException("f/ field must contain exactly one keyword: 'y' or 'n' (case-insensitive).");
        }
        String lower = keywords.get(0).toLowerCase();
        if (!lower.equals("y") && !lower.equals("n")) {
            throw new ParseException("f/ field only accepts 'y' or 'n' (case-insensitive).");
        }
    }

    private void validateRoleKeywords(List<String> keywords) throws ParseException {
        if (keywords.size() != 1) {
            throw new ParseException("r/ field must contain exactly one keyword: 'professor' "
                    + "or 'TA' (case-insensitive).");
        }
        String lower = keywords.get(0).toLowerCase();
        if (!lower.equals("professor") && !lower.equals("ta")) {
            throw new ParseException("r/ field only accepts 'professor' or 'TA' (case-insensitive).");
        }
    }

    private static boolean isPrefixPresent(ArgumentMultimap argMultimap, Prefix prefix) {
        return argMultimap.getValue(prefix).isPresent();
    }

    @FunctionalInterface
    private interface Validator<T> {
        void validate(T t) throws ParseException;
    }
}
