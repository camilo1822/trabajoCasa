/**
 * 
 */
package co.bdigital.cmm.jpa.services;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.ClienteTemp;

/**
 * @author hansel.ospino@pragma.com.co
 *
 */
public interface ClienteTempJPAService {

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
    public ClienteTemp getClienteTempByPhoneNumberAndCountryCode(
            String phoneNumber, String countryCode, EntityManager em)
            throws JPAException;
    
    /**
     * Metodo que actualiza el id de proceso
     * 
     * @param client
     * @param processId
     * @param em
     * @throws JPAException
     */
    public void updateClienteTemp(
            ClienteTemp client, EntityManager em)
            throws JPAException;

}
