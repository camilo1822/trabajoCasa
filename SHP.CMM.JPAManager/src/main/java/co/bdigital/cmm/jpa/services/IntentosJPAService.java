package co.bdigital.cmm.jpa.services;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.Intento;

public interface IntentosJPAService {

    /**
     * Metodo que retorna el numero de intentos restantes delimitado por el tipo
     * de acceso, este puede ser de tipo OTP o biometria (BIO)
     * 
     * @param em
     * @param phone_number
     * @param access_type
     * @return String
     * @throws JPAException
     */
    public String getAttempts(EntityManager em, String phone_number,
            String access_type) throws JPAException;

    /**
     * <p>
     * M&eacute;todo que consulta el registro de intentos para el tipo de OTP y
     * celular especificado.
     * </p>
     * 
     * @param em
     * @param phoneNumber
     *            n&uacute;mero de celular.
     * @param accessType
     *            tipo de OTP.
     * @return Registro de intentos para el celular y tipo de OTP especificado.
     * @throws JPAException
     */
    public Intento getAttempt(EntityManager em, String phoneNumber,
            String accessType) throws JPAException;

    /**
     * <p>
     * M&eacute;todo que elimina el registro de intentos para el tipo de OTP y
     * celular especificado.
     * </p>
     * 
     * @param em
     * @param intento
     * @throws JPAException
     */
    public void deleteAttempts(EntityManager em, Intento intento)
            throws JPAException;

}
