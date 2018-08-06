package co.bdigital.cmm.jpa.services;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.Invitado;

public interface InvitadoJPAService {

    /**
     * Metodo que retorna la invitacion por Id
     * 
     * @param em
     * @param id
     * @return Invitado
     * @throws JPAException
     */
    public Invitado getInvitationById(EntityManager em, String id,
            String paisId) throws JPAException;

    /**
     * Metodo que retorna la invitacion por Celular
     * 
     * @param em
     * @param phoneNumber
     * @return
     * @throws JPAException
     */
    public Invitado getInvitationByPhoneNumber(EntityManager em,
            String phoneNumber, String paisId) throws JPAException;

    /**
     * Método para obtener un código disponible
     * 
     * @param em
     * @return
     * @throws JPAException
     */
    public Invitado getAvalaibleInvitationCode(EntityManager em, String paisId)
            throws JPAException;

}
