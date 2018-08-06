package co.bdigital.cmm.jpa.service.impl;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.EnumJPAException;
import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.TipoEstadoCat;
import co.bdigital.cmm.jpa.services.TipoEstadoCatsJPAServices;

/**
 * 
 * @author john.perez
 *
 */
public class TipoEstadoCatsJPAServicesIMPL
        implements TipoEstadoCatsJPAServices {

    private static TipoEstadoCatsJPAServicesIMPL instance;

    public static TipoEstadoCatsJPAServicesIMPL getInstance() {
        if (null == instance)
            instance = new TipoEstadoCatsJPAServicesIMPL();
        return instance;
    }

    @Override
    public TipoEstadoCat getTipoEstadoCatByStatus(String estado,
            EntityManager em) throws JPAException {
        final String metodo = "getTipoEstadoCatByStatus";

        try {
            return em.find(TipoEstadoCat.class, estado);
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + metodo, e);
        }
    }

}
