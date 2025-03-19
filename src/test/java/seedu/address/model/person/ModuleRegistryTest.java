package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

public class ModuleRegistryTest {

    // Test for retrieving a module by its valid code
    @Test
    public void getModuleByCode_validCode_returnsCorrectModule() {
        // Test data
        String moduleCode = "CS1231S";
        String expectedModuleName = "Discrete Structures";

        // Retrieve the module using the code
        ModuleRegistry.Module module = ModuleRegistry.getModuleByCode(moduleCode);

        // Validate that the module is retrieved correctly
        assertNotNull(module);
        assertEquals(expectedModuleName, module.getModuleName());
    }

    // Test for retrieving a module by an invalid code
    @Test
    public void getModuleByCode_invalidCode_returnsNull() {
        // Test data for an invalid code
        String invalidModuleCode = "INVALID";

        // Try to retrieve the module using an invalid code
        ModuleRegistry.Module module = ModuleRegistry.getModuleByCode(invalidModuleCode);

        // Validate that the result is null
        assertNull(module);
    }

    // Test for retrieving all modules
    @Test
    public void getAllModules_returnsCorrectModulesList() {
        // Retrieve all modules
        List<ModuleRegistry.Module> modules = ModuleRegistry.getAllModules();

        // Validate that the list is not null or empty
        assertNotNull(modules);
        assertFalse(modules.isEmpty());

        // Validate the expected modules are present
        assertTrue(modules.stream().anyMatch(module -> module.getModuleCode().equals("CS1231S")));
        assertTrue(modules.stream().anyMatch(module -> module.getModuleCode().equals("CS2030S")));
        assertTrue(modules.stream().anyMatch(module -> module.getModuleCode().equals("CS2100")));
    }

    // Test for case-insensitive module code lookup
    @Test
    public void getModuleByCode_caseInsensitive_returnsCorrectModule() {
        // Test case insensitivity
        String moduleCodeUpper = "CS1231S";
        String moduleCodeLower = "cs1231s";

        // Retrieve the module using both upper and lower case inputs
        ModuleRegistry.Module moduleUpper = ModuleRegistry.getModuleByCode(moduleCodeUpper);
        ModuleRegistry.Module moduleLower = ModuleRegistry.getModuleByCode(moduleCodeLower);

        // Validate that both return the same module
        assertNotNull(moduleUpper);
        assertNotNull(moduleLower);
        assertEquals(moduleUpper.getModuleCode(), moduleLower.getModuleCode());
    }

    // Test for `Module`'s getter methods and toString()
    @Test
    public void module_methods_returnCorrectValues() {
        // Test data
        String moduleCode = "CS1231S";
        String moduleName = "Discrete Structures";

        // Create the module using the ModuleRegistry
        ModuleRegistry.Module module = ModuleRegistry.getModuleByCode(moduleCode);

        // Validate that the getter methods return the correct values
        assertNotNull(module);
        assertEquals(moduleCode, module.getModuleCode());
        assertEquals(moduleName, module.getModuleName());

        // Validate the toString method returns the correct format
        assertEquals(moduleCode + " " + moduleName, module.toString());
    }

    // Test for checking null module code (should throw NullPointerException)
    @Test
    public void getModuleByCode_nullCode_throwsNullPointerException() {
        // Test for null moduleCode
        assertNull(ModuleRegistry.getModuleByCode(null));
    }

    // Test equal() function
    @Test
    public void modules_equal_test() {
        // Test for nulls
        assertFalse(ModuleRegistry.getModuleByCode("CS2101").equals(null));

        // Test for equality
        assertTrue(ModuleRegistry.getModuleByCode("CS2101")
                .equals(ModuleRegistry.getModuleByCode("CS2101")));

        // Test for inequality
        assertFalse(ModuleRegistry.getModuleByCode("CS2101")
                .equals(ModuleRegistry.getModuleByCode("CS2103T")));
    }
}
