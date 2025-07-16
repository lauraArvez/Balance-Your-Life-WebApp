package com.laura.balance.adapter.in.web.controller;

import org.springframework.http.ResponseEntity;

import com.laura.balance.adapter.in.web.dto.BalanceRequestDTO;
import com.laura.balance.application.ports.in.BalanceQueryUseCase;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST dedicado a consultas específicas sobre el balance existencial.
 * 
 * <p>Actúa como adaptador de entrada que expone endpoints para obtener 
 * el total del activo o del pasivo por separado.</p>
 */
@RestController
@RequestMapping("/api/v1/balance/query")
public class BalanceQueryController {
    
    private final BalanceQueryUseCase balanceQueryUseCase;
    
    public BalanceQueryController(BalanceQueryUseCase balanceQueryUseCase) {
        this.balanceQueryUseCase = balanceQueryUseCase;
    }

    /**
     * Calcula el total del Activo a partir de los valores recibidos.
     *
     * @param dto DTO con los ítems del activo y pasivo
     * @return número entero que representa la suma del activo
     */
    @PostMapping("/total-activo")
    public ResponseEntity<Integer> obtenerTotalActivo(@Valid @RequestBody BalanceRequestDTO dto) {
        int total = balanceQueryUseCase.calcularTotalActivo(dto);
        return ResponseEntity.ok(total);
    }

    /**
     * Calcula el total del Pasivo a partir de los valores recibidos.
     *
     * @param dto DTO con los ítems del activo y pasivo
     * @return número entero que representa la suma del pasivo
     */
    @PostMapping("/total-pasivo")
    public ResponseEntity<Integer> obtenerTotalPasivo(@Valid @RequestBody BalanceRequestDTO dto) {
        int total = balanceQueryUseCase.calcularTotalPasivo(dto);
        return ResponseEntity.ok(total);
    }
}
