package co.bdigital.cmm.jpa.services;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;

public interface BilleteroJPAService {

    public String getbalanceForEnroll(String phoneNumber, EntityManager em) throws JPAException;
}
