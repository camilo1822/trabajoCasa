package co.bdigital.cmm.jpa.services;

import java.util.List;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.AdminBioDetalle;

/**
 * @author john.perez@pragma.com.co
 *
 */
public interface AdminBioDetalleJPAService {

    /**
     * Consulta de los detalles de biometria
     * 
     * @param em
     * @param clienteId
     * @param trnId
     * @param maxResult
     * @return List<AdminBioDetalle>
     * @throws JPAException
     */
    public List<AdminBioDetalle> getListBioDetail(EntityManager em,
            Long clienteId, String trnId, int maxResult) throws JPAException;

}
