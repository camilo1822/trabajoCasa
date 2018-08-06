/**
 * 
 */
package co.bdigital.cmm.jpa.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import co.bdigital.cmm.jpa.exception.EnumJPAException;
import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.Cliente;
import co.bdigital.cmm.jpa.model.Contrato;
import co.bdigital.cmm.jpa.model.DivisionGeografica;
import co.bdigital.cmm.jpa.services.ContactsStateJPAService;

/**
 * Consulta JPA para validar si un contacto es Nequi(enrolados y vinculados), en
 * el caso contrario no se incluye en la respuesta.
 * 
 * @author eduardo.altamar@pragma.com.co
 * @since 01/06/2016 16:00
 * @version 1.0.0
 * 
 * 
 */
public class ContactsStateJPAServiceIMPL implements ContactsStateJPAService {

    private static final String METHOD = "contactsState";
    private static final String CELLPHONE_FIELD = "celular";
    private static final String COMMON_STRING_COUNTRY = "divisionGeografica";
    private static final String COMMON_STRING_DIVISION_CODE = "codigoDivision";
    private static final Integer LIMITE_ELEMENTOS_CLAUSULA_IN_ORACLE = 1000;

    @Override
    public List<Cliente> ContactStateList(List<String> phoneNumbersList,
            String countryCode, EntityManager em) throws JPAException {

        try {
            int indexConditionIn = 1;
            int numberQuerys = 0;
            int lastIndex = 0;
            int fromIndex;
            int toIndex;
            
            List<String> phoneNumbersSubList;
            TypedQuery<Cliente> typedQuery;

            em.getEntityManagerFactory().getCache().evict(Cliente.class);
            
            List<Cliente> resultados = new ArrayList<>();
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

            CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder
                    .createQuery(Cliente.class);

            Root<Cliente> from = criteriaQuery.from(Cliente.class);

            Join<Contrato, DivisionGeografica> countryJoin = from
                    .join(COMMON_STRING_COUNTRY);

            criteriaQuery.select(from);

            List<Predicate> conditions = new ArrayList<>();

            conditions.add(criteriaBuilder.equal(
                    countryJoin.get(COMMON_STRING_DIVISION_CODE), countryCode));

            // Obtengo la cantidad de llamados a BD, segun parametro 1000 en
            // clasula IN
            if (null != phoneNumbersList && !phoneNumbersList.isEmpty()) {
                numberQuerys = (int) Math
                        .ceil((double) phoneNumbersList.size()
                                / LIMITE_ELEMENTOS_CLAUSULA_IN_ORACLE);
                lastIndex = phoneNumbersList.size();
            }
            
            // Ejecuto n llamados con condicion IN con 1000 parametros, si hay
            // menos de 1000, solo sera una vez
            for (int i = 0; i < numberQuerys; i++) {
                fromIndex = LIMITE_ELEMENTOS_CLAUSULA_IN_ORACLE * i;
                toIndex = fromIndex + LIMITE_ELEMENTOS_CLAUSULA_IN_ORACLE;
                // Si el numero de contactos a buscar (toIndex) max 1000,
                // es mayor al tamaño de la lista (o es la ultima iteración). 
                // Solo incluye esos numeros de contactos.
                if (toIndex > lastIndex) {
                    toIndex = lastIndex;
                }
                phoneNumbersSubList = new ArrayList<>();

                if (phoneNumbersList != null && !phoneNumbersList.isEmpty()) {
                    phoneNumbersSubList = phoneNumbersList.subList(fromIndex,
                            toIndex);
                }
                conditions.add(indexConditionIn,
                        from.get(CELLPHONE_FIELD).in(phoneNumbersSubList));

                criteriaQuery.where(conditions.toArray(new Predicate[] {}));

                typedQuery = em.createQuery(criteriaQuery);
                resultados.addAll(typedQuery.getResultList());

                conditions.remove(indexConditionIn);
            }
            return (null == resultados || resultados.isEmpty()) ? null
                    : resultados;

        } catch (Exception e) {
            throw new JPAException(EnumJPAException.DB_ERROR,
                    this.getClass().getName() + METHOD, e);
        }
    }
}
