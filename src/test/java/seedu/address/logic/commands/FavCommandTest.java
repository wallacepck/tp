package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

public class FavCommandTest {
    private FavCommand favFirst = new FavCommand(INDEX_FIRST_PERSON);
    private FavCommand sameFavFirst = new FavCommand(INDEX_FIRST_PERSON);
    private FavCommand favSecond = new FavCommand(INDEX_SECOND_PERSON);
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());


    @Test
    public void execute_validIndex_success() throws CommandException {
        //marks the person first.
        Person personToMark = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person markedPerson = personToMark.toggleFav();
        FavCommand favCommand = new FavCommand(INDEX_FIRST_PERSON);

        String markMessage = String.format(FavCommand.MESSAGE_MARK_PERSON_SUCCESS,
                personToMark.getName());

        String unmarkMessage = String.format(FavCommand.MESSAGE_UNMARK_PERSON_SUCCESS,
                personToMark.getName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToMark, markedPerson);

        assertCommandSuccess(favCommand, model, markMessage, expectedModel);

        //unmarks a favourite person.

        expectedModel.setPerson(markedPerson, personToMark);

        assertCommandSuccess(favCommand, model, unmarkMessage, expectedModel);

    }

    @Test
    public void execute_markInvalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        FavCommand favCommand = new FavCommand(outOfBoundIndex);

        assertCommandFailure(favCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        FavCommand favCommand = new FavCommand(targetIndex);
        String expected = FavCommand.class.getCanonicalName() + "{index=" + targetIndex + "}";
        assertEquals(expected, favCommand.toString());
    }

    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(favFirst.equals(favFirst));

        // same values -> returns true
        assertTrue(favFirst.equals(sameFavFirst));

        // different types -> returns false
        assertFalse(favFirst.equals(1));

        // null -> returns false
        assertFalse(favFirst.equals(null));

        // different index -> returns false
        assertFalse(favFirst.equals(favSecond));
    }
}
