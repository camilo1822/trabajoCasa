/**
 * 
 */
package co.bdigital.cmm.jpa.services;

import java.util.List;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.Parametro;

/**
 * @author daniel.pareja@pragma.com.co
 *
 */
public interface ParameterJPAService {

    /**
     * Consulta de parametros por pais y tipoparametro
     * 
     * @param em
     * @return
     * @throws JPAException
     */
    public List<Parametro> getRegionParameter(String tipoParametroId,
            String countryCode, EntityManager em) throws JPAException;

    /**
     * Consulta de parametros por tipoparametro
     * 
     * @param em
     * @return
     * @throws JPAException
     */
    public List<Parametro> getParamByType(String tipoParametroId,
            EntityManager em) throws JPAException;

    /**
     * Consulta parametro por medio de la llave primaria.
     * 
     * @param parameterCode
     * @param em
     * @return
     * @throws JPAException
     */
    public Parametro getParameterByCode(String parameterCode, EntityManager em)
            throws JPAException;

}
