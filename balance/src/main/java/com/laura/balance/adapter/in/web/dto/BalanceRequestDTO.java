package com.laura.balance.adapter.in.web.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para representar una solicitud de creación de balance existencial.
 *
 * <p>Contiene listas de los elementos del Activo y Pasivo tal como se reciben en
 * la capa de entrada (web), normalmente a través de una petición HTTP POST
 * con cuerpo JSON.</p>
 *
 * <p> Deben incluir exactamente 5 elementos cada una, representados con {@link ItemDTO}.</p>
 * <p>Este DTO será convertido a un modelo de dominio en el caso de uso.</p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BalanceRequestDTO {

    /** Lista de dimensiones del Activo (debe tener 5 elementos). */
    @Valid
    @Size(min = 5, max = 5, message = "Se requieren 5 elementos en activo")
    private List<ItemDTO> activo;
    
    /** Lista de dimensiones del Pasivo (debe tener 5 elementos). */
    @Valid
    @Size(min = 5, max = 5, message = "Se requieren 5 elementos en pasivo")
    private List<ItemDTO> pasivo;
}
