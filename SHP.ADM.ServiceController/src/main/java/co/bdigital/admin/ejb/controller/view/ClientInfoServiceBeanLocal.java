package co.bdigital.admin.ejb.controller.view;

import co.bdigital.admin.model.StatusResponse;
import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.Cliente;
import co.bdigital.cmm.jpa.model.Intento;

/**
 * Interfaz para funciones propias del cliente
 * 
 * @author daniel
 *
 */
public interface ClientInfoServiceBeanLocal {
    /**
     * Método que retorna a un cliente filtrado por número de documento
     * 
     * @param documentId
     * @param documentType
     * @param region
     * @return <code>Cliente</code>
     */
    public Cliente getClientByNumDoc(String documentId, String documentType,
            String region);

    /**
     * Método que actualiza datos del cliente
     * 
     * @param cliente
     * @param region
     * @return <code>StatusResponse</code>
     */
    public StatusResponse updateClientInfo(Cliente cliente, String region);

    /**
     * Metodo para buscar un cliente por numero de celular
     * 
     * @param documentId
     * @param documentType
     * @return
     */
    public Cliente getClientByPhoneNumber(String phoneNumber);

    /**
     * Metodo que devuelve el intento delimitado por el tipo de acceso, este
     * puede ser de tipo OTP o biometria (BIO)
     * 
     * @param phone_number
     * @param access_type
     * @return String
     * @throws JPAException
     * 
     */
    public Intento getAttempts(String phone_number, String access_type);
}
