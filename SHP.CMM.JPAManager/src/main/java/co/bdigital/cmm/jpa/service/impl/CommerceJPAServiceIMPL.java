/**
 * 
 */
package co.bdigital.cmm.jpa.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import co.bdigital.cmm.jpa.exception.EnumJPAException;
import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.Comercio;
import co.bdigital.cmm.jpa.model.Dispositivo;
import co.bdigital.cmm.jpa.services.CommerceJPAServie;

/**
 * @author eduardo.altamar@pragma.com.co
 *
 */

public class CommerceJPAServiceIMPL implements CommerceJPAServie {

    private static CommerceJPAServiceIMPL instance;
    private static final String COMMON_STRING_CELULAR = "celular";
    private static final String COMMON_STRING_DEVICE = "dispositivos";

    public CommerceJPAServiceIMPL() {
    }

    public static CommerceJPAServiceIMPL getInstance() {
        if (null == instance)
            instance = new CommerceJPAServiceIMPL();
        return instance;
    }

    /**
     * Metodo que consulta en BD un Comercio filtrando por celular
     * 
     * @param cellPhoneNumber
     * @param em
     * @return Comercio
     */
    @Override
    public Comercio getCommerceByPhone(String cellPhoneNumber, EntityManager em)
            throws JPAException {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

        CriteriaQuery<Comercio> criteriaQuery = criteriaBuilder
                .createQuery(Comercio.class);

        Root<Comercio> from = criteriaQuery.from(Comercio.class);
        Join<Comercio, Dispositivo> device = from.join(COMMON_STRING_DEVICE);

        List<Predicate> conditions = new ArrayList<Predicate>();

        conditions.add(criteriaBuilder.equal(device.get(COMMON_STRING_CELULAR),
                cellPhoneNumber));

        criteriaQuery.select(from);
        criteriaQuery.where(conditions.toArray(new Predicate[] {}));

        em.getEntityManagerFactory().getCache().evict(Comercio.class);
        TypedQuery<Comercio> typedQuery = em.createQuery(criteriaQuery);

        try {
            List<Comercio> commerceAux = typedQuery.getResultList();
            if (!commerceAux.isEmpty())
                return commerceAux.get(0);

        } catch (NoResultException e) {
            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + this.getClass().getName(), e);
        }
        return null;
    }

    /**
     * Metodo que consulta en BD un Comercio filtrando por ID
     * 
     * @param id
     * @param em
     * @return Comercio
     */
    @Override
    public Comercio getCommerceByCode(String id, EntityManager em)
            throws JPAException {
        try {
            Comercio comercio = em.find(Comercio.class, id);

            return comercio;

        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + this.getClass().getName(), e);
        }
    }

}
