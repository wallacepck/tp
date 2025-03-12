package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import seedu.address.model.Model;
import seedu.address.commons.core.index.Index;
import seedu.address.model.person.Person;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.module.Module;
import seedu.address.model.person.module.ModuleRegistry;

public class AddModuleCommand extends Command {

    public static final String COMMAND_WORD = "module";

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

    public AddModuleCommand(Index index, String remark) {
        requireAllNonNull(index, remark);

        this.targetIndex = index;
        this.moduleCode = remark;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToAddModule = lastShownList.get(targetIndex.getZeroBased());

        Module module = ModuleRegistry.getModuleByCode(this.moduleCode);
        if (module == null) {
            throw new CommandException(Messages.MESSAGE_INVALID_MODULE_CODE);
        }
        personToAddModule.setModule(module);

        throw new CommandException(String.format(MESSAGE_ARGUMENTS, this.targetIndex.getOneBased(), this.moduleCode));
    }

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
