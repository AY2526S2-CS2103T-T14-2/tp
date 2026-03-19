package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        PersonContainsKeywordsPredicate firstPredicate =
                new PersonContainsKeywordsPredicate(firstPredicateKeywordList);
        PersonContainsKeywordsPredicate secondPredicate =
                new PersonContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonContainsKeywordsPredicate firstPredicateCopy =
                new PersonContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_emptyKeywordList_returnsFalse() {
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));
    }

    @Test
    public void test_generalKeywordsMatchName_returnsTrue() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("Ali"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_generalKeywordsMatchPhone_returnsTrue() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("91234567"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("91234567").build()));

        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("9123"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("91234567").build()));
    }

    @Test
    public void test_generalKeywordsMatchAddress_returnsTrue() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("Street"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("123 Main Street").build()));

        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("Main"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("123 Main Street").build()));
    }

    @Test
    public void test_generalKeywordsMatchEmail_returnsTrue() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("alice"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));

        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("example"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));
    }

    @Test
    public void test_generalKeywordsMatchDetails_returnsTrue() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("friend"));
        assertTrue(predicate.test(new PersonBuilder().withDetails("Good friend from school").build()));

        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("school"));
        assertTrue(predicate.test(new PersonBuilder().withDetails("Good friend from school").build()));
    }

    @Test
    public void test_generalKeywordsNoMatch_returnsFalse() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("Charlie"));
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice Bob")
                .withPhone("91234567")
                .withEmail("alice@example.com")
                .withAddress("123 Main Street")
                .withDetails("friend")
                .build()));
    }

    // ... (all other test methods continue exactly as in your original file, but with trailing spaces removed)

}
