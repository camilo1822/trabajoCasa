package co.bdigital.cmm.jpa.service.impl;

import java.util.ArrayList;
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
import co.bdigital.cmm.jpa.model.Billetero;
import co.bdigital.cmm.jpa.model.Cliente;
import co.bdigital.cmm.jpa.model.Comercio;
import co.bdigital.cmm.jpa.services.BilleteroJPAService;

/**
 * @author john_perez
 *
 */
public class BilleteroJPAServiceIMPL implements BilleteroJPAService {

    private static BilleteroJPAServiceIMPL instance;
    private static final String COMMON_STRING_CLIENTE = "cliente";
    private static final String COMMON_STRING_CELULAR = "celular";
    
    public BilleteroJPAServiceIMPL() {
    }
    
    public static BilleteroJPAServiceIMPL getInstance() {
        if (null == instance)
            instance = new BilleteroJPAServiceIMPL();
        return instance;
    }

    /**
     * Metodo que consulta en BD del saldo de un usuario enrolado
     * 
     * @param honeNumber
     * @param em
     * @return String Saldo
     */
    @Override
    public String getbalanceForEnroll(String phoneNumber, EntityManager em)
            throws JPAException {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        
        CriteriaQuery<Billetero> criteriaQuery = criteriaBuilder.createQuery(Billetero.class);
        
        Root<Billetero> from = criteriaQuery.from(Billetero.class);
        Join<Billetero, Cliente> billetero = from.join(COMMON_STRING_CLIENTE);
        
        List<Predicate> conditions = new ArrayList<Predicate>();
        
        conditions.add(criteriaBuilder.equal(billetero.get(COMMON_STRING_CELULAR),
                phoneNumber));
        
        criteriaQuery.select(from);
        criteriaQuery.where(conditions.toArray(new Predicate[] {}));
        em.getEntityManagerFactory().getCache().evict(Billetero.class);
        TypedQuery<Billetero> typedQuery = em.createQuery(criteriaQuery);
        
        try {
            Billetero billeteroCliente = typedQuery.getSingleResult();
            if (null != billeteroCliente) {
                return billeteroCliente.getSaldo().toString();
            }
        } catch (NoResultException e) {
            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + this.getClass().getName(), e);
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + this.getClass().getName(), e);
        }
        return null;
    }

}
