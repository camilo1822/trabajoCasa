/**
 * 
 */
package co.bdigital.cmm.jpa.services;

import java.util.List;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.ImagenS3;

public interface DocumentValidationJPAService {

    /**
     *
     * @param clientId
     * @param tipoImagen
     * @param em
     * @throws JPAException
     */
    public List<ImagenS3> getStatus(String clientId, String tipoImagen,
            EntityManager em) throws JPAException;

    /**
     *
     * @param clientId
     * @param tipoImagen
     * @param em
     * @throws JPAException
     */
    public List<ImagenS3> getAllStatus(String clientId, String tipoImagen,
            EntityManager em) throws JPAException;

}
