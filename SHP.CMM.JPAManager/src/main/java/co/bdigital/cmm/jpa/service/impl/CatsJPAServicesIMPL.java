package co.bdigital.cmm.jpa.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
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
import co.bdigital.cmm.jpa.model.CatsMig;
import co.bdigital.cmm.jpa.services.CatsJPAServices;
import co.bdigital.cmm.jpa.util.ConstantJPA;

/**
 * 
 * @author daniel.pareja
 *
 */
public class CatsJPAServicesIMPL implements CatsJPAServices {

    private static CatsJPAServicesIMPL instance;
    private static final String NUM_DOC = "numDoc";
    private static final String STATUS = "estado";

    public static CatsJPAServicesIMPL getInstance() {
        if (null == instance)
            instance = new CatsJPAServicesIMPL();
        return instance;
    }

    @Override
    public CatsMig getCatsMigByDocument(String numDoc, EntityManager em)
            throws JPAException {
        final String metodo = "getCatsMigByDocument";
        CatsMig catsMig = null;

        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<CatsMig> criteriaQuery = criteriaBuilder
                    .createQuery(CatsMig.class);

            Root<CatsMig> from = criteriaQuery.from(CatsMig.class);

            List<Predicate> conditions = new ArrayList<Predicate>();

            conditions.add(criteriaBuilder.equal(from.get(NUM_DOC),
                    Long.valueOf(numDoc)));

            criteriaQuery.select(from);
            criteriaQuery.where(conditions.toArray(new Predicate[] {}));

            em.getEntityManagerFactory().getCache().evict(CatsMig.class);
            TypedQuery<CatsMig> typedQuery = em.createQuery(criteriaQuery);

            catsMig = typedQuery.getSingleResult();

        } catch (NoResultException e) {
            return null;

        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + metodo, e);
        }
        return catsMig;
    }

    @Override
    public List<CatsMig> getCatsMigByStatus(String estado, EntityManager em)
            throws JPAException {
        final String metodo = "getCatsMigByStatus";
        List<CatsMig> catsMig = null;

        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<CatsMig> criteriaQuery = criteriaBuilder
                    .createQuery(CatsMig.class);

            Root<CatsMig> from = criteriaQuery.from(CatsMig.class);

            List<Predicate> conditions = new ArrayList<Predicate>();

            conditions.add(criteriaBuilder.equal(from.get(STATUS),
                    BigDecimal.valueOf(Long.valueOf(estado))));

            criteriaQuery.select(from);
            criteriaQuery.where(conditions.toArray(new Predicate[] {}));

            em.getEntityManagerFactory().getCache().evict(CatsMig.class);
            TypedQuery<CatsMig> typedQuery = em.createQuery(criteriaQuery);

            catsMig = typedQuery.getResultList();

        } catch (NoResultException e) {
            return null;

        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + metodo, e);
        }
        return catsMig;
    }

    @Override
    public void mergeOrPersistCatsMigStatus(CatsMig catsMig,
            EntityManager em) throws JPAException {
        try {

            CatsMig catsMigMerge = em.find(CatsMig.class, catsMig.getNumDoc());
            
            if(catsMigMerge != null){
                catsMigMerge.setEstado(catsMig.getEstado());
                catsMigMerge.setFechaModificacion(new Timestamp(new Date().getTime()));
                catsMigMerge.setUsuarioModificacion(ConstantJPA.COMMON_STRING_MDW);
                
            } else {
                catsMig.setFechaCreacion(new Timestamp(new Date().getTime()));
                catsMig.setUsuarioCreacion(ConstantJPA.COMMON_STRING_MDW);
                catsMigMerge = catsMig;
            }
            em.merge(catsMigMerge);
            
        } catch (Exception e) {
            StringBuilder errorString = new StringBuilder(
                    CifinInformationJPAServiceIMPL.class.getCanonicalName());
            errorString
                    .append(ConstantJPA.COMMON_STRING_UPDATE_CIFIN_INFO_METHOD);
            throw new JPAException(errorString.toString(), e);
        }
        
    }

    @Override
    public void mergeCatsMig(CatsMig catsMig, EntityManager em)
            throws JPAException {
        try {

            catsMig.setFechaModificacion(new Timestamp(new Date().getTime()));
            catsMig.setUsuarioModificacion(
                    ConstantJPA.COMMON_STRING_NEQUI_UPPER);
            em.merge(catsMig);

        } catch (Exception e) {
            StringBuilder errorString = new StringBuilder(
                    CatsJPAServicesIMPL.class.getCanonicalName());
            errorString.append(ConstantJPA.COMMON_STRING_MERGE_CATS_MIG_METHOD);
            throw new JPAException(errorString.toString(), e);
        }

    }

    @Override
    public void persistCatsMig(CatsMig catsMig, EntityManager em)
            throws JPAException {
        try {

            catsMig.setFechaCreacion(new Timestamp(new Date().getTime()));
            catsMig.setUsuarioCreacion(ConstantJPA.COMMON_STRING_NEQUI_UPPER);
            em.persist(catsMig);

        } catch (Exception e) {
            StringBuilder errorString = new StringBuilder(
                    CatsJPAServicesIMPL.class.getCanonicalName());
            errorString
                    .append(ConstantJPA.COMMON_STRING_PERSIST_CATS_MIG_METHOD);
            throw new JPAException(errorString.toString(), e);
        }

    }

}
