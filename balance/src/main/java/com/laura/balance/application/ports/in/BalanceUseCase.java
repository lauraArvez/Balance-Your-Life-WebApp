package com.laura.balance.application.ports.in;

import com.laura.balance.adapter.in.web.dto.BalanceRequestDTO;
import com.laura.balance.adapter.in.web.dto.BalanceResponseDTO;

/**
 * Puerto de entrada principal que orquesta la interpretación completa de un Balance Existencial.
 *
 * <p>Forma parte de la capa de aplicación de la arquitectura hexagonal y define
 * el contrato que debe cumplir cualquier servicio que desee interpretar el balance.</p>
 */
public interface BalanceUseCase {

    /**
     * Interpreta un balance completo recibido desde el adaptador web.
     *
     * <p>Calcula totales del activo y pasivo, evalúa si el balance es coherente,
     * y genera un mensaje interpretativo en lenguaje natural.</p>
     *
     * @param dto objeto con las puntuaciones del activo y pasivo
     * @return DTO con totales, detalle, coherencia e interpretación
     */
    BalanceResponseDTO interpretarBalance(BalanceRequestDTO dto);
}
