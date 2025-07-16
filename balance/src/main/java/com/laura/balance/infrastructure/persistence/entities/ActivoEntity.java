package com.laura.balance.infrastructure.persistence.entities;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 */
@Getter
@Setter
@Builder
public class ActivoEntity {
    
    @NotNull
    @Min(value = 0, message = "La puntuación mínima es 0")
    @Max(value = 10, message = "La puntuación máxima es 10")
    private int amor;

    @NotNull
    @Min(value = 0, message = "La puntuación mínima es 0")
    @Max(value = 10, message = "La puntuación máxima es 10")
    private int familia;

    @NotNull
    @Min(value = 0, message = "La puntuación mínima es 0")
    @Max(value = 10, message = "La puntuación máxima es 10")
    private int realizacionPersonal;

    @NotNull
    @Min(value = 0, message = "La puntuación mínima es 0")
    @Max(value = 10, message = "La puntuación máxima es 10")
    private int coherenciaInterna;

    @NotNull
    @Min(value = 0, message = "La puntuación mínima es 0")
    @Max(value = 10, message = "La puntuación máxima es 10")
    private int confortMaterial;

    /**
     * Calcula la puntuación todal del activo del balance existencial
     * @return suma de las puntuaciones del activo
     */
    public int calcularTotalPuntuacionActivo(){
        return amor + familia + realizacionPersonal + coherenciaInterna + confortMaterial;
    }
}
