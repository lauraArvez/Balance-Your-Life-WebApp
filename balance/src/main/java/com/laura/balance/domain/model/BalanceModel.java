package com.laura.balance.domain.model;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.laura.balance.domain.model.rules.BalanceRules;

/**
 * Modelo de dominio que representa un Balance Existencial,
 * combinando un conjunto de valores Activos y Pasivos.
 *
 * <p>El balance es coherente si la diferencia entre ambas puntuaciones
 * no supera un umbral determinado.</p>
 *
 * <p>Este modelo representa una entidad del núcleo del dominio,
 * siguiendo los principios de la arquitectura hexagonal.</p>
 */
public class BalanceModel{
    
    private static final Logger logger = LoggerFactory.getLogger(BalanceModel.class);
    
    private final ActivoModel activo;
    private final PasivoModel pasivo;

    /**
     * Crea una instancia de BalanceModel a partir de dos modelos ya validados.
     *
     * @param activo Modelo de Activo
     * @param pasivo Modelo de Pasivo
     * @throws IllegalArgumentException si alguno de los parámetros es nulo
     */
    public BalanceModel(ActivoModel activo, PasivoModel pasivo) {
        if(activo == null || pasivo == null){
            throw new IllegalArgumentException("Activo y Pasivo no pueden ser nulos");
        }

        this.activo = activo;
        this.pasivo = pasivo;

        logger.debug("BalanceModel creado con Activo: {} y Pasivo: {}", activo, pasivo);
    }

    /**
     * Crea un BalanceModel a partir de estructuras clave-valor (por ejemplo desde JSON).
     * Este constructor facilita la conversión desde DTOs hacia modelos del dominio.
     *
     * @param valoresActivo mapa de dimensiones del activo (nombre → puntuación)
     * @param valoresPasivo mapa de dimensiones del pasivo (nombre → puntuación)
     * @throws IllegalArgumentException si los mapas son nulos o incompletos
     */
    public BalanceModel(Map<String, Integer> valoresActivo, Map<String, Integer> valoresPasivo) {
        if (valoresActivo == null || valoresPasivo == null) {
            throw new IllegalArgumentException("Los mapas de valores de Activo y Pasivo no pueden ser nulos.");
        }

        if (!valoresActivo.keySet().containsAll(BalanceRules.CLAVES_ACTIVO)) {
            throw new IllegalArgumentException("Faltan valor en el Activo. Se esperaban: " + BalanceRules.CLAVES_ACTIVO);
        }

        if (!valoresPasivo.keySet().containsAll(BalanceRules.CLAVES_PASIVO)) {
            throw new IllegalArgumentException("Faltan valor en el Pasivo. Se esperaban: " + BalanceRules.CLAVES_PASIVO);
        }

        this.activo = new ActivoModel(valoresActivo);
        this.pasivo = new PasivoModel(valoresPasivo);

        logger.debug("BalanceModel creado a partir de mapas con Activo: {} y Pasivo: {}", valoresActivo, valoresPasivo);
    }


    /**
     * Obtiene el modelo de activo asociado al balance.
     *
     * @return ActivoModel
     */
    public ActivoModel getActivo() {
        return activo;
    }

    /**
     * Obtiene el modelo de pasivo asociado al balance.
     *
     * @return PasivoModel
     */
    public PasivoModel getPasivo() {
        return pasivo;
    }

    /**
     * Calcula la suma total del pasivo.
     *
     * @return Puntuación total del pasivo
     */
    public int getTotalPasivo() {
        int total = pasivo.calcularTotalPasivo();
        logger.debug("Total pasivo: {}", total);
        return total;
    }

    /**
     * Calcula la suma total del activo.
     *
     * @return Puntuación total del activo
     */
    public int getTotalActivo() {
        int total = activo.calcularTotalActivo();
        logger.debug("Total activo: {}", total);
        return total;
    }

    
    @Override
    public String toString() {
        return "BalanceModel [activo=" + activo + ", pasivo=" + pasivo + "]";
    }

    /**
     * Evalúa si el balance entre activo y pasivo es coherente,
     * según la diferencia máxima aceptada definida por las reglas del dominio.
     *
     * @return true si la diferencia es menor o igual al umbral; false en caso contrario
     */
    public boolean esBalanceCoherente(){
        int diferencia = Math.abs(getTotalActivo() - getTotalPasivo());
        boolean coherente = diferencia <= BalanceRules.DIFERENCIA_MAXIMA_COHERENTE;
        
        logger.debug("Diferencia entre activo y pasivo: {} - ¿Coherente?: {}", diferencia, coherente);
        return coherente;
    }

    /**
     * Devuelve un mapa con el detalle de cada dimensión del Activo.
     *
     * @return Mapa con nombres y valores del activo
     */
    public Map<String, Integer> getDetalleActivo() {
        return activo.getValores();
    }

    /**
     * Devuelve un mapa con el detalle de cada dimensión del Pasivo.
     *
     * @return Mapa con nombres y valores del pasivo
     */
    public Map<String, Integer> getDetallePasivo() {
        return pasivo.getValores();
    }

}
