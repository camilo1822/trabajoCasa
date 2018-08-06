package co.bdigital.admin.ejb.controller.view;

import co.bdigital.cmm.messaging.general.RequestMessageObjectType;

public interface NequiPointsServiceBeanLocal {

    /**
     * Metodo para notificaciones slack
     * 
     * @param request
     * @param message
     * @param region
     * @return
     */
    boolean slackNotification(RequestMessageObjectType request, String message,
            String region);

}
