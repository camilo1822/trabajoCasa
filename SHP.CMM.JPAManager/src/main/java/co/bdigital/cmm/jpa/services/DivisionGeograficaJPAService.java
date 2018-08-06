package co.bdigital.cmm.jpa.services;

import java.util.List;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.DivisionGeografica;

public interface DivisionGeograficaJPAService {

    /**
     * Método que obtiene DivisionGeografica por indicativo
     * 
     * @param code
     * @param em
     * @return
     * @throws JPAException
     */
    public DivisionGeografica getDivisionGeograficaByCode(String code,
            EntityManager em) throws JPAException;

    /**
     * Metodo que obtiene la division geografica por abreviatura.
     * 
     * @param abbreviation
     *            abreviatura que referencia a la columna
     *            <code>abreviatura</code>.
     * @param em
     * @return DivisionGeografica
     * @throws JPAException
     */
    public DivisionGeografica getDivisionGeograficaByAbbreviation(
            String abbreviation, EntityManager em) throws JPAException;

    /**
     * <p>
     * Consulta un entity de clase <code>DivisionGeografica</code> por su codigo
     * de divisi&ocaute;n.
     * </p>
     * 
     * @param codigoDivision
     *            c&oacute;digo de la divisi&oacute;n.
     * @param em
     * @return DivisionGeografica
     * @throws JPAException
     */
    public DivisionGeografica findDivisionGeografica(String codigoDivision,
            EntityManager em) throws JPAException;

    /**
     * <p>
     * M&eacute;todo que consulta las divisiones geograficas.
     * </p>
     * 
     * @param em
     * @return Listado de todas las divisiones geograficas en el sistema.
     * @throws JPAException
     */
    public List<DivisionGeografica> findAllDivisionGeografica(EntityManager em)
            throws JPAException;

    /**
     * Metodo que consulta las divisiones geográficas por codigo del padre.
     * 
     * @param parentId
     * @param em
     * @return {@link List} de {@link DivisionGeografica} que corresponde con el
     *         código del padre dado.
     * @throws JPAException
     */
    public List<DivisionGeografica> getDivisionGeograficaByParent(
            String parentId, EntityManager em) throws JPAException;

}
