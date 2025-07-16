package com.laura.balance.adapter.in.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.laura.balance.adapter.in.web.dto.BalanceRequestDTO;
import com.laura.balance.adapter.in.web.dto.BalanceResponseDTO;
import com.laura.balance.adapter.in.web.dto.BalanceResumenDTO;
import com.laura.balance.application.ports.in.BalanceQueryUseCase;
import com.laura.balance.application.ports.in.BalanceUseCase;
import com.laura.balance.domain.model.BalanceModel;

import jakarta.validation.Valid;

/**
 * Controlador REST que expone el endpoint de cálculo del Balance Existencial.
 *
 * <p>Recibe una petición con datos de activo y pasivo, delega en el servicio
 * de aplicación, y devuelve una respuesta con los resultados calculados.</p>
 *
 * <p>Este controlador actúa como punto de entrada de la arquitectura
 * hexagonal (adaptador de entrada).</p>
 */
@RestController
@RequestMapping("api/v1/balance")
public class BalanceController {

    private final BalanceUseCase balanceUseCase;
    private final BalanceQueryUseCase balanceQueryUseCase;

    /**
     * Constructor que inyecta el servicio de aplicación.
     *
     * @param service servicio que contiene la lógica de aplicación del balance
     */
    public BalanceController(BalanceUseCase balanceUseCase, BalanceQueryUseCase balanceQueryUseCase) {
        this.balanceQueryUseCase = balanceQueryUseCase;
        this.balanceUseCase = balanceUseCase;
    }

    /**
     * Endpoint principal que interpreta el balance completo.
     *
     * @param dto DTO con los datos de entrada (activo y pasivo)
     * @return DTO con resultados: totales, detalle, coherencia e interpretación textual
     */
    @PostMapping("/interpretar")
    public ResponseEntity<BalanceResponseDTO> interpretarBalance(@Valid @RequestBody BalanceRequestDTO dto) {
        BalanceResponseDTO respuesta = balanceUseCase.interpretarBalance(dto);
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Endpoint alternativo para obtener solo el resumen del balance:
     * totales, diferencia y si es coherente.
     *
     * @param dto DTO con los datos del balance
     * @return DTO resumen con total activo, total pasivo, diferencia y coherencia
     */
    @PostMapping("/resumen")
    public ResponseEntity<BalanceResumenDTO> obtenerResumen(@Valid @RequestBody BalanceRequestDTO dto) {
        BalanceModel balance = balanceQueryUseCase.evaluar(dto);
        BalanceResumenDTO resumen = new BalanceResumenDTO(
            balance.getTotalActivo(),
            balance.getTotalPasivo(),
            balance.getTotalActivo() - balance.getTotalPasivo(),
            balance.esBalanceCoherente()
        );
        return ResponseEntity.ok(resumen);
    }
}
