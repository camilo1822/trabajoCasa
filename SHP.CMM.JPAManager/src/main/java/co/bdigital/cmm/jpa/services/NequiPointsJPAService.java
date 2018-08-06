package co.bdigital.cmm.jpa.services;

import java.util.List;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.DiasAtencion;
import co.bdigital.cmm.jpa.model.Promocion;
import co.bdigital.cmm.jpa.model.PuntosNequi;

/**
 * Clase que expone los metodos de consulta de los puntos NEQUI
 * 
 * @author john_perez
 *
 */
public interface NequiPointsJPAService {

    /**
     * Metodo que consulta los puntos nequi.
     * 
     * @param longitud
     * @param latitud
     * @param radio
     * @param filter
     * @return List<PuntosNequi>
     * @throws JPAException
     */
    public List<PuntosNequi> getNequipoints(Double longitud, Double latitud,
            Double radio, Double filter, String regionId, EntityManager em)
            throws JPAException;

    /**
     * Metodo que consulta la informacion de un punto Nequi.
     * 
     * @param idPoint
     * @param em
     * @return PuntosNequi
     * @throws JPAException
     */
    public PuntosNequi infoNequiPoint(Double idPoint, EntityManager em)
            throws JPAException;

    /**
     * Metodo que consulta los puntos Nequi de acuerdo a los filtros de
     * busqueda.
     * 
     * @param filter
     * @param typeLocation
     * @param pageIndex
     * @param pageLength
     * @param em
     * @return List<PuntosNequi>
     * @throws JPAException
     */
    public List<PuntosNequi> filterNequiPoint(String filter, EntityManager em,
            int pageIndex, int pageLength, String regionId) throws JPAException;

    /**
     * Metodo que busca las promociones existentes por un solo punto nequi
     * 
     * @param idPoint
     * @param em
     * @return
     * @throws JPAException
     */
    public List<Promocion> infoSalesByPoint(Double idPoint, EntityManager em)
            throws JPAException;

    /**
     * Metodo para devolver las promociones existentes en una lista de puntos
     * 
     * @param idsPoint
     * @param em
     * @return
     * @throws JPAException
     */
    public List<Promocion> infoSalesByListPoint(List<Long> idsPoint,
            EntityManager em) throws JPAException;

    /**
     * Metodo para contar el numero de comercios enviados en la lista que tienen
     * promocion. Solo se cuenta una promocion por comercio
     * 
     * @param idsPoint
     * @param em
     * @return
     * @throws JPAException
     */
    public Long countDistinctSalesByPoints(List<Long> idsPoint,
            EntityManager em) throws JPAException;

    /**
     * Metodo para consultar los dias de atencion de los puntos Nequi y tipo de
     * dia.
     * 
     * @param nequiPoints
     * @param dayType
     *            dia de la semana en forma corta (pe Lunes = Lun).
     * @param em
     * @return Listado de dias de atencion para los puntos Nequi especificados.
     * @throws JPAException
     */
    public List<DiasAtencion> getAttendaceDaysAndSchedulesByNequiPointsAndDayType(
            List<Long> nequiPoints, String dayType, EntityManager em)
            throws JPAException;
}
