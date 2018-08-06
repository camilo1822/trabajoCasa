/**
 * 
 */
package co.bdigital.cmm.jpa.service.impl;

import java.util.ArrayList;
import java.util.Date;
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
import co.bdigital.cmm.jpa.model.ImagenS3;
import co.bdigital.cmm.jpa.services.ImagenS3JPAService;

/**
 * @author cristian.martinez@pragma.com.co
 *
 */
public class ImagenS3JPAServiceIMPL implements ImagenS3JPAService {
    public static final String COMMON_STRING_CREATION_DATE = "fechaCreacion";

    /**
     * Metodo que retorna un Entity <code>ImagenS3</code>, que le pertenezca a
     * <code>clienteId</code>.
     * 
     * @param em
     * @param clienteId
     * @return <code>ImagenS3</code>
     * @throws JPAException
     */
    public List<ImagenS3> findImagenS3ByClienteId(EntityManager em,
            Long clienteId) throws JPAException {

        Query query;
        List<ImagenS3> listImagenS3;

        try {

            em.getEntityManagerFactory().getCache().evict(ImagenS3.class);

            query = em.createNamedQuery("ImagenS3.findByClienteClienteId");

            query.setParameter("clienteId", clienteId);

            listImagenS3 = query.getResultList();

        } catch (NoResultException e) {

            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + this.getClass().getName(), e);

        } catch (Exception e) {

            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + this.getClass().getName(), e);

        }

        return listImagenS3;
    }

    @Override
    public List<ImagenS3> findImageS3ByCreationDate(EntityManager em,
            Date dateToFilter) throws JPAException {
        final String metodo = "findImageS3ByCreationDate";

        try {

            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<ImagenS3> criteriaQuery = criteriaBuilder
                    .createQuery(ImagenS3.class);
            Root<ImagenS3> from = criteriaQuery.from(ImagenS3.class);

            List<Predicate> conditions = new ArrayList<Predicate>();

            conditions.add(criteriaBuilder.lessThan(
                    from.<Date> get(COMMON_STRING_CREATION_DATE),
                    dateToFilter));

            criteriaQuery.select(from);
            criteriaQuery.where(conditions.toArray(new Predicate[] {}));

            TypedQuery<ImagenS3> typedQuery = em.createQuery(criteriaQuery);
            return typedQuery.getResultList();

        } catch (NoResultException e) {

            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + this.getClass().getName(), e);

        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + metodo, e);

        }

    }

}
