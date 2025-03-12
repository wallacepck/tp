package seedu.address.model.person.module;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public enum ModuleRegistry {
    CS1231S(new Module("CS1231S", "Discrete Structures")),
    CS2030S(new Module("CS2030S", "Programming Methodology II")),
    CS2040S(new Module("CS2040S", "Data Structures and Algorithms")),
    CS2100(new Module("CS2100", "Computer Organisation")),
    CS2103T(new Module("CS2103T", "Software Engineering")),
    CS2106(new Module("CS2106", "Introduction to Operating Systems")),
    CS2109S(new Module("CS2109S", "Introduction to AI and Machine Learning")),
    CS3230(new Module("CS3230", "Design and Analysis of Algorithms")),
    CS2101(new Module("CS2101", "Effective Communication for Computing Professionals"));

    private final Module module;

    ModuleRegistry(Module module) {
        this.module = module;
    }

    public Module getModule() {
        return module;
    }

    public static Module getModuleByCode(String code) {
        for (ModuleRegistry entry : ModuleRegistry.values()) {
            if (entry.module.getModuleCode().equalsIgnoreCase(code)) {
                return entry.module;
            }
        }
        return null;
    }

    public static List<Module> getAllModules() {
        List<Module> modules = new ArrayList<>();
        for (ModuleRegistry entry : ModuleRegistry.values()) {
            modules.add(entry.module);
        }
        return Collections.unmodifiableList(modules);
    }
}
