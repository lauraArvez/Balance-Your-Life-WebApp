package com.laura.balance.application.ports.in;

import com.laura.balance.domain.model.BalanceModel;

/**
 * Puerto de entrada encargado de procesar y validar un Balance Existencial
 * desde la capa de aplicación.
 *
 * <p>Representa operaciones más específicas o reutilizables como validación 
 * estructural o lógica, y puede ser utilizado como parte de otros flujos de negocio.</p>
 */
public interface BalanceService {

    /**
     * Procesa el modelo de dominio del balance, aplicando validaciones 
     * y devolviendo el resultado si es válido.
     *
     * @param balanceModel modelo con los datos ya convertidos desde DTO
     * @return el mismo modelo, si es válido
     */
    BalanceModel procesarBalance(BalanceModel balanceModel);

    /**
     * Valida que el balance sea coherente según las reglas del dominio.
     *
     * <p>Lanza una excepción si la diferencia entre activo y pasivo
     * supera el umbral permitido.</p>
     *
     * @param balance modelo a validar
     * @throws IllegalStateException si el balance no es coherente
     */
    void validarBalanceCoherente(BalanceModel balance);
}