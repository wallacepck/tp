package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.module.Module;
import seedu.address.model.person.module.ModuleRegistry;

/**
 * Adds or replaces a module for a person identified by their index in the last displayed person list.
 */
public class AddModuleCommand extends Command {

    public static final String COMMAND_WORD = "module";

    /**
     * Usage message for the {@code AddModuleCommand}, detailing command syntax and an example.
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds or replaces a module to "
            + "the person identified by the index "
            + "number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_MODULE + "[MODULE CODE]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_MODULE + "CS2040S";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Module: %2$s";

    private final Index targetIndex;
    private final String moduleCode;

    /**
     * Constructs an {@code AddModuleCommand} with the given person index and module code.
     *
     * @param index The index of the person in the displayed list.
     * @param moduleCode The module code to assign to the person.
     */
    public AddModuleCommand(Index index, String moduleCode) {
        requireAllNonNull(index, moduleCode);

        this.targetIndex = index;
        this.moduleCode = moduleCode;
    }

    /**
     * Executes the command to assign a module to the specified person.
     *
     * @param model The model containing the list of persons.
     * @return A {@code CommandResult} indicating the outcome of the execution.
     * @throws CommandException If the index is invalid or the module code is not recognized.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        // Check if the target index is within the list bounds
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToAddModule = lastShownList.get(targetIndex.getZeroBased());

        // Retrieve the module from the registry
        Module module = ModuleRegistry.getModuleByCode(this.moduleCode);
        if (module == null) {
            throw new CommandException(Messages.MESSAGE_INVALID_MODULE_CODE);
        }

        // Assign the module to the person
        personToAddModule.setModule(module);

        throw new CommandException(String.format(MESSAGE_ARGUMENTS, this.targetIndex.getOneBased(), this.moduleCode));
    }

    /**
     * Checks if this {@code AddModuleCommand} is equal to another object.
     *
     * @param other The other object to compare with.
     * @return {@code true} if both objects are equivalent, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddModuleCommand m)) {
            return false;
        }

        return targetIndex.equals(m.targetIndex) && moduleCode.equals(m.moduleCode);
    }
}
