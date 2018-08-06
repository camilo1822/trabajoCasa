/**
 * 
 */
package co.bdigital.cmm.jpa.service.impl;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.CifInformacion;
import co.bdigital.cmm.jpa.model.CifInformacionPK;
import co.bdigital.cmm.jpa.services.CifinInformationJPAService;
import co.bdigital.cmm.jpa.util.ConstantJPA;

/**
 * @author eduardo.altamar@pragma.com.co
 *
 */
public class CifinInformationJPAServiceIMPL
        implements CifinInformationJPAService {

    private static CifinInformationJPAServiceIMPL instance;

    public static CifinInformationJPAServiceIMPL getInstance() {
        if (null == instance)
            instance = new CifinInformationJPAServiceIMPL();
        return instance;
    }

    @Override
    public void persistCifInformacion(CifInformacion cifinInformacion,
            EntityManager em) throws JPAException {
        try {

            Timestamp fechaActualTimestamp = new Timestamp(
                    new Date().getTime());

            cifinInformacion.setFechaCreacion(fechaActualTimestamp);
            cifinInformacion
                    .setUsrCreacion(ConstantJPA.COMMON_STRING_NEQUI_UPPER);
            em.persist(cifinInformacion);

        } catch (Exception e) {
            StringBuilder errorString = new StringBuilder(
                    CifinInformationJPAServiceIMPL.class.getCanonicalName());
            errorString.append(
                    ConstantJPA.COMMON_STRING_PERSIST_CIFIN_INFO_METHOD);
            throw new JPAException(errorString.toString(), e);
        }

    }

    @Override
    public void mergeCifInformacion(CifInformacion cifinInformacion,
            EntityManager em) throws JPAException {
        try {

            cifinInformacion
                    .setFechaModificacion(new Timestamp(new Date().getTime()));
            cifinInformacion
                    .setUsrModificacion(ConstantJPA.COMMON_STRING_NEQUI_UPPER);
            em.merge(cifinInformacion);

        } catch (Exception e) {
            StringBuilder errorString = new StringBuilder(
                    CifinInformationJPAServiceIMPL.class.getCanonicalName());
            errorString
                    .append(ConstantJPA.COMMON_STRING_UPDATE_CIFIN_INFO_METHOD);
            throw new JPAException(errorString.toString(), e);
        }

    }

    @Override
    public CifInformacion getCifInformacion(String documentType,
            String documentNumber, EntityManager em) throws JPAException {

        try {
            CifInformacionPK cifInformacionPK = new CifInformacionPK();
            cifInformacionPK.setDocumento(documentNumber);
            cifInformacionPK.setTipoDocumento(documentType);

            return em.find(CifInformacion.class, cifInformacionPK);

        } catch (Exception e) {
            StringBuilder errorString = new StringBuilder(
                    CifinInformationJPAServiceIMPL.class.getCanonicalName());
            errorString.append(ConstantJPA.COMMON_STRING_GET_CIFIN_INFO_METHOD);
            throw new JPAException(errorString.toString(), e);
        }

    }

}
