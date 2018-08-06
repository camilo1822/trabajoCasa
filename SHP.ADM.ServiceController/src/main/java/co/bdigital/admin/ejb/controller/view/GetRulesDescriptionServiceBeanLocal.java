package co.bdigital.admin.ejb.controller.view;

import java.util.List;

import co.bdigital.admin.messaging.services.getrulesdescription.DescriptionType;

public interface GetRulesDescriptionServiceBeanLocal {

    /**
     * Metodo para retornar las descripciones de las promociones
     * 
     * @param region
     * @return
     */
    List<DescriptionType> getRulesDescription(String region);

}
