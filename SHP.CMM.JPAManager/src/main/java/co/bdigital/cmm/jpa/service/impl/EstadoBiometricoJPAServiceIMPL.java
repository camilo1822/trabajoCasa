package co.bdigital.cmm.jpa.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import co.bdigital.cmm.jpa.exception.EnumJPAException;
import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.EstadoBiometrico;
import co.bdigital.cmm.jpa.services.EstadoBiometricoJPAService;
import co.bdigital.cmm.jpa.util.ConstantJPA;

/**
 * @author john.perez@pragma.com.co
 *
 */
public class EstadoBiometricoJPAServiceIMPL
        implements EstadoBiometricoJPAService {

    private static EstadoBiometricoJPAServiceIMPL instance;

    public EstadoBiometricoJPAServiceIMPL() {
    }

    public static EstadoBiometricoJPAServiceIMPL getInstance() {
        if (null == instance)
            instance = new EstadoBiometricoJPAServiceIMPL();
        return instance;
    }

    /**
     * Consulta de biometrias que tiene el usuario filtrando por clientId.
     * 
     * @param clientId
     * @param em
     * @return List<EstadoBiometrico>
     * @throws JPAException
     */
    @Override
    public List<EstadoBiometrico> getBiometricStateByClientId(String clientId,
            EntityManager em) throws JPAException {
        final String metodo = "getBiometricStateByClientId";

        try {
            Query query = em.createNamedQuery(
                    ConstantJPA.QUERY_ESTADOBIOMETRICO_BY_CLIENTID);
            query.setParameter(ConstantJPA.COMMON_PARAMETER_CLIENTID,
                    Long.parseLong(clientId));

            em.getEntityManagerFactory().getCache()
                    .evict(EstadoBiometrico.class);

            // si llegan varios resultados tomará solo el primero
            List<EstadoBiometrico> resultado = new ArrayList<EstadoBiometrico>();

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
}
