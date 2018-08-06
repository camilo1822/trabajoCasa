package co.bdigital.cmm.jpa.services;

import java.util.List;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.AwRol;

/**
 * 
 * @author juan.molinab
 *
 */
public interface AWRolJPAService {

    /**
     * Metodo para devolver la lista de roles de los usuarios
     * 
     * @param em
     * @param countryId
     * @return
     * @throws JPAException
     */
    public List<AwRol> getListRol(String countryId, EntityManager em)
            throws JPAException;

    /**
     * Consulta de rol por la llave primaria <code>idRol</code>.
     * 
     * @param awRolId
     * @param em
     * @return <code>AwRol</code>
     * @throws JPAException
     */
    public AwRol findAwRolById(Long awRolId, EntityManager em)
            throws JPAException;
}
