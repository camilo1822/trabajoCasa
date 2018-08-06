package co.bdigital.cmm.jpa.services;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.PoliticaCliDetalle;

public interface PoliticaJPAService {

    public PoliticaCliDetalle getPoliticaCliDetalleByClientId(String policyId,
            Long clientId, EntityManager em) throws JPAException;

}
