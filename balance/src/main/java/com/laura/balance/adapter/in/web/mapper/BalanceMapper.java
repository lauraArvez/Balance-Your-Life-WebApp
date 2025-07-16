package com.laura.balance.adapter.in.web.mapper;


import java.util.Map;
import java.util.stream.Collectors;

import com.laura.balance.adapter.in.web.dto.*;
import com.laura.balance.domain.model.*;
import com.laura.balance.domain.model.rules.BalanceRules;

/**
 * Mapper que transforma objetos entre la capa web (DTOs)
 * y la capa de dominio (Modelos).
 *
 * <p>Permite desacoplar el modelo del dominio
 * de los datos que viajan en la API, cumpliendo con el
 * principio de separación de responsabilidades.</p>
 */
public class BalanceMapper {
    
    /**
     * Transforma un DTO de solicitud en un modelo del dominio.
     *
     * <p>Este método se usa cuando queremos construir directamente los objetos
     * ActivoModel y PasivoModel desde los datos web, sin delegar esa lógica 
     * en el constructor de BalanceModel.</p>
     *
     * @param dto el DTO recibido desde el frontend
     * @return el BalanceModel con la estructura del dominio
     */
    public static BalanceModel toModel(BalanceRequestDTO dto) {
        Map<String, Integer> activoMap = dto.getActivo().stream()
            .collect(Collectors.toMap(ItemDTO::getNombre, ItemDTO::getValor));

        Map<String, Integer> pasivoMap = dto.getPasivo().stream()
            .collect(Collectors.toMap(ItemDTO::getNombre, ItemDTO::getValor));

        ActivoModel activo = new ActivoModel(activoMap);
        PasivoModel pasivo = new PasivoModel(pasivoMap);

        return new BalanceModel(activo, pasivo);
    }


    /**
     * Transforma un BalanceModel del dominio en un DTO de respuesta.
     *
     * <p>Este método se llama tras procesar la lógica de negocio. Se usa
     * para enviar una respuesta completa al cliente: totales, coherencia,
     * detalle e interpretación textual.</p>
     *
     * @param model el modelo ya evaluado
     * @return DTO para enviar al frontend como JSON
     */
    public static BalanceResponseDTO toResponseDTO(BalanceModel model) {
        return new BalanceResponseDTO(
            model.getTotalActivo(),
            model.getTotalPasivo(),
            model.esBalanceCoherente(),
            model.getActivo().getValores(),  // << detalle del activo
            model.getPasivo().getValores(),   // << detalle del pasivo
            generarInterpretacionResumen(model)
        );
    }

    /**
     * Genera una interpretación simplificada del balance, pensada para respuestas de tipo resumen.
     *
     * <p>Esta versión no contempla tantos matices como la lógica completa de interpretación,
     * pero permite ofrecer un mensaje motivacional o de advertencia simple basado en 
     * umbrales conocidos.</p>
     * @param model el balance evaluado, con los totales ya calculados
     * @return cadena de texto con la interpretación personalizada del balance
     */
    private static String generarInterpretacionResumen(BalanceModel model) {
        int totalActivo = model.getTotalActivo();
        int totalPasivo = model.getTotalPasivo();
        int diferencia = Math.abs(totalActivo - totalPasivo);

        StringBuilder interpretacion = new StringBuilder();

        if (totalActivo >= 35 && totalActivo <= 45) {
            interpretacion.append("✅ Indicador de bienestar personal y social. ")
                        .append("Según Antoni Bolinches: 'entre el 35 y el 40 no habría tanta neurosis colectiva, ")
                        .append("tanta ambición personal ni tanta crispación social'. ");
                        
        }

        if (diferencia > BalanceRules.DIFERENCIA_MAXIMA_COHERENTE) {
            interpretacion.append("Tan perjudicial es pecar por exceso (sobrevalorar el activo o atribuirlo todo a uno mismo) ")
                        .append("como por defecto (devaluar el activo o atribuirlo todo a factores externos como la suerte o la ayuda). ")
                        .append("Se busca un punto de equilibrio.");
        } else {
            interpretacion.append("Tu balance es coherente. Buen trabajo en mantener un equilibrio saludable.");
        }

        return interpretacion.toString();
    }
}