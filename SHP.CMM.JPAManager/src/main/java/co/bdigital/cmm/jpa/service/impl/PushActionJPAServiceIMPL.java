/**
 * 
 */
package co.bdigital.cmm.jpa.service.impl;

import java.util.ArrayList;
import java.util.Date;
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
import co.bdigital.cmm.jpa.model.Cliente;
import co.bdigital.cmm.jpa.model.EstadoPushAction;
import co.bdigital.cmm.jpa.model.PushAction;
import co.bdigital.cmm.jpa.services.PushActionJPAService;

/**
 * @author hansel.ospino@pragma.com.co
 *
 */
public class PushActionJPAServiceIMPL implements PushActionJPAService {

    private static final String COMMON_STRING_PUSH_ACTION_ID = "pushActionId";
    private static final String COMMON_STRING_CLIENTE_ID = "clienteId";
    private static final String COMMON_STRING_ESTADO_PUSH_ACTION_ID = "estadoPushActionId";
    private static final String COMMON_STRING_TIPO_PUSH_ACTION_ID = "tipoPushAction";
    private static final String COMMON_STRING_CREATION_DATE_ID = "fechaCreacion";
    private static final String COMMON_STRING_TRANSACTION_ID = "transactionId";
    private static final String COMMON_STRING_TICKET_ID = "ticketId";
    private static final String COMMON_STRING_TRAZABILITY_CODE = "trazabilityCode";

    /**
	 * 
	 */
    public PushActionJPAServiceIMPL() {
    }

    @Override
    public List<PushAction> getPushActionByClientAndTypeAndStatusAndStartDate(
            Long clientId, String pushActionStatusId, String pushActionTypeId,
            Date startDate, EntityManager em) throws JPAException {

        final String metodo = "getPushActionByClientAndTypeAndStatusAndStartDate";

        try {

            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<PushAction> criteriaQuery = criteriaBuilder
                    .createQuery(PushAction.class);
            Root<PushAction> from = criteriaQuery.from(PushAction.class);

            Join<PushAction, EstadoPushAction> statusJoin = from
                    .join(COMMON_STRING_ESTADO_PUSH_ACTION_ID);

            Join<PushAction, Cliente> clientJoin = from
                    .join(COMMON_STRING_CLIENTE_ID);

            List<Predicate> conditions = new ArrayList<Predicate>();

            conditions.add(criteriaBuilder.equal(
                    statusJoin.get(COMMON_STRING_ESTADO_PUSH_ACTION_ID),
                    pushActionStatusId));

            conditions.add(criteriaBuilder.equal(
                    clientJoin.get(COMMON_STRING_CLIENTE_ID), clientId));

            conditions.add(criteriaBuilder.equal(
                    from.get(COMMON_STRING_TIPO_PUSH_ACTION_ID),
                    pushActionTypeId.trim()));

            conditions
                    .add(criteriaBuilder.greaterThanOrEqualTo(
                            from.<Date> get(COMMON_STRING_CREATION_DATE_ID),
                            startDate));

            criteriaQuery.select(from);
            criteriaQuery.where(conditions.toArray(new Predicate[] {}));
            criteriaQuery.orderBy(criteriaBuilder.desc(from
                    .get(COMMON_STRING_CREATION_DATE_ID)));

            TypedQuery<PushAction> typedQuery = em.createQuery(criteriaQuery);
            return typedQuery.getResultList();

        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + metodo, e);
        }

    }

    @Override
    public PushAction getPushActionById(Long pushActionId, EntityManager em)
            throws JPAException {

        final String metodo = "getPushActionById";

        try {

            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<PushAction> criteriaQuery = criteriaBuilder
                    .createQuery(PushAction.class);
            Root<PushAction> from = criteriaQuery.from(PushAction.class);

            List<Predicate> conditions = new ArrayList<Predicate>();

            conditions.add(criteriaBuilder.equal(
                    from.get(COMMON_STRING_PUSH_ACTION_ID), pushActionId));

            criteriaQuery.select(from);
            criteriaQuery.where(conditions.toArray(new Predicate[] {}));

            TypedQuery<PushAction> typedQuery = em.createQuery(criteriaQuery);
            return typedQuery.getSingleResult();

        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + metodo, e);
        }

    }

    @Override
    public PushAction getPushActionByIdAndClientId(Long pushActionId,
            Long clientId, EntityManager em) throws JPAException {

        final String metodo = "getPushActionByIdAndClientId";

        try {

            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<PushAction> criteriaQuery = criteriaBuilder
                    .createQuery(PushAction.class);
            Root<PushAction> from = criteriaQuery.from(PushAction.class);

            Join<PushAction, Cliente> clientJoin = from
                    .join(COMMON_STRING_CLIENTE_ID);

            List<Predicate> conditions = new ArrayList<Predicate>();

            conditions.add(criteriaBuilder.equal(
                    from.get(COMMON_STRING_PUSH_ACTION_ID), pushActionId));

            conditions.add(criteriaBuilder.equal(
                    clientJoin.get(COMMON_STRING_CLIENTE_ID), clientId));

            criteriaQuery.select(from);
            criteriaQuery.where(conditions.toArray(new Predicate[] {}));

            TypedQuery<PushAction> typedQuery = em.createQuery(criteriaQuery);
            return typedQuery.getSingleResult();

        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + metodo, e);
        }

    }

    @Override
    public PushAction getPushActionByTransactionIdAndClientId(
            String transactionId, Long clientId, EntityManager em)
            throws JPAException {

        final String metodo = "getPushActionByTransactionIdAndClientId";

        try {

            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<PushAction> criteriaQuery = criteriaBuilder
                    .createQuery(PushAction.class);
            Root<PushAction> from = criteriaQuery.from(PushAction.class);

            Join<PushAction, Cliente> clientJoin = from
                    .join(COMMON_STRING_CLIENTE_ID);

            List<Predicate> conditions = new ArrayList<Predicate>();

            conditions.add(criteriaBuilder.equal(
                    from.get(COMMON_STRING_TRANSACTION_ID), transactionId));

            conditions.add(criteriaBuilder.equal(
                    clientJoin.get(COMMON_STRING_CLIENTE_ID), clientId));

            criteriaQuery.select(from);
            criteriaQuery.where(conditions.toArray(new Predicate[] {}));

            TypedQuery<PushAction> typedQuery = em.createQuery(criteriaQuery);
            return typedQuery.getSingleResult();

        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + metodo, e);
        }

    }

    /**
     * <p>
     * Consulta de un push action para un cliente y ticket id especificado.
     * </p>
     * 
     * @param ticketId
     * @param clientId
     * @param em
     * @return PushAction para el cliente y el ticket id especificado.
     * @throws JPAException
     */
    @Override
    public PushAction getPushActionByTicketIdAndClientId(String ticketId,
            Long clientId, EntityManager em) throws JPAException {

        final String metodo = "getPushActionByTicketIdAndClientId";

        try {

            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<PushAction> criteriaQuery = criteriaBuilder
                    .createQuery(PushAction.class);
            Root<PushAction> from = criteriaQuery.from(PushAction.class);

            Join<PushAction, Cliente> clientJoin = from
                    .join(COMMON_STRING_CLIENTE_ID);

            List<Predicate> conditions = new ArrayList<Predicate>();

            conditions.add(criteriaBuilder.equal(
                    from.get(COMMON_STRING_TICKET_ID), ticketId));

            conditions.add(criteriaBuilder.equal(
                    clientJoin.get(COMMON_STRING_CLIENTE_ID), clientId));

            criteriaQuery.select(from);
            criteriaQuery.where(conditions.toArray(new Predicate[] {}));

            TypedQuery<PushAction> typedQuery = em.createQuery(criteriaQuery);
            return typedQuery.getSingleResult();

        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + metodo, e);
        }

    }

    /**
     * <p>
     * Consulta de un push action para un cliente y ticket id especificado.
     * </p>
     * 
     * @param ticketId
     * @param clientId
     * @param trazabilityCode
     * @param em
     * @return PushAction para el cliente y el ticket id especificado.
     * @throws JPAException
     */
    @Override
    public PushAction getPushActionByTicketIdAndClientIdAndTrazabilityCode(
            String ticketId, Long clientId, String trazabilityCode,
            EntityManager em) throws JPAException {

        final String metodo = "getPushActionByTicketIdAndClientIdAndTrazabilityCode";

        try {

            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<PushAction> criteriaQuery = criteriaBuilder
                    .createQuery(PushAction.class);
            Root<PushAction> from = criteriaQuery.from(PushAction.class);

            Join<PushAction, Cliente> clientJoin = from
                    .join(COMMON_STRING_CLIENTE_ID);

            List<Predicate> conditions = new ArrayList<Predicate>();

            conditions.add(criteriaBuilder.equal(
                    from.get(COMMON_STRING_TICKET_ID), ticketId));

            conditions.add(criteriaBuilder.equal(
                    from.get(COMMON_STRING_TRAZABILITY_CODE), trazabilityCode));

            conditions.add(criteriaBuilder.equal(
                    clientJoin.get(COMMON_STRING_CLIENTE_ID), clientId));

            criteriaQuery.select(from);
            criteriaQuery.where(conditions.toArray(new Predicate[] {}));

            TypedQuery<PushAction> typedQuery = em.createQuery(criteriaQuery);
            return typedQuery.getSingleResult();

        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + metodo, e);
        }

    }
}
