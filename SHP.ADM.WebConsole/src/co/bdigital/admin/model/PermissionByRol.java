package co.bdigital.admin.model;

import java.io.Serializable;

/**
 * 
 * @author juan.molinab
 *
 */
public class PermissionByRol implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /** Valor del atributo de lectura de un perfil **/
    private Integer read;
    /** Valor del atributo de Moficiacion de un perfil **/
    private Integer write;

    /**
     * Constructor de la clase
     */
    public PermissionByRol() {

    }

    /**
     * @return the read
     */
    public Integer getRead() {
        return read;
    }

    /**
     * @param read
     *            the read to set
     */
    public void setRead(Integer read) {
        this.read = read;
    }

    /**
     * @return the write
     */
    public Integer getWrite() {
        return write;
    }

    /**
     * @param write
     *            the write to set
     */
    public void setWrite(Integer write) {
        this.write = write;
    }
}
