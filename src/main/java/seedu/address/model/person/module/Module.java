package seedu.address.model.person.module;

public class Module {
    private final String moduleCode;
    private final String moduleName;

    public Module(String moduleCode, String moduleName) {
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;
    }

    public String getModuleCode() {
        return this.moduleCode;
    }

    public String getModuleName() {
        return this.moduleName;
    }

    @Override
    public String toString() {
        return this.moduleCode + " " + this.moduleName;
    }
}