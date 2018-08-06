/**
 * 
 */
package co.bdigital.cmm.jpa.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import co.bdigital.cmm.jpa.exception.EnumJPAException;
import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.HomologaError;
import co.bdigital.cmm.jpa.model.HomologacionTipo;
import co.bdigital.cmm.jpa.services.HomologadorService;

/**
 * @author ricardo.paredes
 *
 */
public class HomologadorServiceImpl implements HomologadorService {

    private static HomologadorServiceImpl instance;
    private static final String CATALOGO_ERROR_1 = "catalogoError1";
    private static final String STRING_ID = "id";
    private static final String STRING_CODIGO = "codigo";
    private static final String STRING_NOMBRE = "nombre";
    private static final String STRING_SISTEMA = "sistema";
    private static final String CATALOGO_ERROR_2 = "catalogoError2";

    private static final String STRING_KEY_CODE = "tipo";
    private static final String STRING_SOURCE_SYSTEM = "sistemaOrigen";
    private static final String STRING_DESTINATION_SYSTEM = "sistemaDestino";
    private static final String STRING_SOURCE_VALUE = "valorOrigen";

    /**
     * 
     */
    public HomologadorServiceImpl() {

    }

    public static HomologadorServiceImpl getInstance() {
        if (null == instance)
            instance = new HomologadorServiceImpl();
        return instance;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * co.bdigital.mdw.jpa.services.HomologadorService#getErrorHomologation(
     * java.lang.String, java.lang.String, java.lang.String, java.lang.Long)
     */
    @Override
    public HomologaError getErrorHomologation(String codigoIn, String sistemaIn,
            String sistemaDestino, EntityManager em) throws JPAException {

        try {

            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<HomologaError> criteriaQuery = criteriaBuilder
                    .createQuery(HomologaError.class);

            Root<HomologaError> from = criteriaQuery.from(HomologaError.class);
            // Constructing list of parameters
            List<Predicate> predicates = new ArrayList<>();
            Predicate condition1 = criteriaBuilder.equal(from
                    .get(CATALOGO_ERROR_1).get(STRING_ID).get(STRING_CODIGO),
                    codigoIn);
            predicates.add(condition1);
            Predicate condition2 = criteriaBuilder
                    .equal(from.get(CATALOGO_ERROR_1).get(STRING_SISTEMA)
                            .get(STRING_NOMBRE), sistemaIn.toUpperCase());
            predicates.add(condition2);
            Predicate condition3 = criteriaBuilder
                    .equal(from.get(CATALOGO_ERROR_2).get(STRING_SISTEMA)
                            .get(STRING_NOMBRE), sistemaDestino.toUpperCase());
            predicates.add(condition3);
            criteriaQuery.select(from);
            criteriaQuery.where(
                    predicates.toArray(new Predicate[predicates.size()]));

            TypedQuery<HomologaError> typedQuery = em
                    .createQuery(criteriaQuery);
            return typedQuery.getSingleResult();

        } catch (Exception e) {
            StringBuilder errorString = new StringBuilder(
                    HomologadorServiceImpl.class.getCanonicalName());
            errorString.append(":getErrorHomologation:");
            throw new JPAException(errorString.toString(), e);
        }
    }

    /**
     * Metodo que realiza consulta a BD para homologar tipos de bolsillos
     * 
     * @param sourceSystem
     * @param destinationSystem
     * @param keyCode
     * @param sourceValue
     * @param em
     * @return destinationvalue
     * @throws JPAException
     */
    @Override
    public String getHomologationTypePocket(String sourceSystem,
            String destinationSystem, String keyCode, String sourceValue,
            EntityManager em) throws JPAException {
        final String metodo = "getHomologationTypePocket";
        try {

            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<HomologacionTipo> criteriaQuery = criteriaBuilder
                    .createQuery(HomologacionTipo.class);

            Root<HomologacionTipo> from = criteriaQuery
                    .from(HomologacionTipo.class);
            List<Predicate> conditions = new ArrayList<>();

            conditions.add(criteriaBuilder.equal(from.get(STRING_SOURCE_SYSTEM),
                    sourceSystem));

            conditions.add(criteriaBuilder.equal(
                    from.get(STRING_DESTINATION_SYSTEM), destinationSystem));

            conditions.add(
                    criteriaBuilder.equal(from.get(STRING_KEY_CODE), keyCode));

            conditions.add(criteriaBuilder.equal(from.get(STRING_SOURCE_VALUE),
                    sourceValue));

            criteriaQuery.select(from);
            criteriaQuery.where(conditions.toArray(new Predicate[] {}));

            em.getEntityManagerFactory().getCache()
                    .evict(HomologacionTipo.class);

            TypedQuery<HomologacionTipo> typedQuery = em
                    .createQuery(criteriaQuery);

            // si llegan varios resultados tomará solo el primero
            HomologacionTipo resultado = typedQuery.getSingleResult();
            if (null != resultado) {
                return resultado.getValorDestino();

            }
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + metodo, e);
        }

        return null;
    }
    
    /**
     * Metodo que realiza consulta a BD para homologar tipos
     * 
     * @param sourceSystem
     * @param destinationSystem
     * @param keyCode
     * @param sourceValue
     * @param em
     * @return destinationvalue
     * @throws JPAException
     */
    @Override
    public String getHomologationType(String sourceSystem,
            String destinationSystem, String keyCode, String sourceValue,
            EntityManager em) throws JPAException {

        try {

            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<HomologacionTipo> criteriaQuery = criteriaBuilder
                    .createQuery(HomologacionTipo.class);

            Root<HomologacionTipo> from = criteriaQuery
                    .from(HomologacionTipo.class);
            List<Predicate> conditions = new ArrayList<Predicate>();

            conditions.add(criteriaBuilder.equal(
                    from.get(STRING_SOURCE_SYSTEM),
                    sourceSystem));

            conditions.add(criteriaBuilder.equal(
                    from.get(STRING_DESTINATION_SYSTEM),
                    destinationSystem));

            conditions.add(criteriaBuilder.equal(
                    from.get(STRING_KEY_CODE), keyCode));

            conditions.add(criteriaBuilder.equal(
                    from.get(STRING_SOURCE_VALUE),
                    sourceValue));

            criteriaQuery.select(from);
            criteriaQuery.where(conditions.toArray(new Predicate[] {}));

            em.getEntityManagerFactory().getCache()
                    .evict(HomologacionTipo.class);

            TypedQuery<HomologacionTipo> typedQuery = em
                    .createQuery(criteriaQuery);

            HomologacionTipo resultado = typedQuery.getSingleResult();
            if (null != resultado) {
                return resultado.getValorDestino();

            }
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName(),
                    e);
        }

        return null;
    }
}
