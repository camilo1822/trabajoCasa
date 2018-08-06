package co.bdigital.cmm.jpa.services;

import java.util.List;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.CanalAtributo;

public interface CanalAtributoJPAService {

    /**
     * Método que consulta los atributos según el canal
     * 
     * @param canalId
     * @param em
     * @return
     * @throws JPAException
     */
    public List<CanalAtributo> getCanalAtributoByCanalId(String canalId,
            EntityManager em) throws JPAException;

}
