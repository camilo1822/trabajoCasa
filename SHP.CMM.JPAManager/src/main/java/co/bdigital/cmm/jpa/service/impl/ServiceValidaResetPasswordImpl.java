/**
 * 
 */
package co.bdigital.cmm.jpa.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import co.bdigital.cmm.jpa.exception.EnumJPAException;
import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.UserPwdReset;
import co.bdigital.cmm.jpa.services.ServiceValidate;

/**
 * @author hildebrando.rios
 *
 */
public class ServiceValidaResetPasswordImpl implements ServiceValidate {
    private static final String COMMON_STRING_CODIGO = "codigo";
    private static ServiceValidaResetPasswordImpl instance;

    /**
	 * 
	 */
    public ServiceValidaResetPasswordImpl() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @return ServiceValidaResetPasswordImpl
     */
    public static ServiceValidaResetPasswordImpl getInstance() {
        if (null == instance)
            instance = new ServiceValidaResetPasswordImpl();
        return instance;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * co.bdigital.mdw.jpa.services.ServiceValidate#getResetPasswordCodeValidation
     * (java.lang.String, java.lang.String, javax.persistence.EntityManager)
     */
    @Override
    public UserPwdReset getResetPasswordCodeValidation(String email,
            String controlCode, EntityManager em) throws JPAException {

        // construccion de la consulta
        em.getEntityManagerFactory().getCache().evict(UserPwdReset.class);
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<UserPwdReset> criteriaQuery = criteriaBuilder
                .createQuery(UserPwdReset.class);
        Root<UserPwdReset> from = criteriaQuery.from(UserPwdReset.class);
        Predicate condition = criteriaBuilder.equal(
                from.get(COMMON_STRING_CODIGO), controlCode);
        criteriaQuery.select(from);
        criteriaQuery.where(condition);

        // ejecución de la consulta
        TypedQuery<UserPwdReset> typedQuery = em.createQuery(criteriaQuery);

        em.getEntityManagerFactory().getCache().evict(UserPwdReset.class);
        UserPwdReset userPwdReset = new UserPwdReset();

        try {
            userPwdReset = typedQuery.getSingleResult();
            return userPwdReset;
        } catch (NoResultException e) {
            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + this.getClass().getName(), e);
        }
    }

}
