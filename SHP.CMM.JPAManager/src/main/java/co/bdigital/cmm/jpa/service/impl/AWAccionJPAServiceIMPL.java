package co.bdigital.cmm.jpa.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import co.bdigital.cmm.jpa.exception.EnumJPAException;
import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.AwAccion;
import co.bdigital.cmm.jpa.services.AWAccionJPAService;

/**
 * @author eduardo.altamar@pragma.com.co
 *
 */
public class AWAccionJPAServiceIMPL implements AWAccionJPAService {

    private static AWAccionJPAServiceIMPL instance;

    public static AWAccionJPAServiceIMPL getInstance() {
        if (null == instance)
            instance = new AWAccionJPAServiceIMPL();
        return instance;
    }

    /**
     * Metodo para devolver la lista de acciones de la web administrativa.
     * 
     * @param em
     * @return <code>AwAccion</code>
     * @throws JPAException
     */
    public AwAccion getAWAccion(long idAccion, EntityManager em)
            throws JPAException {

        final String metodo = "getAWAccion";
        AwAccion awAccion;
        try {

            awAccion = em.find(AwAccion.class, idAccion);
            return awAccion;

        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + metodo, e);
        }
    }

}
