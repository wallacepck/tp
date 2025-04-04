package seedu.address.testutil;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.person.Email;
import seedu.address.model.person.ModuleRegistry;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Telegram;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditPersonDescriptorBuilder {

    private EditPersonDescriptor descriptor;

    public EditPersonDescriptorBuilder() {
        descriptor = new EditPersonDescriptor();
    }

    public EditPersonDescriptorBuilder(EditPersonDescriptor descriptor) {
        this.descriptor = new EditPersonDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditPersonDescriptorBuilder(Person person) {
        descriptor = new EditPersonDescriptor();
        descriptor.setName(person.getName());
        descriptor.setPhone(person.getPhone());
        descriptor.setEmail(person.getEmail());
        descriptor.setTelegram(person.getTelegram());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Parses the {@code modules} into a {@code Set<Module>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withModule(String... modules) {
        Set<ModuleRegistry.Module> moduleSet = Stream.of(modules).map(ModuleRegistry::getModuleByCode)
                .collect(Collectors.toSet());
        descriptor.setModules(moduleSet);
        return this;
    }

    /**
     * Parse the {@code telegram} into a {@code Optional<Telegram>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withTelegram(String telegram) {
        descriptor.setTelegram(Optional.of(new Telegram(telegram)));
        return this;
    }

    /**
     * Sets the {@code telegram} of {@code EditPersonDescriptor} that we are building to {@code Optiona.empty}.
     */
    public EditPersonDescriptorBuilder withEmptyTelegram() {
        descriptor.setTelegram(Optional.empty());
        return this;
    }

    public EditPersonDescriptor build() {
        return descriptor;
    }
}
