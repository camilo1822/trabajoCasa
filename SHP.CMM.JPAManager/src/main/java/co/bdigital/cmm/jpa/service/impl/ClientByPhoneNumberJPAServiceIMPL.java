package co.bdigital.cmm.jpa.service.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.persistence.Cache;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import co.bdigital.cmm.jpa.exception.EnumJPAException;
import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.Cliente;
import co.bdigital.cmm.jpa.services.ClientByPhoneNumberJPAService;
import co.bdigital.cmm.jpa.util.ConstantJPA;

/**
 * @author john_perez
 *
 */
public class ClientByPhoneNumberJPAServiceIMPL
        implements ClientByPhoneNumberJPAService {

    public static final String COMMON_STRING_CIFID = "cifId";
    public static final String COMMON_STRING_CELULAR = "celular";
    public static final String COMMON_STRING_DOCUMENTID = "numeroId";
    public static final String COMMON_STRING_TIPO_ID = "tipoId";
    public static final String COMMON_STRING_PAIS_ID = "paisId";

    private static final String COMMON_STRING_CLIENT_DETAIL = "detalleClientes";
    private static final String COMMON_STRING_STATUS = "estadoId";
    private static final String COMMON_STRING_VALUE = "valor";
    private static final String COMMON_STRING_NAME = "nombre";
    private static final String COMMON_STRING_ID = "id";
    private static final String COMMON_STRING_MODIFICATION_DATE = "fechaModificacion";
    private static final String COMMON_STRING_CREATION_DATE = "FechaVinculacion";
    private static ClientByPhoneNumberJPAServiceIMPL instance = null;

    public static ClientByPhoneNumberJPAServiceIMPL getInstance() {
        if (null == instance)
            instance = new ClientByPhoneNumberJPAServiceIMPL();
        return instance;
    }

    @Override
    public Cliente getClientByPhoneNumber(String phoneNumber, EntityManager em)
            throws JPAException {

        final String metodo = "getClientByPhoneNumber";
        Cliente cliente = null;
        try {
            Query query = em
                    .createNamedQuery(ConstantJPA.QUERY_CLIENTE_BY_PHONENUMBER);
            query.setParameter(ConstantJPA.COMMON_PARAMETER_PHONENUMBER,
                    phoneNumber);
            cliente = (Cliente) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + metodo, e);
        }
        return cliente;
    }

    /**
     * Consulta de cliente por la llave primaria <code>clienteId</code>.
     * 
     * @param clienteId
     * @param em
     * @return <code>Cliente</code>
     * @throws JPAException
     */
    @Override
    public Cliente findClientByClienteId(Long clienteId, EntityManager em)
            throws JPAException {

        final String metodo = "findClientByClienteId";
        Cliente cliente;
        Cache cache;

        try {

            cache = em.getEntityManagerFactory().getCache();
            cache.evict(Cliente.class);

            cliente = em.find(Cliente.class, clienteId);

        } catch (IllegalArgumentException e) {

            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + metodo, e);

        } catch (Exception e) {

            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + metodo, e);

        }

        return cliente;
    }

    @Override
    public Cliente findClientByDocumentId(String documentId,
            String documentType, EntityManager em) throws JPAException {
        final String metodo = "findClientByDocumentId";
        Cliente cliente = null;
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder
                    .createQuery(Cliente.class);

            Cache cache = em.getEntityManagerFactory().getCache();
            cache.evictAll();
            Root<Cliente> from = criteriaQuery.from(Cliente.class);
            List<Predicate> conditions = new ArrayList<Predicate>();

            conditions.add(criteriaBuilder
                    .equal(from.get(COMMON_STRING_DOCUMENTID), documentId));

            if (null != documentType && !documentType.isEmpty()) {
                conditions.add(criteriaBuilder
                        .equal(from.get(COMMON_STRING_TIPO_ID), documentType));
            }
            criteriaQuery.select(from);
            criteriaQuery.where(conditions.toArray(new Predicate[] {}));

            TypedQuery<Cliente> typedQuery = em.createQuery(criteriaQuery);
            cliente = typedQuery.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + metodo, e);
        }
        return cliente;
    }

    /**
     * Consulta de cliente por documentId y por region
     * 
     * @param documentId
     * @param documentTypes
     * @param region
     * @param em
     * @return <code>Cliente</code>
     * @throws JPAException
     */
    public Cliente findClientByDocumentIdAndRegion(String documentId,
            String documentType, String region, EntityManager em)
            throws JPAException {
        final String metodo = "findClientByDocumentIdAndRegion";
        Cliente cliente = null;
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder
                    .createQuery(Cliente.class);

            Cache cache = em.getEntityManagerFactory().getCache();
            cache.evictAll();
            Root<Cliente> from = criteriaQuery.from(Cliente.class);
            List<Predicate> conditions = new ArrayList<Predicate>();

            conditions.add(criteriaBuilder
                    .equal(from.get(COMMON_STRING_DOCUMENTID), documentId));

            if (null != documentType && !documentType.isEmpty()) {
                conditions.add(criteriaBuilder
                        .equal(from.get(COMMON_STRING_TIPO_ID), documentType));
            }

            if (null != region && !region.isEmpty()) {
                conditions.add(criteriaBuilder
                        .equal(from.get(COMMON_STRING_PAIS_ID), region));
            }

            criteriaQuery.select(from);
            criteriaQuery.where(conditions.toArray(new Predicate[] {}));

            TypedQuery<Cliente> typedQuery = em.createQuery(criteriaQuery);
            cliente = typedQuery.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + metodo, e);
        }
        return cliente;
    }

    /**
     * Consulta de cliente por el number de celular sin usar caché L2.
     * 
     * @param phoneNumber
     * @param em
     * @return <code>Cliente</code>
     * @throws JPAException
     */
    @Override
    public Cliente getClientByPhoneNumberNoCache(String phoneNumber,
            EntityManager em) throws JPAException {

        final String metodo = "getClientByPhoneNumberNoCache";
        Cliente cliente = null;
        try {

            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder
                    .createQuery(Cliente.class);

            Cache cache = em.getEntityManagerFactory().getCache();
            cache.evictAll();
            Root<Cliente> from = criteriaQuery.from(Cliente.class);
            Predicate condition1 = criteriaBuilder
                    .equal(from.get(COMMON_STRING_CELULAR), phoneNumber);

            criteriaQuery.select(from);
            criteriaQuery.where(condition1);

            TypedQuery<Cliente> typedQuery = em.createQuery(criteriaQuery);
            cliente = typedQuery.getSingleResult();
            em.refresh(cliente);
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + metodo, e);
        }
        return cliente;
    }

    /**
     * Consulta de cliente por el CIFID
     * 
     * @param cifId
     * @param em
     * @return <code>Cliente</code>
     * @throws JPAException
     */
    @Override
    public Cliente getClientByCifId(String cifId, EntityManager em)
            throws JPAException {

        final String metodo = "getClientByCifId";
        Cliente cliente = null;
        try {

            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder
                    .createQuery(Cliente.class);

            Cache cache = em.getEntityManagerFactory().getCache();
            cache.evictAll();
            Root<Cliente> from = criteriaQuery.from(Cliente.class);
            Predicate condition1 = criteriaBuilder
                    .equal(from.get(COMMON_STRING_CIFID), cifId);

            criteriaQuery.select(from);
            criteriaQuery.where(condition1);

            TypedQuery<Cliente> typedQuery = em.createQuery(criteriaQuery);
            cliente = typedQuery.getSingleResult();
            em.refresh(cliente);
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + metodo, e);
        }
        return cliente;
    }

    @Override
    public List<Cliente> getClientByDate(Date vinculationDate, EntityManager em)
            throws JPAException {

        final String metodo = "getClientByDate";
        try {

            // Se hace parseo de fecha para eliminar time y asignar por defecto
            // 00:00:00
            DateFormat sdf = new SimpleDateFormat(
                    ConstantJPA.COMMON_FORMAT_DATE_WITHOUT_TIME);
            DateFormat formatoFinacle = new SimpleDateFormat(
                    ConstantJPA.COMMON_STRING_DATE_LARGE_FORMAT);
            // Se hace cálculo para obtener la hora de inicio del día
            Date minDate = sdf.parse(sdf.format(vinculationDate));
            // Se hace cálculo para obtener la hora de fin del día (23:59)
            Date maxDate = new Date(
                    minDate.getTime() + TimeUnit.DAYS.toMillis(1));
            String minDateString = formatoFinacle.format(minDate);
            String maxDateString = formatoFinacle.format(maxDate);

            // Se inicializan variables con estados de cliente que vamos a
            // validar
            Long estadoVinculado = Long
                    .valueOf(ConstantJPA.COMMON_STRING_THREE);
            Long estadoCuentaCerrada = Long
                    .valueOf(ConstantJPA.COMMON_STRING_DOSCIENTOS);

            Cache cache = em.getEntityManagerFactory().getCache();
            cache.evictAll();
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder
                    .createQuery(Cliente.class);

            Root<Cliente> from = criteriaQuery.from(Cliente.class);

            Path<String> pathClientDetail = from
                    .get(COMMON_STRING_CLIENT_DETAIL).get(COMMON_STRING_VALUE);
            Path<Date> pathClientModificationDate = from
                    .get(COMMON_STRING_MODIFICATION_DATE);

            // Se crea condicion que valida que se cumplan uno de los
            // estados requeridos Y dependiendo del estado, se hace respectiva
            // lógica:
            // Se crea consulta para obtener registro donde el valor del campo
            // sea 'FechaVinculacion' y validar que sea menor a 24 hrs en caso
            // de que el estado del cliente sea vinculado.
            // Si el estado es de cuenta cerrada, la validación se hace contra
            // la fecha de modificación

            Predicate conditions = criteriaBuilder
                    .or(criteriaBuilder.and(
                            criteriaBuilder
                                    .equal(from.get(COMMON_STRING_STATUS),
                                            BigDecimal.valueOf(Long
                                                    .valueOf(estadoVinculado))),
                            criteriaBuilder.and(
                                    criteriaBuilder.equal(
                                            from.get(
                                                    COMMON_STRING_CLIENT_DETAIL)
                                                    .get(COMMON_STRING_ID)
                                                    .get(COMMON_STRING_NAME),
                                            COMMON_STRING_CREATION_DATE),
                                    criteriaBuilder.between(pathClientDetail,
                                            minDateString, maxDateString))),

                            criteriaBuilder.and(criteriaBuilder.equal(
                                    from.get(COMMON_STRING_STATUS),
                                    BigDecimal.valueOf(
                                            Long.valueOf(estadoCuentaCerrada))),

                                    criteriaBuilder.between(
                                            pathClientModificationDate, minDate,
                                            maxDate)));

            criteriaQuery.select(from);
            criteriaQuery.where(conditions);
            TypedQuery<Cliente> typedQuery = em.createQuery(criteriaQuery);

            List<Cliente> resultado = new ArrayList<Cliente>();

            resultado = typedQuery.getResultList();
            if (null == resultado || resultado.isEmpty()) {
                return null;
            }
            return resultado;

        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + metodo, e);
        }
    }

    /**
     * Consulta de cliente por el número de celular y region
     * 
     * @param phoneNumber
     * @param em
     * @return Cliente
     * @param region
     * @throws JPAException
     */
    public Cliente getClientByPhoneNumberAndRegion(String phoneNumber,
            String region, EntityManager em) throws JPAException {
        final String metodo = "getClientByPhoneNumber";
        Cliente cliente = null;
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder
                    .createQuery(Cliente.class);
            Cache cache = em.getEntityManagerFactory().getCache();
            cache.evictAll();
            Root<Cliente> from = criteriaQuery.from(Cliente.class);
            List<Predicate> conditions = new ArrayList<Predicate>();

            conditions.add(criteriaBuilder
                    .equal(from.get(COMMON_STRING_CELULAR), phoneNumber));

            if (null != region && !region.isEmpty()) {
                conditions.add(criteriaBuilder
                        .equal(from.get(COMMON_STRING_PAIS_ID), region));
            }

            criteriaQuery.select(from);
            criteriaQuery.where(conditions.toArray(new Predicate[] {}));

            TypedQuery<Cliente> typedQuery = em.createQuery(criteriaQuery);
            cliente = typedQuery.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + metodo, e);
        }
        return cliente;
    }

}
