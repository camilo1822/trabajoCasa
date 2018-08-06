package co.bdigital.cmm.jpa.services;

import java.util.List;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.BloqueoUsuario;

/**
 * @author johnpere@bancolombia.com.co
 *
 */
public interface BloqueoUsuarioJPAService {

    /**
     * Metodo que retorna lista de bloqueos de usuario por clientId
     * 
     * @param clientId
     * @param em
     * @return List<BloqueoUsuario>
     * @throws JPAException
     */
    public List<BloqueoUsuario> getBloqueoUsuarioByClientId(String clientId,
            EntityManager em) throws JPAException;

    /**
     * Metodo para remover un bloqueo
     * 
     * @param bloqueoUsuario
     * @param em
     * @throws JPAException
     */
    public void removeBloqueoUsuario(BloqueoUsuario bloqueoUsuario,
            EntityManager em) throws JPAException;
    
    /**
     * Inserta un entity <code>BloqueoUsuario</code> en Base de Datos.
     * 
     * @param bloqueoUsuario
     * @param em
     * @throws JPAException
     */
    public void persistBloqueoUsuario(BloqueoUsuario bloqueoUsuario,
            EntityManager em) throws JPAException;
    
    /**
     * Consulta de Bloqueo de un cliente
     * 
     * @param blockType
     * @param customerId
     * @param em
     * @return
     * @throws JPAException
     */
    public BloqueoUsuario getBloqueoUsuario(String blockType, long customerId,
            EntityManager em) throws JPAException;

    /**
     * metodo para actualizar bloqueo de usuario
     * 
     * @param bloqueoUsuario
     * @param em
     * @throws JPAException
     */
    public void updateBloqueoUsuario(BloqueoUsuario bloqueoUsuario,
            EntityManager em) throws JPAException;
}
