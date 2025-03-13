package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddModuleCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code AddModuleCommand} object.
 */
public class AddModuleCommandParser implements Parser<AddModuleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code AddModuleCommand}
     * and returns an {@code AddModuleCommand} object for execution.
     *
     * @param args User input arguments as a String.
     * @return A new instance of {@code AddModuleCommand} with the parsed values.
     * @throws ParseException If the input does not conform to the expected format.
     */
    public AddModuleCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // Tokenizes the input arguments based on the module prefix.
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_MODULE);

        Index index;
        try {
            // Parses the index from the command's preamble (e.g., "1" in "module 1 m/CS1010").
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            // Throws a ParseException if the index is invalid.
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddModuleCommand.MESSAGE_USAGE), ive);
        }

        // Extracts the module code from the parsed arguments, defaulting to an empty string if absent.
        String moduleCode = argMultimap.getValue(PREFIX_MODULE).orElse("");

        // Returns the parsed command object with the extracted index and module code.
        return new AddModuleCommand(index, moduleCode);
    }
}
