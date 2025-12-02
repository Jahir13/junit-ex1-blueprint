package ec.edu.epn;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Simple test class for StringValidator.
 * Contains only two basic tests as requested.
 */
class StringValidatorTest {

    private StringValidator stringValidator;

    @BeforeEach
    void setUp() {
        stringValidator = new StringValidator();
    }

    @Test
    void testValidateNotEmpty() {
        // Test valid input
        assertDoesNotThrow(() -> stringValidator.validateNotEmpty("hello"));

        // Test invalid inputs
        assertThrows(IllegalArgumentException.class, () -> stringValidator.validateNotEmpty(null));
        assertThrows(IllegalArgumentException.class, () -> stringValidator.validateNotEmpty(""));
        assertThrows(IllegalArgumentException.class, () -> stringValidator.validateNotEmpty("   "));
    }

    @TestFactory
    Collection<DynamicTest> dynamicPalindromeTests() {

        List<TestData> testDataList = Arrays.asList(
            new TestData("reconocer", true),
            new TestData("Ana", true),
            new TestData("Hola", false)
        );

        return testDataList.stream()
            .map(data -> DynamicTest.dynamicTest("isPalindrome('" + data.input + "') == " + data.expected, () -> {
                boolean result = stringValidator.isPalindrome(data.input);
                assertEquals(data.expected, result);
            }))
            .collect(Collectors.toList());
    }

    static class TestData {
        String input;
        boolean expected;

        TestData(String input, boolean expected) {
            this.input = input;
            this.expected = expected;
        }
    }

}
