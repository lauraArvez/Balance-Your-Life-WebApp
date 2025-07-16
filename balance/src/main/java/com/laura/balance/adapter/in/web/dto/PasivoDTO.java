package com.laura.balance.adapter.in.web.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para representar los valores del Pasivo recibidos desde el cliente.
 *
 * <p>Este objeto se usa en el adaptador de entrada (web o REST) para recoger
 * las puntuaciones del usuario.</p>
 *
 * <p>Cada dimensión está validada para aceptar sólo valores entre 0 y 10.</p>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PasivoDTO {
    
    /** Puntuación de la dimensión Capacidad (0-10). */
    @Min(0)
    @Max(10)
    public int capacidad;

    /** Puntuación de la dimensión Madurez (0-10). */
    @Min(0)
    @Max(10)
    public int madurez;

    /** Puntuación de la dimensión Esfuerzo (0-10). */
    @Min(0)
    @Max(10)
    public int esfuerzo;

    /** Puntuación de la dimensión Suerte (0-10). */
    @Min(0)
    @Max(10)
    public int suerte;

    /** Puntuación de la dimensión Ayuda (0-10). */
    @Min(0)
    @Max(10)
    public int ayuda;
}
