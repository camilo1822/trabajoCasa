package co.bdigital.admin.ejb.controller.view;

import java.util.List;

import co.bdigital.admin.messaging.services.bco.acs502.MassiveCloseAccountServiceRequestType;
import co.bdigital.admin.model.StatusResponse;
import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.Parametro;

/**
 * 
 * @author juan.molinab
 *
 */
public interface CloseAccountServiceBeanLocal {

    /**
     * Metodo para cerrar la cuenta de un cliente
     * 
     * @param phoneNumber
     *            numero del celular
     * @param region
     * 
     * @return response con un estado y descripcion de la operacion
     */
    public StatusResponse closeAccount(String phoneNumber, String region);

    /**
     * Metodo para devolver una lista con las razones del cierre de cuenta para
     * Easy Solution
     * 
     * @param tipoParametroId
     * @param region
     * @return
     */
    public List<Parametro> listReasonCode(String tipoParametroId, String region)
            throws JPAException;

    /**
     * Metodo para ejecutar el cierre masivo de las cuentas en segundo plano.
     * 
     * @param sourceFilePath
     *            Ruta del archivo a procesar.
     * @param region
     *            Código de la región.
     * @return {@link StatusResponse} información de la respuesta de la
     *         operación.
     */
    public StatusResponse massiveCloseAccount(
            MassiveCloseAccountServiceRequestType request, String region);
}
