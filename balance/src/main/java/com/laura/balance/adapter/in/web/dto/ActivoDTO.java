package com.laura.balance.adapter.in.web.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para representar los valores del Activo recibidos desde el cliente.
 *
 * <p>Este objeto es usado en el adaptador de entrada para recoger las
 * puntuaciones del usuario desde un formulario o una API REST.</p>
 *
 * <p>Cada dimensión está validada para aceptar sólo valores entre 0 y 10.</p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivoDTO {

     /** Puntuación de la dimensión Amor (0-10). */
    @Min(0)
    @Max(10)
    private int amor;

    /** Puntuación de la dimensión Familia (0-10). */
    @Min(0)
    @Max(10)
    private int familia;

    /** Puntuación de la dimensión Realización Personal (0-10). */
    @Min(0)
    @Max(10)
    private int realizacionPersonal;

    /** Puntuación de la dimensión Coherencia Interna (0-10). */
    @Min(0)
    @Max(10)
    private int coherenciaInterna;

    /** Puntuación de la dimensión Confort Material (0-10). */
    @Min(0)
    @Max(10)
    private int confortMaterial;
}
