package co.bdigital.admin.services.processor.impl;

import java.io.Serializable;

import co.bdigital.admin.services.processor.AbstractProcessor;
import co.bdigital.cmm.ejb.generic.ServiceController;
import co.bdigital.cmm.messaging.general.RequestMessageObjectType;
import co.bdigital.cmm.messaging.general.ResponseMessageObjectType;

/**
 * @author hansel.ospino
 *
 */
public class UserManagementServicesProcessor extends AbstractProcessor
        implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1979503289589299005L;

    /*
     * (non-Javadoc)
     * 
     * @see
     * co.bdigital.mdw.services.processor.AbstractProcessor#processOperation
     * (co.bdigital.mdw.messaging.general.RequestMessageObjectType,
     * co.bdigita.mdw.ejb.generic.ServiceController)
     */
    @Override
    public ResponseMessageObjectType processOperation(
            RequestMessageObjectType request, ServiceController serviceBean) {
        ResponseMessageObjectType responseWS = serviceBean
                .executeOperation(request);

        return responseWS;
    }

}
