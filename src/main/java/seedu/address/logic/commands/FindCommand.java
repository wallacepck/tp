package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FAVOURITE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MULTIPLE_MODULES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEGRAM;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.PersonContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds contacts based on a specified field. "
            + "Supported fields: "
            + PREFIX_NAME + " "
            + PREFIX_PHONE + " "
            + PREFIX_FAVOURITE + " "
            + PREFIX_ROLE + " "
            + PREFIX_TELEGRAM + " "
            + PREFIX_EMAIL + " "
            + " and " + PREFIX_MULTIPLE_MODULES + ".\n"
            + "Examples:\n"
            + "  " + COMMAND_WORD + " n/alice bob charlie\n"
            + "  " + COMMAND_WORD + " p/91234567\n"
            + "  " + COMMAND_WORD + " mm/CS2103T\n"
            + "  " + COMMAND_WORD + " f/y\n"
            + "  " + COMMAND_WORD + " r/professor\n"
            + "  " + COMMAND_WORD + " e/alice@example.com\n"
            + "  " + COMMAND_WORD + " t/@sourceacademy\n";

    private final PersonContainsKeywordsPredicate predicate;

    public FindCommand(PersonContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof FindCommand otherFindCommand)) {
            return false;
        }
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
