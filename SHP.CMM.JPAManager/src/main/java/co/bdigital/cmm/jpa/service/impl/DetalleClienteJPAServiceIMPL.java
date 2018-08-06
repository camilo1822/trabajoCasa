/**
 * 
 */
package co.bdigital.cmm.jpa.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import co.bdigital.cmm.jpa.exception.EnumJPAException;
import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.DetalleCliente;
import co.bdigital.cmm.jpa.services.DetalleClienteJPAService;
import co.bdigital.cmm.jpa.util.ConstantJPA;

/**
 * @author hansel.ospino@pragma.com.co
 *
 */
public class DetalleClienteJPAServiceIMPL implements DetalleClienteJPAService {

    private static DetalleClienteJPAServiceIMPL instance;

    private static final String SENTENCE_SQL_REGISTER_CUSTOMERS_BY_TIME_RANGE = "SELECT det_cli.* FROM SHBANCA_DIGITAL.DETALLE_CLIENTE det_cli INNER JOIN SHBANCA_DIGITAL.CLIENTE cli ON det_cli.ID_CLIENTE = cli.CLIENTE_ID WHERE det_cli.NOMBRE = ? AND cli.PAIS_ID = ? AND cli.ESTADO_ID = 3 AND TO_TIMESTAMP(det_cli.VALOR, ?) BETWEEN ? AND ?";

    /*
     * Constructor por defecto
     */
    public static DetalleClienteJPAServiceIMPL getInstance() {
        if (null == instance)
            instance = new DetalleClienteJPAServiceIMPL();
        return instance;
    }

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
     *            fecha m&aacute;xima desde para el rango de comparación de la
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
    @Override
    public List<DetalleCliente> getDetalleClienteByRegisterDateAndPreviousTime(
            Date initialTime, Date finalTime, String registerTimeFormat,
            String registerDateName, String countryRegion, EntityManager em)
            throws JPAException {
        final String metodo = "getDetalleClienteByRegisterDateAndPreviousTime";

        try {

            Query query = em.createNativeQuery(
                    SENTENCE_SQL_REGISTER_CUSTOMERS_BY_TIME_RANGE,
                    DetalleCliente.class);
            query.setParameter(1, registerDateName);
            query.setParameter(2, countryRegion);
            query.setParameter(3, registerTimeFormat);
            query.setParameter(4, initialTime);
            query.setParameter(5, finalTime);
            List<DetalleCliente> result = query.getResultList();
            return result;

        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + metodo, e);
        }
    }

    @Override
    public DetalleCliente getDetalleClienteByIdClienteAndName(Long idCliente,
            String nombre, EntityManager em) throws JPAException {
        final String metodo = "getDetalleClienteByIdClienteAndName";

        try {
            Query query = em.createNamedQuery(
                    ConstantJPA.QUERY_DETALLE_CLIENTE_BY_IDCLIENT_AND_NAME);
            query.setParameter(ConstantJPA.COMMON_PARAMETER_IDCLIENT,
                    idCliente);
            query.setParameter(ConstantJPA.COMMON_PARAMETER_NAME, nombre);
            DetalleCliente result = (DetalleCliente) query.getSingleResult();
            return result;
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + metodo, e);
        }
    }

    /**
     * Método para insertar un registro en detalle cliente
     * 
     * @param detalleCliente
     * @param em
     * @return
     * @throws JPAException
     */
    public void persistDetalleCliente(DetalleCliente detalleCliente,
            EntityManager em) throws JPAException {

        try {
            Date fechaActual = new Date();
            Timestamp fechaActualTimestamp = new Timestamp(
                    fechaActual.getTime());
            detalleCliente.setFechaCreacion(fechaActualTimestamp);
            detalleCliente.setUsuarioCreacion(ConstantJPA.COMMON_STRING_MDW);

            em.persist(detalleCliente);

        } catch (Exception e) {
            StringBuilder errorString = new StringBuilder(
                    BloqueoUsuarioJPAServiceIMPL.class.getCanonicalName());
            errorString.append(ConstantJPA.METHOD_PERSIST_DETALLE_CLIENTE);
            throw new JPAException(errorString.toString(), e);
        }
    }
}
