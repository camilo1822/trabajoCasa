/**
 * 
 */
package co.bdigital.cmm.jpa.service.impl;

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
import co.bdigital.cmm.jpa.model.ClienteTemp;
import co.bdigital.cmm.jpa.services.ClienteTempJPAService;
import co.bdigital.cmm.jpa.util.ConstantJPA;

/**
 * @author hansel.ospino@pragma.com.co
 *
 */
public class ClienteTempJPAServiceIMPL implements ClienteTempJPAService {

    /*
     * Constructor por defecto
     */
    public ClienteTempJPAServiceIMPL() {
    }

    /**
     * <p>
     * Consulta para la informaci&oacute;n temporal del cliente.
     * </p>
     * 
     * @param phoneNumber
     * @param countryCode
     * @param em
     * @return Informaci&oacute;n temporal del cliente.
     * @throws JPAException
     */
    @Override
    public ClienteTemp getClienteTempByPhoneNumberAndCountryCode(
            String phoneNumber, String countryCode, EntityManager em)
            throws JPAException {
        final String metodo = "getClienteTempByPhoneNumberAndCountryCode";

        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<ClienteTemp> criteriaQuery = criteriaBuilder
                    .createQuery(ClienteTemp.class);
            Root<ClienteTemp> from = criteriaQuery.from(ClienteTemp.class);

            List<Predicate> conditions = new ArrayList<Predicate>();

            conditions.add(criteriaBuilder.equal(from.get(ConstantJPA.COMMON_STRING_ID)
                    .get(ConstantJPA.COMMON_STRING_PHONE), phoneNumber));
            conditions.add(criteriaBuilder.equal(from.get(ConstantJPA.COMMON_STRING_ID)
                    .get(ConstantJPA.COMMON_STRING_COUNTRY_CODE), countryCode));

            criteriaQuery.select(from);
            criteriaQuery.where(conditions.toArray(new Predicate[] {}));

            TypedQuery<ClienteTemp> typedQuery = em.createQuery(criteriaQuery);
            return typedQuery.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + metodo, e);
        }
    }

    @Override
    public void updateClienteTemp(ClienteTemp client,
            EntityManager em) throws JPAException {   
        try { 
            Date currentDate = new Date();
            Timestamp time = new Timestamp(currentDate.getTime());
            client.setFechaModificacion(time);
            client.setUsuarioModificacion(ConstantJPA.COMMON_STRING_NEQUI_UPPER);
            em.persist(client);
            em.flush();       
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + ConstantJPA.METHOD_UPDATE_PROCESS_ID, e);
        }
        
    }
}
