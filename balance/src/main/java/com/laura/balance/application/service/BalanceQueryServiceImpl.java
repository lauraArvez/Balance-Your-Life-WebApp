package com.laura.balance.application.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.laura.balance.adapter.in.web.dto.BalanceRequestDTO;
import com.laura.balance.adapter.in.web.dto.ItemDTO;
import com.laura.balance.application.ports.in.BalanceQueryUseCase;
import com.laura.balance.domain.model.BalanceModel;

/**
 * Servicio de aplicación que implementa operaciones de consulta
 * sobre los datos del balance existencial (totales, coherencia, evaluación).
 *
 * <p>Este servicio forma parte de la capa de aplicación en la arquitectura hexagonal,
 * actuando como intermediario entre la entrada (controladores web)
 * y el núcleo del dominio.</p>
 */
@Service
public class BalanceQueryServiceImpl implements BalanceQueryUseCase{

    /**
     * Calcula el total de puntuaciones del Activo.
     *
     * @param dto DTO con los valores del activo
     * @return suma total del activo
     */
    @Override
    public int calcularTotalActivo(BalanceRequestDTO dto) {
        return sumarValores(dto.getActivo());
    }

    /**
     * Calcula el total de puntuaciones del Pasivo.
     *
     * @param dto DTO con los valores del pasivo
     * @return suma total del pasivo
     */
    @Override
    public int calcularTotalPasivo(BalanceRequestDTO dto) {
        return sumarValores(dto.getPasivo());
    }
    
    /**
     * Convierte los datos del DTO en un modelo de dominio BalanceModel.
     *
     * @param dto datos recibidos desde el cliente
     * @return modelo con activo y pasivo representados en el dominio
     */
    @Override
    public BalanceModel evaluar(BalanceRequestDTO dto) {
        Map<String, Integer> valoresActivo = dto.getActivo().stream()
                .collect(Collectors.toMap(ItemDTO::getNombre, ItemDTO::getValor));
        Map<String, Integer> valoresPasivo = dto.getPasivo().stream()
                .collect(Collectors.toMap(ItemDTO::getNombre, ItemDTO::getValor));

        return new BalanceModel(valoresActivo, valoresPasivo);
    }

    /**
     * Evalúa si el balance es coherente según la lógica de dominio.
     *
     * @param dto DTO con los valores del balance
     * @return true si el balance es coherente; false en caso contrario
     */
    @Override
    public boolean esCoherente(BalanceRequestDTO dto) {
        return evaluar(dto).esBalanceCoherente();
    }
    
    /**
     * Suma los valores de una lista de ItemDTO.
     *
     * @param items lista de valores (activo o pasivo)
     * @return suma total
     */
    private int sumarValores(List<ItemDTO> items) {
        if (items == null) return 0;
        return items.stream()
                    .mapToInt(ItemDTO::getValor)
                    .sum();
    }
}
