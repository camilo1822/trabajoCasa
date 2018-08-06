/**
 * 
 */
package co.bdigital.cmm.jpa.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import co.bdigital.cmm.jpa.exception.EnumJPAException;
import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.ConsultaCifin;
import co.bdigital.cmm.jpa.services.ConsultaCifinJPAService;
import co.bdigital.cmm.jpa.util.ConstantJPA;

/**
 * @author cristian.martinez@pragma.com.co
 *
 */
public class ConsultaCifinJPAServiceIMPL implements ConsultaCifinJPAService {

    private static final String CONSULTA_CIFIN_STATUSES = "cifinStatuses";
    private static final String CONSULTA_CIFIN_RETRIES = "retries";
    private static final String CONSULTA_CIFIN_CLIENT_STATUSES = "clientStatuses";
    private static final String CONSULTA_CIFIN_CLIENT_DOCUMENT_TYPES = "documentTypes";
    private static final String CONSULTA_CIFIN_CLIENT_COUNTRY_CODE = "countryCode";
    private static final String CONSULTA_CIFIN_FIND_IN_ESTADOS_AND_REINTENTO_IN_CLIENTE_ESTADO_ID_IN_TIPO_IDS = "ConsultaCifin.findInEstadosAndReintentoInClienteEstadoIdInTipoIds";

    /**
     * Metodo que retorna una lista de Entity <code>ConsultaCifin</code>, con
     * estado que coincida con el valor que le llega como parametro.
     * 
     * @param em
     * @param estado
     * @return <code>ConsultaCifin</code>
     * @throws JPAException
     */
    @Override
    public List<ConsultaCifin> findConsultaCifinByEstado(EntityManager em,
            String estado) throws JPAException {

        Query query;
        List<ConsultaCifin> listConsultaCifin;

        try {

            em.getEntityManagerFactory().getCache().evict(ConsultaCifin.class);

            query = em
                    .createNamedQuery(ConstantJPA.CONSULTA_CIFIN_FIND_BY_ESTADO);

            query.setParameter(ConstantJPA.CONSULTA_CIFIN_ESTADO, estado);

            listConsultaCifin = query.getResultList();

        } catch (NoResultException e) {

            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + this.getClass().getName(), e);

        } catch (Exception e) {

            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + this.getClass().getName(), e);

        }

        return listConsultaCifin;
    }

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
    @Override
    public List<ConsultaCifin> findByEstadoReintentoClienteEstadoIdTipoId(
            EntityManager em, String estado, String reintento,
            BigDecimal clienteEstadoId, String clienteTipoId,
            String clientCodDivisionGeografica) throws JPAException {

        Query query;
        List<ConsultaCifin> listConsultaCifin;

        try {

            em.getEntityManagerFactory().getCache().evict(ConsultaCifin.class);

            query = em
                    .createNamedQuery(ConstantJPA.CONSULTA_CIFIN_FIND_BY_ESTADO_REINTENTO_CLIENTE_ESTADO_ID_TIPO_ID);

            query.setParameter(ConstantJPA.CONSULTA_CIFIN_ESTADO, estado);
            query.setParameter(ConstantJPA.CONSULTA_CIFIN_REINTENTO, reintento);
            query.setParameter(ConstantJPA.CONSULTA_CIFIN_CLIENTE_ESTADO_ID,
                    clienteEstadoId);
            query.setParameter(ConstantJPA.CONSULTA_CIFIN_CLIENTE_TIPO_ID,
                    clienteTipoId);
            query.setParameter(
                    ConstantJPA.CONSULTA_CIFIN_CLIENT_COD_DIVISION_GEOGRAFICA,
                    clientCodDivisionGeografica);

            listConsultaCifin = query.getResultList();

        } catch (NoResultException e) {

            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + this.getClass().getName(), e);

        } catch (Exception e) {

            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + this.getClass().getName(), e);

        }

        return listConsultaCifin;
    }

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
    @Override
    public List<ConsultaCifin> findByCifinStatusAndRetriesAndClientStatusesAndDocumentTypes(
            List<String> cifinStatuses, String retries,
            List<String> clientStatuses, List<String> documentTypes,
            String countryCode, EntityManager em) throws JPAException {

        final String metodo = "findByCifinStatusAndRetriesAndClientStatusesAndDocumentTypes";
        Query query;

        try {

            em.getEntityManagerFactory().getCache().evict(ConsultaCifin.class);

            query = em
                    .createNamedQuery(CONSULTA_CIFIN_FIND_IN_ESTADOS_AND_REINTENTO_IN_CLIENTE_ESTADO_ID_IN_TIPO_IDS);

            query.setParameter(CONSULTA_CIFIN_STATUSES, cifinStatuses);
            query.setParameter(CONSULTA_CIFIN_RETRIES, retries);
            query.setParameter(CONSULTA_CIFIN_CLIENT_STATUSES, clientStatuses);
            query.setParameter(CONSULTA_CIFIN_CLIENT_DOCUMENT_TYPES,
                    documentTypes);
            query.setParameter(CONSULTA_CIFIN_CLIENT_COUNTRY_CODE, countryCode);

            return query.getResultList();

        } catch (NoResultException e) {

            return null;

        } catch (Exception e) {

            throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
                    .getName() + metodo, e);

        }

    }
}
