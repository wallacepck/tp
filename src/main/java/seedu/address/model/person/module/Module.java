package seedu.address.model.person.module;

/**
 * Represents a Module that stores the module code and name.
 * This class is immutable.
 */
public class Module {
    private final String moduleCode; // Unique identifier for the module (e.g., "CS1010")
    private final String moduleName; // Full name of the module (e.g., "Programming Methodology")

    /**
     * Constructs a Module with the specified module code and name.
     *
     * @param moduleCode The unique code of the module.
     * @param moduleName The name of the module.
     */
    public Module(String moduleCode, String moduleName) {
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;
    }

    /**
     * Returns the module code.
     *
     * @return The module code as a String.
     */
    public String getModuleCode() {
        return this.moduleCode;
    }

    /**
     * Returns the module name.
     *
     * @return The module name as a String.
     */
    public String getModuleName() {
        return this.moduleName;
    }

    /**
     * Returns a string representation of the module in the format:
     * "moduleCode moduleName".
     *
     * @return A formatted string representation of the module.
     */
    @Override
    public String toString() {
        return this.moduleCode + " " + this.moduleName;
    }
}
