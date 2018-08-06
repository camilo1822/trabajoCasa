package co.bdigital.cmm.jpa.services;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.Canal;

public interface ChannelJPAService {

    /**
     * Metodo que retorna el canal por Id
     * 
     * @param em
     * @param id
     * @return Canal
     * @throws JPAException
     */
    public Canal getChannelById(EntityManager em, String id)
            throws JPAException;

    /**
     * <p>
     * M&eacute;todo que consulta un canal por ID y por regi&oacute;n.
     * </p>
     * 
     * @param em
     * @param id
     *            ID del canal
     * @param countryCode
     *            c&oacute;digo del pa&iacute;s
     * @return Canal
     * @throws JPAException
     */
    public Canal getChannelByIdAndCountryCode(EntityManager em, String id,
            String countryCode) throws JPAException;

}
