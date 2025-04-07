package seedu.address.model.person;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Name} or {@code Phone} matches any of the keywords given.
 * The search is case-insensitive and allows partial matches for phone numbers.
 */
public class PersonContainsKeywordsPredicate implements Predicate<Person> {
    private final Map<SearchField, List<String>> fieldKeywordMap;

    /**
     * Represents the search field type for finding persons.
     */
    public enum SearchField {
        NAME,
        PHONE,
        MODULE,
        FAVOURITE,
        ROLE,
        TELEGRAM,
        EMAIL,
    }

    /**
     * Constructs a {@code PersonContainsKeywordsPredicate} to find persons by the specified field and keywords.
     *
     * @param fieldKeywordMap A Map containing the field to search (either {@code NAME}
     *                        or {@code PHONE} or{@code MODULE}) and a list of keywords
     *                        to match the corresponding specified field.
     */
    public PersonContainsKeywordsPredicate(Map<SearchField, List<String>> fieldKeywordMap) {
        this.fieldKeywordMap = fieldKeywordMap;
    }

    @Override
    public boolean test(Person person) {
        return fieldKeywordMap.entrySet().stream()
                .allMatch(entry ->
                        testField(person, entry.getKey(), entry.getValue()));
    }

    private boolean testField(Person person, SearchField searchField, List<String> keywords) {
        return switch (searchField) {
        case NAME -> testName(person, keywords);
        case PHONE -> testPhone(person, keywords);
        case MODULE -> testModule(person, keywords);
        case FAVOURITE -> testFavourite(person, keywords);
        case ROLE -> testRole(person, keywords);
        case TELEGRAM -> testTelegram(person, keywords);
        case EMAIL -> testEmail(person, keywords);
        };
    }

    private boolean testName(Person person, List<String> keywords) {
        return keywords.stream()
                .anyMatch(keyword -> person
                        .getName()
                        .fullName
                        .toLowerCase()
                        .contains(keyword.toLowerCase()));
    }

    private boolean testPhone(Person person, List<String> keywords) {
        return keywords.stream()
                .anyMatch(keyword -> person
                        .getPhone().value
                        .contains(keyword));
    }

    private boolean testModule(Person person, List<String> keywords) {
        return keywords.stream()
                .anyMatch(keyword -> person.getModules().stream()
                        .anyMatch(module -> module
                                .getModuleCode()
                                .toLowerCase()
                                .contains(keyword.toLowerCase())));
    }

    private boolean testFavourite(Person person, List<String> keywords) {
        return keywords.stream()
                .anyMatch(keyword -> {
                    String lowerKeyword = keyword.toLowerCase();
                    boolean isFavourite = person.getIsFavourite();
                    return (lowerKeyword.equals("y") && isFavourite
                            || lowerKeyword.equals("n") && !isFavourite);
                });
    }

    private boolean testRole(Person person, List<String> keywords) {
        return keywords.stream()
                .anyMatch(keyword -> person
                        .getRole()
                        .toString()
                        .toLowerCase()
                        .contains(keyword.toLowerCase()));
    }

    private boolean testTelegram(Person person, List<String> keywords) {
        return keywords.stream()
                .anyMatch(keyword -> person.getTelegram()
                        .map(telegram -> telegram.toString().toLowerCase())
                        .orElse("")
                        .contains(keyword.toLowerCase()));
    }

    private boolean testEmail(Person person, List<String> keywords) {
        return keywords.stream()
                .anyMatch(keyword -> person
                        .getEmail()
                        .toString()
                        .toLowerCase()
                        .contains(keyword.toLowerCase()));
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PersonContainsKeywordsPredicate otherPredicate)) {
            return false;
        }
        return fieldKeywordMap.equals(otherPredicate.fieldKeywordMap);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("fieldKeywordsMap", fieldKeywordMap).toString();
    }
}
