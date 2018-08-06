package co.bdigital.cmm.jpa.services;

import java.util.List;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.OperadorRecarga;

/**
 * @author john.perez
 *
 */
public interface OperadorRecargaJPAService {

    /**
     * Consulta de Operadores por pais y tipo.
     * 
     * @param countryCode
     * @param operatorType
     * @param em
     * @return List<OperadorRecarga>
     * @throws JPAException
     */
    public List<OperadorRecarga> getListOperators(String countryCode,
            String operatorType, EntityManager em) throws JPAException;

    /**
     * Consulta de Operadores por id, pais y tipo.
     * 
     * @param countryCode
     * @param operatorType
     * @param id
     * @param em
     * @return List<OperadorRecarga>
     * @throws JPAException
     */
    public List<OperadorRecarga> getListOperatorsByIdAndType(String countryCode,
            String operatorType, String id, EntityManager em)
            throws JPAException;

}
