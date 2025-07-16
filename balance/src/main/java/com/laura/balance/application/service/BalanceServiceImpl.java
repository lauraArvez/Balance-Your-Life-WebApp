package com.laura.balance.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.laura.balance.application.ports.in.BalanceService;
import com.laura.balance.domain.model.BalanceModel;
import com.laura.balance.domain.model.rules.BalanceRules;

/**
 * Servicio de aplicación para procesar un balance existencial.
 *
 * <p>Este servicio orquesta la lógica de validación del balance y expone el resultado.
 * Pertenece a la capa de aplicación dentro de la arquitectura hexagonal, actuando como
 * intermediario entre los adaptadores de entrada (controlador web) y el modelo de dominio.</p>
 */
@Service
public class BalanceServiceImpl implements BalanceService{
    
    private static final Logger logger = LoggerFactory.getLogger(BalanceServiceImpl.class);
    
    /**
     * Procesa un balance recibido. Valida que sea coherente y lo devuelve.
     * 
     * <p>En caso de que el balance no sea coherente según las reglas del dominio,
     * se lanza una excepción.</p>
     *
     * @param balanceModel el modelo del balance con activo y pasivo
     * @return el mismo modelo, si pasa la validación de coherencia
     */
    @Override
    public BalanceModel procesarBalance(BalanceModel balanceModel) {
        validarBalanceCoherente(balanceModel);
        logger.info("Balance procesado correctamente: {}", balanceModel);
        return balanceModel;
    }

    /**
     * Valida la coherencia del balance aplicando la regla de negocio definida:
     * la diferencia entre la suma del activo y del pasivo no debe superar el umbral.
     *
     * @param balance el modelo a validar
     * @throws IllegalStateException si el balance no es coherente
     */
    @Override
    public void validarBalanceCoherente(BalanceModel balance) {
        if(!balance.esBalanceCoherente()){
            throw new IllegalStateException("El balance no es coherente: la diferencia supera los "
                + BalanceRules.DIFERENCIA_MAXIMA_COHERENTE + " puntos.");
        }
    }

}
