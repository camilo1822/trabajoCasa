package co.bdigital.admin.ejb.controller.view;

import java.util.ArrayList;

import co.bdigital.admin.messaging.validatecustomer.CustomerType;

/**
 * @author eduardo.altamar@pragma.com.co
 *
 */
public interface ControlListValidationServiceBeanLocal {

    /**
     * Metodo que valida una lista de clientes en listas de control
     * 
     * @param customerList
     * @param region
     * @return <code> ArrayList<CustomerType></code>
     */
    public ArrayList<CustomerType> validateCustomerList(
            ArrayList<CustomerType> customerList, String region);
}
