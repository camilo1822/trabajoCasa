/**
 * 
 */
package co.bdigital.cmm.jpa.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import co.bdigital.cmm.jpa.exception.EnumJPAException;
import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.Cliente;
import co.bdigital.cmm.jpa.model.ReversoPendiente;
import co.bdigital.cmm.jpa.services.PendingReverselJPAService;
import co.bdigital.cmm.jpa.util.ConstantJPA;

/**
 * @author eduardo.altamar@pragma.com.co
 *
 */
public class PendingReverseJPAServiceIMPL implements PendingReverselJPAService {

    public static final String COMMON_STRING_STATUS = "estado";
    private static PendingReverseJPAServiceIMPL instance;

    public static PendingReverseJPAServiceIMPL getInstance() {
        if (null == instance)
            instance = new PendingReverseJPAServiceIMPL();
        return instance;
    }

    @Override
    public List<ReversoPendiente> getPendingReverseByStatus(String status,
            EntityManager em) throws JPAException {
        final String metodo = "getPendingReverseByStatus";

        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<ReversoPendiente> criteriaQuery = criteriaBuilder
                    .createQuery(ReversoPendiente.class);

            Root<ReversoPendiente> from = criteriaQuery
                    .from(ReversoPendiente.class);

            Predicate condition = criteriaBuilder.equal(
                    from.get(COMMON_STRING_STATUS), status);

            criteriaQuery.select(from);
            criteriaQuery.where(condition);
            em.getEntityManagerFactory().getCache().evict(Cliente.class);

            TypedQuery<ReversoPendiente> typedQuery = em
                    .createQuery(criteriaQuery);

            return typedQuery.getResultList();

        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + metodo, e);
        }
    }

    /**
     * Inserta un entity <code>ReversoPendiente</code> en Base de Datos.
     * 
     * @param reversoPendiente
     * @param em
     * @return <code>ReversoPendiente<code>
     * @throws JPAException
     */
    @Override
    public ReversoPendiente persistPendingReverse(
            ReversoPendiente reversoPendiente, EntityManager em)
            throws JPAException {

        try {

            Date fechaActual = new Date();
            Timestamp fechaActualTimestamp = new Timestamp(
                    fechaActual.getTime());
            reversoPendiente.setFechaCreacion(fechaActualTimestamp);
            reversoPendiente.setFechaModificacion(fechaActualTimestamp);
            reversoPendiente
                    .setUsuarioCreacion(ConstantJPA.COMMON_STRING_NEQUI_UPPER);

            em.persist(reversoPendiente);

            return reversoPendiente;

        } catch (Exception e) {
            StringBuilder errorString = new StringBuilder(
                    SeguridadJPAServiceIMPL.class.getCanonicalName());
            errorString.append(ConstantJPA.PERSIST_PENDING_REVERSE);
            throw new JPAException(errorString.toString(), e);
        }
    }

}
