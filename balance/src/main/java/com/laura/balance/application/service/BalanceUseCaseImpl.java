package com.laura.balance.application.service;

import java.util.Map;
import org.springframework.stereotype.Service;

import com.laura.balance.adapter.in.web.dto.BalanceRequestDTO;
import com.laura.balance.adapter.in.web.dto.BalanceResponseDTO;
import com.laura.balance.adapter.in.web.mapper.MapperHelper;
import com.laura.balance.application.ports.in.BalanceUseCase;
import com.laura.balance.domain.model.rules.BalanceRules;

/**
 * Servicio de aplicación que interpreta un Balance Existencial completo.
 *
 * <p>Recibe los valores de entrada (activo/pasivo), calcula totales, evalúa coherencia 
 * y genera una interpretación personalizada en lenguaje natural, basada en 
 * criterios inspirados en Antoni Bolinches.</p>
 */
@Service
public class BalanceUseCaseImpl implements BalanceUseCase{

    @Override
    public BalanceResponseDTO interpretarBalance(BalanceRequestDTO dto) {

        Map<String, Integer> detalleActivo = MapperHelper.convertirListaAMapa(dto.getActivo());
        Map<String, Integer> detallePasivo = MapperHelper.convertirListaAMapa(dto.getPasivo());

        int totalActivo = calcularTotal(detalleActivo);
        int totalPasivo = calcularTotal(detallePasivo);
        int diferencia = totalActivo - totalPasivo;

        boolean balanceCoherente = diferencia >= 0 && diferencia <= BalanceRules.DIFERENCIA_MAXIMA_COHERENTE;

        String interpretacion = generarInterpretacion(totalActivo, totalPasivo, diferencia);
        
        return new BalanceResponseDTO(
            totalActivo,
            totalPasivo,
            balanceCoherente,
            detalleActivo,
            detallePasivo,
            interpretacion.toString()
        );
    }


    /**
     * Suma los valores de un mapa de puntuaciones.
     *
     * @param valores mapa con puntuaciones
     * @return suma total
     */
    private int calcularTotal(Map<String, Integer> valores) {
        return valores.values().stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    /**
     * Genera una interpretación textual del resultado del balance.
     *
     * <p>Se basa en reglas heurísticas inspiradas en Antoni Bolinches,
     * teniendo en cuenta el rango del total activo, total pasivo y la diferencia.</p>
     *
     * <p>El objetivo es ofrecer al usuario una retroalimentación significativa
     * más allá de los números, promoviendo reflexión personal.</p>
     *
     * @param totalActivo puntuación total del activo
     * @param totalPasivo puntuación total del pasivo
     * @param diferencia diferencia entre activo y pasivo
     * @return interpretación en texto enriquecido (HTML) para mostrar en frontend
     */
    private String generarInterpretacion(int totalActivo, int totalPasivo, int diferencia) {
    StringBuilder interpretacion = new StringBuilder();
    
        // Evaluación basada en puntuaciones máximas
        if (totalActivo == 50 && totalPasivo == 50) {
            interpretacion.append("Has alcanzado la puntuación máxima en activo y pasivo (50/50).<br>");
            interpretacion.append("El 10 sería la máxima percepción... un nueve es lo máximo que se puede obtener pero<br>");
            interpretacion.append("también lo máximo que se puede desear porque todavía nos queda un pequeño margen para mejorar.<br>");
        }

        // Casos principales según rangos definidos
        else if (totalActivo >= 45 && totalActivo < 50 && totalPasivo >= 45 && totalPasivo < 50) {
            if (diferencia < 0) {
                interpretacion.append("El activo está por debajo del pasivo.<br>");
                interpretacion.append("La valoración del activo puede fluctuar con el tiempo debido a las circunstancias vitales.<br>");
            } else if (diferencia > 0) {
                interpretacion.append("Revisa las puntuaciones del pasivo.<br>");
                interpretacion.append("Una puntuación entre 35 y 45 se considera un indicador de bienestar personal y social.<br>");
            } else {
                interpretacion.append("Puntuaciones en equilibrio.<br>");
                interpretacion.append("Una puntuación entre 35 y 45 se considera un indicador de bienestar personal y social.<br>");
                interpretacion.append("El 10 sería la máxima percepción... un nueve es lo máximo que se puede obtener pero<br>");
                interpretacion.append("también lo máximo que se puede desear porque todavía nos queda un pequeño margen para mejorar.<br>");
            }
        } else if (totalActivo >= 45 && totalActivo < 50 && totalPasivo >= 35 && totalPasivo < 45) {
            if (diferencia > 5) {
                interpretacion.append("Desbalance importante.<br>");
                interpretacion.append("La diferencia entre el ACTIVO y el PASIVO supera los 5 puntos.<br>");
                interpretacion.append("Esto sugiere la necesidad de una reflexión y ajuste.<br>");
            } else {
                interpretacion.append("Pequeño desajuste, revisar puntuaciones.<br>");
                interpretacion.append("Este ejercicio fomenta la autorreflexión y el diálogo interno.<br>");
            }
        } else if (totalActivo >= 35 && totalActivo <= 45 && totalPasivo >= 35 && totalPasivo <= 45) {
            if (diferencia < 0) {
                interpretacion.append("Activo por debajo del pasivo.<br>");
                interpretacion.append("Recuerda que ACTIVO (activo es lo que tenemos) por debajo del Pasivo (pasivo es a quien o a que se lo debemos).<br>");
                interpretacion.append("La valoración del activo puede fluctuar con el tiempo debido a las circunstancias vitales.<br>");
            } else if (diferencia == 0) {
                interpretacion.append("Balance coherente.<br>");
                interpretacion.append("Una puntuación entre 35 y 45 se considera un indicador de bienestar personal y social.<br>");
                interpretacion.append("Cuando uno está armonizado consigo mismo no entra en conflicto con los demás.<br>");
                interpretacion.append("La valoración del activo puede fluctuar con el tiempo debido a las circunstancias vitales.”.<br>");
            } else if (diferencia <= 5) {
                interpretacion.append("Pequeño desajuste del pasivo.<br>");
                interpretacion.append("Una puntuación entre 35 y 45 se considera un indicador de bienestar personal y social.<br>");
                interpretacion.append("La valoración del activo puede fluctuar con el tiempo debido a las circunstancias vitales.”.<br>");
            } else {
                interpretacion.append("Desbalance importante.<br>");
                interpretacion.append("Tan perjudicial es pecar por exceso (sobrevalorar el activo o atribuirlo todo a uno mismo).<br>");
                interpretacion.append("Como por defecto (devaluar el activo o atribuirlo todo a factores externos como la suerte o la ayuda).<br>Se busca un punto de equilibrio.<br>");
            }
        } else if (totalActivo >= 35 && totalActivo <= 45 && totalPasivo < 35) {
            if (diferencia <= 5) {
                interpretacion.append("Pequeño desajuste del pasivo.<br>");
                interpretacion.append("Una puntuación entre 35 y 45 se considera un indicador de bienestar personal y social.<br>");
                interpretacion.append("La valoración del activo puede fluctuar con el tiempo debido a las circunstancias vitales.<br>");
            } else {
                interpretacion.append("Desbalance importante.<br>");
                interpretacion.append("Tan perjudicial es pecar por exceso (sobrevalorar el activo o atribuirlo todo a uno mismo).<br>");
                interpretacion.append("Como por defecto (devaluar el activo o atribuirlo todo a factores externos como la suerte o la ayuda). Se busca un punto de equilibrio.<br>");
            }
        } else if (totalActivo < 35 && totalPasivo >= 35 && totalPasivo <= 45) {
            if (diferencia < 0) {
                interpretacion.append("Activo muy por debajo del pasivo.<br>");
                interpretacion.append("Es necesario revisar tu percepción del bienestar actual.<br>");
            }
        } else {
            interpretacion.append("Reflexiona sobre tus puntuaciones.<br>");
            interpretacion.append("Recuerda que este test busca fomentar la autorreflexión y ayudarte a ordenar tu balance interno.<br>");
            interpretacion.append("Tan perjudicial es pecar por exceso (sobrevalorar el activo o atribuirlo todo a uno mismo).<br>");
            interpretacion.append("Como por defecto (devaluar el activo o atribuirlo todo a factores externos como la suerte o la ayuda). Se busca un punto de equilibrio.<br>");
        }

        return interpretacion.toString();
    }
}
