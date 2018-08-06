/**
 * 
 */
package co.bdigital.cmm.jpa.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import co.bdigital.cmm.jpa.exception.EnumJPAException;
import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.EstadoTransaccionPse;
import co.bdigital.cmm.jpa.services.EstadoTransaccionPseJPAService;

/**
 * @author hansel.ospino@pragma.com.co
 *
 */
public class EstadoTransaccionPseJPAServiceIMPL implements
        EstadoTransaccionPseJPAService {

    private static final String COMMON_STRING_NOMBRE = "etNombre";
    private static final String COMMON_STRING_ID = "etId";

    public EstadoTransaccionPseJPAServiceIMPL() {
    }

    @Override
    public EstadoTransaccionPse getEstadoTransaccionPseByNombre(String nombre,
            EntityManager em) throws JPAException {

        final String metodo = "getEstadoTransaccionPseByNombre";

        try {
            if (nombre != null && !nombre.trim().isEmpty()) {

                CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
                CriteriaQuery<EstadoTransaccionPse> criteriaQuery = criteriaBuilder
                        .createQuery(EstadoTransaccionPse.class);
                Root<EstadoTransaccionPse> from = criteriaQuery
                        .from(EstadoTransaccionPse.class);

                criteriaQuery.where(criteriaBuilder.equal(
                        from.get(COMMON_STRING_NOMBRE), nombre.trim()));
                criteriaQuery.select(from);

                TypedQuery<EstadoTransaccionPse> typedQuery = em
                        .createQuery(criteriaQuery);
                return typedQuery.getSingleResult();

            } else {
                return null;
            }

        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + metodo, e);
        }

    }

    @Override
    public EstadoTransaccionPse getEstadoTransaccionPseById(
            String transactionStatusId, EntityManager em) throws JPAException {

        final String metodo = "getEstadoTransaccionPseById";

        try {
            if (transactionStatusId != null
                    && !transactionStatusId.trim().isEmpty()) {

                CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
                CriteriaQuery<EstadoTransaccionPse> criteriaQuery = criteriaBuilder
                        .createQuery(EstadoTransaccionPse.class);
                Root<EstadoTransaccionPse> from = criteriaQuery
                        .from(EstadoTransaccionPse.class);

                criteriaQuery
                        .where(criteriaBuilder.equal(
                                from.get(COMMON_STRING_ID),
                                transactionStatusId.trim()));
                criteriaQuery.select(from);

                TypedQuery<EstadoTransaccionPse> typedQuery = em
                        .createQuery(criteriaQuery);
                return typedQuery.getSingleResult();

            } else {
                return null;
            }

        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + metodo, e);
        }

    }

    @Override
    public List<EstadoTransaccionPse> getAllEstadoTransaccionPse(
            EntityManager em) throws JPAException {

        final String metodo = "getAllEstadoTransaccionPse";

        try {

            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<EstadoTransaccionPse> criteriaQuery = criteriaBuilder
                    .createQuery(EstadoTransaccionPse.class);
            Root<EstadoTransaccionPse> from = criteriaQuery
                    .from(EstadoTransaccionPse.class);

            criteriaQuery.select(from);

            TypedQuery<EstadoTransaccionPse> typedQuery = em
                    .createQuery(criteriaQuery);
            return typedQuery.getResultList();

        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + metodo, e);
        }

    }

}
