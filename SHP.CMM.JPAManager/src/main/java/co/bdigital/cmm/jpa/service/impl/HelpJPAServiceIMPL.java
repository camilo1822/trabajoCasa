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
import javax.persistence.metamodel.SingularAttribute;

import co.bdigital.cmm.jpa.exception.EnumJPAException;
import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.Ayuda;
import co.bdigital.cmm.jpa.model.Help;
import co.bdigital.cmm.jpa.model.HojaAyuda;
import co.bdigital.cmm.jpa.services.HelpJPAService;

/*
 * @author daniel.pareja@pragma.com.co
 */
public class HelpJPAServiceIMPL implements
HelpJPAService {

	private static final String COMMON_STRING_AYUDA = "ayuda";
	private static final String COMMON_STRING_AYUDA_ID = "ayudaId";
	private static final String COMMON_STRING_ORDEN = "orden";
	private static final String COMMON_STRING_UNO = "1";

	@Override
	public List<Help> getHelpById(EntityManager em, String helpId)
			throws JPAException {
		
		try {
		

			CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
			CriteriaQuery<Ayuda> criteriaQuery = criteriaBuilder
					.createQuery(Ayuda.class);

			Root<Ayuda> from = criteriaQuery
					.from(Ayuda.class);
			Long helpIdLong = Long.parseLong(helpId);
			Predicate condition1 = criteriaBuilder.equal(from.get(COMMON_STRING_AYUDA).get(COMMON_STRING_AYUDA_ID), helpIdLong);
			criteriaQuery.select(from);
			criteriaQuery.where(condition1);
			criteriaQuery.orderBy(criteriaBuilder.asc(from.get(COMMON_STRING_ORDEN)));
			
			TypedQuery<Ayuda> typedQuery = em
					.createQuery(criteriaQuery);
			
			List<Ayuda> valueArray = typedQuery.getResultList();
		
		    List<Help> lista = new ArrayList<Help>();
			for (Ayuda values : valueArray) {
			    Help help = new Help();
			    help.setContent(values.getTitulo());
			    help.setHelpId(String.valueOf(values.getAyudaId()));
			    help.setIsLeaf(Boolean.FALSE.toString());
			    if (COMMON_STRING_UNO.equalsIgnoreCase(helpId)) {
			    	help.setHelpIdFather("");
				}else {
					help.setHelpIdFather(String.valueOf(values.getAyuda().getAyuda().getAyudaId()));
				}
			    
			    lista.add(help);
			}
			if (lista.size()==0) {
				criteriaBuilder = em.getCriteriaBuilder();
				CriteriaQuery<HojaAyuda> criteriaQuery1 = criteriaBuilder
						.createQuery(HojaAyuda.class);
	
				Root<HojaAyuda> from1 = criteriaQuery1
						.from(HojaAyuda.class);
				condition1 = criteriaBuilder.equal(from.get(COMMON_STRING_AYUDA).get(COMMON_STRING_AYUDA_ID), helpIdLong);
				criteriaQuery1.select(from1);
				criteriaQuery1.where(condition1);
	
				TypedQuery<HojaAyuda> typedQuery1 = em
						.createQuery(criteriaQuery1);
				HojaAyuda hojaAyuda = new HojaAyuda();
				try {
					hojaAyuda = typedQuery1.getSingleResult();
				} catch (NoResultException e) {
					// TODO: actividad para Ricardo
					throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
							.getName(), e);
					
				}
				
				Help help = new Help();
				help.setContent(hojaAyuda.getContenido());
				help.setHelpId(String.valueOf(hojaAyuda.getHojaAyudaId()));
				help.setIsLeaf(Boolean.TRUE.toString());
				help.setHelpIdFather(String.valueOf(hojaAyuda.getAyuda().getAyuda().getAyudaId()));
				lista.add(help);
			}
		
			return lista;

		} catch (Exception e) {
			// TODO: actividad para Ricardo
			throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
					.getName(), e);
		}
	}
}
