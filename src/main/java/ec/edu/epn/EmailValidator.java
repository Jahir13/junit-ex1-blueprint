package ec.edu.epn;

/**
 * Clase responsable de validar direcciones de correo electrónico.
 *
 * Principios SOLID aplicados:
 * - Single Responsibility: Solo se encarga de validar emails.
 * - Interface Segregation: Métodos pequeños y específicos.
 *
 * Clean Code aplicado:
 * - Nombres descriptivos de métodos.
 * - Métodos cortos con una sola responsabilidad.
 * - Validaciones claras y ordenadas.
 *
 * @author Laboratorio de Pruebas Unitarias
 */
public class EmailValidator {

    /**
     * Valida si un string cumple con los requisitos básicos de un email.
     *
     * Un email válido debe cumplir:
     * 1. No ser null
     * 2. No estar vacío (ni solo espacios en blanco)
     * 3. Contener el carácter '@'
     * 4. Contener el carácter '.'
     *
     * @param email El string a validar como email.
     * @return true si el email es válido, false en caso contrario.
     */
    public boolean isValidEmail(String email) {
        // Validación 1: No debe ser null
        if (email == null) {
            return false;
        }

        // Validación 2: No debe estar vacío ni contener solo espacios
        if (email.trim().isEmpty()) {
            return false;
        }

        // Validación 3: Debe contener '@'
        if (!email.contains("@")) {
            return false;
        }

        // Validación 4: Debe contener '.'
        if (!email.contains(".")) {
            return false;
        }

        return true;
    }

    /**
     * Verifica si el email no es null.
     * Método auxiliar que sigue el principio de Single Responsibility.
     *
     * @param email El string a verificar.
     * @return true si no es null, false si es null.
     */
    public boolean isNotNull(String email) {
        return email != null;
    }

    /**
     * Verifica si el email no está vacío (excluyendo espacios en blanco).
     *
     * @param email El string a verificar.
     * @return true si no está vacío, false si está vacío o es null.
     */
    public boolean isNotEmpty(String email) {
        return email != null && !email.trim().isEmpty();
    }

    /**
     * Verifica si el email contiene el carácter '@'.
     *
     * @param email El string a verificar.
     * @return true si contiene '@', false en caso contrario o si es null.
     */
    public boolean containsAtSymbol(String email) {
        return email != null && email.contains("@");
    }

    /**
     * Verifica si el email contiene el carácter '.'.
     *
     * @param email El string a verificar.
     * @return true si contiene '.', false en caso contrario o si es null.
     */
    public boolean containsDot(String email) {
        return email != null && email.contains(".");
    }
}
