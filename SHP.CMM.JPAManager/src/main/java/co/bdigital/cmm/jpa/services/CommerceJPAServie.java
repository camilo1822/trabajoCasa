package co.bdigital.cmm.jpa.services;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.Comercio;

public interface CommerceJPAServie {

    public Comercio getCommerceByPhone(String username, EntityManager em)
            throws JPAException;

    public Comercio getCommerceByCode(String id, EntityManager em)
            throws JPAException;
}
