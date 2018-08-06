package co.bdigital.cmm.jpa.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import co.bdigital.cmm.jpa.exception.EnumJPAException;
import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.Cliente;
import co.bdigital.cmm.jpa.model.DivisionGeografica;
import co.bdigital.cmm.jpa.services.DivisionGeograficaJPAService;
import co.bdigital.cmm.jpa.util.ConstantJPA;

public class DivisionGeograficaJPAServiceIMPL
        implements DivisionGeograficaJPAService {
    public static final String COMMON_STRING_POSTAL_CODE = "codigoPostal";
    public static final String COMMON_STRING_ABBREVIATION = "abreviatura";
    public static final String COMMON_STRING_PARENT_ID = "padre";
    public static final String METHOD_NAME_GET_DIVISION_GEOGRAFICA_BY_PARENT = "getDivisionGeograficaByParent";

    private static DivisionGeograficaJPAServiceIMPL instance;

    /**
     * Metodo para obtener una única instancia de la clase
     * DivisionGeograficaJPAServiceIMPL
     * 
     * @return DivisionGeograficaJPAServiceIMPL
     */
    public static DivisionGeograficaJPAServiceIMPL getInstance() {
        if (null == instance)
            instance = new DivisionGeograficaJPAServiceIMPL();
        return instance;
    }

    @Override
    public DivisionGeografica getDivisionGeograficaByCode(String code,
            EntityManager em) throws JPAException {
        final String metodo = "getDivisionGeograficaByCode";

        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<DivisionGeografica> criteriaQuery = criteriaBuilder
                    .createQuery(DivisionGeografica.class);

            Root<DivisionGeografica> from = criteriaQuery
                    .from(DivisionGeografica.class);
            Predicate condition = criteriaBuilder
                    .equal(from.get(COMMON_STRING_POSTAL_CODE), code);
            criteriaQuery.select(from);
            criteriaQuery.where(condition);
            em.getEntityManagerFactory().getCache().evict(Cliente.class);
            TypedQuery<DivisionGeografica> typedQuery = em
                    .createQuery(criteriaQuery);
            DivisionGeografica divisionGeografica = null;

            // si llegan varios resultados tomará solo el primero
            List<DivisionGeografica> resultado = new ArrayList<DivisionGeografica>();

            resultado = typedQuery.getResultList();
            if (null == resultado || resultado.isEmpty()) {
                return null;
            }
            divisionGeografica = resultado.get(0);

            return divisionGeografica;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + metodo, e);
        }
    }

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
    @Override
    public DivisionGeografica getDivisionGeograficaByAbbreviation(
            String abbreviation, EntityManager em) throws JPAException {
        final String metodo = "getDivisionGeograficaByAbbreviation";

        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<DivisionGeografica> criteriaQuery = criteriaBuilder
                    .createQuery(DivisionGeografica.class);

            Root<DivisionGeografica> from = criteriaQuery
                    .from(DivisionGeografica.class);
            Predicate condition = criteriaBuilder.equal(
                    from.get(COMMON_STRING_ABBREVIATION),
                    abbreviation.toLowerCase());
            criteriaQuery.select(from);
            criteriaQuery.where(condition);
            em.getEntityManagerFactory().getCache().evict(Cliente.class);
            TypedQuery<DivisionGeografica> typedQuery = em
                    .createQuery(criteriaQuery);
            DivisionGeografica divisionGeografica = null;

            // si llegan varios resultados tomará solo el primero
            List<DivisionGeografica> resultado = new ArrayList<DivisionGeografica>();

            resultado = typedQuery.getResultList();
            if (null == resultado || resultado.isEmpty()) {
                return null;
            }
            divisionGeografica = resultado.get(0);

            return divisionGeografica;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + metodo, e);
        }
    }

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
    @Override
    public DivisionGeografica findDivisionGeografica(String codigoDivision,
            EntityManager em) throws JPAException {

        final String metodo = "getDivisionGeograficaByAbbreviation";

        try {

            return em.find(DivisionGeografica.class, codigoDivision);

        } catch (Exception e) {
            // TODO: actividad para Ricardo
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + metodo, e);
        }
    }

    /**
     * <p>
     * M&eacute;todo que consulta las divisiones geograficas.
     * </p>
     * 
     * @param em
     * @return Listado de todas las divisiones geograficas en el sistema.
     * @throws JPAException
     */
    @Override
    public List<DivisionGeografica> findAllDivisionGeografica(EntityManager em)
            throws JPAException {

        Query query;
        List<DivisionGeografica> listDivisionGeografica;

        try {

            em.getEntityManagerFactory().getCache()
                    .evict(DivisionGeografica.class);

            query = em.createNamedQuery(
                    ConstantJPA.CONSULTA_DIVISION_GEOGRAFICA_FIND_ALL);

            listDivisionGeografica = query.getResultList();

        } catch (NoResultException e) {

            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + this.getClass().getName(), e);

        } catch (Exception e) {

            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + this.getClass().getName(), e);

        }

        return listDivisionGeografica;
    }

    @Override
    public List<DivisionGeografica> getDivisionGeograficaByParent(
            String parentId, EntityManager em) throws JPAException {

        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<DivisionGeografica> criteriaQuery = criteriaBuilder
                    .createQuery(DivisionGeografica.class);

            Root<DivisionGeografica> from = criteriaQuery
                    .from(DivisionGeografica.class);
            Predicate condition = criteriaBuilder
                    .equal(from.get(COMMON_STRING_PARENT_ID), parentId);
            criteriaQuery.select(from);
            criteriaQuery.where(condition);
            em.getEntityManagerFactory().getCache().evict(Cliente.class);
            TypedQuery<DivisionGeografica> typedQuery = em
                    .createQuery(criteriaQuery);

            return typedQuery.getResultList();
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName()
                            + METHOD_NAME_GET_DIVISION_GEOGRAFICA_BY_PARENT,
                    e);
        }
    }
}
