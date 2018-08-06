package co.bdigital.cmm.jpa.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import co.bdigital.cmm.jpa.exception.EnumJPAException;
import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.framework.ServiceOperation;
import co.bdigital.cmm.jpa.model.framework.ServiceVersion;
import co.bdigital.cmm.jpa.services.TimeOutBrokerJPAService;

/**
 * @author eduardo.altamar@pragma.com.co
 *
 */
public class TimeOutBrokerJPAServiceIMPL implements TimeOutBrokerJPAService {

    public static final String COMMON_STRING_NAMESPACE = "namespace";
    public static final String COMMON_STRING_OPERATION_NAME = "operationName";
    public static final String COMMON_STRING_CATALOGUE = "serviceCatalog";
    public static final String COMMON_STRING_CATALOGUE_NAME = "catalogName";

    public TimeOutBrokerJPAServiceIMPL() {

    }

    /**
     * Metodo que consulta los timeOut de los servicios de Broker
     */
    @Override
    public ServiceVersion getTimeOut(EntityManager em, String namespace)
            throws JPAException {

        final String metodo = "getTimeOut";

        try {

            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<ServiceVersion> criteriaQuery = criteriaBuilder
                    .createQuery(ServiceVersion.class);

            Root<ServiceVersion> from = criteriaQuery
                    .from(ServiceVersion.class);
            Predicate condition = criteriaBuilder
                    .equal(from.get(COMMON_STRING_NAMESPACE), namespace);

            criteriaQuery.select(from);
            criteriaQuery.where(condition);

            em.getEntityManagerFactory().getCache().evict(ServiceVersion.class);
            TypedQuery<ServiceVersion> typedQuery = em
                    .createQuery(criteriaQuery);

            return typedQuery.getSingleResult();

        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + metodo, e);
        }
    }

    /**
     * Metodo que consulta los timeOut de las operaciones de Broker
     * 
     * @param em
     * @param name
     * @param operation
     * @return
     * @throws JPAException
     */
    @Override
    public ServiceOperation getOperationTimeOut(EntityManager em, String name,
            String operation) throws JPAException {

        final String metodo = "getOperationTimeOut";

        try {

            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

            CriteriaQuery<ServiceOperation> criteriaQuery = criteriaBuilder
                    .createQuery(ServiceOperation.class);

            Root<ServiceOperation> from = criteriaQuery
                    .from(ServiceOperation.class);

            List<Predicate> conditions = new ArrayList<Predicate>();

            conditions
                    .add(criteriaBuilder.equal(from.get(COMMON_STRING_CATALOGUE)
                            .get(COMMON_STRING_CATALOGUE_NAME), name));

            conditions.add(criteriaBuilder
                    .equal(from.get(COMMON_STRING_OPERATION_NAME), operation));

            criteriaQuery.select(from);
            criteriaQuery.where(conditions.toArray(new Predicate[] {}));

            em.getEntityManagerFactory().getCache()
                    .evict(ServiceOperation.class);

            TypedQuery<ServiceOperation> typedQuery = em
                    .createQuery(criteriaQuery);

            return typedQuery.getSingleResult();

        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + metodo, e);
        }
    }

}