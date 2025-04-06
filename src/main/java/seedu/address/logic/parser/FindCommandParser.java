package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FAVOURITE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MULTIPLE_MODULES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEGRAM;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    private static final List<String> ALLOWED_PREFIXES = List.of(PREFIX_NAME.toString(), PREFIX_PHONE.toString(),
            PREFIX_MULTIPLE_MODULES.toString(), PREFIX_FAVOURITE.toString(), PREFIX_ROLE.toString(),
            PREFIX_TELEGRAM.toString(), PREFIX_EMAIL.toString());

    @Override
    public FindCommand parse(String args) throws ParseException {
        Map<SearchField, List<String>> fieldKeywordMap = new HashMap<>();

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenizeFind(args, PREFIX_NAME, PREFIX_PHONE,
                PREFIX_MULTIPLE_MODULES, PREFIX_FAVOURITE, PREFIX_ROLE, PREFIX_TELEGRAM, PREFIX_EMAIL
        );
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_MULTIPLE_MODULES,
                PREFIX_FAVOURITE, PREFIX_ROLE, PREFIX_TELEGRAM, PREFIX_EMAIL
        );

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            List<String> nameKeywords = extractKeywords(argMultimap, PREFIX_NAME);
            ParserUtil.validateNameKeywords(nameKeywords);
            fieldKeywordMap.put(SearchField.NAME, nameKeywords);
        }

        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            List<String> phoneKeywords = extractKeywords(argMultimap, PREFIX_PHONE);
            ParserUtil.validatePhoneKeywords(phoneKeywords);
            fieldKeywordMap.put(SearchField.PHONE, phoneKeywords);
        }

        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            List<String> emailKeywords = extractKeywords(argMultimap, PREFIX_EMAIL);
            ParserUtil.validateEmailKeywords(emailKeywords);
            fieldKeywordMap.put(SearchField.EMAIL, emailKeywords);
        }

        if (argMultimap.getValue(PREFIX_MULTIPLE_MODULES).isPresent()) {
            List<String> modulesKeywords = extractKeywords(argMultimap, PREFIX_MULTIPLE_MODULES);
            ParserUtil.validateModuleKeywords(modulesKeywords);
            fieldKeywordMap.put(SearchField.MODULE, modulesKeywords);
        }

        if (argMultimap.getValue(PREFIX_FAVOURITE).isPresent()) {
            List<String> favouriteKeywords = extractKeywords(argMultimap, PREFIX_FAVOURITE);
            ParserUtil.validateFavouriteKeywords(favouriteKeywords);
            fieldKeywordMap.put(SearchField.FAVOURITE, favouriteKeywords);
        }

        if (argMultimap.getValue(PREFIX_ROLE).isPresent()) {
            List<String> roleKeywords = extractKeywords(argMultimap, PREFIX_ROLE);
            ParserUtil.validateRoleKeywords(roleKeywords);
            fieldKeywordMap.put(SearchField.ROLE, roleKeywords);
        }

        if (argMultimap.getValue(PREFIX_TELEGRAM).isPresent()) {
            List<String> telegramKeywords = extractKeywords(argMultimap, PREFIX_TELEGRAM);
            ParserUtil.validateTelegramKeywords(telegramKeywords);
            fieldKeywordMap.put(SearchField.TELEGRAM, telegramKeywords);
        }

        if (fieldKeywordMap.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        return new FindCommand(new PersonContainsKeywordsPredicate(fieldKeywordMap));
    }

    private List<String> extractKeywords(ArgumentMultimap argMultimap, Prefix prefix) {
        return argMultimap.getAllValues(prefix)
                .stream()
                .flatMap(s -> Arrays.stream(s.split("\\s+")))
                .collect(Collectors.toList());
    }
}
