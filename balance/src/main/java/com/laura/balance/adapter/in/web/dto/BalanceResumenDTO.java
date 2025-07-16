package com.laura.balance.adapter.in.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO de respuesta que resume los totales y coherencia del balance.
 *
 * <p>Se utiliza cuando no se requiere el detalle completo de los valores,
 * sino solo los totales y la coherencia del balance.</p>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BalanceResumenDTO {

    /** Total del Activo (suma de puntuaciones). */
    private int totalActivo;

    /** Total del Pasivo (suma de puntuaciones). */
    private int totalPasivo;

    /** Diferencia entre Activo y Pasivo. */
    private int diferencia;

    /** Indica si el balance es coherente. */
    private boolean balanceCoherente;
}
