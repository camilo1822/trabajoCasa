package co.bdigital.cmm.jpa.services;

import java.util.List;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.Cliente;

/**
 * @author eduardo.altamar@pragma.com.co
 */
public interface ContactsStateJPAService {

    /**
     * Consulta de estado de contactos restringido por pais.
     * 
     * @param em
     * @return
     * @throws JPAException
     */
    public List<Cliente> ContactStateList(List<String> phoneNumbersList,
            String countryCode, EntityManager em) throws JPAException;
}
