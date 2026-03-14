package seedu.address.model.person;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class PersonContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> nameKeywords = new ArrayList<>();
    private final List<String> phoneKeywords = new ArrayList<>();
    private final List<String> addressKeywords = new ArrayList<>();
    private final List<String> generalKeywords = new ArrayList<>();

    private boolean isSearchingForName = false;
    private boolean isSearchingForPhone = false;
    private boolean isSearchingForAddress = false;

    public PersonContainsKeywordsPredicate(List<String> keywords) {
        String currentField = "general";

        for (String token : keywords) {
            if (token.startsWith("n/")) {
                currentField = "name";
                isSearchingForName = true;
                String value = token.substring(2);
                if (!value.isEmpty()) {
                    nameKeywords.add(value);
                }
                continue;
            }

            if (token.startsWith("p/")) {
                currentField = "phone";
                isSearchingForPhone = true;
                String value = token.substring(2);
                if (!value.isEmpty()) {
                    phoneKeywords.add(value);
                }
                continue;
            }

            if (token.startsWith("a/")) {
                currentField = "address";
                isSearchingForAddress = true;
                String value = token.substring(2);
                if (!value.isEmpty()) {
                    addressKeywords.add(value);
                }
                continue;
            }

            switch (currentField) {
                case "name":
                    nameKeywords.add(token);
                    break;
                case "phone":
                    phoneKeywords.add(token);
                    break;
                case "address":
                    addressKeywords.add(token);
                    break;
                default:
                    generalKeywords.add(token);
            }
        }
    }

    @Override
    public boolean test(Person person) {
        // General search: no field tags used
        if (!generalKeywords.isEmpty()
                && !isSearchingForName
                && !isSearchingForPhone
                && !isSearchingForAddress) {
            return generalKeywords.stream().anyMatch(keyword ->
                    StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword)
                            || StringUtil.containsWordIgnoreCase(person.getPhone().value, keyword)
                            || StringUtil.containsWordIgnoreCase(person.getAddress().value, keyword)
                            || StringUtil.containsWordIgnoreCase(person.getEmail().value, keyword));
        }

        boolean matchesName = !isSearchingForName || nameKeywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));

        boolean matchesPhone = !isSearchingForPhone || phoneKeywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getPhone().value, keyword));

        boolean matchesAddress = !isSearchingForAddress || addressKeywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getAddress().value, keyword));

        return matchesName && matchesPhone && matchesAddress;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PersonContainsKeywordsPredicate)) {
            return false;
        }

        PersonContainsKeywordsPredicate otherPredicate = (PersonContainsKeywordsPredicate) other;
        return nameKeywords.equals(otherPredicate.nameKeywords)
                && phoneKeywords.equals(otherPredicate.phoneKeywords)
                && addressKeywords.equals(otherPredicate.addressKeywords)
                && generalKeywords.equals(otherPredicate.generalKeywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("generalKeywords", generalKeywords)
                .add("nameKeywords", nameKeywords)
                .add("phoneKeywords", phoneKeywords)
                .add("addressKeywords", addressKeywords)
                .toString();
    }
}
