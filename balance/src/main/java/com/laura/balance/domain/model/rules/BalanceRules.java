package com.laura.balance.domain.model.rules;

import java.util.Set;

/**
 * Reglas del modelo de dominio del Balance Existencial.
 * <p>
 * Esta clase contiene constantes y validaciones comunes utilizadas
 * en los modelos Activo, Pasivo y Balance. Forma parte del núcleo de la lógica de negocio.
 * </p>
 * 
 * <p><b>Nota:</b> Esta clase no debe instanciarse.</p>
 */
public class BalanceRules {

    /** Puntuación máxima permitida por cada dimensión */
    public static final int MAX_PUNTUACION = 10;

    /** Puntuación mínima permitida por cada dimensión */
    public static final int MIN_PUNTUACION = 0;

    /** Diferencia máxima permitida entre activo y pasivo para considerarse coherente */
    public static final int DIFERENCIA_MAXIMA_COHERENTE = 5;

    /** Claves esperadas para el activo */
    public static final Set<String> CLAVES_ACTIVO = Set.of(
        "Amor", "Familia", "Realización Personal", "Coherencia Interna", "Confort Material"
    );

    /** Claves esperadas para el pasivo */
    public static final Set<String> CLAVES_PASIVO = Set.of(
        "Capacidad", "Madurez", "Esfuerzo", "Suerte", "Ayuda"
    );

    
    /**
     * Constructor privado para evitar instanciación de clase de utilidades.
     */
    private BalanceRules() {
        throw new UnsupportedOperationException("Clase de constantes, no debe instanciarse.");
    }

    /**
     * Valida que una puntuación esté dentro del rango permitido (0 a 10).
     *
     * @param valor la puntuación a validar
     * @return el mismo valor si es válida
     * @throws IllegalArgumentException si el valor está fuera del rango permitido
     */
    public static int validarPuntuacion(int valor){
        if(valor < MIN_PUNTUACION || valor > MAX_PUNTUACION){
            throw new IllegalArgumentException(
                "La puntuación debe estar entre: "+ MIN_PUNTUACION + " y "+ MAX_PUNTUACION
            );
        }
        return valor;
    }
}
