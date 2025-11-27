package ec.edu.epn;

/**
 * Clase responsable de calcular impuestos sobre montos.
 *
 * Principios SOLID aplicados:
 * - Single Responsibility: Solo se encarga de cálculos de impuestos.
 * - Open/Closed: Abierta para extensión (se pueden agregar más métodos de cálculo).
 *
 * @author Laboratorio de Pruebas Unitarias
 */
public class TaxCalculator {

    /**
     * Calcula el total de un monto aplicando un porcentaje de impuesto.
     *
     * Fórmula: total = monto + (monto × tax / 100)
     *
     * @param amount  El monto base sobre el cual calcular el impuesto.
     *                Debe ser mayor o igual a cero.
     * @param taxRate El porcentaje de impuesto a aplicar.
     *                Debe ser mayor o igual a cero.
     * @return El total del monto con el impuesto incluido.
     * @throws IllegalArgumentException Si el monto o la tasa de impuesto son negativos.
     */
    public double calculateTotalWithTax(double amount, double taxRate) {
        // Validación de entrada: Clean Code - Fail Fast principle
        if (amount < 0) {
            throw new IllegalArgumentException("El monto no puede ser negativo.");
        }
        if (taxRate < 0) {
            throw new IllegalArgumentException("La tasa de impuesto no puede ser negativa.");
        }

        // Cálculo del impuesto: monto + (monto * tax / 100)
        double taxAmount = amount * (taxRate / 100);
        return amount + taxAmount;
    }

    /**
     * Calcula únicamente el valor del impuesto sin sumarlo al monto original.
     *
     * @param amount  El monto base.
     * @param taxRate El porcentaje de impuesto.
     * @return El valor del impuesto calculado.
     * @throws IllegalArgumentException Si los parámetros son negativos.
     */
    public double calculateTaxAmount(double amount, double taxRate) {
        if (amount < 0) {
            throw new IllegalArgumentException("El monto no puede ser negativo.");
        }
        if (taxRate < 0) {
            throw new IllegalArgumentException("La tasa de impuesto no puede ser negativa.");
        }

        return amount * (taxRate / 100);
    }
}
