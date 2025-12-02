package ec.edu.epn;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class CalculatorTest {
    private Calculator calculator = new Calculator();

    // AAA Method
    @Test
    void add_TwoPositiveNumbers_ReturnsCorrectSum() {
        // Arrange is the setup
        int a = 2;
        int b = 3;

        // Act is the execution
        int result = calculator.add(a, b);

        // Assert is the verification
        assertEquals(5, result);
    }

    @Test
    void subtract_TwoNumbers_ReturnsCorrectDifference() {
        // Arrange is the setup
        int a = 3;
        int b = 2;

        // Act is the execution
        int result = calculator.subtract(a, b);

        // Assert is the verification
        assertEquals(1, result);
    }

    @Test
    void multiply_TwoNumbers_ReturnsCorrectProduct() {
        // Arrange is the setup
        int a = 2;
        int b = 3;

        // Act is the execution
        int result = calculator.multiply(a, b);

        // Assert is the verification
        assertEquals(6, result);
    }

    @Test
    void divide_TwoNumbers_ReturnsCorrectQuotient() {
        // Arrange is the setup
        int a = 4;
        int b = 2;

        // Act is the execution
        double result = calculator.divide(a, b);

        // Assert is the verification
        assertAll(
                () -> assertTrue(result > 0),
                () -> assertEquals(2.0, result, 0.0001));
    }

    @org.junit.jupiter.api.Disabled("Demonstration of disable/enable - remove this annotation to enable the test")
    @org.junit.jupiter.api.Test
    void divide_DisabledDemonstration_RemoveDisabledToRun() {
        // Arrange
        int a = 10;
        int b = 5;

        // Act
        double result = calculator.divide(a, b);

        // Assert
        assertEquals(2.0, result, 0.0001);
    }

    @Test
    void isEven_EvenNumber_ReturnsTrue() {
        // Arrange is the setup
        int number = 4;

        // Act is the execution
        boolean result = calculator.isEven(number);

        // Assert is the verification
        assertTrue(result);
    }

    @Test
    void isEven_OddNumber_ReturnsFalse() {
        // Arrange is the setup
        int number = 3;

        // Act is the execution
        boolean result = calculator.isEven(number);

        // Assert is the verification
        assertFalse(result);
    }

    @org.junit.jupiter.api.AfterAll
    static void tearDownAfterClass() {
        System.out.println("All tests finished");
    }
}