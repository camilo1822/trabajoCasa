package co.bdigital.cmm.jpa.services;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.TipoEstadoCat;

public interface TipoEstadoCatsJPAServices {

    /**
     * Método que consulta en BD el tipo estado CATS por estado
     * 
     * @param estado
     * @param em
     * @return TipoEstadoCat
     * @throws JPAException
     */
    public TipoEstadoCat getTipoEstadoCatByStatus(String estado,
            EntityManager em) throws JPAException;
}
