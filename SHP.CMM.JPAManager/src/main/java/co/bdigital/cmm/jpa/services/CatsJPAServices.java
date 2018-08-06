package co.bdigital.cmm.jpa.services;

import java.util.List;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.CatsMig;

public interface CatsJPAServices {

    /**
     * Método que consulta en BD cats
     * 
     * @param numDoc
     * @param em
     * @return
     */
    public CatsMig getCatsMigByDocument(String numDoc, EntityManager em)
            throws JPAException;

    /**
     * Método que consulta en BD cats por estado
     * 
     * @param estado
     * @param em
     * @return
     * @throws JPAException
     */
    public List<CatsMig> getCatsMigByStatus(String estado, EntityManager em)
            throws JPAException;

    /**
     * Metodo para actualizar la tabla CatsMig
     * 
     * @param catsMig
     * @param em
     * @throws JPAException
     */
    public void mergeCatsMig(CatsMig catsMig, EntityManager em)
            throws JPAException;

    /**
     * Metodo para persistir CatsMig
     * 
     * @param catsMig
     * @param em
     * @throws JPAException
     */
    public void persistCatsMig(CatsMig catsMig, EntityManager em)
            throws JPAException;
	
	/**
	 * Método que actualiza o inserta el estado de un usuario en CATS_MIG
	 * @param catsMig
	 * @param em
	 * @throws JPAException
	 */
	 public void mergeOrPersistCatsMigStatus(CatsMig catsMig,
	            EntityManager em) throws JPAException;
}
