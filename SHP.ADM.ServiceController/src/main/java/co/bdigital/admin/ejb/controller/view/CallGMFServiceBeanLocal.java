package co.bdigital.admin.ejb.controller.view;

import co.bdigital.cmm.jpa.model.Cliente;

public interface CallGMFServiceBeanLocal {

    /**
     * Metodo para marcar o desmarcar cuenta con GMF
     * 
     * @param accountNumber
     * @param option
     * @return String
     */
    public String callGMF(String accountNumber, String option);

    /**
     * Retorna info del cliente por su numero de cuenta
     * 
     * @param accountNumber
     * @return Cliente
     */
    public Cliente clientByAccount(String accountNumber, String region);

}
