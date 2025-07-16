package com.laura.balance.application.ports.in;

import com.laura.balance.adapter.in.web.dto.BalanceRequestDTO;
import com.laura.balance.domain.model.BalanceModel;

/**
 * Puerto de entrada que expone operaciones de consulta y evaluación
 * sobre los datos del balance, sin interpretación textual.
 *
 * <p>Ideal para flujos ligeros que solo requieren los totales, 
 * la diferencia o saber si un balance es coherente.</p>
 */
public interface BalanceQueryUseCase {

    /**
     * Calcula el total de valores del Activo.
     *
     * @param dto DTO con los valores del activo
     * @return suma total del activo (0–50)
     */
    int calcularTotalActivo(BalanceRequestDTO dto);

    /**
     * Calcula el total de valores del Pasivo.
     *
     * @param dto DTO con los valores del pasivo
     * @return suma total del pasivo (0–50)
     */
    int calcularTotalPasivo(BalanceRequestDTO dto);

    /**
     * Indica si el balance es coherente según la diferencia permitida.
     *
     * @param dto DTO con los valores del balance
     * @return true si el balance es coherente; false en caso contrario
     */
    boolean esCoherente(BalanceRequestDTO dto);

    /**
     * Convierte el DTO recibido en un modelo de dominio
     * y evalúa los totales y coherencia.
     *
     * @param dto Datos del balance
     * @return BalanceModel evaluado
     */
    BalanceModel evaluar(BalanceRequestDTO dto);
}
