/**
 * 
 */
package co.bdigital.cmm.jpa.services;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.ConsultaCifin;

/**
 * @author cristian.martinez@pragma.com.co
 *
 */
public interface ConsultaCifinJPAService {

    /**
     * Metodo que retorna una lista de Entity <code>ConsultaCifin</code>, con
     * estado que coincida con el valor que le llega como parametro.
     * 
     * @param em
     * @param estado
     * @return <code>ConsultaCifin</code>
     * @throws JPAException
     */
    public List<ConsultaCifin> findConsultaCifinByEstado(EntityManager em,
            String estado) throws JPAException;

    /**
     * Metodo que retorna una lista de Entity <code>ConsultaCifin</code>, con
     * estado, reintento (menores o iguales), EstadoId del cliente y TipoId del
     * cliente que recibe como parametros.
     * 
     * @param em
     * @param estado
     * @param reintento
     * @param clienteEstadoId
     * @param clienteTipoId
     * @param clientCodDivisionGeografica
     * @return <code>ConsultaCifin</code>
     * @throws JPAException
     */
    public List<ConsultaCifin> findByEstadoReintentoClienteEstadoIdTipoId(
            EntityManager em, String estado, String reintento,
            BigDecimal clienteEstadoId, String clienteTipoId,
            String clientCodDivisionGeografica) throws JPAException;

    /**
     * <p>
     * M&eacute;todo que retorna una lista de Entity <code>ConsultaCifin</code>,
     * con estado, reintento (menores o iguales), EstadoId del cliente y TipoId
     * del cliente que recibe como parametros.
     * </p>
     * 
     * @param cifinStatuses
     * @param retries
     * @param clientStatuses
     * @param documentTypes
     * @param countryCode
     * @param em
     * @return Listado de cifin.
     * @throws JPAException
     */
    public List<ConsultaCifin> findByCifinStatusAndRetriesAndClientStatusesAndDocumentTypes(
            List<String> cifinStatuses, String retries,
            List<String> clientStatuses, List<String> documentTypes,
            String countryCode, EntityManager em) throws JPAException;

}
