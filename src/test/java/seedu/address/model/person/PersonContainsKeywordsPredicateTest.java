package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

class PersonContainsKeywordsPredicateTest {

    @Test
    void test_nameContainsKeyword_returnsTrue() {
        PersonContainsKeywordsPredicate.SearchField field =
                PersonContainsKeywordsPredicate.SearchField.NAME;

        // One keyword
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(field, Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
        // Multiple keyword
        predicate = new PersonContainsKeywordsPredicate(field, Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
        // Only one matching keyword
        predicate = new PersonContainsKeywordsPredicate(field, Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));
        // Mixed-case keywords
        predicate = new PersonContainsKeywordsPredicate(field, Arrays.asList("aLiCe", "bOb"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
        // Partial matching keyword
        predicate = new PersonContainsKeywordsPredicate(field, Arrays.asList("Ali"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").build()));
    }

    @Test
    void test_nameDoesNotContainKeyword_returnsFalse() {
        PersonContainsKeywordsPredicate.SearchField field =
                PersonContainsKeywordsPredicate.SearchField.NAME;

        // Zero keywords
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(field, Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Bob").build()));
        // Non-matching keywords
        predicate = new PersonContainsKeywordsPredicate(field, Arrays.asList("Carol"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
        // Keywords match phone, email and address, but does not match name
        predicate = new PersonContainsKeywordsPredicate(field,
                Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withModule("CS2103T").withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    void test_phoneContainsKeyword_returnsTrue() {
        PersonContainsKeywordsPredicate.SearchField field = PersonContainsKeywordsPredicate.SearchField.PHONE;
        // One keyword
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(field, Collections.singletonList("91234567"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("91234567").build()));
        // Partial matching keyword
        predicate = new PersonContainsKeywordsPredicate(field, List.of("1234"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("91234567").build()));
        // Separated search
        predicate = new PersonContainsKeywordsPredicate(field, List.of("9123", "4567"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("91234567").build()));
    }

    @Test
    void test_phoneDoesNotContainKeyword_returnsFalse() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(PersonContainsKeywordsPredicate.SearchField.PHONE,
                        List.of("9999"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("91234567").build()));
    }

    @Test
    void test_moduleContainsKeyword_returnsTrue() {
        PersonContainsKeywordsPredicate.SearchField field =
                PersonContainsKeywordsPredicate.SearchField.MODULE;
        // Fully matched
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(field, Collections.singletonList("CS2103T"));
        assertTrue(predicate.test(new PersonBuilder().withModule("CS2103T").build()));
        // Partial matched
        predicate = new PersonContainsKeywordsPredicate(field, Collections.singletonList("2103"));
        assertTrue(predicate.test(new PersonBuilder().withModule("CS2103T").build()));
    }

    @Test
    void test_moduleDoesNotContainKeyword_returnsFalse() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(PersonContainsKeywordsPredicate.SearchField.MODULE,
                        List.of("IS2218"));
        assertFalse(predicate.test(new PersonBuilder().withModule("CS2103T").build()));
    }


    @Test
    void test_equals() {
        PersonContainsKeywordsPredicate predicate1 =
                new PersonContainsKeywordsPredicate(PersonContainsKeywordsPredicate.SearchField.NAME, List.of("Alice"));
        PersonContainsKeywordsPredicate predicate2 =
                new PersonContainsKeywordsPredicate(PersonContainsKeywordsPredicate.SearchField.NAME, List.of("Alice"));

        assertTrue(predicate1.equals(predicate2));

        PersonContainsKeywordsPredicate predicate3 =
                new PersonContainsKeywordsPredicate(PersonContainsKeywordsPredicate.SearchField.PHONE,
                        List.of("91234567"));
        assertFalse(predicate1.equals(predicate3));
    }
}
