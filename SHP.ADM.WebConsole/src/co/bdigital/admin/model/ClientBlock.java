package co.bdigital.admin.model;

import java.io.Serializable;

/**
 * Clase para almacenar la informacion de los bloqueos de un cliente
 * 
 * @author juan.molinab
 *
 */
public class ClientBlock implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String tipoBloqueo;
    private String fechaBloqueo;
    private String desc;
    private String unLock;
    private String typeLock;

    /**
     * Constructor por defecto
     */
    public ClientBlock() {

    }

    /**
     * Constructor
     * 
     * @param tipoBloqueo
     * @param fechaBloqueo
     * @param desc
     * @param unLock
     * @param typeLock
     */
    public ClientBlock(String tipoBloqueo, String fechaBloqueo, String desc,
            String unLock, String typeLock) {
        this.tipoBloqueo = tipoBloqueo;
        this.fechaBloqueo = fechaBloqueo;
        this.desc = desc;
        this.unLock = unLock;
        this.typeLock = typeLock;
    }

    /**
     * @return the tipoBloqueo
     */
    public String getTipoBloqueo() {
        return tipoBloqueo;
    }

    /**
     * @param tipoBloqueo
     *            the tipoBloqueo to set
     */
    public void setTipoBloqueo(String tipoBloqueo) {
        this.tipoBloqueo = tipoBloqueo;
    }

    /**
     * @return the fechaBloqueo
     */
    public String getFechaBloqueo() {
        return fechaBloqueo;
    }

    /**
     * @param fechaBloqueo
     *            the fechaBloqueo to set
     */
    public void setFechaBloqueo(String fechaBloqueo) {
        this.fechaBloqueo = fechaBloqueo;
    }

    /**
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @param desc
     *            the desc to set
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUnLock() {
        return unLock;
    }

    public void setUnLock(String unLock) {
        this.unLock = unLock;
    }

    public String getTypeLock() {
        return typeLock;
    }

    public void setTypeLock(String typeLock) {
        this.typeLock = typeLock;
    }

}
