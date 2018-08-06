/**
 * 
 */
package co.bdigital.cmm.jpa.services;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.DetalleCliente;

/**
 * @author hansel.ospino@pragma.com.co
 *
 */
public interface DetalleClienteJPAService {

    /**
     * <p>
     * M&eacute;todo que permite consultar las fechas de vinculaci&oacute;n
     * recientes a la fecha especificada (<code>previousTime</code>).
     * </p>
     * 
     * @param initialTime
     *            fecha m&iacute;nima para el rango de comparación de la fecha
     *            de vinculaci&oacute;n.
     * @param finalTime
     *            fecha m&accute;xima desde para el rango de comparación de la
     *            fecha de vinculaci&oacute;n.
     * @param registerTimeFormat
     *            formato de fecha para parseo de fecha de vinculaci&oacute;n.
     * @param registerDateName
     *            nombre del evento fecha de vinculaci&oacute;n.
     * @param em
     * @return Listado de detalles de clientes para vinculaciones recientes a la
     *         fecha especificada (<code>previousTime</code>).
     * @throws JPAException
     */
    public List<DetalleCliente> getDetalleClienteByRegisterDateAndPreviousTime(
            Date initialTime, Date finalTime, String registerTimeFormat,
            String registerDateName, String countryRegion, EntityManager em)
            throws JPAException;

    /**
     * Método para consultar un registro en detalle cliente
     * 
     * @param idCliente
     * @param nombre
     * @param em
     * @return
     * @throws JPAException
     */
    public DetalleCliente getDetalleClienteByIdClienteAndName(Long idCliente,
            String nombre, EntityManager em) throws JPAException;

    /**
     * Método para insertar un registro en detalle cliente
     * 
     * @param detalleCliente
     * @param em
     * @return
     * @throws JPAException
     */
    public void persistDetalleCliente(DetalleCliente detalleCliente,
            EntityManager em) throws JPAException;
}
