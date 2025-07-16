package com.laura.balance.adapter.in.web.mapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.laura.balance.adapter.in.web.dto.ItemDTO;

/**
 * Clase utilitaria para realizar transformaciones comunes
 * entre estructuras de datos (DTOs, Mapas, etc).
 *
 * <p>Contiene métodos estáticos que pueden ser reutilizados
 * desde cualquier capa de la aplicación.</p>
 */
public class MapperHelper {

    /**
     * Convierte una lista de ItemDTO en un mapa clave-valor (nombre → valor).
     *
     * @param items lista de objetos ItemDTO
     * @return mapa con los nombres como claves y los valores asociados
     */
    public static Map<String, Integer> convertirListaAMapa(List<ItemDTO> items) {
        return items.stream()
                .collect(Collectors.toMap(ItemDTO::getNombre, ItemDTO::getValor));
    }
}