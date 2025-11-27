package ec.edu.epn;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de pruebas unitarias para TaxCalculator.
 *
 * Esta clase demuestra el uso de diferentes tipos de pruebas parametrizadas en JUnit 5:
 *
 * ANOTACIONES UTILIZADAS:
 *
 * @BeforeEach - Se ejecuta ANTES de cada método de test. Útil para inicializar
 *               objetos que se usarán en las pruebas (patrón Arrange).
 *
 * @Test - Marca un método como caso de prueba simple (no parametrizado).
 *
 * @ParameterizedTest - Indica que el test se ejecutará múltiples veces con
 *                      diferentes parámetros. Reemplaza múltiples @Test repetitivos.
 *
 * @ValueSource - Proporciona un array simple de valores (ints, doubles, strings, etc.)
 *                Cada valor se pasa como parámetro al test.
 *
 * @CsvSource - Proporciona datos en formato CSV. Cada línea es un caso de prueba
 *              donde los valores separados por coma se mapean a los parámetros del método.
 *              Ideal para casos input/output esperado.
 *
 * @MethodSource - Referencia a un método estático que retorna un Stream<Arguments>.
 *                 Permite crear casos de prueba complejos con objetos, lógica condicional, etc.
 *
 * @DisplayName - Proporciona un nombre legible para el test en los reportes.
 *
 * @author Laboratorio de Pruebas Unitarias - JUnit 5
 */
class TaxCalculatorTest {

    // Instancia de la clase bajo prueba (SUT - System Under Test)
    private TaxCalculator taxCalculator;

    /**
     * Método de configuración que se ejecuta antes de CADA test.
     *
     * @BeforeEach asegura que cada test tenga una instancia fresca del objeto,
     * evitando efectos secundarios entre tests (principio de aislamiento).
     *
     * Esto es parte del patrón AAA (Arrange-Act-Assert):
     * - Arrange: Preparar los objetos necesarios
     */
    @BeforeEach
    void setUp() {
        // Crear una nueva instancia antes de cada test
        taxCalculator = new TaxCalculator();
    }

    // ==========================================
    // TESTS SIMPLES CON @Test
    // ==========================================

    /**
     * Test simple para verificar el cálculo básico de impuestos.
     *
     * Ejemplo: $100 con 10% de impuesto = $110
     */
    @Test
    @DisplayName("Debería calcular correctamente el total con impuesto básico")
    void shouldCalculateTotalWithBasicTax() {
        // Arrange - Los datos de entrada
        double amount = 100.0;
        double taxRate = 10.0;
        double expectedTotal = 110.0;

        // Act - Ejecutar el método bajo prueba
        double actualTotal = taxCalculator.calculateTotalWithTax(amount, taxRate);

        // Assert - Verificar el resultado
        assertEquals(expectedTotal, actualTotal, 0.001,
            "El total con impuesto debería ser 110.0");
    }

    /**
     * Test para verificar que se lanza excepción con monto negativo.
     */
    @Test
    @DisplayName("Debería lanzar excepción cuando el monto es negativo")
    void shouldThrowExceptionForNegativeAmount() {
        // Arrange
        double negativeAmount = -100.0;
        double taxRate = 10.0;

        // Act & Assert - assertThrows verifica que se lance la excepción esperada
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> taxCalculator.calculateTotalWithTax(negativeAmount, taxRate),
            "Debería lanzar IllegalArgumentException para montos negativos"
        );

        // Verificar el mensaje de la excepción
        assertTrue(exception.getMessage().contains("negativo"));
    }

    // ==========================================
    // TESTS PARAMETRIZADOS CON @ValueSource
    // ==========================================

    /**
     * @ValueSource proporciona un array de valores simples.
     *
     * Cada valor del array se pasa como parámetro al método de test,
     * ejecutando el test una vez por cada valor.
     *
     * Uso ideal: Validar múltiples valores de entrada del mismo tipo
     * que deberían producir el mismo comportamiento.
     *
     * En este caso: Verificamos que diferentes tasas de impuesto negativas
     * lancen la misma excepción.
     */
    @ParameterizedTest(name = "Tasa de impuesto negativa: {0}% debería lanzar excepción")
    @ValueSource(doubles = {-1.0, -5.0, -10.0, -100.0, -0.01})
    @DisplayName("Debería lanzar excepción para tasas de impuesto negativas")
    void shouldThrowExceptionForNegativeTaxRates(double negativeTaxRate) {
        // Arrange
        double validAmount = 100.0;

        // Act & Assert
        assertThrows(
            IllegalArgumentException.class,
            () -> taxCalculator.calculateTotalWithTax(validAmount, negativeTaxRate),
            "Debería lanzar excepción para tasa negativa: " + negativeTaxRate
        );
    }

    /**
     * Otro ejemplo de @ValueSource con valores que deberían ser válidos.
     *
     * Verificamos que montos de cero y positivos NO lancen excepción.
     */
    @ParameterizedTest(name = "Monto válido: ${0} no debería lanzar excepción")
    @ValueSource(doubles = {0.0, 1.0, 100.0, 1000.0, 999999.99})
    @DisplayName("Debería aceptar montos válidos (cero y positivos)")
    void shouldAcceptValidAmounts(double validAmount) {
        // Arrange
        double taxRate = 10.0;

        // Act & Assert - assertDoesNotThrow verifica que NO se lance excepción
        assertDoesNotThrow(
            () -> taxCalculator.calculateTotalWithTax(validAmount, taxRate),
            "No debería lanzar excepción para monto: " + validAmount
        );
    }

    // ==========================================
    // TESTS PARAMETRIZADOS CON @CsvSource
    // ==========================================

    /**
     * @CsvSource permite definir múltiples parámetros en formato CSV.
     *
     * Cada línea del array es un caso de prueba donde:
     * - Los valores están separados por comas
     * - Se mapean en orden a los parámetros del método
     *
     * Formato: "monto, tasaImpuesto, totalEsperado"
     *
     * Uso ideal: Tests con múltiples inputs y un output esperado,
     * típico de pruebas de "tabla de verdad".
     */
    @ParameterizedTest(name = "${0} con {1}% de impuesto = ${2}")
    @CsvSource({
        // monto, tasaImpuesto, totalEsperado
        "100.0,  10.0,   110.0",    // Caso básico: 10%
        "100.0,  0.0,    100.0",    // Sin impuesto
        "200.0,  15.0,   230.0",    // 15% de impuesto
        "50.0,   20.0,   60.0",     // 20% de impuesto
        "1000.0, 12.0,   1120.0",   // Monto grande
        "0.0,    10.0,   0.0",      // Monto cero
        "99.99,  10.0,   109.989",  // Decimales precisos
        "100.0,  100.0,  200.0",    // 100% de impuesto (duplica el monto)
        "100.0,  50.0,   150.0"     // 50% de impuesto
    })
    @DisplayName("Debería calcular correctamente el total con diferentes montos e impuestos")
    void shouldCalculateTotalWithVariousTaxRates(double amount, double taxRate, double expectedTotal) {
        // Act
        double actualTotal = taxCalculator.calculateTotalWithTax(amount, taxRate);

        // Assert - Usamos delta de 0.001 para comparaciones de punto flotante
        assertEquals(expectedTotal, actualTotal, 0.001,
            String.format("Para monto=%.2f y tasa=%.2f%%, el total debería ser %.3f",
                amount, taxRate, expectedTotal));
    }

    /**
     * @CsvSource con casos edge (límite).
     *
     * Es importante probar los casos límite para asegurar robustez.
     */
    @ParameterizedTest(name = "Caso límite: monto={0}, tasa={1}% → total={2}")
    @CsvSource({
        "0.0,    0.0,    0.0",       // Ambos cero
        "0.01,   1.0,    0.0101",    // Valores muy pequeños
        "999999.99, 0.01, 1000099.989999" // Monto grande con tasa pequeña
    })
    @DisplayName("Debería manejar correctamente casos límite")
    void shouldHandleEdgeCases(double amount, double taxRate, double expectedTotal) {
        double actualTotal = taxCalculator.calculateTotalWithTax(amount, taxRate);
        assertEquals(expectedTotal, actualTotal, 0.0001);
    }

    // ==========================================
    // TESTS PARAMETRIZADOS CON @MethodSource
    // ==========================================

    /**
     * @MethodSource referencia un método estático que provee los argumentos.
     *
     * El método fuente debe:
     * - Ser static
     * - Retornar Stream<Arguments>, Collection, Iterable, o array
     * - Tener el mismo nombre que el parámetro de @MethodSource (o del test si no se especifica)
     *
     * Uso ideal:
     * - Casos de prueba complejos
     * - Datos que requieren lógica de construcción
     * - Reutilización de datos entre múltiples tests
     * - Datos generados dinámicamente
     */
    @ParameterizedTest(name = "Escenario: {3}")
    @MethodSource("provideComplexTaxScenarios")
    @DisplayName("Debería calcular impuestos en escenarios complejos de negocio")
    void shouldHandleComplexBusinessScenarios(
            double amount,
            double taxRate,
            double expectedTotal,
            String scenarioDescription) {

        // Act
        double actualTotal = taxCalculator.calculateTotalWithTax(amount, taxRate);

        // Assert
        assertEquals(expectedTotal, actualTotal, 0.001,
            "Fallo en escenario: " + scenarioDescription);
    }

    /**
     * Método estático que provee datos para @MethodSource.
     *
     * Retorna un Stream<Arguments> donde cada Arguments.of() contiene
     * los valores que se mapearán a los parámetros del test.
     *
     * Ventajas de @MethodSource:
     * 1. Permite lógica compleja para generar datos
     * 2. Puede incluir descripciones legibles
     * 3. Permite crear objetos complejos como parámetros
     * 4. Facilita la reutilización de datos
     */
    static Stream<org.junit.jupiter.params.provider.Arguments> provideComplexTaxScenarios() {
        return Stream.of(
            // Arguments.of(monto, tasaImpuesto, totalEsperado, descripción)

            // Escenarios de comercio electrónico
            org.junit.jupiter.params.provider.Arguments.of(
                29.99, 12.0, 33.5888,
                "Producto económico con IVA estándar del 12%"
            ),
            org.junit.jupiter.params.provider.Arguments.of(
                1499.99, 12.0, 1679.9888,
                "Electrónico de gama alta con IVA del 12%"
            ),

            // Escenarios de impuestos especiales
            org.junit.jupiter.params.provider.Arguments.of(
                500.0, 0.0, 500.0,
                "Producto exento de impuestos (canasta básica)"
            ),
            org.junit.jupiter.params.provider.Arguments.of(
                100.0, 35.0, 135.0,
                "Producto de lujo con impuesto especial del 35%"
            ),

            // Escenarios de facturación empresarial
            org.junit.jupiter.params.provider.Arguments.of(
                10000.0, 12.0, 11200.0,
                "Factura corporativa con IVA"
            ),
            org.junit.jupiter.params.provider.Arguments.of(
                50000.0, 15.0, 57500.0,
                "Contrato de servicios profesionales con retención"
            ),

            // Escenarios internacionales
            org.junit.jupiter.params.provider.Arguments.of(
                100.0, 21.0, 121.0,
                "IVA estándar de España (21%)"
            ),
            org.junit.jupiter.params.provider.Arguments.of(
                100.0, 19.0, 119.0,
                "IVA estándar de Alemania (19%)"
            ),
            org.junit.jupiter.params.provider.Arguments.of(
                100.0, 25.0, 125.0,
                "IVA estándar de Suecia (25%)"
            )
        );
    }

    /**
     * Otro ejemplo de @MethodSource para probar el método calculateTaxAmount.
     */
    @ParameterizedTest(name = "Impuesto de ${0} al {1}% = ${2}")
    @MethodSource("provideTaxAmountScenarios")
    @DisplayName("Debería calcular correctamente solo el monto del impuesto")
    void shouldCalculateTaxAmountOnly(double amount, double taxRate, double expectedTax) {
        double actualTax = taxCalculator.calculateTaxAmount(amount, taxRate);
        assertEquals(expectedTax, actualTax, 0.001);
    }

    /**
     * Método fuente para casos de cálculo de monto de impuesto.
     */
    static Stream<org.junit.jupiter.params.provider.Arguments> provideTaxAmountScenarios() {
        return Stream.of(
            org.junit.jupiter.params.provider.Arguments.of(100.0, 10.0, 10.0),
            org.junit.jupiter.params.provider.Arguments.of(200.0, 15.0, 30.0),
            org.junit.jupiter.params.provider.Arguments.of(1000.0, 12.0, 120.0),
            org.junit.jupiter.params.provider.Arguments.of(0.0, 10.0, 0.0),
            org.junit.jupiter.params.provider.Arguments.of(100.0, 0.0, 0.0)
        );
    }
}
