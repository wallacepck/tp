package seedu.address.testutil;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.model.person.Email;
import seedu.address.model.person.ModuleRegistry;
import seedu.address.model.person.ModuleRegistry.Module;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.person.Telegram;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_TELEGRAM = "@AmyTele";
    public static final Role DEFAULT_ROLE = Role.PROFESSOR;
    public static final String DEFAULT_MODULE_CODE = "CS2103T";

    private Name name;
    private Phone phone;
    private Email email;
    private Set<Module> modules;
    private Role role;
    private Boolean isFavourite;
    private Optional<Telegram> telegram;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        telegram = Optional.of(new Telegram(DEFAULT_TELEGRAM));
        modules = new HashSet<>();
        modules.add(ModuleRegistry.getModuleByCode(DEFAULT_MODULE_CODE));
        role = DEFAULT_ROLE;
        isFavourite = false;
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        telegram = personToCopy.getTelegram();
        modules = new HashSet<>(personToCopy.getModules());
        role = personToCopy.getRole();
        isFavourite = personToCopy.getIsFavourite();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Module} of the {@code Person} that we are building.
     */
    public PersonBuilder withModule(String ...modules) {
        this.modules = SampleDataUtil.getModuleSet(modules);
        return this;
    }

    /**
     * Sets the {@code Role} of the {@code Person} that we are building.
     */
    public PersonBuilder withRole(Role role) {
        this.role = role;
        return this;
    }

    /**
     * Sets the {@code isFavourite} of the {@code Person} that we are building.
     */
    public PersonBuilder withFavourite(Boolean favourite) {
        this.isFavourite = favourite;
        return this;
    }

    /**
     * Sets the {@code telegram} of the {@code Person} that we are building.
     */
    public PersonBuilder withTelegram(String telegram) {
        this.telegram = Optional.of(new Telegram(telegram));
        return this;
    }

    public Person build() {
        return new Person(name, phone, email, role, modules, isFavourite, telegram);
    }

}
