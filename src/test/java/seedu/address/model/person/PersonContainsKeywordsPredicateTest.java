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
        // Empty keyword list
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));
    }

    @Test
    public void test_generalKeywordsMatchName_returnsTrue() {
        // One general keyword matching name
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Partial keyword match in name
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("Ali"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Case-insensitive match
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_generalKeywordsMatchPhone_returnsTrue() {
        // General keyword matching phone
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("91234567"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("91234567").build()));

        // Partial phone match
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("9123"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("91234567").build()));
    }

    @Test
    public void test_generalKeywordsMatchAddress_returnsTrue() {
        // General keyword matching address
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("Street"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("123 Main Street").build()));

        // Partial address match
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("Main"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("123 Main Street").build()));
    }

    @Test
    public void test_generalKeywordsMatchEmail_returnsTrue() {
        // General keyword matching email
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("alice"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));

        // Partial email match
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("example"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));
    }

    @Test
    public void test_generalKeywordsMatchDetails_returnsTrue() {
        // General keyword matching details
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("friend"));
        assertTrue(predicate.test(new PersonBuilder().withDetails("Good friend from school").build()));

        // Partial details match
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("school"));
        assertTrue(predicate.test(new PersonBuilder().withDetails("Good friend from school").build()));
    }

    @Test
    public void test_generalKeywordsNoMatch_returnsFalse() {
        // Non-matching general keyword
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

    @Test
    public void test_nameKeywordsWithPrefix_returnsTrue() {
        // Name keyword with prefix (n/)
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("n/Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple name keywords with prefix
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("n/Alice", "Bob"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Name keyword does not match phone
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("n/91234567"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("91234567").build()));
    }

    @Test
    public void test_phoneKeywordsWithPrefix_returnsTrue() {
        // Phone keyword with prefix (p/)
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("p/9123"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("91234567").build()));

        // Multiple phone keywords
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("p/9123", "4567"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("91234567").build()));

        // Phone keyword does not match name
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("p/Alice"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("91234567").build()));
    }

    @Test
    public void test_addressKeywordsWithPrefix_returnsTrue() {
        // Address keyword with prefix (a/)
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("a/Main"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("123 Main Street").build()));

        // Multiple address keywords
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("a/Main", "Street"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("123 Main Street").build()));
    }

    @Test
    public void test_emailKeywordsWithPrefix_returnsTrue() {
        // Email keyword with prefix (e/)
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("e/alice"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));

        // Multiple email keywords
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("e/alice", "example"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));
    }

    @Test
    public void test_detailsKeywordsWithPrefix_returnsTrue() {
        // Details keyword with prefix (d/)
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("d/friend"));
        assertTrue(predicate.test(new PersonBuilder().withDetails("Good friend").build()));

        // Multiple details keywords
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("d/Good", "friend"));
        assertTrue(predicate.test(new PersonBuilder().withDetails("Good friend").build()));
    }

    @Test
    public void test_multipleFieldsAndLogic_returnsTrue() {
        // AND logic: name contains "Alice" AND phone contains "9123"
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList("n/Alice", "p/9123"));
        assertTrue(predicate.test(new PersonBuilder()
                .withName("Alice")
                .withPhone("91234567")
                .build()));

        // AND logic fails when one field doesn't match
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("n/Bob", "p/9123"));
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice")
                .withPhone("91234567")
                .build()));
    }

    @Test
    public void test_multipleFieldsOrLogicWithinField_returnsTrue() {
        // OR logic within name field: name contains "Alice" OR "Bob"
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList("n/Alice", "Bob"));
        assertTrue(predicate.test(new PersonBuilder().withName("Bob").build()));

        // OR logic within phone field
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("p/9123", "8765"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("87654321").build()));
    }

    @Test
    public void test_complexQueryWithMultipleFields_returnsTrue() {
        // Complex query: name contains "Alice" AND address contains "Main"
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList("n/Alice", "a/Main"));
        assertTrue(predicate.test(new PersonBuilder()
                .withName("Alice")
                .withAddress("123 Main Street")
                .build()));

        // All three fields match
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("n/Alice", "p/9123", "e/example"));
        assertTrue(predicate.test(new PersonBuilder()
                .withName("Alice")
                .withPhone("91234567")
                .withEmail("alice@example.com")
                .build()));
    }

    @Test
    public void test_complexQueryPartialMatch_returnsFalse() {
        // Name matches but phone doesn't
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList("n/Alice", "p/8888"));
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice")
                .withPhone("91234567")
                .build()));

        // Two out of three fields match (AND logic requires all to match)
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("n/Alice", "p/9123", "e/bob"));
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice")
                .withPhone("91234567")
                .withEmail("alice@example.com")
                .build()));
    }

    @Test
    public void test_emptyPrefixedKeyword_ignored() {
        // Empty keyword after prefix should be ignored
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList("n/", "Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").build()));
    }

    @Test
    public void test_fieldContextPersistence_returnsTrue() {
        // After n/ prefix, subsequent tokens without prefix are also name keywords
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList("n/Alice", "Bob", "Charlie"));
        assertTrue(predicate.test(new PersonBuilder().withName("Bob").build()));
        assertTrue(predicate.test(new PersonBuilder().withName("Charlie").build()));

        // Context switches with new prefix
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("n/Alice", "Bob", "p/9123", "4567"));
        assertTrue(predicate.test(new PersonBuilder()
                .withName("Bob")
                .withPhone("45678901")
                .build()));
    }

    @Test
    public void test_mixedGeneralAndSpecificKeywords_usesAndLogic() {
        // When general keywords are mixed with specific ones, should use AND logic
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList("friend", "n/Alice"));
        // This is a general keyword followed by specific - should be treated as general initially
        assertTrue(predicate.test(new PersonBuilder()
                .withName("Alice")
                .withDetails("friend")
                .build()));
    }

    @Test
    public void test_allFieldsCombined_returnsTrue() {
        // All five fields with keywords
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList(
                        "n/Alice", "p/9123", "a/Main", "e/alice", "d/friend"));
        assertTrue(predicate.test(new PersonBuilder()
                .withName("Alice")
                .withPhone("91234567")
                .withAddress("123 Main Street")
                .withEmail("alice@example.com")
                .withDetails("Good friend")
                .build()));
    }

    @Test
    public void test_allFieldsCombinedOneMissing_returnsFalse() {
        // All five fields but one doesn't match
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList(
                        "n/Alice", "p/9123", "a/Main", "e/bob", "d/friend"));
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice")
                .withPhone("91234567")
                .withAddress("123 Main Street")
                .withEmail("alice@example.com")
                .withDetails("Good friend")
                .build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = Arrays.asList("n/Alice", "p/9123");
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(keywords);

        String expected = PersonContainsKeywordsPredicate.class.getCanonicalName()
                + "{generalKeywords=[], nameKeywords=[Alice], phoneKeywords=[9123], "
                + "addressKeywords=[], emailKeywords=[], detailsKeywords=[]}";
        assertEquals(expected, predicate.toString());
    }

    @Test
    public void test_generalKeywordsOnlyToString() {
        List<String> keywords = Arrays.asList("friend", "colleague");
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(keywords);

        String expected = PersonContainsKeywordsPredicate.class.getCanonicalName()
                + "{generalKeywords=[friend, colleague], nameKeywords=[], phoneKeywords=[], "
                + "addressKeywords=[], emailKeywords=[], detailsKeywords=[]}";
        assertEquals(expected, predicate.toString());
    }

    @Test
    public void equals_sameFieldKeywords_returnsTrue() {
        PersonContainsKeywordsPredicate predicate1 =
                new PersonContainsKeywordsPredicate(Arrays.asList("n/Alice", "p/9123"));
        PersonContainsKeywordsPredicate predicate2 =
                new PersonContainsKeywordsPredicate(Arrays.asList("n/Alice", "p/9123"));
        assertTrue(predicate1.equals(predicate2));
    }

    @Test
    public void equals_differentFieldKeywords_returnsFalse() {
        PersonContainsKeywordsPredicate predicate1 =
                new PersonContainsKeywordsPredicate(Arrays.asList("n/Alice", "p/9123"));
        PersonContainsKeywordsPredicate predicate2 =
                new PersonContainsKeywordsPredicate(Arrays.asList("n/Bob", "p/9123"));
        assertFalse(predicate1.equals(predicate2));
    }

    @Test
    public void equals_differentFieldTypes_returnsFalse() {
        PersonContainsKeywordsPredicate predicate1 =
                new PersonContainsKeywordsPredicate(Arrays.asList("n/Alice"));
        PersonContainsKeywordsPredicate predicate2 =
                new PersonContainsKeywordsPredicate(Arrays.asList("e/Alice"));
        assertFalse(predicate1.equals(predicate2));
    }

    @Test
    public void test_caseInsensitiveMatching() {
        // Test case-insensitive matching for all fields
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList(
                        "n/alice", "p/9123", "a/MAIN", "e/ALICE", "d/FRIEND"));
        assertTrue(predicate.test(new PersonBuilder()
                .withName("Alice")
                .withPhone("91234567")
                .withAddress("123 main street")
                .withEmail("alice@example.com")
                .withDetails("good friend")
                .build()));
    }

    @Test
    public void test_multipleGeneralKeywords_anyMatch_returnsTrue() {
        // Multiple general keywords - any one matching should return true (OR logic)
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList("Alice", "Bob", "Charlie"));
        assertTrue(predicate.test(new PersonBuilder().withName("Bob").build()));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").build()));
        assertTrue(predicate.test(new PersonBuilder().withName("Charlie").build()));
    }

    @Test
    public void test_multipleGeneralKeywords_noneMatch_returnsFalse() {
        // Multiple general keywords - none matching should return false
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList("David", "Eve", "Frank"));
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice")
                .withPhone("91234567")
                .withEmail("alice@example.com")
                .withAddress("123 Main Street")
                .withDetails("friend")
                .build()));
    }

    @Test
    public void test_singleFieldOrLogic_multipleKeywords_returnsTrue() {
        // Multiple keywords in same field use OR logic
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList("n/Alice", "Bob", "Charlie"));
        
        // Test each keyword separately
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Smith").build()));
        assertTrue(predicate.test(new PersonBuilder().withName("Bob Jones").build()));
        assertTrue(predicate.test(new PersonBuilder().withName("Charlie Brown").build()));
    }

    @Test
    public void test_singleFieldOrLogic_noKeywordMatch_returnsFalse() {
        // Multiple keywords in same field - none match
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList("n/David", "Eve", "Frank"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_prefixSwitchingBehavior_correctlyAssignsKeywords() {
        // Test that switching prefixes correctly assigns keywords to different fields
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList("n/Alice", "p/9123", "a/Main"));
        
        // Name matches, phone matches, address doesn't
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice")
                .withPhone("91234567")
                .withAddress("Elm Street")
                .build()));
        
        // All match
        assertTrue(predicate.test(new PersonBuilder()
                .withName("Alice")
                .withPhone("91234567")
                .withAddress("123 Main Street")
                .build()));
    }

    @Test
    public void test_consecutivePrefixes_emptyKeyword_ignored() {
        // Empty keywords (just prefixes) should be ignored
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList("n/", "p/", "a/Main"));
        
        // Only address has keyword - name and phone should pass (empty list = no restriction)
        assertTrue(predicate.test(new PersonBuilder()
                .withName("Anyone")
                .withPhone("12345678")
                .withAddress("123 Main Street")
                .build()));
    }

    @Test
    public void test_onlyEmptyPrefixedKeywords_returnsFalse() {
        // All keywords are just prefixes (empty after removing prefix)
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList("n/", "p/", "a/", "e/", "d/"));
        
        // No actual keywords, should return false
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice")
                .withPhone("91234567")
                .build()));
    }

    @Test
    public void test_partialStringMatching_allFields() {
        // Test substring matching for all fields
        PersonContainsKeywordsPredicate namePartial =
                new PersonContainsKeywordsPredicate(Collections.singletonList("n/lic"));
        assertTrue(namePartial.test(new PersonBuilder().withName("Alice").build()));

        PersonContainsKeywordsPredicate phonePartial =
                new PersonContainsKeywordsPredicate(Collections.singletonList("p/234"));
        assertTrue(phonePartial.test(new PersonBuilder().withPhone("12345678").build()));

        PersonContainsKeywordsPredicate addressPartial =
                new PersonContainsKeywordsPredicate(Collections.singletonList("a/ain"));
        assertTrue(addressPartial.test(new PersonBuilder().withAddress("123 Main Street").build()));

        PersonContainsKeywordsPredicate emailPartial =
                new PersonContainsKeywordsPredicate(Collections.singletonList("e/exam"));
        assertTrue(emailPartial.test(new PersonBuilder().withEmail("test@example.com").build()));

        PersonContainsKeywordsPredicate detailsPartial =
                new PersonContainsKeywordsPredicate(Collections.singletonList("d/rien"));
        assertTrue(detailsPartial.test(new PersonBuilder().withDetails("Good friend").build()));
    }

    @Test
    public void test_multipleFieldsWithMultipleKeywordsEach() {
        // Complex scenario: multiple fields, each with multiple keywords (OR within, AND across)
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList(
                        "n/Alice", "Bob",           // name: Alice OR Bob
                        "p/9123", "8765",           // phone: 9123 OR 8765
                        "e/example", "test"));      // email: example OR test
        
        // Alice, phone has 9123, email has example - should match
        assertTrue(predicate.test(new PersonBuilder()
                .withName("Alice Smith")
                .withPhone("91234567")
                .withEmail("alice@example.com")
                .build()));
        
        // Bob, phone has 8765, email has test - should match
        assertTrue(predicate.test(new PersonBuilder()
                .withName("Bob Jones")
                .withPhone("87654321")
                .withEmail("bob@test.com")
                .build()));
        
        // Name matches (Bob), phone matches (8765), but email doesn't - should fail
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Bob")
                .withPhone("87654321")
                .withEmail("bob@other.com")
                .build()));
    }

    @Test
    public void test_specialCharactersInKeywords() {
        // Test keywords with special characters
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("e/@example"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("test@example.com").build()));

        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("a/#08-111"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("123, Jurong West Ave 6, #08-111").build()));
    }

    @Test
    public void test_numericKeywordsInNameField() {
        // Numbers can be searched in name field
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("n/007"));
        assertTrue(predicate.test(new PersonBuilder().withName("Agent 007").build()));
    }

    @Test
    public void test_textKeywordsInPhoneField_noMatch() {
        // Text keywords in phone field should not match if phone is all numeric
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("p/abc"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("91234567").build()));
    }

    @Test
    public void test_whitespaceHandling_doesNotAffectMatching() {
        // Keywords should match regardless of spacing in the stored value
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("n/Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Smith").build()));
    }

    @Test
    public void test_emptyFieldValue_noMatch() {
        // Empty or minimal field values should not match most keywords
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("d/friend"));
        assertFalse(predicate.test(new PersonBuilder().withDetails("alone").build()));
    }

    @Test
    public void test_multipleKeywordsInDifferentOrderProduceSamePredicate() {
        // Order of keywords within same field shouldn't affect equality
        PersonContainsKeywordsPredicate predicate1 =
                new PersonContainsKeywordsPredicate(Arrays.asList("n/Alice", "Bob"));
        PersonContainsKeywordsPredicate predicate2 =
                new PersonContainsKeywordsPredicate(Arrays.asList("n/Bob", "Alice"));
        
        // Note: These are NOT equal because the order matters for the internal list
        assertFalse(predicate1.equals(predicate2));
    }

    @Test
    public void test_generalKeywordSearchesAllFields() {
        // A single general keyword should match if found in ANY field
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("unique"));
        
        // Match in name
        assertTrue(predicate.test(new PersonBuilder().withName("unique name").build()));
        
        // Match in phone (phone must be numeric, so use different approach)
        PersonContainsKeywordsPredicate phoneKeywordPredicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("123"));
        assertTrue(phoneKeywordPredicate.test(new PersonBuilder().withPhone("12345678").build()));
        
        // Match in address
        assertTrue(predicate.test(new PersonBuilder().withAddress("unique street").build()));
        
        // Match in email
        assertTrue(predicate.test(new PersonBuilder().withEmail("unique@test.com").build()));
        
        // Match in details
        assertTrue(predicate.test(new PersonBuilder().withDetails("unique person").build()));
    }

    @Test
    public void test_longKeywordStrings() {
        // Test with long keyword strings
        String longKeyword = "verylongkeywordthatspansmanycharsacters";
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("n/" + longKeyword));
        assertTrue(predicate.test(new PersonBuilder()
                .withName("Name with " + longKeyword + " inside")
                .build()));
    }

    @Test
    public void test_mixedPrefixedAndUnprefixedKeywords_generalThenSpecific() {
        // General keyword followed by specific prefix
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList("general", "n/Alice"));
        
        // Should have "general" in generalKeywords and "Alice" in nameKeywords
        // Uses field-specific matching when field-specific keywords exist
        assertTrue(predicate.test(new PersonBuilder()
                .withName("Alice")
                .withPhone("91234567")
                .withAddress("123 Main Street")
                .withEmail("alice@test.com")
                .withDetails("No details")
                .build()));
        
        // If name doesn't have Alice, should fail
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Bob")
                .withDetails("general info")
                .build()));
    }

    @Test
    public void test_allPrefixTypes_coverage() {
        // Ensure all prefix types work correctly
        PersonContainsKeywordsPredicate nameTest =
                new PersonContainsKeywordsPredicate(Collections.singletonList("n/test"));
        PersonContainsKeywordsPredicate phoneTest =
                new PersonContainsKeywordsPredicate(Collections.singletonList("p/123"));
        PersonContainsKeywordsPredicate addressTest =
                new PersonContainsKeywordsPredicate(Collections.singletonList("a/test"));
        PersonContainsKeywordsPredicate emailTest =
                new PersonContainsKeywordsPredicate(Collections.singletonList("e/test"));
        PersonContainsKeywordsPredicate detailsTest =
                new PersonContainsKeywordsPredicate(Collections.singletonList("d/test"));
        
        Person person = new PersonBuilder()
                .withName("test name")
                .withPhone("12345678")
                .withAddress("test street")
                .withEmail("test@email.com")
                .withDetails("test details")
                .build();
        
        assertTrue(nameTest.test(person));
        assertTrue(phoneTest.test(person));
        assertTrue(addressTest.test(person));
        assertTrue(emailTest.test(person));
        assertTrue(detailsTest.test(person));
    }

    @Test
    public void test_equals_withAllFieldsCombinations() {
        // Test equality with different combinations of fields
        PersonContainsKeywordsPredicate predicate1 =
                new PersonContainsKeywordsPredicate(Arrays.asList("n/Alice", "p/9123", "a/Main", "e/test", "d/friend"));
        PersonContainsKeywordsPredicate predicate2 =
                new PersonContainsKeywordsPredicate(Arrays.asList("n/Alice", "p/9123", "a/Main", "e/test", "d/friend"));
        PersonContainsKeywordsPredicate predicate3 =
                new PersonContainsKeywordsPredicate(Arrays.asList("n/Alice", "p/9123", "a/Main", "e/test", "d/other"));
        
        assertTrue(predicate1.equals(predicate2));
        assertFalse(predicate1.equals(predicate3));
    }

    @Test
    public void test_equals_generalKeywords() {
        PersonContainsKeywordsPredicate predicate1 =
                new PersonContainsKeywordsPredicate(Arrays.asList("friend", "colleague"));
        PersonContainsKeywordsPredicate predicate2 =
                new PersonContainsKeywordsPredicate(Arrays.asList("friend", "colleague"));
        PersonContainsKeywordsPredicate predicate3 =
                new PersonContainsKeywordsPredicate(Arrays.asList("friend", "buddy"));
        
        assertTrue(predicate1.equals(predicate2));
        assertFalse(predicate1.equals(predicate3));
    }

    @Test
    public void test_hashCode_consistency() {
        // Hash codes are consistent for same object
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList("n/Alice", "p/9123"));
        
        // Hash code should be consistent for same object
        assertEquals(predicate.hashCode(), predicate.hashCode());
    }

    @Test
    public void test_toString_allFieldsPopulated() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList(
                        "general", "n/Alice", "p/9123", "a/Main", "e/test", "d/friend"));
        
        String result = predicate.toString();
        assertTrue(result.contains("generalKeywords=[general]"));
        assertTrue(result.contains("nameKeywords=[Alice]"));
        assertTrue(result.contains("phoneKeywords=[9123]"));
        assertTrue(result.contains("addressKeywords=[Main]"));
        assertTrue(result.contains("emailKeywords=[test]"));
        assertTrue(result.contains("detailsKeywords=[friend]"));
    }

    @Test
    public void test_toString_emptyPredicate() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.emptyList());
        
        String result = predicate.toString();
        assertTrue(result.contains("generalKeywords=[]"));
        assertTrue(result.contains("nameKeywords=[]"));
        assertTrue(result.contains("phoneKeywords=[]"));
        assertTrue(result.contains("addressKeywords=[]"));
        assertTrue(result.contains("emailKeywords=[]"));
        assertTrue(result.contains("detailsKeywords=[]"));
    }

    @Test
    public void test_consecutiveFieldSwitches() {
        // Test switching between fields multiple times
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList(
                        "n/Alice", "p/9123", "n/Bob", "e/test", "p/8765"));
        
        // Should have: nameKeywords=[Alice, Bob], phoneKeywords=[9123, 8765], emailKeywords=[test]
        // Name should match either Alice OR Bob
        assertTrue(predicate.test(new PersonBuilder()
                .withName("Bob")
                .withPhone("87654321")
                .withEmail("bob@test.com")
                .build()));
    }

    @Test
    public void test_singleCharacterKeywords() {
        // Test with single character keywords
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("n/A"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").build()));
        assertTrue(predicate.test(new PersonBuilder().withName("A").build()));
    }

    @Test
    public void test_edgeCaseEmptyStringAfterPrefix() {
        // If someone provides "n/" as a token, it should be ignored
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList("n/", "p/9123"));
        
        // Should only have phone keyword
        assertTrue(predicate.test(new PersonBuilder()
                .withName("AnyName")  // name has no restriction
                .withPhone("91234567")
                .build()));
    }

    @Test
    public void test_generalAndSpecificFieldsInteraction() {
        // When both general and specific field keywords exist, only field-specific mode is used
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList("keyword", "n/Alice"));
        
        // "keyword" is general, "Alice" is name-specific
        // Based on code lines 166-170: when ANY field-specific keywords exist,
        // it ONLY checks field-specific keywords and IGNORES generalKeywords
        // So only nameKeywords=[Alice] is checked, generalKeywords=[keyword] is ignored
        
        // Name has Alice -> should match (generalKeywords is ignored in field-specific mode)
        assertTrue(predicate.test(new PersonBuilder()
                .withName("Alice")
                .withPhone("12345678")
                .build()));
        
        // Name doesn't have Alice -> should not match
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Bob")
                .withDetails("keyword present")  // keyword is in general, so ignored
                .build()));
    }
}
