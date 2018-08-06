package co.bdigital.cmm.jpa.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cache;
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
import co.bdigital.cmm.jpa.model.BloqueoUsuario;
import co.bdigital.cmm.jpa.services.BloqueoUsuarioJPAService;
import co.bdigital.cmm.jpa.util.ConstantJPA;

public class BloqueoUsuarioJPAServiceIMPL implements BloqueoUsuarioJPAService {

    private static BloqueoUsuarioJPAServiceIMPL instance;

    public static BloqueoUsuarioJPAServiceIMPL getInstance() {
        if (null == instance)
            instance = new BloqueoUsuarioJPAServiceIMPL();
        return instance;
    }

    /**
     * Metodo que retorna lista de bloqueos de usuario por clientId
     * 
     * @param clientId
     * @param em
     * @return List<BloqueoUsuario>
     * @throws JPAException
     */
    @Override
    public List<BloqueoUsuario> getBloqueoUsuarioByClientId(String clientId,
            EntityManager em) throws JPAException {
        final String metodo = "getBloqueoUsuarioByClientId";

        try {
            Query query = em.createNamedQuery(
                    ConstantJPA.QUERY_BLOQUEOUSUARIO_BY_IDCLIENTE);
            query.setParameter(ConstantJPA.COMMON_PARAMETER_CLIENTID,
                    Long.parseLong(clientId));

            em.getEntityManagerFactory().getCache().evict(BloqueoUsuario.class);

            // si llegan varios resultados tomará solo el primero
            List<BloqueoUsuario> resultado = new ArrayList<BloqueoUsuario>();

            resultado = query.getResultList();
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
     * Metodo para borrar un bloqueoUsuario
     * 
     * @param bloqueoUsuario
     * @param em
     * @return
     * @throws JPAException
     */
    @Override
    public void removeBloqueoUsuario(BloqueoUsuario bloqueoUsuario,
            EntityManager em) throws JPAException {
        try {

            em.remove(bloqueoUsuario);

        } catch (Exception e) {
            StringBuilder errorString = new StringBuilder(
                    BloqueoUsuarioJPAServiceIMPL.class.getCanonicalName());
            errorString.append(ConstantJPA.METHOD_REMOVE_BLOQUEO_USUARIO);
            throw new JPAException(errorString.toString(), e);
        }
    }
    
    /**
     * Inserta un entity <code>BloqueoUsuario</code> en Base de Datos.
     * 
     * @param bloqueoUsuario
     * @param em
     * @throws JPAException
     */
    @Override
    public void persistBloqueoUsuario(BloqueoUsuario bloqueoUsuario,
            EntityManager em) throws JPAException {

        Timestamp fechaActualTimestamp;
        Date fechaActual;

        try {

            fechaActual = new Date();
            fechaActualTimestamp = new Timestamp(fechaActual.getTime());
            bloqueoUsuario.setFechaCreacion(fechaActualTimestamp);
            bloqueoUsuario.setFechaBloqueo(fechaActualTimestamp);
            bloqueoUsuario.setUsrCreacion(ConstantJPA.COMMON_STRING_MDW);

            em.persist(bloqueoUsuario);

        } catch (Exception e) {
            StringBuilder errorString = new StringBuilder(
                    BloqueoUsuarioJPAServiceIMPL.class.getCanonicalName());
            errorString.append(ConstantJPA.METHOD_PERSIST_BLOQUEO_USUARIO);
            throw new JPAException(errorString.toString(), e);
        }
    }
    
    /**
     * Consulta de <code>BloqueoUsuario</code> en la Base de Datos.
     * 
     * @param blockType
     * @param customerId
     * @param em
     * @throws JPAException
     */
    @Override
    public BloqueoUsuario getBloqueoUsuario(String blockType, long customerId,
            EntityManager em) throws JPAException {

        try {

            if (customerId <= ConstantJPA.COMMON_INT_ZERO || null == blockType
                    || blockType.trim().isEmpty()) {

                StringBuilder errorString = new StringBuilder(
                        BloqueoUsuarioJPAServiceIMPL.class.getCanonicalName());
                errorString
                        .append(ConstantJPA.ERROR_INCORRECT_INPUT_PARAMETERS);
                throw new JPAException(errorString.toString());
            }

            Cache cache = em.getEntityManagerFactory().getCache();
            cache.evictAll();
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

            CriteriaQuery<BloqueoUsuario> criteriaQuery = criteriaBuilder
                    .createQuery(BloqueoUsuario.class);

            Root<BloqueoUsuario> from = criteriaQuery
                    .from(BloqueoUsuario.class);

            List<Predicate> conditions = new ArrayList<Predicate>();

            conditions
                    .add(criteriaBuilder.equal(
                            from.get(ConstantJPA.COMMON_STRING_CLIENT)
                                    .get(ConstantJPA.COMMON_STRING_CLIENT_ID),
                            customerId));

            conditions.add(criteriaBuilder.equal(
                    from.get(ConstantJPA.COMMON_STRING_ID)
                            .get(ConstantJPA.COMMON_STRING_TYPE_BLOCKING),
                    blockType));

            criteriaQuery.select(from);
            criteriaQuery.where(conditions.toArray(new Predicate[] {}));

            TypedQuery<BloqueoUsuario> typedQuery = em
                    .createQuery(criteriaQuery);

            BloqueoUsuario result = typedQuery.getSingleResult();
            em.getEntityManagerFactory().getCache().evict(result.getClass());

            return result;

        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            StringBuilder errorString = new StringBuilder(
                    BloqueoUsuarioJPAServiceIMPL.class.getCanonicalName());
            errorString.append(ConstantJPA.METHOD_GET_BLOQUEO_USUARIO);
            throw new JPAException(errorString.toString(), e);
        }
    }

    /**
     * Actualizacion de <code>BloqueoUsuario</code> en la Base de Datos.
     * 
     * @param bloqueoUsuario
     * @param em
     * @throws JPAException
     */
    @Override
    public void updateBloqueoUsuario(BloqueoUsuario bloqueoUsuario,
            EntityManager em) throws JPAException {

        try {
            if (null == bloqueoUsuario) {
                StringBuilder errorString = new StringBuilder(
                        BloqueoUsuarioJPAServiceIMPL.class.getCanonicalName());
                errorString
                        .append(ConstantJPA.ERROR_INCORRECT_INPUT_PARAMETERS);
                throw new JPAException(errorString.toString());
            }

            Date currentDate = new Date();
            Timestamp time = new Timestamp(currentDate.getTime());
            bloqueoUsuario.setFechaBloqueo(time);
            em.merge(bloqueoUsuario);

        } catch (JPAException e) {
            throw e;
        } catch (Exception e) {
            StringBuilder errorString = new StringBuilder(
                    BloqueoUsuarioJPAServiceIMPL.class.getCanonicalName());
            errorString.append(ConstantJPA.METHOD_UPDATE_BLOQUEO_USUARIO);
            throw new JPAException(errorString.toString(), e);
        }

    }
}