/**
 * 
 */
package co.bdigital.cmm.jpa.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.CuentaReverso;
import co.bdigital.cmm.jpa.services.AccountReverseJPAService;
import co.bdigital.cmm.jpa.util.ConstantJPA;

/**
 * @author cristian.martinez@pragma.com.co
 *
 */
public class AccountReverseJPAServiceIMPL implements AccountReverseJPAService {

    public static final String COMMON_STRING_STATUS = "estado";
    private static AccountReverseJPAServiceIMPL instance;

    public static AccountReverseJPAServiceIMPL getInstance() {
        if (null == instance)
            instance = new AccountReverseJPAServiceIMPL();
        return instance;
    }

    /**
     * Inserta un entity <code>CuentaReverso</code> en Base de Datos.
     * 
     * @param reversoPendiente
     * @param em
     * @throws JPAException
     */
    @Override
    public void persistAccountReverse(CuentaReverso cuentaReverso,
            EntityManager em) throws JPAException {

        try {

            Date fechaActual = new Date();
            Timestamp fechaActualTimestamp = new Timestamp(
                    fechaActual.getTime());
            cuentaReverso.setFechaCreacion(fechaActualTimestamp);
            cuentaReverso.setFechaModificacion(fechaActualTimestamp);
            cuentaReverso.setUsrCreacion(ConstantJPA.COMMON_STRING_NEQUI_UPPER);

            em.persist(cuentaReverso);

        } catch (Exception e) {
            StringBuilder errorString = new StringBuilder(
                    SeguridadJPAServiceIMPL.class.getCanonicalName());
            errorString.append(ConstantJPA.PERSIST_ACCOUNT_REVERSE);
            throw new JPAException(errorString.toString(), e);
        }
    }

    /**
     * Inserta una lista de entities <code>CuentaReverso</code> en Base de
     * Datos.
     * 
     * @param listCuentaReverso
     * @param em
     * @throws JPAException
     */
    @Override
    public void persistListAccountReverse(
            List<CuentaReverso> listCuentaReverso, EntityManager em)
            throws JPAException {

        try {

            for (CuentaReverso cuentaReverso : listCuentaReverso) {

                this.persistAccountReverse(cuentaReverso, em);
            }

        } catch (Exception e) {
            StringBuilder errorString = new StringBuilder(
                    SeguridadJPAServiceIMPL.class.getCanonicalName());
            errorString.append(ConstantJPA.PERSIST_ACCOUNT_REVERSE);
            throw new JPAException(errorString.toString(), e);
        }
    }

}
