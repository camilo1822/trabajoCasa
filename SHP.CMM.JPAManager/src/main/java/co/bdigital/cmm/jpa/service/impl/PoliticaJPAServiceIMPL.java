package co.bdigital.cmm.jpa.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import co.bdigital.cmm.jpa.exception.EnumJPAException;
import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.PoliticaCliDetalle;
import co.bdigital.cmm.jpa.services.PoliticaJPAService;
import co.bdigital.cmm.jpa.util.ConstantJPA;

public class PoliticaJPAServiceIMPL implements PoliticaJPAService {

    private static PoliticaJPAServiceIMPL instance = null;

    public static PoliticaJPAServiceIMPL getInstance() {
        if (null == instance)
            instance = new PoliticaJPAServiceIMPL();
        return instance;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PoliticaCliDetalle getPoliticaCliDetalleByClientId(String policyId,
            Long clientId, EntityManager em) throws JPAException {
        final String metodo = "getPoliticaCliDetalleByClientId";
        BigDecimal bdIdClient = new BigDecimal(clientId);
        try {
            Query query = em.createNamedQuery(
                    ConstantJPA.QUERY_POLICYCLIDET_BY_IDCLIENTE_AND_IDPOLITICA);
            query.setParameter(ConstantJPA.COMMON_PARAMETER_IDCLIENT,
                    bdIdClient);
            query.setParameter(ConstantJPA.COMMON_PARAMETER_IDPOLITICA,
                    policyId);
            em.getEntityManagerFactory().getCache()
                    .evict(PoliticaCliDetalle.class);
            PoliticaCliDetalle politicaCliDetalle = null;

            // si llegan varios resultados tomará solo el primero
            List<PoliticaCliDetalle> resultado = new ArrayList<PoliticaCliDetalle>();

            resultado = query.getResultList();
            if (null == resultado || resultado.isEmpty()) {
                return null;
            }
            politicaCliDetalle = resultado.get(0);

            return politicaCliDetalle;
        } catch (Exception e) {
            // TODO: actividad para Ricardo
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + metodo, e);
        }
    }

}
