package com.laura.balance.adapter.in.web.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO de respuesta extendida tras procesar un Balance Existencial.
 *
 * <p>Este objeto se envía al cliente tras evaluar un balance,
 * incluyendo los totales del Activo y Pasivo, y el resultado
 * de la validación de coherencia y un mensaje de interpretación</p>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BalanceResponseDTO {
    
    /** Puntuación total obtenida al sumar todos los valores del Activo (0-50). */
    private int totalActivo;

    /** Puntuación total obtenida al sumar todos los valores del Pasivo (0-50). */
    private int totalPasivo;

    /** Indica si el balance es coherente según las reglas que se evalúa desde el modelo de dominio. */
    private boolean balanceCoherente;

    /** Detalle de cada dimensión del Activo (nombre → puntuación). */
    private Map<String, Integer> detalleActivo;
    
    /** Detalle de cada dimensión del Pasivo (nombre → puntuación). */
    private Map<String, Integer> detallePasivo;

    /** Interpretación del resultado del balance, generada según la lógica del dominio. */
    private String interpretacion;
}
