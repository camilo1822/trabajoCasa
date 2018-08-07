package co.bdigital.cmm.jpa.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import co.bdigital.cmm.jpa.exception.EnumJPAException;
import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.PromocionComercio;
import co.bdigital.cmm.jpa.model.PromocionOperacion;
import co.bdigital.cmm.jpa.model.PromocionRegla;
import co.bdigital.cmm.jpa.model.PromocionUsuario;
import co.bdigital.cmm.jpa.services.PromocionUsuarioJPAService;
import co.bdigital.cmm.jpa.util.ConstantJPA;
import co.bdigital.cmm.jpa.util.JPAUtil;

/**
 * @author juan.arboleda
 *
 */
public class PromocionUsuarioJPAServiceIMPL
        implements PromocionUsuarioJPAService {

    private static PromocionUsuarioJPAServiceIMPL instance;

    public static PromocionUsuarioJPAServiceIMPL getInstance() {
        if (null == instance)
            instance = new PromocionUsuarioJPAServiceIMPL();
        return instance;
    }

    @Override
    public void persistPromocionUsuario(PromocionUsuario promocionUsuario,
            EntityManager em) throws JPAException {
        try {
            Date currentDate = new Date();
            Timestamp time = new Timestamp(currentDate.getTime());
            promocionUsuario.setFechaCreacion(time);
            promocionUsuario
                    .setUsuarioCreacion(ConstantJPA.COMMON_STRING_NEQUI_UPPER);
            em.persist(promocionUsuario);

        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    JPAUtil.generateStringConcatenated(
                            this.getClass().getName(),
                            ConstantJPA.COMMON_STRING_PERSIST_PROMOCION_USUARIO),
                    e);
        }
    }

    @Override
    public PromocionOperacion getPromocionOperacionById(String id,
            EntityManager em) throws JPAException {

        try {
            return em.find(PromocionOperacion.class, Long.parseLong(id));

        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    JPAUtil.generateStringConcatenated(
                            this.getClass().getName(),
                            ConstantJPA.COMMON_STRING_METHOD_GET_PROMOCION_OPERACION),
                    e);
        }
    }

    @Override
    public void deleteAllPromocionUsuarioByPromocionOperacion(
            String promcionOperacionId, EntityManager em) throws JPAException {

        try {
            Query query = em.createNamedQuery(
                    ConstantJPA.ELIMINA_REGISTRO_PROMOCION_USUARIO);

            query.setParameter(
                    ConstantJPA.COMMON_PRAMETER_PROMOCION_OPERACION_ID,
                    Long.parseLong(promcionOperacionId));
            em.getEntityManagerFactory().getCache()
                    .evict(PromocionUsuario.class);

            query.executeUpdate();

        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    JPAUtil.generateStringConcatenated(
                            this.getClass().getName(),
                            ConstantJPA.COMMON_STRING_DELETE_PROMOCION_USUARIO),
                    e);
        }

    }

    @Override
    public List<PromocionUsuario> getPromocionUsuario(String id,
            EntityManager em) throws JPAException {

        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<PromocionUsuario> criteriaQuery = criteriaBuilder
                    .createQuery(PromocionUsuario.class);
            Root<PromocionUsuario> from = criteriaQuery
                    .from(PromocionUsuario.class);
            Predicate condition = criteriaBuilder.equal(
                    from.get(ConstantJPA.COMMON_STRING_PROMOCIO_OPERACION)
                            .get(ConstantJPA.COMMON_STRING_PROMOCIO_OPERACION_ID),
                    Long.parseLong(id));

            criteriaQuery.select(from);
            criteriaQuery.where(condition);

            TypedQuery<PromocionUsuario> typedQuery = em
                    .createQuery(criteriaQuery);

            return typedQuery.getResultList();

        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    JPAUtil.generateStringConcatenated(
                            this.getClass().getName(),
                            ConstantJPA.COMMON_STRING_METHOD_GET_PROMOCION_USUARIO),
                    e);
        }
    }

    @Override
    public String getPromocionUsuarioSize(String id, EntityManager em)
            throws JPAException {

        try {
            Query query = em
                    .createNamedQuery(ConstantJPA.COUNT_PROMOCION_USUARIO_ROWS);

            query.setParameter(
                    ConstantJPA.COMMON_PRAMETER_PROMOCION_OPERACION_ID,
                    Long.parseLong(id));

            return query.getSingleResult().toString();

        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    JPAUtil.generateStringConcatenated(
                            this.getClass().getName(),
                            ConstantJPA.COMMON_STRING_METHOD_GET_PROMOCION_USUARIO_SIZE),
                    e);
        }

    }

    @Override
    public void persistPromocionComercio(PromocionComercio promocionComercio,
            EntityManager em) throws JPAException {
        try {
            Date currentDate = new Date();
            Timestamp time = new Timestamp(currentDate.getTime());
            promocionComercio.setFechaCreacion(time);
            promocionComercio
                    .setUsrCreacion(ConstantJPA.COMMON_STRING_NEQUI_UPPER);
            em.persist(promocionComercio);

        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    JPAUtil.generateStringConcatenated(
                            this.getClass().getName(),
                            ConstantJPA.COMMON_STRING_PERSIST_PROMOCION_COMERCIO),
                    e);
        }
    }

    @Override
    public void deleteAllPromocionComercioByPromocionOperacion(
            String promocionOperacionId, EntityManager em) throws JPAException {

        try {
            Query query = em.createNamedQuery(
                    ConstantJPA.ELIMINA_REGISTRO_PROMOCION_COMERCIO);

            query.setParameter(
                    ConstantJPA.COMMON_PRAMETER_PROMOCION_OPERACION_ID,
                    Long.parseLong(promocionOperacionId));
            em.getEntityManagerFactory().getCache()
                    .evict(PromocionUsuario.class);

            query.executeUpdate();

        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    JPAUtil.generateStringConcatenated(
                            this.getClass().getName(),
                            ConstantJPA.COMMON_STRING_DELETE_PROMOCION_USUARIO),
                    e);
        }

    }

    @Override
    public List<PromocionComercio> getPromocionComercio(String id,
            EntityManager em) throws JPAException {
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<PromocionComercio> criteriaQuery = criteriaBuilder
                    .createQuery(PromocionComercio.class);
            Root<PromocionComercio> from = criteriaQuery
                    .from(PromocionComercio.class);
            Predicate condition = criteriaBuilder.equal(
                    from.get(ConstantJPA.COMMON_STRING_PROMOCIO_OPERACION)
                            .get(ConstantJPA.COMMON_STRING_PROMOCIO_OPERACION_ID),
                    Long.parseLong(id));

            criteriaQuery.select(from);
            criteriaQuery.where(condition);

            TypedQuery<PromocionComercio> typedQuery = em
                    .createQuery(criteriaQuery);

            return typedQuery.getResultList();

        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    JPAUtil.generateStringConcatenated(
                            this.getClass().getName(),
                            ConstantJPA.COMMON_STRING_METHOD_GET_PROMOCION_COMERCIO),
                    e);
        }
    }

    @Override
    public List<PromocionUsuario> getPromocionUsuarioLimit(String id,
            String limit, EntityManager em) throws JPAException {

        try {

            TypedQuery<PromocionUsuario> query = em.createNamedQuery(
                    ConstantJPA.NAMED_QUERY_PROMOCION_USUARIO_GET_PROMOCION_USUARIO_LIMIT,
                    PromocionUsuario.class);

            query.setParameter(
                    ConstantJPA.COMMON_PRAMETER_PROMOCION_OPERACION_ID,
                    Long.valueOf(id));

            query.setMaxResults(Integer.valueOf(limit));

            return query.getResultList();

        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    JPAUtil.generateStringConcatenated(
                            this.getClass().getName(),
                            ConstantJPA.COMMON_STRING_METHOD_GET_PROMOCION_USUARIO_LIMIT),
                    e);
        }
    }

    @Override
    public String getPromocionReglaMaxId(EntityManager em) throws JPAException {

        try {
            Query query = em.createNamedQuery(
                    ConstantJPA.NAMED_QUERY_PROMOCION_REGLA_GET_MAX_ID);

            return query.getSingleResult().toString();

        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    JPAUtil.generateStringConcatenated(
                            this.getClass().getName(),
                            ConstantJPA.COMMON_STRING_METHOD_GET_PROMOCION_REGLA_MAX_ID),
                    e);
        }

    }

    @Override
    public void persistPromocionRegla(PromocionRegla promocionRegla,
            EntityManager em) throws JPAException {
        try {
            Date currentDate = new Date();
            Timestamp time = new Timestamp(currentDate.getTime());
            promocionRegla.setFechaCreacion(time);
            promocionRegla
                    .setUsrCreacion(ConstantJPA.COMMON_STRING_NEQUI_UPPER);
            em.persist(promocionRegla);

        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    JPAUtil.generateStringConcatenated(
                            this.getClass().getName(),
                            ConstantJPA.COMMON_STRING_PERSIST_PROMOCION_REGLA),
                    e);
        }
    }

    @Override
    public PromocionOperacion getPromocionOperacionByService(String servicio,
            String region, EntityManager em) throws JPAException {
        try {
            TypedQuery<PromocionOperacion> query = em.createNamedQuery(
                    ConstantJPA.NAMED_QUERY_PROMOCION_REGLA_FIND_BY_SERVICE,
                    PromocionOperacion.class);

            query.setParameter(ConstantJPA.COMMON_STRING_SERVICE_PARAMETER,
                    servicio);
            query.setParameter(ConstantJPA.COMMON_STRING_REGION_PARAMETER,
                    region);

            return query.getSingleResult();

        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    JPAUtil.generateStringConcatenated(
                            this.getClass().getName(),
                            ConstantJPA.METHOD_GET_PROMOTION_OPERATION_BY_SERVICE),
                    e);
        }

    }

    @Override
    public String getPromocionOperacionMaxId(EntityManager em)
            throws JPAException {

        try {
            Query query = em.createNamedQuery(
                    ConstantJPA.NAMED_QUERY_PROMOCION_OPERACION_GET_MAX_ID);

            return query.getSingleResult().toString();

        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    JPAUtil.generateStringConcatenated(
                            this.getClass().getName(),
                            ConstantJPA.COMMON_STRING_METHOD_GET_PROMOCION_OPERACION_MAX_ID),
                    e);
        }

    }

    @Override
    public void persistPromocionOperacion(PromocionOperacion promocionOperacion,
            EntityManager em) throws JPAException {
        try {
            Date currentDate = new Date();
            Timestamp time = new Timestamp(currentDate.getTime());
            promocionOperacion.setFechaCreacion(time);
            promocionOperacion
                    .setUsrCreacion(ConstantJPA.COMMON_STRING_NEQUI_UPPER);
            em.persist(promocionOperacion);

        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    JPAUtil.generateStringConcatenated(
                            this.getClass().getName(),
                            ConstantJPA.COMMON_STRING_PERSIST_PROMOCION_REGLA),
                    e);
        }
    }

}
