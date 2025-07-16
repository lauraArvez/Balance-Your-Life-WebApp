package com.laura.balance.domain.model;

import java.util.LinkedHashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.laura.balance.domain.model.rules.BalanceRules;

/**
 * Modelo de dominio que representa las dimensiones del Activo
 * en el Balance Existencial propuesto por Antoni Bolinches.
 *
 * <p>El activo se compone de cinco dimensiones evaluadas de 0 a 10:</p>
 * <ul>
 *   <li>Amor</li>
 *   <li>Familia</li>
 *   <li>Realización Personal</li>
 *   <li>Coherencia Interna</li>
 *   <li>Confort Material</li>
 * </ul>
 */
public class ActivoModel {
    
    private static final Logger logger = LoggerFactory.getLogger(ActivoModel.class);

    private final int amor;
    private final int familia;
    private final int realizacionPersonal;
    private final int coherenciaInterna;
    private final int confortMaterial;
    

    /**
     * Crea una instancia del Activo validando cada dimensión.
     *
     * @param amor Puntuación para Amor
     * @param familia Puntuación para Familia
     * @param realizacionPersonal Puntuación para Realización Personal
     * @param coherenciaInterna Puntuación para Coherencia Interna
     * @param confortMaterial Puntuación para Confort Material
     * @throws IllegalArgumentException si alguna puntuación está fuera del rango permitido
     */
    public ActivoModel(int amor, int familia, int realizacionPersonal, int coherenciaInterna, int confortMaterial) {
        this.amor = BalanceRules.validarPuntuacion(amor);
        this.familia = BalanceRules.validarPuntuacion(familia);
        this.realizacionPersonal = BalanceRules.validarPuntuacion(realizacionPersonal);
        this.coherenciaInterna = BalanceRules.validarPuntuacion(coherenciaInterna);
        this.confortMaterial = BalanceRules.validarPuntuacion(confortMaterial);
        
        logger.debug("ActivoModel creado con valores -> amor: {}, familia: {}, realizacionPersonal: {}, coherenciaInterna: {}, confortMaterial: {}",
            this.amor, this.familia, this.realizacionPersonal, this.coherenciaInterna, this.confortMaterial);
    }

    /**
     * Constructor alternativo que permite crear un ActivoModel
     * a partir de un mapa clave-valor.
     *
     * <p>Las claves deben ser exactamente:
     * "Amor", "Familia", "Realización Personal", "Coherencia Interna", "Confort Material".</p>
     *
     * <p>Este constructor es útil cuando se reciben estructuras dinámicas
     * como objetos JSON desde el frontend o APIs externas.</p>
     *
     * @param valores Mapa con los nombres de las dimensiones como claves y sus puntuaciones como valores.
     * @throws IllegalArgumentException si falta alguna clave o las puntuaciones son inválidas.
     */
    public ActivoModel(Map<String, Integer> valores) {
        if (!valores.keySet().containsAll(BalanceRules.CLAVES_ACTIVO)) {
            throw new IllegalArgumentException("Faltan una o más claves requeridas en el mapa de Activo: se esperaban " + BalanceRules.CLAVES_ACTIVO);
        }

        this.amor = BalanceRules.validarPuntuacion(valores.get("Amor"));
        this.familia = BalanceRules.validarPuntuacion(valores.get("Familia"));
        this.realizacionPersonal = BalanceRules.validarPuntuacion(valores.get("Realización Personal"));
        this.coherenciaInterna = BalanceRules.validarPuntuacion(valores.get("Coherencia Interna"));
        this.confortMaterial = BalanceRules.validarPuntuacion(valores.get("Confort Material"));

        logger.debug("ActivoModel creado desde Map con valores validados: {}", valores);
    }


    // ================== GETTERS ===================
    
    /**
     * @return Puntuación de Amor
     */
    public int getAmor() {
        return amor;
    }

    /**
     * @return Puntuación de Familia
     */
    public int getFamilia() {
        return familia;
    }

    /**
     * @return Puntuación de Realización Personal
     */
    public int getRealizacionPersonal() {
        return realizacionPersonal;
    }

    /**
     * @return Puntuación de Coherencia Interna
     */
    public int getCoherenciaInterna() {
        return coherenciaInterna;
    }

    /**
     * @return Puntuación de Confort Material
     */
    public int getConfortMaterial() {
        return confortMaterial;
    }


    /**
     * Calcula la puntuación total del activo, sumando todas sus dimensiones.
     *
     * @return Total del activo
     */
    public int calcularTotalActivo(){
        return amor + familia + realizacionPersonal + coherenciaInterna + confortMaterial;
    }

    /**
     * Devuelve un mapa con el detalle de cada puntuación del activo.
     *
     * @return Mapa con nombres y valores de los componentes del activo
    */
    public Map<String, Integer> getValores() {
        Map<String, Integer> detalle = new LinkedHashMap<>();
        detalle.put("Amor", amor);
        detalle.put("Familia", familia);
        detalle.put("Realización Personal", realizacionPersonal);
        detalle.put("Coherencia Interna", coherenciaInterna);
        detalle.put("Confort Material", confortMaterial);
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
        ActivoModel other = (ActivoModel) obj;
        if (amor != other.amor)
            return false;
        if (familia != other.familia)
            return false;
        if (realizacionPersonal != other.realizacionPersonal)
            return false;
        if (coherenciaInterna != other.coherenciaInterna)
            return false;
        if (confortMaterial != other.confortMaterial)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + amor;
        result = prime * result + familia;
        result = prime * result + realizacionPersonal;
        result = prime * result + coherenciaInterna;
        result = prime * result + confortMaterial;
        return result;
    }
    
    /**
    * @return una representación en texto del objeto, útil para logs o trazabilidad.
    */
    @Override
    public String toString() {
        return "ActivoModel{" +
            "amor= " + amor +
            ", familia= " + familia +
            ", realizacionPersonal= " + realizacionPersonal +
            ", coherenciaInterna=" + coherenciaInterna +
            ", confortMaterial=" + confortMaterial +
            "}";
    }
}
