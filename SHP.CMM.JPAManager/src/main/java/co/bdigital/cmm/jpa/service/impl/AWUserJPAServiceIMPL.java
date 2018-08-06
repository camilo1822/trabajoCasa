package co.bdigital.cmm.jpa.service.impl;

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
import co.bdigital.cmm.jpa.model.Activacion;
import co.bdigital.cmm.jpa.model.AwUsuario;
import co.bdigital.cmm.jpa.services.AWUserJPAService;

/**
 * 
 * @author juan.molinab
 *
 */
public class AWUserJPAServiceIMPL implements AWUserJPAService {

    public static final String COMMON_EMAIL_USER = "mail";
    public static final String COMMON_STRING_ROL = "awRol";
    public static final String COMMON_STRING_ACCION = "awAccions";
    public static final String COMMON_ID_ROL = "idRol";
    public static final String COMMON_COUNTRY_ID = "paisId";

    private static AWUserJPAServiceIMPL instance;

    public static AWUserJPAServiceIMPL getInstance() {
        if (null == instance)
            instance = new AWUserJPAServiceIMPL();
        return instance;
    }

    /**
     * Metodo para devolver la lista de usuarios
     * 
     * @param countryId
     * @param em
     * @return
     * @throws JPAException
     */
    public List<AwUsuario> getAWUsers(String countryId, EntityManager em)
            throws JPAException {

        final String metodo = "getAWUsers";

        try {

            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<AwUsuario> criteriaQuery = criteriaBuilder
                    .createQuery(AwUsuario.class);
            Root<AwUsuario> from = criteriaQuery.from(AwUsuario.class);
            Predicate condition = criteriaBuilder.equal(
                    from.get(COMMON_COUNTRY_ID), countryId);

            criteriaQuery.select(from);
            criteriaQuery.where(condition);

            TypedQuery<AwUsuario> typedQuery = em.createQuery(criteriaQuery);
            return typedQuery.getResultList();

        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + metodo, e);
        }
    }

    /**
     * Metodo para encontrar un usuario por medio de su email
     * 
     * @param email
     * @param em
     * @return
     * @throws JPAException
     */
    public AwUsuario getUserByEmail(String email, EntityManager em)
            throws JPAException {
        final String metodo = "getUserByEmail";

        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<AwUsuario> criteriaQuery = criteriaBuilder
                    .createQuery(AwUsuario.class);

            Root<AwUsuario> from = criteriaQuery.from(AwUsuario.class);
            Predicate condition = criteriaBuilder.equal(
                    from.get(COMMON_EMAIL_USER), email);
            criteriaQuery.select(from);
            criteriaQuery.where(condition);
            em.getEntityManagerFactory().getCache().evict(Activacion.class);
            TypedQuery<AwUsuario> typedQuery = em.createQuery(criteriaQuery);
            AwUsuario awUsuario = null;

            try {
                awUsuario = typedQuery.getSingleResult();
            } catch (Exception ee) {
                return null;
            }

            return awUsuario;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + metodo, e);
        }
    }

    /**
     * <p>
     * Metodo que permite crear un usuario de administrativa.
     * </p>
     * 
     * @param awUser
     * @param creationUser
     * @param em
     * @return
     * @throws JPAException
     */
    @Override
    public AwUsuario createUser(AwUsuario awUser, String creationUser,
            EntityManager em) throws JPAException {
        final String metodo = "createUser";

        try {
            awUser.setFechaCreacion(new Date());
            awUser.setUsuarioCreacion(creationUser);
            em.persist(awUser);
            return awUser;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + metodo, e);
        }
    }

    /**
     * <p>
     * Metodo que permite actualizar un usuario de administrativa.
     * </p>
     * 
     * @param awUser
     * @param em
     * @return
     * @throws JPAException
     */
    @Override
    public AwUsuario mergeUser(AwUsuario awUser, EntityManager em)
            throws JPAException {
        final String metodo = "mergeUser";

        try {
            em.merge(awUser);
            return awUser;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + metodo, e);
        }
    }

}
