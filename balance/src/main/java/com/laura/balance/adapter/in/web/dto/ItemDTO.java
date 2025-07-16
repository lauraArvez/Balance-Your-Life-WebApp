package com.laura.balance.adapter.in.web.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO genérico para representar una dimensión del activo o pasivo.
 * Contiene su nombre y puntuación (valores entre 0 y 10).
 *
 * <p>Se utiliza cuando los datos se envían como listas en lugar de campos fijos.</p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {
    
    @Schema(description = "Lista de ítems del activo", example = "[{ \"nombre\": \"amor\", \"valor\": 8 }]")
    @NotNull(message = "El nombre no puede ser nulo")
    private String nombre;

    @Schema(description = "Lista de ítems del pasivo", example = "[{ \"nombre\": \"esfuerzo\", \"valor\": 5 }]")
    @NotNull(message = "El valor no puede ser nulo")
    @Min(value = 0, message = "El valor mínimo permitido es 0")
    @Max(value = 10, message = "El valor máximo permitido es 10")
    private int valor;
}

    
