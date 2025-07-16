package com.laura.balance.domain.model;

import java.util.LinkedHashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.laura.balance.domain.model.rules.BalanceRules;

/**
 * Modelo del Pasivo en el dominio del Balance Existencial.
 * Representa los componentes internos que permiten sostener y equilibrar los logros del Activo.
 *
 * <p>El pasivo se compone de cinco dimensiones evaluadas de 0 a 10:</p>
 * <ul>
 *   <li>Capacidad</li>
 *   <li>Madurez</li>
 *   <li>Esfuerzo</li>
 *   <li>Suerte</li>
 *   <li>Ayuda</li>
 * </ul>
 */
public class PasivoModel {

    private static final Logger logger = LoggerFactory.getLogger(PasivoModel.class);
    
    private final int capacidad;
    private final int madurez;
    private final int esfuerzo;
    private final int suerte;
    private final int ayuda;


    /**
     * Crea una instancia de PasivoModel validando cada valor recibido.
     *
     * @param capacidad Valor de la capacidad (0-10)
     * @param madurez Valor de la madurez (0-10)
     * @param esfuerzo Valor del esfuerzo (0-10)
     * @param suerte Valor de la suerte (0-10)
     * @param ayuda Valor de la ayuda (0-10)
     * @throws IllegalArgumentException si algún valor está fuera del rango permitido
     */
    public PasivoModel(int capacidad, int madurez, int esfuerzo, int suerte, int ayuda) {
        this.capacidad = BalanceRules.validarPuntuacion(capacidad);
        this.madurez = BalanceRules.validarPuntuacion(madurez);
        this.esfuerzo = BalanceRules.validarPuntuacion(esfuerzo);
        this.suerte = BalanceRules.validarPuntuacion(suerte);
        this.ayuda = BalanceRules.validarPuntuacion(ayuda);

        logger.debug("PasivoModel creado con valores -> capacidad: {}, madurez: {}, esfuerzo: {}, suerte: {}, ayuda: {}",
                capacidad, madurez, esfuerzo, suerte, ayuda);
    }

    /**
     * Constructor alternativo que permite crear un PasivoModel
     * a partir de un mapa clave-valor.
     *
     * <p>Las claves deben ser exactamente:
     * "Capacidad", "Madurez", "Esfuerzo", "Suerte", "Ayuda".</p>
     *
     * <p>Este constructor es útil cuando se reciben estructuras dinámicas
     * como objetos JSON desde el frontend o APIs externas.</p>
     *
     * @param valores Mapa con los nombres de las dimensiones como claves y sus puntuaciones como valores.
     * @throws IllegalArgumentException si falta alguna clave o las puntuaciones son inválidas.
     */
    public PasivoModel(Map<String, Integer> valores) {
        if (!valores.keySet().containsAll(BalanceRules.CLAVES_PASIVO)) {
            throw new IllegalArgumentException("Faltan una o más claves requeridas en el mapa de Pasivo: se esperaban " + BalanceRules.CLAVES_PASIVO);
        }

        this.capacidad = BalanceRules.validarPuntuacion(valores.get("Capacidad"));
        this.madurez = BalanceRules.validarPuntuacion(valores.get("Madurez"));
        this.esfuerzo = BalanceRules.validarPuntuacion(valores.get("Esfuerzo"));
        this.suerte = BalanceRules.validarPuntuacion(valores.get("Suerte"));
        this.ayuda = BalanceRules.validarPuntuacion(valores.get("Ayuda"));

        logger.debug("PasivoModel creado desde Map con valores validados: {}", valores);
    }


   // ================== GETTERS ===================

    /**
     * @return Puntuación de capacidad
     */
    public int getCapacidad() {
        return capacidad;
    }

    /**
     * @return Puntuación de madurez
     */
    public int getMadurez() {
        return madurez;
    }

    /**
     * @return Puntuación de esfuerzo
     */
    public int getEsfuerzo() {
        return esfuerzo;
    }

    /**
     * @return Puntuación de suerte
     */
    public int getSuerte() {
        return suerte;
    }

    /**
     * @return Puntuación de ayuda
     */
    public int getAyuda() {
        return ayuda;
    }


    /**
     * Calcula la suma total de las puntuaciones del pasivo.
     *
     * @return Total del pasivo (suma de los 5 elementos)
     */
    public int calcularTotalPasivo(){
        return capacidad + madurez + esfuerzo + suerte + ayuda;
    }

    /**
     * Devuelve un mapa con el detalle de cada puntuación del pasivo.
     *
     * @return Mapa con nombres y valores de los componentes del pasivo
     */
    public Map<String, Integer> getValores() {
        Map<String, Integer> detalle = new LinkedHashMap<>();
        detalle.put("Capacidad", capacidad);
        detalle.put("Madurez", madurez);
        detalle.put("Esfuerzo", esfuerzo);
        detalle.put("Suerte", suerte);
        detalle.put("Ayuda", ayuda);
        return detalle;
    }

    
    // ================ MÉTODOS DE UTILIDAD =================

    /**
     * Compara este objeto con otro para determinar si son iguales.
     * Se utiliza principalmente en pruebas y estructuras de datos como Set o Map.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PasivoModel other = (PasivoModel) obj;
        if (capacidad != other.capacidad)
            return false;
        if (madurez != other.madurez)
            return false;
        if (esfuerzo != other.esfuerzo)
            return false;
        if (suerte != other.suerte)
            return false;
        if (ayuda != other.ayuda)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + capacidad;
        result = prime * result + madurez;
        result = prime * result + esfuerzo;
        result = prime * result + suerte;
        result = prime * result + ayuda;
        return result;
    }

    /**
     * @return una representación en texto del objeto, útil para logs o trazabilidad.
     */
    @Override
    public String toString() {
        return "PasivoModel {" +
            "capacidad=" + capacidad +
            ", madurez=" + madurez +
            ", esfuerzo=" + esfuerzo +
            ", suerte=" + suerte +
            ", ayuda=" + ayuda +
            "}";
    }
}
