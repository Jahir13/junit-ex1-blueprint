package ec.edu.epn;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de pruebas unitarias para EmailValidator.
 *
 * Esta clase demuestra el uso avanzado de pruebas parametrizadas en JUnit 5,
 * incluyendo anotaciones adicionales útiles para validación de strings.
 *
 * ANOTACIONES ADICIONALES DEMOSTRADAS:
 *
 * @NullSource - Proporciona un valor null como parámetro.
 *               Útil para probar el manejo de valores nulos.
 *
 * @EmptySource - Proporciona un string vacío ("") como parámetro.
 *                Útil para probar validaciones de strings vacíos.
 *
 * @NullAndEmptySource - Combina @NullSource y @EmptySource.
 *                       Proporciona null y "" como parámetros.
 *
 * @Nested - Permite agrupar tests relacionados en clases internas.
 *           Mejora la organización y legibilidad de los reportes.
 *
 * @author Laboratorio de Pruebas Unitarias - JUnit 5
 */
class EmailValidatorTest {

    // Instancia de la clase bajo prueba (SUT - System Under Test)
    private EmailValidator emailValidator;

    /**
     * Configuración inicial que se ejecuta antes de CADA test.
     *
     * @BeforeEach garantiza aislamiento entre tests:
     * - Cada test obtiene una instancia nueva y limpia
     * - No hay estado compartido que pueda causar efectos secundarios
     * - Sigue el principio FIRST (Fast, Isolated, Repeatable, Self-validating, Timely)
     */
    @BeforeEach
    void setUp() {
        emailValidator = new EmailValidator();
    }

    // ==========================================
    // TESTS SIMPLES BÁSICOS
    // ==========================================

    /**
     * Test simple para verificar un email válido.
     */
    @Test
    @DisplayName("Debería retornar true para un email válido básico")
    void shouldReturnTrueForValidEmail() {
        // Arrange
        String validEmail = "usuario@ejemplo.com";

        // Act
        boolean result = emailValidator.isValidEmail(validEmail);

        // Assert
        assertTrue(result, "El email 'usuario@ejemplo.com' debería ser válido");
    }

    /**
     * Test simple para verificar que null retorna false.
     */
    @Test
    @DisplayName("Debería retornar false para email null")
    void shouldReturnFalseForNullEmail() {
        // Act
        boolean result = emailValidator.isValidEmail(null);

        // Assert
        assertFalse(result, "Un email null debería ser inválido");
    }

    // ==========================================
    // TESTS CON @ValueSource PARA STRINGS
    // ==========================================

    /**
     * @ValueSource con strings para probar emails válidos.
     *
     * Cada string del array se pasa como parámetro y debe retornar true.
     *
     * Uso: Verificar múltiples casos válidos con la misma aserción.
     */
    @ParameterizedTest(name = "Email válido: \"{0}\"")
    @ValueSource(strings = {
        "usuario@dominio.com",
        "nombre.apellido@empresa.org",
        "test@test.co",
        "a@b.c",
        "usuario123@dominio456.net",
        "mi.email+filtro@gmail.com",
        "MAYUSCULAS@DOMINIO.COM"
    })
    @DisplayName("Debería aceptar emails con formato válido")
    void shouldAcceptValidEmailFormats(String validEmail) {
        assertTrue(emailValidator.isValidEmail(validEmail),
            "El email '" + validEmail + "' debería ser válido");
    }

    /**
     * @ValueSource con strings vacíos y espacios en blanco.
     *
     * Estos casos deberían retornar false ya que violan
     * la regla "no debe estar vacío".
     */
    @ParameterizedTest(name = "String vacío/espacios: \"{0}\" debería ser inválido")
    @ValueSource(strings = {
        "",           // String vacío
        " ",          // Un espacio
        "  ",         // Múltiples espacios
        "\t",         // Tabulador
        "\n",         // Nueva línea
        "   \t\n  "   // Combinación de espacios en blanco
    })
    @DisplayName("Debería rechazar strings vacíos o solo con espacios en blanco")
    void shouldRejectEmptyOrWhitespaceStrings(String emptyOrWhitespace) {
        assertFalse(emailValidator.isValidEmail(emptyOrWhitespace),
            "Un string vacío o con espacios debería ser inválido");
    }

    /**
     * @ValueSource con emails que no contienen '@'.
     */
    @ParameterizedTest(name = "Email sin @: \"{0}\"")
    @ValueSource(strings = {
        "usuariodominio.com",
        "nombreapellido",
        "test.test.com",
        "correo_sin_arroba"
    })
    @DisplayName("Debería rechazar emails sin el símbolo @")
    void shouldRejectEmailsWithoutAtSymbol(String emailWithoutAt) {
        assertFalse(emailValidator.isValidEmail(emailWithoutAt),
            "Email sin '@' debería ser inválido: " + emailWithoutAt);
    }

    /**
     * @ValueSource con emails que no contienen '.'.
     */
    @ParameterizedTest(name = "Email sin punto: \"{0}\"")
    @ValueSource(strings = {
        "usuario@dominio",
        "nombre@empresa",
        "test@localhost"
    })
    @DisplayName("Debería rechazar emails sin punto")
    void shouldRejectEmailsWithoutDot(String emailWithoutDot) {
        assertFalse(emailValidator.isValidEmail(emailWithoutDot),
            "Email sin '.' debería ser inválido: " + emailWithoutDot);
    }

    // ==========================================
    // TESTS CON @NullSource, @EmptySource
    // ==========================================

    /**
     * @NullSource proporciona null como único valor de prueba.
     *
     * Es más expresivo que crear un @Test separado para null,
     * y se puede combinar con otras fuentes de datos.
     */
    @ParameterizedTest(name = "Email null debería ser inválido")
    @NullSource
    @DisplayName("Debería rechazar email null usando @NullSource")
    void shouldRejectNullEmailWithNullSource(String nullEmail) {
        assertFalse(emailValidator.isValidEmail(nullEmail),
            "Un email null debería ser inválido");
    }

    /**
     * @EmptySource proporciona un string vacío ("") como valor.
     */
    @ParameterizedTest(name = "Email vacío debería ser inválido")
    @EmptySource
    @DisplayName("Debería rechazar email vacío usando @EmptySource")
    void shouldRejectEmptyEmailWithEmptySource(String emptyEmail) {
        assertFalse(emailValidator.isValidEmail(emptyEmail),
            "Un email vacío debería ser inválido");
    }

    /**
     * @NullAndEmptySource combina @NullSource y @EmptySource.
     *
     * Ejecuta el test dos veces: una con null y otra con "".
     * Muy útil para validaciones de entrada.
     */
    @ParameterizedTest(name = "Email null/vacío: \"{0}\" debería ser inválido")
    @NullAndEmptySource
    @DisplayName("Debería rechazar emails null y vacíos con @NullAndEmptySource")
    void shouldRejectNullAndEmptyEmails(String nullOrEmptyEmail) {
        assertFalse(emailValidator.isValidEmail(nullOrEmptyEmail));
    }

    /**
     * Combinación de @NullAndEmptySource con @ValueSource.
     *
     * JUnit 5 permite combinar múltiples fuentes de datos.
     * El test se ejecutará con: null, "", y cada valor del @ValueSource.
     */
    @ParameterizedTest(name = "Entrada inválida: \"{0}\"")
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\t", "\n"})
    @DisplayName("Debería rechazar null, vacío y espacios en blanco combinados")
    void shouldRejectAllInvalidInputsCombined(String invalidInput) {
        assertFalse(emailValidator.isValidEmail(invalidInput));
    }

    // ==========================================
    // TESTS CON @CsvSource
    // ==========================================

    /**
     * @CsvSource para probar múltiples emails con su resultado esperado.
     *
     * Formato: "email, resultadoEsperado"
     *
     * Nota: Para representar strings vacíos en CSV usar comillas simples: ''
     */
    @ParameterizedTest(name = "\"{0}\" → válido={1}")
    @CsvSource({
        // Emails válidos
        "usuario@dominio.com, true",
        "test@test.org, true",
        "a@b.co, true",
        "nombre.apellido@empresa.com.ec, true",

        // Emails inválidos - sin @
        "usuariodominio.com, false",
        "solotexto, false",

        // Emails inválidos - sin .
        "usuario@dominio, false",
        "test@localhost, false",

        // Emails inválidos - vacíos (usar '' para string vacío en CSV)
        "'', false"
    })
    @DisplayName("Debería validar correctamente diversos formatos de email")
    void shouldValidateVariousEmailFormats(String email, boolean expectedResult) {
        boolean actualResult = emailValidator.isValidEmail(email);
        assertEquals(expectedResult, actualResult,
            String.format("Para email '%s', esperado: %s, obtenido: %s",
                email, expectedResult, actualResult));
    }

    /**
     * @CsvSource con delimitador personalizado.
     *
     * delimiterString permite usar un delimitador diferente a la coma,
     * útil cuando los datos contienen comas.
     */
    @ParameterizedTest(name = "Email: {0} | Contiene @: {1} | Contiene .: {2} | Válido: {3}")
    @CsvSource(delimiterString = "|", value = {
        "usuario@dominio.com | true  | true  | true",
        "usuariodominio.com  | false | true  | false",
        "usuario@dominio     | true  | false | false",
        "solotexto           | false | false | false"
    })
    @DisplayName("Debería verificar componentes individuales del email")
    void shouldVerifyEmailComponents(
            String email,
            boolean hasAt,
            boolean hasDot,
            boolean isValid) {

        assertEquals(hasAt, emailValidator.containsAtSymbol(email),
            "Verificación de '@' falló para: " + email);
        assertEquals(hasDot, emailValidator.containsDot(email),
            "Verificación de '.' falló para: " + email);
        assertEquals(isValid, emailValidator.isValidEmail(email),
            "Validación completa falló para: " + email);
    }

    // ==========================================
    // TESTS CON @MethodSource
    // ==========================================

    /**
     * @MethodSource para escenarios complejos de validación de email.
     *
     * Permite incluir descripciones detalladas de cada caso de prueba
     * y organizar mejor los datos de prueba.
     */
    @ParameterizedTest(name = "{2}")
    @MethodSource("provideEmailValidationScenarios")
    @DisplayName("Debería manejar escenarios complejos de validación de email")
    void shouldHandleComplexEmailScenarios(
            String email,
            boolean expectedValidity,
            String scenarioDescription) {

        boolean actualValidity = emailValidator.isValidEmail(email);

        assertEquals(expectedValidity, actualValidity,
            "Fallo en escenario: " + scenarioDescription +
            " | Email: '" + email + "'" +
            " | Esperado: " + expectedValidity +
            " | Obtenido: " + actualValidity);
    }

    /**
     * Método estático que provee escenarios de validación de email.
     *
     * Organiza los casos de prueba por categorías para mejor mantenibilidad.
     * Incluye descripciones claras de qué se está probando.
     */
    static Stream<org.junit.jupiter.params.provider.Arguments> provideEmailValidationScenarios() {
        return Stream.of(
            // ---- CASOS VÁLIDOS ----
            org.junit.jupiter.params.provider.Arguments.of(
                "usuario@gmail.com", true,
                "Email corporativo estándar de Gmail"
            ),
            org.junit.jupiter.params.provider.Arguments.of(
                "nombre.apellido@empresa.com.ec", true,
                "Email con subdominio de país (.com.ec)"
            ),
            org.junit.jupiter.params.provider.Arguments.of(
                "user+tag@example.com", true,
                "Email con etiqueta (+tag) para filtros"
            ),
            org.junit.jupiter.params.provider.Arguments.of(
                "a@b.co", true,
                "Email mínimo válido"
            ),
            org.junit.jupiter.params.provider.Arguments.of(
                "123@456.789", true,
                "Email solo con números"
            ),

            // ---- CASOS INVÁLIDOS POR FALTA DE @ ----
            org.junit.jupiter.params.provider.Arguments.of(
                "usuariodominio.com", false,
                "Email sin símbolo @ - error común de escritura"
            ),
            org.junit.jupiter.params.provider.Arguments.of(
                "nombre.apellido.empresa.com", false,
                "Email con puntos pero sin @"
            ),

            // ---- CASOS INVÁLIDOS POR FALTA DE . ----
            org.junit.jupiter.params.provider.Arguments.of(
                "usuario@localhost", false,
                "Email sin punto en dominio"
            ),
            org.junit.jupiter.params.provider.Arguments.of(
                "test@dominio", false,
                "Email con dominio incompleto"
            ),

            // ---- CASOS INVÁLIDOS POR NULL/VACÍO ----
            org.junit.jupiter.params.provider.Arguments.of(
                null, false,
                "Email null - entrada nula"
            ),
            org.junit.jupiter.params.provider.Arguments.of(
                "", false,
                "Email vacío - string vacío"
            ),
            org.junit.jupiter.params.provider.Arguments.of(
                "   ", false,
                "Email solo espacios en blanco"
            ),

            // ---- CASOS EDGE (LÍMITE) ----
            org.junit.jupiter.params.provider.Arguments.of(
                "@.", true,
                "Email mínimo técnicamente válido (solo @ y .)"
            ),
            org.junit.jupiter.params.provider.Arguments.of(
                ".@", true,
                "Email con punto antes de @"
            )
        );
    }

    /**
     * @MethodSource para probar los métodos auxiliares de validación.
     */
    @ParameterizedTest(name = "isNotEmpty(\"{0}\") = {1}")
    @MethodSource("provideNotEmptyTestCases")
    @DisplayName("Debería verificar correctamente si el email no está vacío")
    void shouldVerifyNotEmpty(String email, boolean expected) {
        assertEquals(expected, emailValidator.isNotEmpty(email));
    }

    static Stream<org.junit.jupiter.params.provider.Arguments> provideNotEmptyTestCases() {
        return Stream.of(
            org.junit.jupiter.params.provider.Arguments.of("texto", true),
            org.junit.jupiter.params.provider.Arguments.of("a", true),
            org.junit.jupiter.params.provider.Arguments.of(" texto ", true),
            org.junit.jupiter.params.provider.Arguments.of("", false),
            org.junit.jupiter.params.provider.Arguments.of("   ", false),
            org.junit.jupiter.params.provider.Arguments.of(null, false)
        );
    }

    // ==========================================
    // TESTS AGRUPADOS CON @Nested
    // ==========================================

    /**
     * @Nested permite crear clases internas para agrupar tests relacionados.
     *
     * Ventajas:
     * - Mejor organización de tests por funcionalidad
     * - Reportes más legibles y estructurados
     * - Permite tener @BeforeEach específicos por grupo
     * - Facilita el mantenimiento de tests grandes
     */
    @Nested
    @DisplayName("Tests para el método containsAtSymbol()")
    class ContainsAtSymbolTests {

        @ParameterizedTest(name = "\"{0}\" contiene @: {1}")
        @CsvSource({
            "usuario@dominio.com, true",
            "@inicio, true",
            "final@, true",
            "solo@, true",
            "sinArroba, false",
            "'', false"
        })
        @DisplayName("Debería detectar correctamente la presencia de @")
        void shouldDetectAtSymbol(String email, boolean expected) {
            assertEquals(expected, emailValidator.containsAtSymbol(email));
        }

        @Test
        @DisplayName("Debería retornar false para null")
        void shouldReturnFalseForNull() {
            assertFalse(emailValidator.containsAtSymbol(null));
        }
    }

    @Nested
    @DisplayName("Tests para el método containsDot()")
    class ContainsDotTests {

        @ParameterizedTest(name = "\"{0}\" contiene .: {1}")
        @CsvSource({
            "usuario.apellido, true",
            ".inicio, true",
            "final., true",
            "sin punto, false",
            "'', false"
        })
        @DisplayName("Debería detectar correctamente la presencia de punto")
        void shouldDetectDot(String email, boolean expected) {
            assertEquals(expected, emailValidator.containsDot(email));
        }

        @Test
        @DisplayName("Debería retornar false para null")
        void shouldReturnFalseForNull() {
            assertFalse(emailValidator.containsDot(null));
        }
    }

    @Nested
    @DisplayName("Tests para el método isNotNull()")
    class IsNotNullTests {

        @Test
        @DisplayName("Debería retornar true para string no null")
        void shouldReturnTrueForNonNull() {
            assertTrue(emailValidator.isNotNull("cualquier texto"));
            assertTrue(emailValidator.isNotNull(""));
            assertTrue(emailValidator.isNotNull("   "));
        }

        @Test
        @DisplayName("Debería retornar false para null")
        void shouldReturnFalseForNull() {
            assertFalse(emailValidator.isNotNull(null));
        }
    }
}
