package co.bdigital.cmm.jpa.services;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.AwAccion;

/**
 * @author eduardo.altamar@pragma.com.co
 *
 */
public interface AWAccionJPAService {

    /**
     * Metodo para devolver una entidad <code>AwAccion</code> por llave primaria
     * 
     * @param idAccion
     * @param em
     * @return <code>AwAccion</code>
     * @throws JPAException
     */
    public AwAccion getAWAccion(long idAccion, EntityManager em)
            throws JPAException;

}
