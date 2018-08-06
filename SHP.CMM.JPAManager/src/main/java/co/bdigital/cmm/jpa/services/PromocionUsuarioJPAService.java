/**
 * 
 */
package co.bdigital.cmm.jpa.services;

import java.util.List;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.PromocionComercio;
import co.bdigital.cmm.jpa.model.PromocionOperacion;
import co.bdigital.cmm.jpa.model.PromocionRegla;
import co.bdigital.cmm.jpa.model.PromocionUsuario;

/**
 * @author juan.arboleda
 *
 */
public interface PromocionUsuarioJPAService {

    /**
     * Inserta un entity <code>PromocionUsuario</code> en Base de Datos.
     * 
     * @param promocionUsuario
     * @param em
     * @throws JPAException
     */
    public void persistPromocionUsuario(PromocionUsuario promocionUsuario,
            EntityManager em) throws JPAException;

    /**
     * Inserta un entity <code>PromocionComercio</code> en Base de Datos.
     * 
     * @param promocionComercio
     * @param em
     * @throws JPAException
     */
    public void persistPromocionComercio(PromocionComercio promocionComercio,
            EntityManager em) throws JPAException;

    /**
     * Elimina registros <code>PromocionUsuario</code> que esten relacionados al
     * ide de la promoción.
     * 
     * @param promocionOperacionId
     * @param em
     * @throws JPAException
     */
    public void deleteAllPromocionUsuarioByPromocionOperacion(
            String promocionOperacionId, EntityManager em) throws JPAException;

    /**
     * Elimina registros <code>PromocionComercio</code> que esten relacionados
     * al ide de la promoción.
     * 
     * @param promocionOperacionId
     * @param em
     * @throws JPAException
     */
    public void deleteAllPromocionComercioByPromocionOperacion(
            String promocionOperacionId, EntityManager em) throws JPAException;

    /**
     * Metodo para obtener registro <code>PromocionOperacion</code>
     * 
     * @param id
     * @param em
     * @return PromocionOperacion
     * @throws JPAException
     */
    public PromocionOperacion getPromocionOperacionById(String id,
            EntityManager em) throws JPAException;

    /**
     * Trae todas los registros de la tabla PROMCION_USUARIO asociados al id de
     * la promocion
     * 
     * @param id
     * @param em
     * @return List<PromocionUsuario>
     * @throws JPAException
     */
    public List<PromocionUsuario> getPromocionUsuario(String id,
            EntityManager em) throws JPAException;

    /**
     * Trae todas los registros de la tabla PROMCION_COMERCIO asociados al id de
     * la promocion
     * 
     * @param id
     * @param em
     * @return List<PromocionComercio>
     * @throws JPAException
     */
    public List<PromocionComercio> getPromocionComercio(String id,
            EntityManager em) throws JPAException;

    /**
     * Devuelve el numero de registros relacionados a una promoció
     * 
     * @param id
     * @param em
     * @return String
     * @throws JPAException
     */
    public String getPromocionUsuarioSize(String id, EntityManager em)
            throws JPAException;

    /**
     * Trae número definido de registros de la tabla PROMCION_USUARIO asociados
     * al id de la promocion
     * 
     * @param id
     * @param limit
     * @param em
     * @return List<PromocionUsuario>
     * @throws JPAException
     */
    public List<PromocionUsuario> getPromocionUsuarioLimit(String id,
            String limit, EntityManager em) throws JPAException;

    /**
     * Devuelve el id mayor de la tabla PROMOCION_REGLA
     * 
     * @param em
     * @return String
     * @throws JPAException
     */
    public String getPromocionReglaMaxId(EntityManager em) throws JPAException;

    /**
     * Inserta un entity <code>PromocionRegla</code> en Base de Datos.
     * 
     * @param promocionRegla
     * @param em
     * @throws JPAException
     */
    public void persistPromocionRegla(PromocionRegla promocionRegla,
            EntityManager em) throws JPAException;

    /**
     * Metodo para obtener registro <code>PromocionOperacion</code> por region y
     * servicio
     * 
     * @param servicio
     * @param region
     * @param em
     * @return PromocionOperacion
     * @throws JPAException
     */
    public PromocionOperacion getPromocionOperacionByService(String servicio,
            String region, EntityManager em) throws JPAException;

}
