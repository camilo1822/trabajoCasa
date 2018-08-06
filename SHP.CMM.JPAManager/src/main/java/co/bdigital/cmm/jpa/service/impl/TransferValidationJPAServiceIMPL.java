package co.bdigital.cmm.jpa.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import co.bdigital.cmm.jpa.exception.EnumJPAException;
import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.ControlTransaccion;
import co.bdigital.cmm.jpa.services.TransferValidationJPAService;

/*
 * @author daniel.pareja@pragma.com.co
 */
public class TransferValidationJPAServiceIMPL implements
		TransferValidationJPAService {

	@Override
	public String getTransactionStatus(EntityManager em, String trnId)
			throws JPAException {
		String result = "";

		try {
			CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
			CriteriaQuery<ControlTransaccion> criteriaQuery = criteriaBuilder
					.createQuery(ControlTransaccion.class);

			Root<ControlTransaccion> from = criteriaQuery
					.from(ControlTransaccion.class);
			Predicate condition1 = criteriaBuilder.equal(
					from.get("transaccionId"), trnId);
			criteriaQuery.select(from);
			criteriaQuery.where(condition1);

			TypedQuery<ControlTransaccion> typedQuery = em
					.createQuery(criteriaQuery);
			ControlTransaccion controlTransaccion = null;

			try {
				controlTransaccion = typedQuery.getSingleResult();
			} catch (NoResultException e) {
				System.out.println("NoResultException");
				throw new JPAException(EnumJPAException.DB_ERROR, this
						.getClass().getName(), e);
			}

			return controlTransaccion.getEstado().toString();

		} catch (Exception e) {
			// TODO: actividad para Ricardo
			throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
					.getName(), e);
		}
	}
}
