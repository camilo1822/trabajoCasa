package co.bdigital.admin.ejb.controller.view;

import java.util.List;

import co.bdigital.cmm.jpa.model.PromocionComercio;
import co.bdigital.cmm.jpa.model.PromocionOperacion;
import co.bdigital.cmm.jpa.model.PromocionUsuario;
import co.nequi.message.integration.services.getpromotiondetail.RulesType;

public interface GetPromotionServiceBeanLocal {

    /**
     * Metodo para retornar las promociones
     * 
     * @param actionByRol
     * @return RulesType
     */
    public RulesType getPromotion(String service, String region, String type,
            String operation, String idPromotion, String descrptionRule);

    /**
     * Metodo para eliminar todos los registros relacionados a una promocion
     * 
     * @param promocionOperacionId
     */
    public Boolean deletePromotions(String promocionOperacionId);

    /**
     * Metodo para eliminar todos los registros relacionados a una promocion
     * 
     * @param promocionOperacionId
     */
    public Boolean deletePromotionComercio(String promocionOperacionId);

    /**
     * Metodo para guardar los datos del archivo csv en la tabla
     * promocionUsuario
     * 
     * @param promocionUsuario
     * @param promocionOperacionId
     */
    public Boolean persistPromotions(List<PromocionUsuario> promocionUsuario,
            String promocionOperacionId);

    /**
     * Metodo para guardar los datos del archivo csv en la tabla
     * promocionComercio
     * 
     * @param promocionComercio
     * @param promocionOperacionId
     */
    public Boolean persistPromotionComercio(
            List<PromocionComercio> promocionComercio,
            String promocionOperacionId);

    /**
     * Metodo para consultar un registro de promocion_operacion por id
     * 
     * @param id
     * @return PromocionOperacion
     */
    public PromocionOperacion getPromocionOperacion(String id);

    /**
     * Metodo para consultar los registros de la tabla PROMOCION_USUARIO por id
     * asociado a la promociï¿½n
     * 
     * @param id
     * @return PromocionOperacion
     */
    public String getPromocionUsuarioSize(String id);

    /**
     * Metodo para consultar un registro de promocion_comercio por id
     * 
     * @param id
     * @return PromocionComercio
     */
    public List<PromocionComercio> getPromocionComercio(String id);

    /**
     * Metodo para consultar número determinado de registros de
     * promocion_usuario por id
     * 
     * @param id
     * @param region
     * @return PromocionUsuario
     */
    public List<PromocionUsuario> getPromocionUsuarioLimit(String id,
            String region);

    /**
     * Metodo para insertar registro en la tabla PROMOCION_REGLA
     * 
     * @param name
     * @param promocionOperacion
     * @return Boolean
     */
    public Boolean persistPromocionRegla(String name,PromocionOperacion promocionOperacion);
    
    /**
     * Metodo para consultar un registro de promocion_operacion por id
     * 
     * @param servicio
     * @param region
     * @return PromocionOperacion
     */
    public PromocionOperacion getPromocionOperacionByService(String servicio,
            String region);

}
