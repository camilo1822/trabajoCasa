package co.bdigital.cmm.jpa.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import co.bdigital.cmm.jpa.exception.EnumJPAException;
import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.Intento;
import co.bdigital.cmm.jpa.services.IntentosJPAService;
import co.bdigital.cmm.jpa.util.ConstantJPA;

public class IntentosJPAServiceIMPL implements IntentosJPAService {

    public static final String COMMON_STRING_CELULAR = "celular";
    public static final String COMMON_STRING_ACCESS_TYPE = "tipoAcceso";
    public static final String COMMON_STRING_ID = "id";

    private static IntentosJPAServiceIMPL instance;

    public static IntentosJPAServiceIMPL getInstance() {
        if (null == instance)
            instance = new IntentosJPAServiceIMPL();
        return instance;
    }

    @Override
    public String getAttempts(EntityManager em, String phoneNumber,
            String accessType) throws JPAException {
        final String metodo = "getAttempts";
        Intento intento = null;

        try {
            Query query = em.createNamedQuery(
                    ConstantJPA.QUERY_INTENTO_BY_PHONENUMBER_AND_TYPE);
            query.setParameter(ConstantJPA.COMMON_PARAMETER_PHONENUMBER,
                    phoneNumber);
            query.setParameter(ConstantJPA.COMMON_PARAMETER_TYPE, accessType);

            em.getEntityManagerFactory().getCache().evict(Intento.class);

            intento = (Intento) query.getSingleResult();

        } catch (NoResultException ee) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + metodo, e);
        }
        return intento.getIntentos();
    }

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
    @Override
    public Intento getAttempt(EntityManager em, String phoneNumber,
            String accessType) throws JPAException {

        final String metodo = "getAttempt";
        Intento intento = null;

        try {
            Query query = em.createNamedQuery(
                    ConstantJPA.QUERY_INTENTO_BY_PHONENUMBER_AND_TYPE);
            query.setParameter(ConstantJPA.COMMON_PARAMETER_PHONENUMBER,
                    phoneNumber);
            query.setParameter(ConstantJPA.COMMON_PARAMETER_TYPE, accessType);

            em.getEntityManagerFactory().getCache().evict(Intento.class);

            intento = (Intento) query.getSingleResult();
            return intento;

        } catch (NoResultException e) {
            return intento;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + metodo, e);
        }
    }

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
            throws JPAException {
        final String metodo = "deleteAttempts";
        try {
            em.refresh(intento);
            em.remove(intento);
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + metodo, e);
        }
    }

}
