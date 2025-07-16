package com.laura.balance.infrastructure.persistence.entities;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasivoEntity {
    
    @NotNull
    @Min(value = 0, message = "La puntuación mínima es 0 (cero)")
    @Max(value = 10, message = "La puntuación máxima es 10")
    private int capacidad;

    @NotNull
    @Min(value = 0, message = "La puntuación mínima es 0 (cero)")
    @Max(value = 10, message = "La puntuación máxima es 10")
    private int madurez;

    @NotNull
    @Min(value = 0, message = "La puntuación mínima es 0 (cero)")
    @Max(value = 10, message = "La puntuación máxima es 10")
    private int esfuerzo;

    @NotNull
    @Min(value = 0, message = "La puntuación mínima es 0 (cero)")
    @Max(value = 10, message = "La puntuación máxima es 10")
    private int suerte;

    @NotNull
    @Min(value = 0, message = "La puntuación mínima es 0 (cero)")
    @Max(value = 10, message = "La puntuación máxima es 10")
    private int ayuda;


    /**
     * Calcula la puntuación todal del pasivo del balance existencial
     * @return suma de las puntuaciones del pasivo
     */
    public int calcularTotalPuntuacionPasivo(){
        return capacidad + madurez + esfuerzo + suerte + ayuda;
    }
}
