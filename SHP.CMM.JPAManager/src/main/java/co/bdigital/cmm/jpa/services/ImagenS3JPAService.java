/**
 * 
 */
package co.bdigital.cmm.jpa.services;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.ImagenS3;

/**
 * @author cristian.martinez@pragma.com.co
 *
 */
public interface ImagenS3JPAService {

    /**
     * Metodo que retorna un Entity <code>ImagenS3</code>, que le pertenezca a
     * <code>clienteId</code>.
     * 
     * @param em
     * @param clienteId
     * @return <code>ImagenS3</code>
     * @throws JPAException
     */
    public List<ImagenS3> findImagenS3ByClienteId(EntityManager em,
            Long clienteId) throws JPAException;

    /**
     * Método que retorna las imágenes creadas antes de la fecha recibida como
     * parámetro
     * 
     * @param em
     * @param date
     * @return
     * @throws JPAException
     */
    public List<ImagenS3> findImageS3ByCreationDate(EntityManager em,
            Date dateToFilter) throws JPAException;

}
