package ec.edu.epn;

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
        assertEquals(2.0, result);
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
}