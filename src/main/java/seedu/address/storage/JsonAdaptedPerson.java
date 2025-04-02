package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Email;
import seedu.address.model.person.ModuleRegistry;
import seedu.address.model.person.ModuleRegistry.Module;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.person.Telegram;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String telegram;
    private final List<String> modules = new ArrayList<>();
    private final String role;
    private final Boolean isFavourite;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("email") String email,
            @JsonProperty("modules") List<String> modules,
            @JsonProperty("role") String role, @JsonProperty("isFavourite") Boolean isFavourite,
            @JsonProperty("telegram") String telegram) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.modules.addAll(modules);
        this.role = role;
        this.isFavourite = isFavourite;
        this.telegram = telegram;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        modules.addAll(source.getModules().stream()
                .map(Module::getModuleCode)
                .collect(Collectors.toList()));
        role = source.getRole().roleName;
        isFavourite = source.getIsFavourite();
        telegram = source.getTelegram().isPresent()
                ? source.getTelegram().get().toString()
                : "";
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Module> personModules = new ArrayList<>();
        for (String module : modules) {
            Module toAdd = ModuleRegistry.getModuleByCode(module);
            if (toAdd == null) {
                throw new IllegalValueException(ModuleRegistry.MESSAGE_UNREGISTERED_MODULE);
            }
            personModules.add(ModuleRegistry.getModuleByCode(module));
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (telegram == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "telegram"));
        }
        final Optional<Telegram> modelTelegram;
        if (telegram.equals("")) {
            modelTelegram = Optional.empty();
        } else {
            if (!Telegram.isValidHandle(telegram)) {
                throw new IllegalValueException(Telegram.MESSAGE_CONSTRAINTS);
            }
            modelTelegram = Optional.of(new Telegram(telegram));
        }

        if (role == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Role.class.getSimpleName()));
        }
        if (!Role.isValidRole(role)) {
            throw new IllegalValueException(Role.MESSAGE_CONSTRAINTS);
        }
        final Role modelRole = Role.getRole(role);

        if (isFavourite == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "is Favourite"));
        }

        final Boolean modelIsFavourite = isFavourite;

        final Set<Module> modelModules = new HashSet<>(personModules);
        return new Person(modelName, modelPhone, modelEmail, modelRole, modelModules, modelIsFavourite,
                modelTelegram);
    }
}
