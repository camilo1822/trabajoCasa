package co.bdigital.admin.ejb.controller.view;

import java.util.List;

import co.bdigital.cmm.jpa.model.Parametro;

/**
 * 
 * @author hansel.ospino
 *
 */
public interface CommonServiceBeanLocal {

    /**
     * Metodo para devolver una lista con las razones del cierre de cuenta para
     * Easy Solution
     * 
     * @param tipoParametroId
     * @param region
     * @return
     */
    public List<Parametro> getParameters(String tipoParametroId, String region);
}
