/**
 * 
 */
package co.bdigital.cmm.jpa.services;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.HomologaError;

/**
 * @author ricardo.paredes
 *
 */
public interface HomologadorService {

    public HomologaError getErrorHomologation(String codigoIn,
            String sistemaIn, String sistemaDestino, EntityManager em)
            throws JPAException;

    /**
     * Metodo que realiza consulta a BD para homologar tipos de bolsillos
     * 
     * @param sourceSystem
     * @param destinationSystem
     * @param keyCode
     * @param sourceValue
     * @param em
     * @return destinationvalue
     * @throws JPAException
     */
    public String getHomologationTypePocket(String sourceSystem,
            String destinationSystem, String keyCode, String sourceValue,
            EntityManager em) throws JPAException;
    
    /**
     * Metodo que realiza consulta a BD para homologar tipos
     * 
     * @param sourceSystem
     * @param destinationSystem
     * @param keyCode
     * @param sourceValue
     * @param em
     * @return destinationvalue
     * @throws JPAException
     */
    public String getHomologationType(String sourceSystem,
            String destinationSystem, String keyCode, String sourceValue,
            EntityManager em) throws JPAException;
}
