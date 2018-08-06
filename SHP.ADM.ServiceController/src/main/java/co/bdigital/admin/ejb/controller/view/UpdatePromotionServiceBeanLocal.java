package co.bdigital.admin.ejb.controller.view;

public interface UpdatePromotionServiceBeanLocal {

    /**
     * Metodo para actualizar las promociones
     * 
     * @param actionByRol
     * @return
     * @return
     */
    boolean updatePromotion(String id, String description, String fromDate,
            String toDate, String minValue, String maxValue, String value,
            String valueType, String accountingAccount, String availableValue,
            String ocurrence, String status, String notificationType,
            String subject, String message, String region, String maximumBudget,
            String frequencyMinimum, String frequencyRestart);

}
